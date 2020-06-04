package ir.ac.sku.service.digiservice.model;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import ir.ac.sku.service.digiservice.util.WebService;

public class SearchModel {
    private Boolean ok;
    private Integer code;
    private String message;
    private List<Data> data = new ArrayList<>();

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

        private Integer id;
        private Integer primaryAreaId;
        private String labName;
        private Integer cost;
        private Integer score;
        private Integer scoreCount;
        private String picture;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPrimaryAreaId() {
            return primaryAreaId;
        }

        public void setPrimaryAreaId(Integer primaryAreaId) {
            this.primaryAreaId = primaryAreaId;
        }

        public String getLabName() {
            return labName;
        }

        public void setLabName(String labName) {
            this.labName = labName;
        }

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
            this.cost = cost;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getScoreCount() {
            return scoreCount;
        }

        public void setScoreCount(Integer scoreCount) {
            this.scoreCount = scoreCount;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    public static void fetchFromWeb(Context context, HashMap<String, String> params, MyHandler handler) {
        Gson gson = new Gson();

        WebService webService = new WebService(context);
        String url = MyAPI.SEARCH + "?" + ManagerHelper.enCodeParameters(params);
        webService.requestAPI(url, Request.Method.GET, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    SearchModel searchModel = gson.fromJson(new String(obj.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), SearchModel.class);
                    if (searchModel.ok)
                        handler.onResponse(true, searchModel);
                }
            }
        });
    }
}
