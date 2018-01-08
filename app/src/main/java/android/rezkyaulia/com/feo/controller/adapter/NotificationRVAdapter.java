package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.databinding.ListItemNotificationBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 1/2/2018.
 */

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.ViewHolder> {
    Context mContext;
    List<NotificationTbl> mItems;

    public NotificationRVAdapter(Context mContext, List<NotificationTbl> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
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
        holder.binding.textViewDate.setText(item.getCreatedDate());

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
