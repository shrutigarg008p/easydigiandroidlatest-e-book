package com.EsyDigi.esyDigi;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.Config;
import com.folioreader.Constants;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.AppUtil;
import com.folioreader.util.OnHighlightListener;
import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.Interface.ApiResponse;
import com.EsyDigi.esyDigi.api.API;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.GetBookDetail;
import com.EsyDigi.esyDigi.api.GetPaymentDatum;
import com.EsyDigi.esyDigi.api.GetPaymentResponse;
import com.EsyDigi.esyDigi.api.LoginData;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.api.SavePaymentResponse;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;
import com.EsyDigi.esyDigi.utility.ConnectionDetector;
import com.EsyDigi.esyDigi.utility.PathUtils;
import com.EsyDigi.esyDigi.utility.Security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;
import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_DOWNLOADBOOK;
import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_FCMTOKEN;
import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_GETPAYMENTDATA;
import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_SAVEPAYMENT;


public class MainActivity extends ParentActivity implements OnHighlightListener, ApiResponse, PurchasesUpdatedListener {
    TextToSpeech tts;
    LinearLayout openbook, logout, downloadbook, settings, myForms, profile, reminder, audioplayer, CheckList;
    FolioReader folioReader;
    ProgressBar pb;
    android.app.ProgressDialog dialog;
    String removableStoragePath;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
    ProgressDialog progressBar;
    String token;
    Button changelang;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    String dwnload_file_path = " ";
    String swidish_file_path = " ";
    String audio_file_path = " ";
    BillingClient mBillingClient;
    private boolean unpaid = false;
    private boolean paid = false;
    private boolean expired = false;
    String base64Key = " MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr2Q8h6PcJ2Vww + 8gjamixECUeZY9hrJIpjFo1iXlXhAHawBXIPpsW4Rn6OtiMeZqXZ4JbROpqNVaqnhWz1VoNf67v3UmWpWZV3O8baayJvXlOyXhF6xtflwmyDDzKYH2u322ZCVFZZNypZ1HsxJFuwuT88GQzSaRMe6v3IT1BXCRpbHczcKr5E7zpxWqK + 0JDcN5f \u200B\u200B/ WinJQzvRoVJNzkk0YepRxtwOBAN4gtDfwmrfLI + IQP1wRH98eVAxY73v6U0mjE / l69OAqpsdAN + ay2K4yTA / Exibx7lsgytKSYLTGmePBzOUGvAPHgbsmElzfqsGb0GcRM0JjMFN450eMrEQIDAQAB";

//  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr2Q8h6PcJ2Vww + 8gjamixECUeZY9hrJIpjFo1iXlXhAHawBXIPpsW4Rn6OtiMeZqXZ4JbROpqNVaqnhWz1VoNf67v3UmWpWZV3O8baayJvXlOyXhF6xtflwmyDDzKYH2u322ZCVFZZNypZ1HsxJFuwuT88GQzSaRMe6v3IT1BXCRpbHczcKr5E7zpxWqK + 0JDcN5f \u200B\u200B/ WinJQzvRoVJNzkk0YepRxtwOBAN4gtDfwmrfLI + IQP1wRH98eVAxY73v6U0mjE / l69OAqpsdAN + ay2K4yTA / Exibx7lsgytKSYLTGmePBzOUGvAPHgbsmElzfqsGb0GcRM0JjMFN450eMrEQIDAQAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        checkAndRequestPermissions();


        GetPaymentStatus();
        getBookDetail();


        dirPath = PathUtils.getRootDirPath(getApplicationContext());
        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        myForms = findViewById(R.id.myforms);
        pb = (ProgressBar) findViewById(R.id.progressBar);


        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();


        // mBillingClient = BillingClient.newBuilder(getBaseContext()).setListener(getApplicationContext()).build();

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                    Log.d(TAG, "In-app Billing is set up OK");
//                    Toast.makeText(MainActivity.this, "Success to Connect Billing", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(MainActivity.this, +billingResponseCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Toast.makeText(MainActivity.this, "You are disconnect from Billing", Toast.LENGTH_SHORT).show();
            }
        });
        // mTitle.setText("Home");


        //setting the title

        ClipboardManager clipboardManager = (ClipboardManager)
                getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText("");
        folioReader = FolioReader.get()
                .setOnHighlightListener(this);
        // TextToSpeech tts = new TextToSpeech(getApplicationContext(), this);
        //tts.setLanguage(Locale.US);

