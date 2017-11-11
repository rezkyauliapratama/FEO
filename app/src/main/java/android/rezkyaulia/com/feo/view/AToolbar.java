package android.rezkyaulia.com.feo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.infideap.stylishwidget.view.Stylish;

/**
 * Created by Shiburagi on 01/06/2017.
 */

public class AToolbar extends Toolbar {
    public AToolbar(Context context) {
        super(context);
    }

    public AToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof TextView) {
            ((TextView) child).setTypeface(Stylish.getInstance().getRegular());
        }
        super.addView(child, index, params);


    }
}
