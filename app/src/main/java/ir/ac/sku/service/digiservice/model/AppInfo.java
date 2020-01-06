package ir.ac.sku.service.digiservice.model;

import android.content.Context;
import android.view.textclassifier.TextLinks;

import com.android.volley.Request;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import ir.ac.sku.service.digiservice.util.WebService;

public class AppInfo {
    private boolean ok;
    private int code;
    private String message;
    private List<Data> data = null;


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
        private int id;
        private int minimumVersion;
        private int latestVersion;
        private String downloadAndroidUrl;
        private String updateMessage;
        private String forceUpdateMessage;
        private String developmentTeamUrl;
        private String contactSupportId;
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

    public static void fetchFromWeb(Context context, HashMap<String, String> params, MyHandler handler) {
        Gson gson = new Gson();

        WebService webService = new WebService(context);
        String url = MyAPI.SYSTEM_INFO + "?" + ManagerHelper.enCodeParameters(params);
        webService.requestAPI(url, Request.Method.GET, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    AppInfo appInfo = gson.fromJson(new String(obj.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), AppInfo.class);
                    if (appInfo.ok)
                        handler.onResponse(true, appInfo);
                }
            }
        });
    }
}
