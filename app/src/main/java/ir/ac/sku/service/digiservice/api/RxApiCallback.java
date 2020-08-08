package ir.ac.sku.service.digiservice.api;

public interface RxApiCallback<T> {

    void onSuccess(T t);

    void onFailed(String errorMsg);
}