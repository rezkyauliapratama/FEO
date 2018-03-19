package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.infideap.stylishwidget.view.ATextView;
import com.app.infideap.stylishwidget.view.Stylish;

import java.util.List;

/**
 * Created by InfiDeaP on 4/12/2015.
 */
public class SpinnerArrayAdapter<T> extends ArrayAdapter<T> {

    private static final String TAG = SpinnerArrayAdapter.class.getSimpleName();
    private int[] exclude;
    private int adjustment = 0;

    private int current = 0;
    private int currentDropDown = 0;

    List<T> t;

    public SpinnerArrayAdapter(Context context, List<T> objects, int... exclude) {
        super(context, R.layout.textview_spinner, objects);

        t = objects;
        this.exclude = exclude;
    }

    public SpinnerArrayAdapter(Context context, int layout, List<T> objects, int... exclude) {
        super(context, R.layout.textview_spinner, objects);

        t = objects;
        this.exclude = exclude;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = super.getView(position, null, parent);
        TextView textView = (TextView) convertView;
        if (!(convertView instanceof ATextView))
            textView.setTypeface(Stylish.getInstance().getRegular());
        textView.setId(1000 + position);
        if (position == 0) {
            textView.setTextColor(getContext().getResources()
                    .getColor(android.support.v7.appcompat.R.color.abc_hint_foreground_material_light));
        }

        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return convertView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = super.getDropDownView(position, null, parent);
        if (position == 0) {
            TextView textView = new TextView(getContext());
            textView.setHeight(0);
            textView.setVisibility(View.GONE);

            convertView = textView;
        }
        if (!(convertView instanceof ATextView))
            ((TextView) convertView).setTypeface(Stylish.getInstance().getRegular());

//        parent.setVerticalScrollBarEnabled(false);
        return convertView;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setExclude(int[] position) {
        this.exclude = position;

        notifyDataSetChanged();
    }


    @Override
    public void notifyDataSetChanged() {
        current = 0;
        currentDropDown = 0;

        super.notifyDataSetChanged();
    }

}

