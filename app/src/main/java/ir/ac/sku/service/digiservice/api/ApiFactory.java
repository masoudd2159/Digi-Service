package ir.ac.sku.service.digiservice.api;

import com.google.gson.GsonBuilder;

import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.MyApplication;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiFactory {

    private static Retrofit provideApi() {
        return new Retrofit.Builder()
                .baseUrl(MyAPI.BASE_URL)
                .client(MyApplication.getAppInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static <S> S createProvideApiService(Class<S> serviceClass) {
        return provideApi().create(serviceClass);
    }
}
