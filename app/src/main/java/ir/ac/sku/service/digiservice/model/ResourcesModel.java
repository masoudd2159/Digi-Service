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

public class ResourcesModel {
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
        private int primaryAreaId;
        private String labName;
        private String title;
        private String titleEnglish;
        private String usagePeriodType;
        private int quantity;
        private int cost;
        private boolean isActived;
        private int score;
        private int scoreCount;
        private int maxSelectableValue;
        private boolean isPanelActived;
        private String picture;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPrimaryAreaId() {
            return primaryAreaId;
        }

        public void setPrimaryAreaId(int primaryAreaId) {
            this.primaryAreaId = primaryAreaId;
        }

        public String getLabName() {
            return labName;
        }

        public void setLabName(String labName) {
            this.labName = labName;
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

        public String getUsagePeriodType() {
            return usagePeriodType;
        }

        public void setUsagePeriodType(String usagePeriodType) {
            this.usagePeriodType = usagePeriodType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public boolean isActived() {
            return isActived;
        }

        public void setActived(boolean actived) {
            isActived = actived;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScoreCount() {
            return scoreCount;
        }

        public void setScoreCount(int scoreCount) {
            this.scoreCount = scoreCount;
        }

        public int getMaxSelectableValue() {
            return maxSelectableValue;
        }

        public void setMaxSelectableValue(int maxSelectableValue) {
            this.maxSelectableValue = maxSelectableValue;
        }

        public boolean isPanelActived() {
            return isPanelActived;
        }

        public void setPanelActived(boolean panelActived) {
            isPanelActived = panelActived;
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
        String url = MyAPI.RESOURCES + "?" + ManagerHelper.enCodeParameters(params);
        webService.requestAPI(url, Request.Method.GET, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    ResourcesModel resourcesModel = gson.fromJson(new String(obj.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), ResourcesModel.class);
                    if (resourcesModel.ok)
                        handler.onResponse(true, resourcesModel);
                }
            }
        });
    }
}
