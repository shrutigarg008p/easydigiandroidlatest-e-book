package com.EsyDigi.esyDigi.api;

import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.Interface.ApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class API {



        /*____________________________Login Module Api_____________________________________________*/


        /*................................... 1.Register Api ..............................................*/
        public static void register(String userId, final ApiResponse listener,final int code) {
            BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
            Call<GetUserNotification> call = service.getUserNotification(userId);
            call.enqueue(new Callback<GetUserNotification>() {
                @Override
                public void onResponse(Call<GetUserNotification> call, Response<GetUserNotification> response) {
                    if (response.body() != null) {
                        listener.onSuccessJson(response.body(),code);
                    } else
                        listener.onSuccess(response,code);
                }

                @Override
                public void onFailure(Call<GetUserNotification> call, Throwable t) {
                    listener.onFailure(t.getCause(),code);
                }
            });
        }

    public static void LOGIN(Map map, final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<LoginData> call = service.login(map);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
//                    listener.onSuccess(response);
                listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }
    public static void AudioFile( final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<GetAudioFiles> call = service.getAudio();
        call.enqueue(new Callback<GetAudioFiles>() {
            @Override
            public void onResponse(Call<GetAudioFiles> call, Response<GetAudioFiles> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
//                    listener.onSuccess(response);
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<GetAudioFiles> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }

    public static void getTopicDetails(String projectID,String user_id, final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<GetTopicDetails> call = service.getTopic(projectID, user_id);
        call.enqueue(new Callback<GetTopicDetails>() {
            @Override
            public void onResponse(Call<GetTopicDetails> call, Response<GetTopicDetails> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<GetTopicDetails> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }

    public static void setTopicDetails(int topicId, String projectId, String check,String userId, final ApiResponse listener ,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<SetTopicModel> call = service.setTopic(topicId,projectId,check,userId);
        call.enqueue(new Callback<SetTopicModel>() {
            @Override
            public void onResponse(Call<SetTopicModel> call, Response<SetTopicModel> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<SetTopicModel> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }

    public static void PUSHTOKENTOSERVER (Map map, final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<LoginData> call = service.UPDATEFCMTOKEN(map);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
//                    listener.onSuccess(response);
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }


    public static void GetPaymentData (String user_id,final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<GetPaymentResponse> call = service.getpaymentdata(user_id);
        call.enqueue(new Callback<GetPaymentResponse>() {
            @Override
            public void onResponse(Call<GetPaymentResponse> call, Response<GetPaymentResponse> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
//                    listener.onSuccess(response);
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<GetPaymentResponse> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }


    public static void savePaymentDatatoServer (String user_id,String status, final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<SavePaymentResponse> call = service.savepayment(user_id,status);
        call.enqueue(new Callback<SavePaymentResponse>() {
            @Override
            public void onResponse(Call<SavePaymentResponse> call, Response<SavePaymentResponse> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
//                    listener.onSuccess(response);
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<SavePaymentResponse> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }
    public static void getBookDownload( final ApiResponse listener,final int code) {
        BasicRequest service = ServiceHandler.createService(BasicRequest.class, true);
        Call<GetBookDetail> call = service.getBookDetail();
        call.enqueue(new Callback<GetBookDetail>() {
            @Override
            public void onResponse(Call<GetBookDetail> call, Response<GetBookDetail> response) {
                if (response.body() != null) {
                    listener.onSuccessJson(response.body(),code);
                } else
//                    listener.onSuccess(response);
                    listener.onSuccess(response,code);
            }

            @Override
            public void onFailure(Call<GetBookDetail> call, Throwable t) {
                listener.onFailure(t.getCause(),code);
            }
        });
    }
}
