package android.rezkyaulia.com.feo.controller.adapter;

import android.graphics.Color;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.LanguagesFragment;
import android.rezkyaulia.com.feo.model.Language;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.infideap.stylishwidget.util.Utils;

import java.util.List;

public class LanguagesRecyclerViewAdapter extends RecyclerView.Adapter<LanguagesRecyclerViewAdapter.ViewHolder> {

    private final List<Language> mValues;
    private final LanguagesFragment.OnListFragmentInteractionListener mListener;

    public LanguagesRecyclerViewAdapter(List<Language> items, LanguagesFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_languages2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).text);

        if (holder.mItem.tick) {
            int tempColor = ContextCompat.getColor(holder.mView.getContext(), R.color.colorPrimary);

            int color = Utils.adjustAlpha(tempColor, 50);

            holder.mView.setBackgroundColor(color);
            holder.mIdView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_grey_24dp,0);
        }else{

            holder.mView.setBackgroundColor(Color.TRANSPARENT);
            holder.mIdView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Language mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
