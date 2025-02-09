package com.EsyDigi.esyDigi.utility;

import android.text.TextUtils;
import android.util.Base64;

import com.android.billingclient.util.BillingHelper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Security {

    private static final String TAG = "IABUtil/Security";

    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr2Q8h6PcJ2Vww + 8gjamixECUeZY9hrJIpjFo1iXlXhAHawBXIPpsW4Rn6OtiMeZqXZ4JbROpqNVaqnhWz1VoNf67v3UmWpWZV3O8baayJvXlOyXhF6xtflwmyDDzKYH2u322ZCVFZZNypZ1HsxJFuwuT88GQzSaRMe6v3IT1BXCRpbHczcKr5E7zpxWqK + 0JDcN5f \u200B\u200B/ WinJQzvRoVJNzkk0YepRxtwOBAN4gtDfwmrfLI + IQP1wRH98eVAxY73v6U0mjE / l69OAqpsdAN + ay2K4yTA / Exibx7lsgytKSYLTGmePBzOUGvAPHgbsmElzfqsGb0GcRM0JjMFN450eMrEQIDAQAB";

    /**
     * Verifies that the data was signed with the given signature, and returns the verified
     * purchase.
     * @param base64PublicKey the base64-encoded public key to use for verifying.
     * @param signedData the signed JSON string (signed, not encrypted)
     * @param signature the signature for the data, signed with the private key
     * @throws IOException if encoding algorithm is not supported or key specification
     * is invalid
     */
    public static boolean verifyPurchase(String base64PublicKey, String signedData,
                                         String signature) throws IOException {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey)
                || TextUtils.isEmpty(signature)) {
            BillingHelper.logWarn(TAG, "Purchase verification failed: missing data.");
            return false;
        }

        PublicKey key = generatePublicKey(base64PublicKey);
        return verify(key, signedData, signature);
    }

    /**
     * Generates a PublicKey instance from a string containing the Base64-encoded public key.
     *
     * @param encodedPublicKey Base64-encoded public key
     * @throws IOException if encoding algorithm is not supported or key specification
     * is invalid
     */
    public static PublicKey generatePublicKey(String encodedPublicKey) throws IOException {
        try {
            byte[] decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            // "RSA" is guaranteed to be available.
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            String msg = "Invalid key specification: " + e;
            BillingHelper.logWarn(TAG, msg);
            throw new IOException(msg);
        }
    }

    /**
     * Verifies that the signature from the server matches the computed signature on the data.
     * Returns true if the data is correctly signed.
     *
     * @param publicKey public key associated with the developer account
     * @param signedData signed data from server
     * @param signature server signature
     * @return true if the data and signature match
     */
    public static boolean verify(PublicKey publicKey, String signedData, String signature) {
        byte[] signatureBytes;
        try {
            signatureBytes = Base64.decode(signature, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            BillingHelper.logWarn(TAG, "Base64 decoding failed.");
            return false;
        }
        try {
            Signature signatureAlgorithm = Signature.getInstance(SIGNATURE_ALGORITHM);
            signatureAlgorithm.initVerify(publicKey);
            signatureAlgorithm.update(signedData.getBytes());
            if (!signatureAlgorithm.verify(signatureBytes)) {
                BillingHelper.logWarn(TAG, "Signature verification failed.");
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            // "RSA" is guaranteed to be available.
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            BillingHelper.logWarn(TAG, "Invalid key specification.");
        } catch (SignatureException e) {
            BillingHelper.logWarn(TAG, "Signature exception.");
        }
        return false;
    }
}
