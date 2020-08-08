package ir.ac.sku.service.digiservice.api.office;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface OfficeService {
    @GET(MyAPI.AREAS)
    Observable<JsonObject> getAreas();

    @GET(MyAPI.RESOURCES)
    Observable<JsonObject> getResources(@QueryMap HashMap<String, String> params);

    @GET(MyAPI.DEPARTMENTS_FILTER)
    Observable<JsonObject> getDepartments(@QueryMap HashMap<String, String> params);
}
