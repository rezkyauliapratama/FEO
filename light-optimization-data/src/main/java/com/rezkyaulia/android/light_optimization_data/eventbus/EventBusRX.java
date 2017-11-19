package com.rezkyaulia.android.light_optimization_data.eventbus;

import java.lang.reflect.Type;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Mutya Nayavashti on 02/01/2017.
 */

public class EventBusRX<T> {
    private final Subject<T, T> subject;

    private static EventBusRX mInstance;
    private Type type;

    public static EventBusRX getInstance() {
        if (mInstance == null) {
            mInstance = new EventBusRX();
        }
        return mInstance;
    }

    public EventBusRX() {
        this(PublishSubject.<T>create());
    }

    public EventBusRX(Subject<T, T> subject) {
        this.subject = subject;
        this.type = subject.getClass();
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