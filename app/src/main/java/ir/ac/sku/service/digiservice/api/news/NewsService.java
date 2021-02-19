package ir.ac.sku.service.digiservice.api.news;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface NewsService {
    @GET(MyAPI.NEWS)
    Observable<JsonObject> getNews(@QueryMap HashMap<String, String> params);
}
