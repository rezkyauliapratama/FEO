package android.rezkyaulia.com.feo.observer;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class RxBus implements EventBusInterface {

    private static class SingletonHolder{
        public static RxBus singletonInstance =
                new RxBus();
    }
    // SingletonExample prevents any other class from instantiating
    private RxBus() {
    }

    // Providing Global point of access
    @Contract(pure = true)
    public static RxBus getInstance() {
        return SingletonHolder.singletonInstance;
    }

    private final Subject<Object> mBusSubject = PublishSubject.create();


    @Override
    public void post(@NonNull Object event) {
        if (this.mBusSubject.hasObservers()) {
            this.mBusSubject.onNext(event);
        }
    }

    @Override
    public <T> Observable<T> observable(@NonNull Class<T> eventClass) {
        return this.mBusSubject
                .filter(o -> o != null) // Filter out null objects, better safe than sorry
                .filter(eventClass::isInstance) // We're only interested in a specific event class
                .cast(eventClass); // Cast it for easier usage
    }


}
