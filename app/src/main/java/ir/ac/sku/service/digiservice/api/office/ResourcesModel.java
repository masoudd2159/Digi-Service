package ir.ac.sku.service.digiservice.api.office;

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
public class ResourcesModel {
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
            Log.i(MyLog.RESOURCES, "No Internet Access");
            ManagerHelper.noInternetAccess(context);
            return;
        }

        Observable<JsonObject> observable = ApiFactory.createProvideApiService(OfficeService.class).getResources(params);

        RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
            @Override public void onSuccess(JsonObject jsonObject) {
                Log.i(MyLog.RESOURCES, "Object Successfully Receive");
                ResourcesModel response = new Gson().fromJson(jsonObject, ResourcesModel.class);
                if (response.isOk()) {
                    handler.onResponse(true, response);
                }
            }

            @Override public void onFailed(String errorMsg) {
                Log.i(MyLog.RESOURCES, errorMsg);
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
        private int id;

        @SerializedName("primaryAreaId")
        @Expose
        private int primaryAreaId;

        @SerializedName("labName")
        @Expose
        private String labName;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("titleEnglish")
        @Expose
        private String titleEnglish;

        @SerializedName("usagePeriodType")
        @Expose
        private String usagePeriodType;

        @SerializedName("quantity")
        @Expose
        private int quantity;

        @SerializedName("cost")
        @Expose
        private int cost;

        @SerializedName("isActived")
        @Expose
        private boolean isActived;

        @SerializedName("score")
        @Expose
        private int score;

        @SerializedName("scoreCount")
        @Expose
        private int scoreCount;

        @SerializedName("maxSelectableValue")
        @Expose
        private int maxSelectableValue;

        @SerializedName("isPanelActived")
        @Expose
        private boolean isPanelActived;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleEnglish() {
            return titleEnglish;
        }

        public void setTitleEnglish(String titleEnglish) {
            this.titleEnglish = titleEnglish;
        }

        public String getUsagePeriodType() {
            return usagePeriodType;
        }

        public void setUsagePeriodType(String usagePeriodType) {
            this.usagePeriodType = usagePeriodType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public boolean isActived() {
            return isActived;
        }

        public void setActived(boolean actived) {
            isActived = actived;
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

        public int getMaxSelectableValue() {
            return maxSelectableValue;
        }

        public void setMaxSelectableValue(int maxSelectableValue) {
            this.maxSelectableValue = maxSelectableValue;
        }

        public boolean isPanelActived() {
            return isPanelActived;
        }

        public void setPanelActived(boolean panelActived) {
            isPanelActived = panelActived;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
