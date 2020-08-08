package ir.ac.sku.service.digiservice.api.appInfo;

import com.google.gson.JsonObject;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import rx.Observable;

public interface AppInfoService {
    @GET(MyAPI.SYSTEM_INFO)
    Observable<JsonObject> getAppInfo();
}
