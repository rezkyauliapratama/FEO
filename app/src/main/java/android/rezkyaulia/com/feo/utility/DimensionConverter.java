package android.rezkyaulia.com.feo.utility;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shiburagi on 01/01/2017.
 */
public class DimensionConverter {

    // Step 1: private static variable of INSTANCE variable
    private static volatile DimensionConverter INSTANCE;

    // Step 2: private constructor
    private DimensionConverter() {

    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static DimensionConverter getInstance() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (DimensionConverter.class) {
                if(null == INSTANCE){
                    INSTANCE = new DimensionConverter();
                }
            }
        }
        return INSTANCE;
    }

    // -- Initialize dimension string to constant lookup.
    public final Map<String, Integer> dimensionConstantLookup = initDimensionConstantLookup();

    private Map<String, Integer> initDimensionConstantLookup() {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("px", TypedValue.COMPLEX_UNIT_PX);
        m.put("dip", TypedValue.COMPLEX_UNIT_DIP);
        m.put("dp", TypedValue.COMPLEX_UNIT_DIP);
        m.put("sp", TypedValue.COMPLEX_UNIT_SP);
        m.put("pt", TypedValue.COMPLEX_UNIT_PT);
        m.put("in", TypedValue.COMPLEX_UNIT_IN);
        m.put("mm", TypedValue.COMPLEX_UNIT_MM);
        return Collections.unmodifiableMap(m);
    }

    // -- Initialize pattern for dimension string.
    private final Pattern DIMENSION_PATTERN = Pattern.compile("^\\-?\\s*(\\d+(\\.\\d+)*)\\s*([a-zA-Z]+)\\s*");

    public int stringToDimensionPixelSize(String dimension, DisplayMetrics metrics) {
        // -- Mimics TypedValue.complexToDimensionPixelSize(int data, DisplayMetrics metrics).
        InternalDimension internalDimension = stringToInternalDimension(dimension);
        final float value = internalDimension.value;
        final float f = TypedValue.applyDimension(internalDimension.unit, value, metrics);
        final int res = (int) (f + 0.5f);
        if (res != 0) return res;
        if (value == 0) return 0;
        if (value > 0) return 1;
        return -1;
    }

    public float stringToDimension(String dimension, DisplayMetrics metrics) {
        // -- Mimics TypedValue.complexToDimension(int data, DisplayMetrics metrics).
        try {
            InternalDimension internalDimension = stringToInternalDimension(dimension);
            return TypedValue.applyDimension(internalDimension.unit, internalDimension.value, metrics);
        } catch (Exception e) {
            return 0;
        }
    }

    private InternalDimension stringToInternalDimension(String dimension) {
        // -- Match target against pattern.
        Matcher matcher = DIMENSION_PATTERN.matcher(dimension);
        if (matcher.matches()) {
            // -- Match found.
            // -- Extract value.
            float value = Float.valueOf(matcher.group(1)).floatValue();
            // -- Extract dimension units.
            String unit = matcher.group(3).toLowerCase();
            // -- Get Android dimension constant.
            Integer dimensionUnit = dimensionConstantLookup.get(unit);
            if (dimensionUnit == null) {
                // -- Invalid format.
                throw new NumberFormatException();
            } else {
                // -- Return valid dimension.
                return new InternalDimension(value, dimensionUnit);
            }
        } else {
            // -- Invalid format.
            throw new NumberFormatException();
        }
    }


    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private class InternalDimension {
        float value;
        int unit;

        public InternalDimension(float value, int unit) {
            this.value = value;
            this.unit = unit;
        }
    }

}
