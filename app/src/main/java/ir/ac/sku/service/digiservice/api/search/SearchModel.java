package ir.ac.sku.service.digiservice.api.search;

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
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import rx.Observable;

@SuppressLint("LongLogTag")
public class SearchModel {
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

    public static void fetchFromWeb(Context context, HashMap<String, String> params, MyHandler handler) {
        if (!ManagerHelper.isInternetAvailable(context)) {
            Log.i(MyLog.SEARCH, "No Internet Access");
            ManagerHelper.noInternetAccess(context);
            return;
        }

        Observable<JsonObject> observable = ApiFactory.createProvideApiService(SearchService.class).getSearch(params);

        RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
            @Override public void onSuccess(JsonObject jsonObject) {
                Log.i(MyLog.SEARCH, "Object Successfully Receive");
                SearchModel response = new Gson().fromJson(jsonObject, SearchModel.class);
                if (response.isOk()) {
                    handler.onResponse(response.isOk(), response);
                } else {
                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override public void onFailed(String errorMsg) {
                Log.i(MyLog.SEARCH, errorMsg);
                Toast.makeText(context, R.string.unable_to_connect_to_the_server, Toast.LENGTH_SHORT).show();
            }
        });
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
        private Integer id;

        @SerializedName("primaryAreaId")
        @Expose
        private Integer primaryAreaId;

        @SerializedName("labName")
        @Expose
        private String labName;

        @SerializedName("cost")
        @Expose
        private Integer cost;

        @SerializedName("score")
        @Expose
        private Integer score;

        @SerializedName("scoreCount")
        @Expose
        private Integer scoreCount;

        @SerializedName("picture")
        @Expose
        private String picture;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPrimaryAreaId() {
            return primaryAreaId;
        }

        public void setPrimaryAreaId(Integer primaryAreaId) {
            this.primaryAreaId = primaryAreaId;
        }

        public String getLabName() {
            return labName;
        }

        public void setLabName(String labName) {
            this.labName = labName;
        }

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
            this.cost = cost;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getScoreCount() {
            return scoreCount;
        }

        public void setScoreCount(Integer scoreCount) {
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
