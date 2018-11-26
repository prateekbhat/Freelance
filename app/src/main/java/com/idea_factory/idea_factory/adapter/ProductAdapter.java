package com.idea_factory.idea_factory.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.fragments.ProductFragment;
import com.idea_factory.idea_factory.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.idea_factory.idea_factory.utils.Constants.IMAGE_URL;

/**
 * Created by Admin on 9/18/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Fragment fragment;
    Activity activity;
    ArrayList<ProductModel> productArrayList;
    int statusCreateViewHolder = 2;
    ArrayList<ProductAdapter> listSelectedItems = new ArrayList<>();

    private List<ProductModel> productModelListLocal = null;


    public ProductAdapter(Activity activity, Fragment fragment, ArrayList<ProductModel> productArrayList) {
        this.activity = activity;
        this.fragment = fragment;
        this.productArrayList = productArrayList;
        this.productModelListLocal = new ArrayList<>();
        productModelListLocal.addAll(productArrayList);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 2) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_category, parent, false);
        } else {
            view = LayoutInflater.from(activity).inflate(R.layout.item_category_list, parent, false);
        }
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ProductModel item = productArrayList.get(position);

        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        //viewHolder.getItemName().setText(item.getItemName());

        if (item.isVisibleStatus()) {
            viewHolder.getImgChecked().setVisibility(View.VISIBLE);
            viewHolder.selected_greyRel.setVisibility(View.VISIBLE);
        } else {
            viewHolder.getImgChecked().setVisibility(View.GONE);
            viewHolder.selected_greyRel.setVisibility(View.GONE);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new FitCenter(), new RoundedCorners(16));

        Glide.with(activity)
                .load(IMAGE_URL + item.getProduct_image())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.getProgressBar().setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.getProgressBar().setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(viewHolder.getItemImage());

        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductFragment) fragment).onSingleClick(viewHolder.getView(), item, position);
            }
        });


        viewHolder.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((ProductFragment) fragment).onLongClick(viewHolder.getView(), item, position);
                /*ArrayList<ImageView> list = new ArrayList<ImageView>();
                list.add(viewHolder.getItemImage());
                list.add(viewHolder.getItemImage());
                list.add(viewHolder.getItemImage());
                OnClickShare(activity,list);*/
                return true;
            }
        });

    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        ImageView itemImage, imgChecked;
        View view;
        ProgressBar progressBar;
        RelativeLayout selected_greyRel;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            progressBar = itemView.findViewById(R.id.progressBar);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            selected_greyRel = itemView.findViewById(R.id.selected_greyRel);
            view = itemView;
        }

       /* public TextView getItemName() {
            return itemName;
        }*/

        public ImageView getItemImage() {
            return itemImage;
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public ImageView getImgChecked() {
            return imgChecked;
        }

        public View getView() {
            return view;
        }
    }


    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productArrayList.clear();
        if (charText.length() == 0) {
            productArrayList.addAll(productModelListLocal);
        } else {
            for (ProductModel productModel : productModelListLocal) {
                if (productModel.getSub_category_name().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    productArrayList.add(productModel);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateCreateViewHolder(int statusCreateViewHolder) {
        this.statusCreateViewHolder = statusCreateViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return (statusCreateViewHolder == 2 ? 2 : 1);
    }


}
