package com.EsyDigi.esyDigi.BasicRequests;




import com.EsyDigi.esyDigi.api.Forgotpassword;
import com.EsyDigi.esyDigi.api.GetAudioFiles;
import com.EsyDigi.esyDigi.api.GetBookDetail;
import com.EsyDigi.esyDigi.api.GetDriverDetail;
import com.EsyDigi.esyDigi.api.GetNotiDetail;
import com.EsyDigi.esyDigi.api.GetPaymentResponse;
import com.EsyDigi.esyDigi.api.GetProfileInfo;
import com.EsyDigi.esyDigi.api.GetTopicDetails;
import com.EsyDigi.esyDigi.api.GetUserNotification;
import com.EsyDigi.esyDigi.api.LoginData;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.Register;
import com.EsyDigi.esyDigi.api.SavePaymentResponse;
import com.EsyDigi.esyDigi.api.SetTopicModel;
import com.EsyDigi.esyDigi.utility.Constants;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;



public interface BasicRequest {

    @GET(Constants.BASE_LINK+"getBooks")
    Call<GetBookDetail> getBookDetail();
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "login")
    Call<LoginData> login(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "prefferedlanguage")
    Call<Message> selectlanguage(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "get_userinfo")
    Call<GetProfileInfo> getProfileInfo(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "update_profile")
    Call<Message> updateProfileInfo(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "change_password")
    Call<Message> changepassword(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "logout")
    Call<Message> doLogout(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST (Constants.BASE_LINK+"create_user_noti")
    Call<Message>setalarm (@Field("user_id") String userid,  @Field("days[]") ArrayList<String> day, @Field("time[]") ArrayList<String> timing);



    @FormUrlEncoded
    @POST (Constants.BASE_LINK+"create_accompanying_driver")
    Call<Message>postdriverdetail (@Field("user_id") String userid,@Field("learner_valid_date") String lerner_validity  ,  @Field("accompanying_valid_date") String accompanying_validity, @Field("alternate_driver1[]") ArrayList<String> alternate1, @Field("alternate_driver2[]") ArrayList<String> alternate2, @Field("alternate_driver3[]") ArrayList<String> alternate3 ,@Field("course_review_date")String course_review_date
,@Field("special_condition[]") ArrayList<String> special_condition,@Field("vehicle_handeling[]") ArrayList<String> vehicle_handeling,@Field("country_roads[]") ArrayList<String> country_roads,@Field("built_up_areas[]") ArrayList<String> built_up_areas,@Field("the_instructor_overall_assesment[]") ArrayList<String> the_instructor_overall_assesment,@Field("builtup_areas_and_country_roads[]") ArrayList<String> builtup_areas_and_country_roads
                                   );



    @FormUrlEncoded
    @POST(Constants.BASE_LINK +"get_accompanying_driver")
    Call<GetDriverDetail> getDriverDetail(@FieldMap Map<String, String> fields);



    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "register")
    Call<Register> registration(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "retrieve_password")
    Call<Forgotpassword>  forgotpassword(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK + "get_notification")
    Call<GetNotiDetail>  getNotificationDetail(@FieldMap Map<String, String> fields);
    @Streaming
    @GET("wp-content/uploads/anthologize-temp/3916a10fe68548bcabfdaee40d80f5a17d7a6aa9/book.epub")
    Call<ResponseBody> downloadFileWithDynamicUrlSync();

    @FormUrlEncoded
    @POST(Constants.BASE_LINK+"getUserNotification")
    Call<GetUserNotification> getUserNotification(@Field("user_id") String userid);


    @GET(Constants.BASE_LINK+"/book_with_mp3/")
    Call<GetAudioFiles> getAudio();


    @FormUrlEncoded
    @POST(Constants.BASE_LINK+"getTopicCheckStaus")
    Call<GetTopicDetails> getTopic(@Field("projectId") String projectId,@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK+"setTopicCheckStaus")
    Call<SetTopicModel> setTopic(@Field("topicId") int topicId,@Field("projectId") String projectId,@Field("check") String check,@Field("user_id")String user_id);
    @FormUrlEncoded
    @POST(Constants.BASE_LINK+"updateUserToken")
    Call<LoginData> UPDATEFCMTOKEN(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(Constants.BASE_LINK+"getPayment")
    Call<GetPaymentResponse> getpaymentdata(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Constants.BASE_LINK+"savePayment")
    Call<SavePaymentResponse> savepayment(@Field("user_id") String user_id,@Field("status") String status);
}
