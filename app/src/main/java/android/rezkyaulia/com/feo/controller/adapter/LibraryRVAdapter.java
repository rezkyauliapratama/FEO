package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.ListItemLibraryBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/11/2017.
 */

public class LibraryRVAdapter extends RecyclerView.Adapter<LibraryRVAdapter.ViewHolder> {
    List<LibraryTbl> mItems;
    Context mContext;

    public LibraryRVAdapter(Context mContext,List<LibraryTbl> mItems) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_library, parent, false);
        return new LibraryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LibraryTbl item = mItems.get(position);
        holder.binding.textviewTitle.setText(item.getTitle());
        holder.binding.textviewAuthor.setText(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemLibraryBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemLibraryBinding.bind(itemView);
        }
    }
}
