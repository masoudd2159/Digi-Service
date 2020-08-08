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
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import rx.Observable;

public class SliderModel {
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
            Log.i(MyLog.SLIDER, "No Internet Access");
            ManagerHelper.noInternetAccess(context);
            return;
        }

        Observable<JsonObject> observable = ApiFactory.createProvideApiService(HomeService.class).getSlider(params);

        RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
            @Override public void onSuccess(JsonObject jsonObject) {
                Log.i(MyLog.SLIDER, "Object Successfully Receive");
                SliderModel response = new Gson().fromJson(jsonObject, SliderModel.class);
                if (response.isOk()) {
                    handler.onResponse(true, response);
                }
            }

            @Override public void onFailed(String errorMsg) {
                Log.i(MyLog.SLIDER, errorMsg);
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

        @SerializedName("picture")
        @Expose
        private String picture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
