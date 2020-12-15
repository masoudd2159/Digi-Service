package ir.ac.sku.service.digiservice.api;

import android.util.Log;

import ir.ac.sku.service.digiservice.config.MyLog;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxApiCallHelper {

    public static <T> Subscription call(Observable<T> observable, final RxApiCallback<T> rxApiCallback) {
        if (observable == null) {
            throw new IllegalArgumentException("Observable must not be null.");
        }

        if (rxApiCallback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
                .subscribe(t -> {
                    rxApiCallback.onSuccess(t);
                    Log.i(MyLog.DIGI_SERVICE, t.toString());
                }, throwable -> {
                    rxApiCallback.onFailed(throwable.getMessage());
                    Log.i(MyLog.DIGI_SERVICE, throwable.getMessage());
                });
    }
}
