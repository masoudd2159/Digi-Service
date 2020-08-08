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
public class AreasModel {
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
            Log.i(MyLog.AREAS, "No Internet Access");
            ManagerHelper.noInternetAccess(context);
            return;
        }

        Observable<JsonObject> observable = ApiFactory.createProvideApiService(OfficeService.class).getAreas();

        RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
            @Override public void onSuccess(JsonObject jsonObject) {
                Log.i(MyLog.AREAS, "Object Successfully Receive");
                AreasModel response = new Gson().fromJson(jsonObject, AreasModel.class);
                if (response.isOk()) {
                    handler.onResponse(true, response);
                }
            }

            @Override public void onFailed(String errorMsg) {
                Log.i(MyLog.AREAS, errorMsg);
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

        @SerializedName("departmentId")
        @Expose
        private int departmentId;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("titleEnglish")
        @Expose
        private String titleEnglish;

        @SerializedName("isActived")
        @Expose
        private boolean isActived;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
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

        public boolean isActived() {
            return isActived;
        }

        public void setActived(boolean actived) {
            isActived = actived;
        }
    }
}
