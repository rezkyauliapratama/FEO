package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.LibraryFragment;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.ListItemLibraryBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/11/2017.
 */

public class LibraryRVAdapter extends RecyclerView.Adapter<LibraryRVAdapter.ViewHolder> {
    List<LibraryTbl> mItems;
    Context mContext;
    LibraryFragment.OnListFragmentInteractionListener mListener;

    public LibraryRVAdapter(Context mContext, List<LibraryTbl> mItems, LibraryFragment.OnListFragmentInteractionListener mListener) {
        this.mItems = mItems;
        this.mContext = mContext;
        this.mListener = mListener;
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

        if (item.getReadFlag() == 1){
            Timber.e("FLAG : "+new Gson().toJson(item));
            holder.binding.cardview.setBackgroundColor(ContextCompat.getColor(mContext, (R.color.colorGrey_100)));
            holder.binding.lottieview.setVisibility(View.VISIBLE);
            holder.binding.lottieview.playAnimation();
        }else{
            holder.binding.cardview.setBackgroundColor(ContextCompat.getColor(mContext, (R.color.colorWhite)));

            holder.binding.lottieview.setVisibility(View.GONE);
        }
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.e("Onclick : "+new Gson().toJson(item));
                mListener.onListFragmentInteraction(item);
            }
        });
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
