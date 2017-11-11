package com.rezkyaulia.android.light_optimization_data.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Mutya Nayavashti on 02/01/2017.
 */

public class EventBus<T> {
    private final Subject<T, T> subject;

    private static EventBus mInstance;

    public static EventBus getInstance() {
        if (mInstance == null) {
            mInstance = new EventBus();
        }
        return mInstance;
    }

    public EventBus() {
        this(PublishSubject.<T>create());
    }

    public EventBus(Subject<T, T> subject) {
        this.subject = subject;
    }

    public <E extends T> void post(E event) {
        subject.onNext(event);
    }

    public Observable<T> observe() {
        return subject;
    }

    public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
        return subject.ofType(eventClass);//pass only events of specified type, filter all other
    }
}