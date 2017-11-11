package android.rezkyaulia.com.feo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.app.infideap.stylishwidget.view.ATextView;
import com.app.infideap.stylishwidget.view.Stylish;
//import com.app.c;


/**
 * Created by Shiburagi on 05/06/2017.
 */

public class ATabLayout extends TabLayout {
    public ATabLayout(Context context) {
        super(context);
    }

    public ATabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ATabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        changeFont(tab);
        super.addTab(tab, position, setSelected);
    }

    private void changeFont(Tab tab) {
        if (tab.getText() != null) {
            LinearLayout layout = new LinearLayout(getContext());
//            layout.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            ATextView newTab = new ATextView(getContext());
//            newTab.setSupportTextAppearance(R.style.TextAppearance_Design_Tab);
            newTab.setTypeface(Stylish.getInstance().getBold());
            newTab.setAllCaps(true);
            newTab.setGravity(Gravity.CENTER);
            newTab.setTextColor(getTabTextColors());
            newTab.setMaxLines(1);
            newTab.setEllipsize(TextUtils.TruncateAt.END);
            newTab.setText(tab.getText());

            layout.addView(newTab);


            tab.setCustomView(layout);
        }
    }

}
