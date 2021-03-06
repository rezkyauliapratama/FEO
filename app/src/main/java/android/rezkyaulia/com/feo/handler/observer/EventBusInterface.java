package android.rezkyaulia.com.feo.handler.observer;

import android.support.annotation.NonNull;

import io.reactivex.Observable;


/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public interface EventBusInterface {
    void post(@NonNull Object event);
    void complete();

    <T> Observable<T> observable(@NonNull Class<T> eventClass);
}
