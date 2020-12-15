package ir.ac.sku.service.digiservice.api.enter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.ApiFactory;
import ir.ac.sku.service.digiservice.api.RxApiCallHelper;
import ir.ac.sku.service.digiservice.api.RxApiCallback;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

import static ir.ac.sku.service.digiservice.config.MyLog.DIGI_SERVICE;

public class LoginModel {
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

    public static void fetchUserKey(@NonNull Context context, Map<String, String> params, MyHandler handler) {
        if (ManagerHelper.checkInternetServices(context)) {
            Observable<JsonObject> observable = ApiFactory.createProvideApiService(LoginService.class).getUserKey(params);
            RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    Log.i(DIGI_SERVICE + LoginModel.class.getSimpleName(), "Object Successfully Receive");
                    LoginModel response = new Gson().fromJson(jsonObject, LoginModel.class);
                    handler.onResponse(response.isOk(), response);
                }

                @Override
                public void onFailed(String errorMsg) {
                    Log.i(DIGI_SERVICE + LoginModel.class.getSimpleName(), errorMsg);
                    Toast.makeText(context, R.string.unable_to_connect_to_the_server, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void fetchFromWeb(@NonNull Context context, String params, MyHandler handler) {
        if (ManagerHelper.checkInternetServices(context)) {
            Observable<JsonObject> observable = ApiFactory.createProvideApiService(LoginService.class).getEnter(params);
            RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    Log.i(DIGI_SERVICE + LoginModel.class.getSimpleName(), "Object Successfully Receive");
                    LoginModel response = new Gson().fromJson(jsonObject, LoginModel.class);
                    handler.onResponse(response.isOk(), response);
                }

                @Override
                public void onFailed(String errorMsg) {
                    Log.i(DIGI_SERVICE + LoginModel.class.getSimpleName(), errorMsg);
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
        @SerializedName("passwordSalt")
        @Expose
        private String passwordSalt;

        public String getPasswordSalt() {
            return passwordSalt;
        }

        public void setPasswordSalt(String passwordSalt) {
            this.passwordSalt = passwordSalt;
        }
    }
}
