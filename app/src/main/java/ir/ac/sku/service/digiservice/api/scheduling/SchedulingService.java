package ir.ac.sku.service.digiservice.api.scheduling;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SchedulingService {
    @GET(MyAPI.SCHEDULE)
    Observable<JsonObject> getScheduling(@QueryMap HashMap<String, String> params);
}
