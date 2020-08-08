package ir.ac.sku.service.digiservice.api.selectedResource;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.config.MyAPI;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SelectedResourceService {
    @GET(MyAPI.SELECTED_RESOURCES)
    Observable<JsonObject> getSelectedResource(@QueryMap HashMap<String, String> params);
}