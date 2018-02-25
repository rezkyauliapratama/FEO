package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.NotificationFragment;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.databinding.ListItemNotificationBinding;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 1/2/2018.
 */

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.ViewHolder> {
    Context mContext;
    List<NotificationTbl> mItems;
    NotificationFragment.OnListFragmentInteractionListener mListener;

    public NotificationRVAdapter(Context mContext, List<NotificationTbl> mItems, NotificationFragment.OnListFragmentInteractionListener mListener) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notification, parent, false);
        return new NotificationRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationTbl item = mItems.get(position);

        holder.binding.textViewTitle.setText(item.getTitle());
        holder.binding.textViewBody.setText(item.getBody());
        Date date = Utils.getInstance().time().parseDate(item.getCreatedDate());
        holder.binding.textViewDate.setText(Utils.getInstance().time().getUserFriendlyDuration(mContext,date));

        holder.binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteNotificationInteraction(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemNotificationBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemNotificationBinding.bind(itemView);
        }
    }
}
