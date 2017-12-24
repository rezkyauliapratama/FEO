package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.databinding.ListItemPlanBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 12/23/2017.
 */

public class SubscribeRBAdapter extends RecyclerView.Adapter<SubscribeRBAdapter.ViewHolder> {
    Context mContext;
    List<PlanTbl> mItems;

    private int lastPosition = -1;
    private int animationCount = 0;

    public SubscribeRBAdapter(Context mContext, List<PlanTbl> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public SubscribeRBAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_plan, parent, false);
        return new SubscribeRBAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscribeRBAdapter.ViewHolder holder, int position) {
        PlanTbl item = mItems.get(position);

        holder.binding.textviewTitle.setText(item.getPlanName());
        holder.binding.textviewPrice.setText("Price : "+item.getPrice());


        Glide.with(mContext)
                .load(item.getImageName())
                .asBitmap()
                .error(R.drawable.ic_error_sing)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.binding.imageView);

        holder.binding.textViewDesc.setText(item.getPlanDesc());

        setAnimation(holder.binding.getRoot(),position);

    }

    @Override
    public int getItemCount() {
        if (mItems == null){
            return 0;
        }else{
            return mItems.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemPlanBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemPlanBinding.bind(itemView);

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
