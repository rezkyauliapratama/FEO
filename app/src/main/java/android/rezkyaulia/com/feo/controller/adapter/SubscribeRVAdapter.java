package android.rezkyaulia.com.feo.controller.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ListItemPlanBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.api.Api;

import java.util.List;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/23/2017.
 */

public class SubscribeRVAdapter extends RecyclerView.Adapter<SubscribeRVAdapter.ViewHolder> {
    Context mContext;
    List<PlanTbl> mItems;

    private int lastPosition = -1;
    private int animationCount = 0;

    OnAdapterInteractionListener mListener;
    public SubscribeRVAdapter(Context mContext, List<PlanTbl> mItems, OnAdapterInteractionListener mListener) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mListener = mListener;
    }

    @Override
    public SubscribeRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_plan, parent, false);
        return new SubscribeRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscribeRVAdapter.ViewHolder holder, int position) {
        PlanTbl item = mItems.get(position);

        holder.binding.textviewTitle.setText(item.getPlanName());
        holder.binding.textviewPrice.setText(mContext.getString(R.string.price)+item.getPrice());

//            Bitmap bitmap = ApiClient.getInstance().plan().getImage(mContext,item.getImageName());
        Timber.e("path image : "+ApiClient.getInstance().plan().pathImage.concat(item.getImageName()));
            Glide.with(mContext)
                    .load(ApiClient.getInstance().plan().pathImage.concat(item.getImageName()))
                    .error(R.drawable.ic_error_sing)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.binding.imageView);


        holder.binding.textViewDesc.setText(item.getPlanDesc());

        holder.binding.buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListInteraction(item);
            }
        });
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

    public interface OnAdapterInteractionListener {
        // TODO: Update argument type and name

        void onListInteraction(PlanTbl planTbl);

    }


}
