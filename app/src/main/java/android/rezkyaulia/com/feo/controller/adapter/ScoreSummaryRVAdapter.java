package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.databinding.ListItemSummaryBinding;
import android.rezkyaulia.com.feo.databinding.ListItemSummaryScoresBinding;
import android.rezkyaulia.com.feo.utility.Constant;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/23/2017.
 */

public class ScoreSummaryRVAdapter extends RecyclerView.Adapter<ScoreSummaryRVAdapter.ViewHolder> {
    Context mContext;
    List<ScoreTbl> mItems;


    private int lastPosition = -1;
    private int animationCount = 0;

    public ScoreSummaryRVAdapter(Context mContext, List<ScoreTbl> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_summary_scores, parent, false);
        return new ScoreSummaryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScoreTbl item = mItems.get(position);

//        holder.binding.textViewNumber.setText(String.valueOf(position+1));
        if (item.getFlagAnswer() == Constant.getInstance().FLAG_ANSWER_FALSE) {
//            holder.binding.imageViewResult.setAnimation("animation/x_pop.json");
            holder.binding.imageViewResult.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_cross_mark));
        }else {
            holder.binding.imageViewResult.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_correct_symbol));
//            holder.binding.imageViewResult.setAnimation("animation/check.json");
        }

        holder.binding.textViewCorrectAnswer.setText(item.getCorrectAnswer());
        holder.binding.textViewYourAnswer.setText(item.getAnswer());
        holder.binding.textViewWpm.setText(String.valueOf(item.getScore()));
        holder.binding.textViewWpm.setText(String.valueOf(item.getScore()));

        Date date = Utils.getInstance().time().parseDate(item.getCreatedDate());
        String datestr = Utils.getInstance().time().getUserFriendlyDateWithoutYear(date);
        String time = Utils.getInstance().time().getTimeString(date);

        holder.binding.textViewDate.setText(datestr +"\n" + time);
        setAnimation(holder.binding.getRoot(),position);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemSummaryScoresBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemSummaryScoresBinding.bind(itemView);

        }
    }


    private void setAnimation(final View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
        final Animation animation = AnimationUtils.loadAnimation(
                viewToAnimate.getContext(), android.R.anim.slide_in_left);
        animationCount++;
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationCount--;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(300);
        viewToAnimate.setVisibility(View.GONE);
        viewToAnimate.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewToAnimate.setVisibility(View.VISIBLE);
                viewToAnimate.startAnimation(animation);

            }
        }, animationCount * 100);
        lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.binding.getRoot().setVisibility(View.VISIBLE);
        holder.binding.getRoot().clearAnimation();
    }

}
