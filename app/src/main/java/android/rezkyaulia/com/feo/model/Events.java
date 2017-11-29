package android.rezkyaulia.com.feo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/29/2017.
 */

public class Events <T> {
    final Class<T> typeParameterClass;

    public Events(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public List<T> ListValue=new ArrayList<>();

    public Class<T> getTypeParameterClass(){
        return  typeParameterClass;
    }
}
