package ir.ac.sku.service.digiservice.api.appInfo;

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

public class AppInfo {
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

    @SuppressLint("LongLogTag")
    public static void fetchFromWeb(Context context, HashMap<String, String> params, MyHandler handler) {
        if (!ManagerHelper.isInternetAvailable(context)) {
            Log.i(MyLog.DIGI_SERVICE + AppInfo.class.getSimpleName(), "No Internet Access");
            ManagerHelper.noInternetAccess(context);
            return;
        }

        Observable<JsonObject> observable = ApiFactory.createProvideApiService(AppInfoService.class).getAppInfo();

        RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
            @Override public void onSuccess(JsonObject jsonObject) {
                Log.i(MyLog.DIGI_SERVICE + AppInfo.class.getSimpleName(), "Object Successfully Receive");
                AppInfo response = new Gson().fromJson(jsonObject, AppInfo.class);
                if (response.isOk()) {
                    handler.onResponse(true, response);
                }
            }

            @Override public void onFailed(String errorMsg) {
                Log.i(MyLog.NEWS, errorMsg);
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

        @SerializedName("minimumVersion")
        @Expose
        private int minimumVersion;

        @SerializedName("latestVersion")
        @Expose
        private int latestVersion;

        @SerializedName("downloadAndroidUrl")
        @Expose
        private String downloadAndroidUrl;

        @SerializedName("updateMessage")
        @Expose
        private String updateMessage;

        @SerializedName("forceUpdateMessage")
        @Expose
        private String forceUpdateMessage;

        @SerializedName("developmentTeamUrl")
        @Expose
        private String developmentTeamUrl;

        @SerializedName("contactSupportId")
        @Expose
        private String contactSupportId;

        @SerializedName("runnable")
        @Expose
        private boolean runnable;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMinimumVersion() {
            return minimumVersion;
        }

        public void setMinimumVersion(int minimumVersion) {
            this.minimumVersion = minimumVersion;
        }

        public int getLatestVersion() {
            return latestVersion;
        }

        public void setLatestVersion(int latestVersion) {
            this.latestVersion = latestVersion;
        }

        public String getDownloadAndroidUrl() {
            return downloadAndroidUrl;
        }

        public void setDownloadAndroidUrl(String downloadAndroidUrl) {
            this.downloadAndroidUrl = downloadAndroidUrl;
        }

        public String getUpdateMessage() {
            return updateMessage;
        }

        public void setUpdateMessage(String updateMessage) {
            this.updateMessage = updateMessage;
        }

        public String getForceUpdateMessage() {
            return forceUpdateMessage;
        }

        public void setForceUpdateMessage(String forceUpdateMessage) {
            this.forceUpdateMessage = forceUpdateMessage;
        }

        public String getDevelopmentTeamUrl() {
            return developmentTeamUrl;
        }

        public void setDevelopmentTeamUrl(String developmentTeamUrl) {
            this.developmentTeamUrl = developmentTeamUrl;
        }

        public String getContactSupportId() {
            return contactSupportId;
        }

        public void setContactSupportId(String contactSupportId) {
            this.contactSupportId = contactSupportId;
        }

        public boolean isRunnable() {
            return runnable;
        }

        public void setRunnable(boolean runnable) {
            this.runnable = runnable;
        }
    }
}
