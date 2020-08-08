package ir.ac.sku.service.digiservice.api.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.ApiFactory;
import ir.ac.sku.service.digiservice.api.RxApiCallHelper;
import ir.ac.sku.service.digiservice.api.RxApiCallback;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import rx.Observable;

@SuppressLint("LongLogTag")
public class HomePageModel {
    @SerializedName("ok")
    @Expose
    private boolean ok;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public static void fetchFromWeb(Context context, String urlApi, HashMap<String, String> params, MyHandler handler) {
        if (!ManagerHelper.isInternetAvailable(context)) {
            Log.i(MyLog.HOME, "No Internet Access");
            ManagerHelper.noInternetAccess(context);
        } else {
            Observable<JsonObject> observable = null;

            if (urlApi.equals(MyAPI.RECENT_RESOURCES)) {
                observable = ApiFactory.createProvideApiService(HomeService.class).getRecentResource(params);
            } else if (urlApi.equals(MyAPI.POPULAR_RESOURCES)) {
                observable = ApiFactory.createProvideApiService(HomeService.class).getPopularResource(params);
            }

            RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
                @Override public void onSuccess(JsonObject jsonObject) {
                    Log.i(MyLog.HOME, "Object Successfully Receive");
                    HomePageModel response = new Gson().fromJson(jsonObject, HomePageModel.class);
                    if (response.isOk()) {
                        handler.onResponse(true, response);
                    }
                }

                @Override public void onFailed(String errorMsg) {
                    Log.i(MyLog.HOME, errorMsg);
                    Toast.makeText(context, R.string.unable_to_connect_to_the_server, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("primaryAreaId")
        @Expose
        private int primaryAreaId;

        @SerializedName("labName")
        @Expose
        private String labName;

        @SerializedName("cost")
        @Expose
        private int cost;

        @SerializedName("score")
        @Expose
        private int score;

        @SerializedName("scoreCount")
        @Expose
        private int scoreCount;

        @SerializedName("picture")
        @Expose
        private String picture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPrimaryAreaId() {
            return primaryAreaId;
        }

        public void setPrimaryAreaId(int primaryAreaId) {
            this.primaryAreaId = primaryAreaId;
        }

        public String getLabName() {
            return labName;
        }

        public void setLabName(String labName) {
            this.labName = labName;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScoreCount() {
            return scoreCount;
        }

        public void setScoreCount(int scoreCount) {
            this.scoreCount = scoreCount;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
