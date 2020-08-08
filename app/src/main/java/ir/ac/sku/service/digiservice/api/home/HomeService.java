package ir.ac.sku.service.digiservice.api.home;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface HomeService {
    @GET(MyAPI.RECENT_RESOURCES)
    Observable<JsonObject> getRecentResource(@QueryMap HashMap<String, String> params);

    @GET(MyAPI.POPULAR_RESOURCES)
    Observable<JsonObject> getPopularResource(@QueryMap HashMap<String, String> params);

    @GET(MyAPI.SLIDER)
    Observable<JsonObject> getSlider(@QueryMap HashMap<String, String> params);
}
