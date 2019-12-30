package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.model.IsOK;

public class WebService {
    private Context context;
    private RequestQueue queue;
    private Gson gson;
    private IsOK isOK;

    @SuppressLint("LongLogTag")
    public WebService(Context context) {
        Log.i(MyLog.WEB_SERVICE, "Context : " + context.toString());
        this.context = context;
        queue = Volley.newRequestQueue(context);
        gson = new Gson();
        isOK = new IsOK();
    }

    @SuppressLint("LongLogTag")
    public void requestAPI(final String url, final int method, final MyHandler handler) {
        Log.i(MyLog.WEB_SERVICE, "Handler : ‌" + handler.toString());
        Log.i(MyLog.WEB_SERVICE, "URL : ‌" + url);

        queue.add(new StringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        isOK = gson.fromJson(new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), IsOK.class);

                        Log.i(MyLog.WEB_SERVICE, "Ok : " + isOK.isOk());
                        Log.i(MyLog.WEB_SERVICE, "Code : " + isOK.getCode());
                        Log.i(MyLog.WEB_SERVICE, "Message : " + isOK.getMessage());

                        if (isOK.isOk()) {
                            handler.onResponse(true, response);
                        } else {
                            handler.onResponse(false, "");
                            if (isOK.getCode() == 401) {
                                Toast.makeText(context, isOK.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (isOK.getCode() == 402) {
                                Toast.makeText(context, isOK.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (isOK.getCode() == 403) {
                                Toast.makeText(context, isOK.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(MyLog.WEB_SERVICE, "VolleyError : " + error.getMessage());
                        requestAPI(url, method, handler);
                    }
                }));
    }

    @SuppressLint("LongLogTag")
    public void requestAPI(final String url, final int method, final HashMap<String, String> params, final MyHandler handler) {
        Log.i(MyLog.WEB_SERVICE, "Handler : ‌" + handler.toString());
        Log.i(MyLog.WEB_SERVICE, "URL : " + url);
        Log.i(MyLog.WEB_SERVICE, "Params : " + params.toString());

        queue.add(new StringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        isOK = gson.fromJson(new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), IsOK.class);

                        Log.i(MyLog.WEB_SERVICE, "Ok : " + isOK.isOk());
                        Log.i(MyLog.WEB_SERVICE, "Code : " + isOK.getCode());
                        Log.i(MyLog.WEB_SERVICE, "Message : " + isOK.getMessage());

                        if (isOK.isOk()) {
                            handler.onResponse(true, response);
                        } else {
                            handler.onResponse(false, "");
                            if (isOK.getCode() == 401) {
                                Toast.makeText(context, isOK.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (isOK.getCode() == 402) {
                                Toast.makeText(context, isOK.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (isOK.getCode() == 403) {
                                Toast.makeText(context, isOK.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(MyLog.WEB_SERVICE, "VolleyError : " + error.getMessage());
                        requestAPI(url, method, handler);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        });
    }
}
