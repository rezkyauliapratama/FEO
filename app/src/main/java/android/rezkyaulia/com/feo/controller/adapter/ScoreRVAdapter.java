package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.databinding.ListItemSummaryBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/23/2017.
 */

public class ScoreRVAdapter extends RecyclerView.Adapter<ScoreRVAdapter.ViewHolder> {
    Context mContext;
    List<ScoreTbl> mItems;

    public ScoreRVAdapter(Context mContext, List<ScoreTbl> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_summary, parent, false);
        return new ScoreRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScoreTbl item = mItems.get(position);

//        holder.binding.textViewNumber.setText(String.valueOf(position+1));
        if (item.getScore() == 0)
            holder.binding.imageViewResult.setAnimation("animation/x_pop.json");
        else
            holder.binding.imageViewResult.setAnimation("animation/check.json");


        holder.binding.textViewCorrectAnswer.setText(item.getCorrectAnswer());
        holder.binding.textViewYourAnswer.setText(item.getAnswer());
        holder.binding.textViewWpm.setText(String.valueOf(item.getScore()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemSummaryBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemSummaryBinding.bind(itemView);

        }
    }
}
