package ir.ac.sku.service.digiservice.api.search;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SearchService {
    @GET(MyAPI.SEARCH)
    Observable<JsonObject> getSearch(@QueryMap HashMap<String, String> params);
}
