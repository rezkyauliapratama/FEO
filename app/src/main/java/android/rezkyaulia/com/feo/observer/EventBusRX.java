package android.rezkyaulia.com.feo.observer;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Mutya Nayavashti on 02/01/2017.
 */

public class EventBusRX<T> {
    private final Subject<T> subject;

    private static EventBusRX mInstance;

    public static EventBusRX getInstance() {
        if (mInstance == null) {
            mInstance = new EventBusRX();
        }
        return mInstance;
    }

    public EventBusRX() {
        this(PublishSubject.<T>create());
    }

    public EventBusRX(Subject<T> subject) {
        this.subject = subject;
    }

    public <E extends T> void post(E event) {
            subject.onNext(event);
    }

    public Observable<T> observe() {
        return subject;
    }

    public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
        /*subject. filter(o -> o != null) // Filter out null objects, better safe than sorry
                .filter(eventClass::isInstance) // We're only interested in a specific event class
                .cast(eventClass); // Cast it for easier usage*/
        return subject.ofType(eventClass);//pass only events of specified type, filter all other
    }
}