//        Intent intent = new Intent(MainActivity.this, FolioActivity.class);
//        intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE, FolioActivity.EpubSourceType.RAW);
//        intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, R.raw.moby_dick);
//        startActivity(intent);
        openbook = findViewById(R.id.openbook);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        profile = findViewById(R.id.profile);
        reminder = findViewById(R.id.reminder);
        changelang = findViewById(R.id.changelanguage);
        audioplayer = findViewById(R.id.audioplayer);
        CheckList = findViewById(R.id.CheckList);


        FCMTOKENTOSERVER();


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);
            }
        }

        changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if (unpaid==true) {
//                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
//                            .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                    /*int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, builder.build());*/

                    if (mBillingClient.isReady()) {

                        BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                                .setSku("esydigi_123").setType(BillingClient.SkuType.INAPP);
                        int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());

                        /**
                         * To purchase an Subscription
                         */


                        List<String> skuList = new ArrayList<>();
                        skuList.add("esydigi_123");
//                skuList.add("product_2");
                        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                        mBillingClient.querySkuDetailsAsync(params.build(),
                                new SkuDetailsResponseListener() {
                                    @Override
                                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                        if (responseCode == BillingClient.BillingResponse.OK
                                                && skuDetailsList != null) {
                                            for (SkuDetails skuDetails : skuDetailsList) {
                                                String sku = skuDetails.getSku();
                                                String price = skuDetails.getPrice();
                                                if ("product_1".equals(sku)) {

                                                    Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "cant query product", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                    }
                                });
                        mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                                (responseCode, purchasesList) -> {
                                    if (responseCode == BillingClient.BillingResponse.OK
                                            && purchasesList != null) {
                                        for (Purchase purchase : purchasesList) {
                                            Toast.makeText(MainActivity.this, purchase.getOrderId(), Toast.LENGTH_SHORT).show();
                                            // Process the result.
                                        }
                                    }
                                });

                        // mBillingClient.consumeAsync(purchaseToken,listener);
                        ConsumeResponseListener listener = (responseCode, outToken) -> {
                            if (responseCode == BillingClient.BillingResponse.OK) {
                                Log.d("Success", "Got a purchase: ");
                                return;
                                // Handle the success of the consume operation.
                                // For example, increase the number of coins inside the user's basket.
                            }
                        };
                    }



                } else if (paid == true) {
                    Config config = AppUtil.getSavedConfig(getApplicationContext());
                    if (config == null)
                        config = new Config();

                    config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL).setThemeColorRes(R.color.colorPrimary).setFont(Constants.FONT_LORA).setFontSize(2);
                    // folioReader = FolioReader.get();

                    //create a new file, to save the downloaded file

                    folioReader.setConfig(config, true).openBook(R.raw.moby_dick);

                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } else if (expired == true) {
                    expiredDialogue();

                }

                //setLocale("sv");





            }
        });


        //downloadbook = findViewById(R.id.downloadbook);


        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (unpaid==true) {
//                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
//                            .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                    /*int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, builder.build());*/

                    if (mBillingClient.isReady()) {


                    BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                            .setSku("esydigi_123").setType(BillingClient.SkuType.INAPP);
                    int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());

                    /**
                     * To purchase an Subscription
                     */


                    List<String> skuList = new ArrayList<>();
                    skuList.add("esydigi_123");
//                skuList.add("product_2");
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    mBillingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                    if (responseCode == BillingClient.BillingResponse.OK
                                            && skuDetailsList != null) {
                                        for (SkuDetails skuDetails : skuDetailsList) {
                                            String sku = skuDetails.getSku();
                                            String price = skuDetails.getPrice();
                                            if ("product_1".equals(sku)) {

                                                Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(MainActivity.this, "cant query product", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                }
                            });
                    mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                            new PurchaseHistoryResponseListener() {
                                @Override
                                public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                      List<Purchase> purchasesList) {
                                    if (responseCode == BillingClient.BillingResponse.OK
                                            && purchasesList != null) {
                                        for (Purchase purchase : purchasesList) {
                                            Toast.makeText(MainActivity.this, purchase.getOrderId(), Toast.LENGTH_SHORT).show();
                                            // Process the result.
                                        }
                                    }
                                }
                            });

                    ConsumeResponseListener listener = new ConsumeResponseListener() {
                        @Override
                        public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                            if (responseCode == BillingClient.BillingResponse.OK) {
                                Log.d("Success", "Got a purchase: ");


                                return;
                                // Handle the success of the consume operation.
                                // For example, increase the number of coins inside the user's basket.
                            }
                        }
                    };


                        // mBillingClient.consumeAsync(purchaseToken,listener);
                    }
                } else if (paid == true) {

                    Intent in = new Intent(MainActivity.this, TimerSettings.class);
                    startActivity(in);

                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } else if (expired == true) {
                    expiredDialogue();

                }






            }
        });


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.System.canWrite(getBaseContext())) {
                        Intent in = new Intent(v.getContext(), MainSetting.class);
                        startActivity(in);

                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    } else {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + v.getContext().getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }


            }
        });
        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                    Intent in = new Intent(v.getContext(), MyProfile.class);
                    startActivity(in);

                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);





            }
        });

        myForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (unpaid==true) {
//                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
//                            .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                    /*int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, builder.build());*/

                    if (mBillingClient.isReady()) {


                        BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                                .setSku("esydigi_123").setType(BillingClient.SkuType.INAPP);
                        int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());

                        /**
                         * To purchase an Subscription
                         */


                        List<String> skuList = new ArrayList<>();
                        skuList.add("esydigi_123");
//                skuList.add("product_2");
                        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                        mBillingClient.querySkuDetailsAsync(params.build(),
                                new SkuDetailsResponseListener() {
                                    @Override
                                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                        if (responseCode == BillingClient.BillingResponse.OK
                                                && skuDetailsList != null) {
                                            for (SkuDetails skuDetails : skuDetailsList) {
                                                String sku = skuDetails.getSku();
                                                String price = skuDetails.getPrice();
                                                if ("product_1".equals(sku)) {

                                                    Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "cant query product", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                    }
                                });
                        mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                                new PurchaseHistoryResponseListener() {
                                    @Override
                                    public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                          List<Purchase> purchasesList) {
                                        if (responseCode == BillingClient.BillingResponse.OK
                                                && purchasesList != null) {
                                            for (Purchase purchase : purchasesList) {
                                                Toast.makeText(MainActivity.this, purchase.getOrderId(), Toast.LENGTH_SHORT).show();
                                                // Process the result.
                                            }
                                        }
                                    }
                                });

                        ConsumeResponseListener listener = new ConsumeResponseListener() {
                            @Override
                            public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                                if (responseCode == BillingClient.BillingResponse.OK) {
                                    Log.d("Success", "Got a purchase: ");


                                    return;
                                    // Handle the success of the consume operation.
                                    // For example, increase the number of coins inside the user's basket.
                                }
                            }


                            // mBillingClient.consumeAsync(purchaseToken,listener);
                        };
                    }
                } else if (paid == true) {
                    Intent in = new Intent(v.getContext(), MyForms.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } else if (expired == true) {
                    expiredDialogue();

                }





            }
        });


        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//
                //downloadbyRetrofit();

                openlogoutdialog();


            }
        });

        audioplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (unpaid==true) {
//                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
//                            .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                    /*int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, builder.build());*/

                    if (mBillingClient.isReady()) {


                        BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                                .setSku("esydigi_123").setType(BillingClient.SkuType.INAPP);
                        int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());

                        /**
                         * To purchase an Subscription
                         */


                        List<String> skuList = new ArrayList<>();
                        skuList.add("esydigi_123");
//                skuList.add("product_2");
                        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                        mBillingClient.querySkuDetailsAsync(params.build(),
                                new SkuDetailsResponseListener() {
                                    @Override
                                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                        if (responseCode == BillingClient.BillingResponse.OK
                                                && skuDetailsList != null) {
                                            for (SkuDetails skuDetails : skuDetailsList) {
                                                String sku = skuDetails.getSku();
                                                String price = skuDetails.getPrice();
                                                if ("product_1".equals(sku)) {

                                                    Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "cant query product", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                    }
                                });
                        mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                                new PurchaseHistoryResponseListener() {
                                    @Override
                                    public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                          List<Purchase> purchasesList) {
                                        if (responseCode == BillingClient.BillingResponse.OK
                                                && purchasesList != null) {
                                            for (Purchase purchase : purchasesList) {
                                                Toast.makeText(MainActivity.this, purchase.getOrderId(), Toast.LENGTH_SHORT).show();
                                                // Process the result.
                                            }
                                        }
                                    }
                                });

                        ConsumeResponseListener listener = new ConsumeResponseListener() {
                            @Override
                            public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                                if (responseCode == BillingClient.BillingResponse.OK) {
                                    Log.d("Success", "Got a purchase: ");


                                    return;
                                    // Handle the success of the consume operation.
                                    // For example, increase the number of coins inside the user's basket.
                                }
                            }


                            // mBillingClient.consumeAsync(purchaseToken,listener);
                        };
                    }
                } else if (paid == true) {
                    Intent in = new Intent(view.getContext(), AudioActivity.class);
                    startActivity(in);

                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } else if (expired == true) {
                    expiredDialogue();

                }





                }
            });
        CheckList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (unpaid) {
//                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
//                            .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                        /*int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, builder.build());*/

                        if (mBillingClient.isReady()) {


                            BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                                    .setSku("esydigi_123").setType(BillingClient.SkuType.INAPP);
                            int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());

                            /**
                             * To purchase an Subscription
                             */


                            List<String> skuList = new ArrayList<>();
                            skuList.add("esydigi_123");
//                skuList.add("product_2");
                            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                            mBillingClient.querySkuDetailsAsync(params.build(),
                                    new SkuDetailsResponseListener() {
                                        @Override
                                        public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                            if (responseCode == BillingClient.BillingResponse.OK
                                                    && skuDetailsList != null) {
                                                for (SkuDetails skuDetails : skuDetailsList) {
                                                    String sku = skuDetails.getSku();
                                                    String price = skuDetails.getPrice();
                                                    if ("product_1".equals(sku)) {

                                                        Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "cant query product", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                        }
                                    });
                            mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                                    new PurchaseHistoryResponseListener() {
                                        @Override
                                        public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                              List<Purchase> purchasesList) {
                                            if (responseCode == BillingClient.BillingResponse.OK
                                                    && purchasesList != null) {
                                                for (Purchase purchase : purchasesList) {
                                                    Toast.makeText(MainActivity.this, purchase.getOrderId(), Toast.LENGTH_SHORT).show();
                                                    // Process the result.
                                                }
                                            }
                                        }
                                    });

                            ConsumeResponseListener listener = new ConsumeResponseListener() {
                                @Override
                                public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                                    if (responseCode == BillingClient.BillingResponse.OK) {
                                        Log.d("Success", "Got a purchase: ");


                                        return;
                                        // Handle the success of the consume operation.
                                        // For example, increase the number of coins inside the user's basket.
                                    }
                                }


                                // mBillingClient.consumeAsync(purchaseToken,listener);
                            };
                        }
                    } else if (paid == true) {
                        Intent in = new Intent(view.getContext(), BookCheckListActivity.class);
                        startActivity(in);

                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    } else if (expired == true) {
                        expiredDialogue();

                    }


                }
            });


        openbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (unpaid==true) {
//                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
//                            .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                        /*int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, builder.build());*/

                        if (mBillingClient.isReady()) {


                            BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                                    .setSku("esydigi_123").setType(BillingClient.SkuType.INAPP);
                            int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());


                            List<String> skuList = new ArrayList<>();
                            skuList.add("esydigi_123");
//                skuList.add("product_2");
                            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                            mBillingClient.querySkuDetailsAsync(params.build(),
                                    new SkuDetailsResponseListener() {
                                        @Override
                                        public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                            if (responseCode == BillingClient.BillingResponse.OK
                                                    && skuDetailsList != null) {
                                                for (SkuDetails skuDetails : skuDetailsList) {
                                                    String sku = skuDetails.getSku();
                                                    String price = skuDetails.getPrice();
                                                    if ("product_1".equals(sku)) {

                                                        Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "cant query product", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                        }
                                    });
                            mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                                    new PurchaseHistoryResponseListener() {
                                        @Override
                                        public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                              List<Purchase> purchasesList) {
                                            if (responseCode == BillingClient.BillingResponse.OK
                                                    && purchasesList != null) {
                                                for (Purchase purchase : purchasesList) {
                                                    Toast.makeText(MainActivity.this, purchase.getOrderId(), Toast.LENGTH_SHORT).show();
                                                    // Process the result.
                                                }
                                            }
                                        }
                                    });

                            ConsumeResponseListener listener = new ConsumeResponseListener() {
                                @Override
                                public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                                    if (responseCode == BillingClient.BillingResponse.OK) {
                                        Log.d("Success", "Got a purchase: ");


                                        return;
                                        // Handle the success of the consume operation.
                                        // For example, increase the number of coins inside the user's basket.
                                    }
                                }


                                // mBillingClient.consumeAsync(purchaseToken,listener);
                            };
                        }
                } else if (paid == true) {
                    if (MySharedPreferenceClass.getLanguageName(getApplicationContext()).equals("english")) {

                        downloadoropnbook();
                    } else if (MySharedPreferenceClass.getLanguageName(getApplicationContext()).equalsIgnoreCase("swedish") || MySharedPreferenceClass.getLanguageName(MainActivity.this).equalsIgnoreCase("swidish")) {
                        openordownloadswidishBook();
                    }
                } else if (expired == true) {
                    expiredDialogue();

                }


            }
        });


    }

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            Log.e(TAG, "Got an exception trying to validate a purchase: " + e);
            return false;
        }
    }


    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);

        startActivity(refresh);

    }

    public void downloadoropnbook() {


        //File SDCardRoot = Environment.getExternalStorageDirectory();
        //File path = new File(SDCardRoot, "mainepubfiled3.epub");
        File path = new File(getCacheDir(), "mainepubfiled3.epub");

        if (checkAndRequestPermissions()) {

            if (!path.exists() || !MySharedPreferenceClass.getEnglishbookDetail(this)) {
                progressBar = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

                progressBar.setMessage("downloading ...");
                progressBar.setCancelable(false);

                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


                progressBar.show();
                new Thread(new Runnable() {
                    public void run() {

                        downloadFile();
                    }
                }).start();
            } else {

                Config config = AppUtil.getSavedConfig(getApplicationContext());
                if (config == null)
                    config = new Config();
                config.setShowTts(true);

                config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL).setThemeColorRes(R.color.colorPrimary).setFont(Constants.FONT_LORA).setFontSize(2).setShowTts(true);
                // folioReader = FolioReader.get();

                //create a new file, to save the downloaded file

                folioReader.setConfig(config, true).openBook(path.getAbsolutePath());


            }
        }


    }


    public void downloadAudioFIle() {


        // File SDCardRoot = Environment.getExternalStorageDirectory();
        // File path = new File(SDCardRoot, "swedishmainepubfiled1.epub");
        File path = new File(getCacheDir(), "swedishAudiofile.epub");

        if (checkAndRequestPermissions()) {

            if (!path.exists() || !MySharedPreferenceClass.getSwedishbookDetail(this)) {
                progressBar = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

                progressBar.setMessage("downloading ...");
                progressBar.setCancelable(false);

                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


                progressBar.show();
                new Thread(new Runnable() {
                    public void run() {
//                        downloadAudio();
                    }
                }).start();
            } else {

                Config config = AppUtil.getSavedConfig(getApplicationContext());
                if (config == null)
                    config = new Config();

                config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL).setThemeColorRes(R.color.colorPrimary).setFont(Constants.FONT_LORA).setFontSize(2);
                config.setShowTts(false);
                // folioReader = FolioReader.get();

                //create a new file, to save the downloaded file

                folioReader.setConfig(config, true).openBook(path.getAbsolutePath());


            }
        }


    }


    public void openordownloadswidishBook() {


        // File SDCardRoot = Environment.getExternalStorageDirectory();
        // File path = new File(SDCardRoot, "swedishmainepubfiled1.epub");
        File path = new File(getCacheDir(), "swedishmainepubfiled1.epub");

        if (checkAndRequestPermissions()) {

            if (!path.exists() || !MySharedPreferenceClass.getSwedishbookDetail(this)) {
                progressBar = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

                progressBar.setMessage("downloading ...");
                progressBar.setCancelable(false);

                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


                progressBar.show();
                new Thread(new Runnable() {
                    public void run() {
                        downloadSwedishbook();
                    }
                }).start();
            } else {

                Config config = AppUtil.getSavedConfig(getApplicationContext());
                if (config == null)
                    config = new Config();

                config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL).setThemeColorRes(R.color.colorPrimary).setFont(Constants.FONT_LORA).setFontSize(2);
                config.setShowTts(false);
                // folioReader = FolioReader.get();

                //create a new file, to save the downloaded file

                folioReader.setConfig(config, true).openBook(path.getAbsolutePath());


            }
        }


    }


    public void downloadSwedishbook() {
        try {
            URL url = new URL(swidish_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            //File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            // File file = new File(SDCardRoot, "swedishmainepubfiled1.epub");
            File file = new File(getCacheDir(), "swedishmainepubfiled1.epub");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();


            runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[2856000];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setProgress(downloadedSize);
                        float per = ((float) downloadedSize / totalSize) *
                                100;
                        //       cur_val.setText("Downloaded " + downloadedSize +

                        //            "byte / " + totalSize + "Byte (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            progressBar.dismiss();
            MySharedPreferenceClass.setIsSwedishbookdetail(this, true);
            runOnUiThread(new Runnable() {
                public void run() {
                    // hideProgressDialog();
                    // progressBar.dismiss();
                    // if you want close it..
                }
            });

        } catch (final MalformedURLException e) {
            progressBar.dismiss();
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            progressBar.dismiss();
            showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (final Exception e) {
            progressBar.dismiss();
            showError("Error : Please check your internet connection " +
                    e);
        }


    }





    public void opendialogueforLanguage() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose Language");

// add a list
        String[] animals = {"English", "Swedish", "Cancel"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        downloadoropnbook();
                        break;

                    case 1:
                        openordownloadswidishBook();
                        break;
                    case 2: // camel
                        dialog.cancel();

                }
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void openlogoutdialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);

        alertDialogBuilder.setTitle(R.string.Areyousuretologot).setMessage(R.string.ReminderStudyAlert);


        alertDialogBuilder.setPositiveButton(R.string.Yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        sendLogoutRequest();
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                //alertDialog.dismiss();

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    public void expiredDialogue() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);

        alertDialogBuilder.setTitle(R.string.one_year);


        alertDialogBuilder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * To purchase an Subscription
                 */

                BillingFlowParams.Builder builder_ = BillingFlowParams.newBuilder()
                        .setSku("easydigi123_").setType(BillingClient.SkuType.SUBS);
                int response_Code = mBillingClient.launchBillingFlow(MainActivity.this, builder_.build());


                List<String> skuList = new ArrayList<>();
                skuList.add("easydigi123_");
//                skuList.add("product_2");
                SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                mBillingClient.querySkuDetailsAsync(params.build(),
                        new SkuDetailsResponseListener() {
                            @Override
                            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                if (responseCode == BillingClient.BillingResponse.OK
                                        && skuDetailsList != null) {
                                    for (SkuDetails skuDetails : skuDetailsList) {
                                        String sku = skuDetails.getSku();
                                        String price = skuDetails.getPrice();
                                        if ("product_1".equals(sku)) {
                                            Toast.makeText(MainActivity.this, "Product 1", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }

                            }
                        });
                mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                        new PurchaseHistoryResponseListener() {
                            @Override
                            public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                  List<Purchase> purchasesList) {
                                if (responseCode == BillingClient.BillingResponse.OK
                                        && purchasesList != null) {
                                    for (Purchase purchase : purchasesList) {
                                        // Process the result.
                                    }
                                }
                            }
                        });

                ConsumeResponseListener listener = new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                        if (responseCode == BillingClient.BillingResponse.OK) {

                            // Handle the success of the consume operation.
                            // For example, increase the number of coins inside the user's basket.
                        }
                    }
                };
            }
        });


        alertDialogBuilder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        try {
            alertDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
//
    }

    public void sendLogoutRequest() {
        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));


        Call<Message> call = apiService.doLogout(map);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {


                if (response.body() != null) {
                    Log.e("response", response.body().toString());
                    hideDialog();

                    if (response.body().status.equalsIgnoreCase("ok")) {
                        deleteCache(getApplicationContext());
                        Toast.makeText(getApplicationContext(), response.body().message.toString(), Toast.LENGTH_SHORT).show();

                        MySharedPreferenceClass.clear(getApplicationContext());
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();


                    } else {
                        hideDialog();


                        Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();


                    }
                } else {
                    hideDialog();

                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });
    }


    void showProgress(String file_path) {
        showProgressDialog();

        cur_val = findViewById(R.id.tv1);


    }


    void showError(final String err) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, err,
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
        Toast.makeText(this,
                "highlight id = " + highlight.getUUID() + " type = " + type + highlight.getContent(),
                Toast.LENGTH_SHORT).show();
    }

    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<HighLight> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    highlightList = objectMapper.readValue(
                            loadAssetTextAsString("highlights/highlights_data.json"),
                            new TypeReference<List<HighlightData>>() {
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(highlightList, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }

    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }


    public void downloadbyRetrofit() {


        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://testdemo.website/easydigy/");

        Retrofit retrofit = builder.build();
        BasicRequest req = retrofit.create(BasicRequest.class);
        Call<ResponseBody> call = req.downloadFileWithDynamicUrlSync();


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "yeah", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Log.d("Server Connected", "server contacted and has file");
                    showProgressDialog();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                            Log.d("DownLoaded", "file download was a success? " + writtenToDisk);
                            return null;
                        }
                    }.execute();
                } else {
                    Log.d("Error", "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void downloadFile() {

        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection)
                    url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            // File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            // File file = new File(SDCardRoot, "mainepubfiled3.epub");
            File file = new File(getCacheDir(), "mainepubfiled3.epub");
            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[2856000];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setProgress(downloadedSize);
                        float per = ((float) downloadedSize / totalSize) *
                                100;
                        //       cur_val.setText("Downloaded " + downloadedSize +

                        //            "byte / " + totalSize + "Byte (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            progressBar.dismiss();
            MySharedPreferenceClass.setIsEnglishbookdetail(this, true);
            runOnUiThread(new Runnable() {
                public void run() {
                    // hideProgressDialog();
                    // progressBar.dismiss();
                    // if you want close it..
                }
            });

        } catch (final MalformedURLException e) {
            progressBar.dismiss();
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            progressBar.dismiss();
            showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (final Exception e) {
            progressBar.dismiss();
            showError("Error : Please check your internet connection " +
                    e);
        }
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {


        File SDCardRoot = Environment.getExternalStorageDirectory();
        //create a new file, to save the downloaded file
        File file = new File(SDCardRoot, "mainepubfiled.epub");
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    hideProgressDialog();
                    Log.d("FileSize is::", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    private boolean checkAndRequestPermissions() {
        int writeexternalstorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        int write_setting = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_SETTINGS);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (writeexternalstorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
//        if (write_setting != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
//        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);


            return false;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    progressBar = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);


                } else {


                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }


    }


    public void getBookDetail() {

        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);


        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();


                API.getBookDownload( this, REQUEST_DOWNLOADBOOK);
            } else {
                Toast.makeText(this, "Connection TimeOut", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception data:" + e);
        }






    }

    private static String dirPath;

    public static void deleteCache(Context context) {
        try {
            File audioFile = new File(dirPath + "/" + "Audio Files");
            if (audioFile.exists()) {
                deleteDir(audioFile);
            }
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void FCMTOKENTOSERVER() {


        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(MainActivity.this));

        map.put("pushtoken", MySharedPreferenceClass.getPUSHTOKEN(MainActivity.this));
        map.put("devicetype", "Android");
        map.put("deviceId", MySharedPreferenceClass.getDEviceID(MainActivity.this));

        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();


                API.PUSHTOKENTOSERVER(map, this, REQUEST_FCMTOKEN);
            } else {
                Toast.makeText(this, "Connection TimeOut", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception data:" + e);
        }



       /* Call<LoginData> call = apiService.login(map);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {


                if (response.body() != null) {
                    Log.e("response",response.body().toString());
                    hideDialog();
                    System.out.println("Response..."+response.body().message);
                    Log.d( TAG, "Login " + response.body());
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {


                        MySharedPreferenceClass.setcookie(getApplicationContext(), response.body().cookie);
                        MySharedPreferenceClass.setMyUserName(getApplicationContext(), response.body().user.username);
                        MySharedPreferenceClass.setEmail(getApplicationContext(), response.body().user.email);
                        MySharedPreferenceClass.setDisplayname(getApplicationContext(), response.body().user.displayname);
                        MySharedPreferenceClass.setMyUserId(getApplicationContext(),response.body().user.id.toString());
                        MySharedPreferenceClass.setLanguageName(getApplicationContext(),response.body().user.prefferedlanguage.toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        hideDialog();

                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();


                    }
                } else {
                    hideDialog();

                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });*/
    }


    @Override
    public void onSuccess(Response res, int requestCode) {
        hideDialog();
        if (res != null) {
//            Toast.makeText(this, res.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Throwable res, int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessJson(Object res, int requestCode) {
        hideDialog();
        if (requestCode == REQUEST_FCMTOKEN) {
            final LoginData body = (LoginData) res;
            if (body != null) {
                Log.e("response", body.toString());
                hideDialog();
//            System.out.println("Response..."+body.message);
//            Log.d( TAG, "Login " + response.body());
                //userId=response.body().id+"";
                if (body.getStatus().equalsIgnoreCase("ok")) {
//                MySharedPreferenceClass.setcookie(getApplicationContext(), body.get);
                  /*  final UserDatum user = body.getUser();

                    MySharedPreferenceClass.setMyUserName(getApplicationContext(), user.getUsername());
                    MySharedPreferenceClass.setEmail(getApplicationContext(), user.getEmail());
                    MySharedPreferenceClass.setDisplayname(getApplicationContext(), user.getDisplayname());
                    MySharedPreferenceClass.setMyUserId(getApplicationContext(), user.getId().toString());
                    MySharedPreferenceClass.setLanguageName(getApplicationContext(), user.getPrefferedlanguage().toString());*/



              /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();*/


                } else {
                    hideDialog();
//                    Toast.makeText(MainActivity.this, body.getMessage(), Toast.LENGTH_LONG).show();


                }
            } else {
                hideDialog();
//                Toast.makeText(MainActivity.this, body.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
        if (requestCode == REQUEST_GETPAYMENTDATA) {
            final GetPaymentResponse body = (GetPaymentResponse) res;
            if (body != null) {
                Log.e("response", body.toString());
                hideDialog();
//            System.out.println("Response..."+body.message);
//            Log.d( TAG, "Login " + response.body());
                //userId=response.body().id+"";
                if (body.getStatus().equalsIgnoreCase("ok")) {
                    final GetPaymentDatum user = body.getData();
                    if (user.getStatus().equalsIgnoreCase("unpaid")) {
                        unpaid = true;
                    } else if ((user.getStatus().equalsIgnoreCase("paid"))) {
                        paid = true;
                    } else if ((user.getStatus().equalsIgnoreCase("expired"))) {
                        expired = true;

                    } else {

                    }

//                MySharedPreferenceClass.setcookie(getApplicationContext(), body.get);
                  /*  final UserDatum user = body.getUser();

                    MySharedPreferenceClass.setMyUserName(getApplicationContext(), user.getUsername());
                    MySharedPreferenceClass.setEmail(getApplicationContext(), user.getEmail());
                    MySharedPreferenceClass.setDisplayname(getApplicationContext(), user.getDisplayname());
                    MySharedPreferenceClass.setMyUserId(getApplicationContext(), user.getId().toString());
                    MySharedPreferenceClass.setLanguageName(getApplicationContext(), user.getPrefferedlanguage().toString());*/



              /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();*/


                } else {
                    hideDialog();
//                    Toast.makeText(MainActivity.this, body.getMessage(), Toast.LENGTH_LONG).show();


                }
            } else {
                hideDialog();
                Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_LONG).show();

            }
        }

        if (requestCode == REQUEST_SAVEPAYMENT) {
            final SavePaymentResponse body = (SavePaymentResponse) res;
            if (body != null) {
                Log.e("response", body.toString());
                hideDialog();
//            System.out.println("Response..."+body.message);
//            Log.d( TAG, "Login " + response.body());
                //userId=response.body().id+"";
                if (body.getStatus().equalsIgnoreCase("ok")) {
                    paid=true;

                   /* if (MySharedPreferenceClass.getLanguageName(getApplicationContext()).equals("english")) {

                        downloadoropnbook();
                    } else if (MySharedPreferenceClass.getLanguageName(getApplicationContext()).equalsIgnoreCase("swedish") || MySharedPreferenceClass.getLanguageName(MainActivity.this).equalsIgnoreCase("swidish")) {
                        openordownloadswidishBook();*/



//                MySharedPreferenceClass.setcookie(getApplicationContext(), body.get);
                  /*  final UserDatum user = body.getUser();

                    MySharedPreferenceClass.setMyUserName(getApplicationContext(), user.getUsername());
                    MySharedPreferenceClass.setEmail(getApplicationContext(), user.getEmail());
                    MySharedPreferenceClass.setDisplayname(getApplicationContext(), user.getDisplayname());
                    MySharedPreferenceClass.setMyUserId(getApplicationContext(), user.getId().toString());
                    MySharedPreferenceClass.setLanguageName(getApplicationContext(), user.getPrefferedlanguage().toString());*/



              /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();*/


                } else {
                    hideDialog();
//                    Toast.makeText(MainActivity.this, body.getMessage(), Toast.LENGTH_LONG).show();


                }
            } else {
                hideDialog();
                Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_LONG).show();

            }
        }
        else if (requestCode == REQUEST_DOWNLOADBOOK){
            final  GetBookDetail bookDetail = (GetBookDetail)res;

            if (bookDetail != null) {
                hideDialog();
                dwnload_file_path = bookDetail.getData().get(0).getEpubLink();
                swidish_file_path = bookDetail.getData().get(1).getEpubLink();

            }
            else {
                Toast.makeText(this, ((GetBookDetail) res).getStatus(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void GetPaymentStatus() {


        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);


        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();


                API.GetPaymentData(MySharedPreferenceClass.getMyUserId(MainActivity.this), this, REQUEST_GETPAYMENTDATA);
            } else {
                Toast.makeText(this, "Connection TimeOut", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception data:" + e);
        }

    }



    public void SavePaymentStatustoServer() {


        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);


        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();


                API.savePaymentDatatoServer(MySharedPreferenceClass.getMyUserId(MainActivity.this), "paid", this, REQUEST_SAVEPAYMENT);
            } else {
                Toast.makeText(this, "Connection TimeOut", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception data:" + e);
        }

    }


    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode ==0){

            SavePaymentStatustoServer();
            try {
                Toast.makeText(this, "purchase Item:" + purchases.size(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                System.out.println("EXCEPTIONDATA..." + e);
            }
        }
        else {
            return;
//            expiredDialogue();
        }
        /*if(paid==true){
            SavePaymentStatustoServer();
            try {
                Toast.makeText(this, "purchase Item:" + purchases.size(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                System.out.println("EXCEPTIONDATA..." + e);
            }

        } else {
            expiredDialogue();
        }
*/
    }
}


