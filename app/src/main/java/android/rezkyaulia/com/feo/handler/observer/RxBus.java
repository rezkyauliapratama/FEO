package android.rezkyaulia.com.feo.handler.observer;

import android.support.annotation.NonNull;
import android.widget.ImageButton;

import org.jetbrains.annotations.Contract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;


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
    public void complete() {
        if (this.mBusSubject.hasObservers()) {
            this.mBusSubject.onComplete();
        }
    }

    @Override
    public <T> Observable<T> observable(@NonNull Class<T> eventClass) {
        return this.mBusSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(o -> o != null) // Filter out null objects, better safe than sorry
                .filter(eventClass::isInstance) // We're only interested in a specific event class
                .cast(eventClass); // Cast it for easier usage
    }








}
