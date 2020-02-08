package ir.ac.sku.service.digiservice.model;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import ir.ac.sku.service.digiservice.util.WebService;

public class AreasModel {
    private boolean ok;
    private int code;
    private String message;
    private List<Data> data = null;


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
        private int id;
        private int departmentId;
        private String title;
        private String titleEnglish;
        private boolean isActived;

         public int getId() {
             return id;
         }

         public void setId(int id) {
             this.id = id;
         }

         public int getDepartmentId() {
             return departmentId;
         }

         public void setDepartmentId(int departmentId) {
             this.departmentId = departmentId;
         }

         public String getTitle() {
             return title;
         }

         public void setTitle(String title) {
             this.title = title;
         }

         public String getTitleEnglish() {
             return titleEnglish;
         }

         public void setTitleEnglish(String titleEnglish) {
             this.titleEnglish = titleEnglish;
         }

         public boolean isActived() {
             return isActived;
         }

         public void setActived(boolean actived) {
             isActived = actived;
         }
     }

    public static void fetchFromWeb(Context context, HashMap<String, String> params, MyHandler handler) {
        Gson gson = new Gson();

        WebService webService = new WebService(context);
        String url = MyAPI.AREAS + "?" + ManagerHelper.enCodeParameters(params);
        webService.requestAPI(url, Request.Method.GET, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    AreasModel areasModel = gson.fromJson(new String(obj.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), AreasModel.class);
                    if (areasModel.ok)
                        handler.onResponse(true, areasModel);
                }
            }
        });
    }
}
