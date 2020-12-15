package ir.ac.sku.service.digiservice.api.enter;

import com.google.gson.JsonObject;

import java.util.Map;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface LoginService {
    @GET
    Observable<JsonObject> getEnter(@Url String url);

    @GET(MyAPI.USER_KEY)
    Observable<JsonObject> getUserKey(@QueryMap Map<String, String> params);


}
