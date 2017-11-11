package android.rezkyaulia.com.feo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Alessandro on 03/03/2017.
 * http://stackoverflow.com/questions/40494623/android-imageview-169
 */

public class AImageView extends ImageView {
    public AImageView(Context context) {
        super(context);
    }

    public AImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();

        //force a 16:9 aspect ratio
        int height = Math.round(width * .5625f);
        setMeasuredDimension(width, height);
    }

}
