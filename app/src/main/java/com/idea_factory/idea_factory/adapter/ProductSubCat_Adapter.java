package com.idea_factory.idea_factory.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.idea_factory.idea_factory.model.SubCategoriesModel;
import com.idea_factory.idea_factory.utils.Constants;
import com.idea_factory.idea_factory.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductSubCat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static Context context;
    private static Activity act;
    private List<SubCategoriesModel> Product_list;
    private ArrayList<SubCategoriesModel> arraylist;
    private GeneralUtils mGeneralUtils;

    public ProductSubCat_Adapter(Activity activity,Context _context, List<SubCategoriesModel> chatList) {
        this.Product_list = chatList;
        this.arraylist = new ArrayList<SubCategoriesModel>();
        this.arraylist.addAll(chatList);
        this.context = _context;
        this.act=activity;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcat_product_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ViewHolder myViewHolder = (ViewHolder) holder;
            final SubCategoriesModel modelPending = Product_list.get(position);

            mGeneralUtils = new GeneralUtils(((AppCompatActivity)context));
            //Picasso.get().load("http://idea-factory.in/images/15-55one.jpg").fit().into(myViewHolder.productimage);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new FitCenter(), new RoundedCorners(2));

            Glide.with(act)
                    .load("http://idea-factory.in/images/" + modelPending.getSub_category_image())
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //viewHolder.getProgressBar().setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            //viewHolder.getProgressBar().setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(myViewHolder.productimage);



            myViewHolder.productimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("data", modelPending.getSub_category_name());
                    ProductFragment mProductFragment=  new ProductFragment();
                    mProductFragment.setArguments(bundle);
                    mGeneralUtils.moveFragmentForDashBoard(mProductFragment, Constants.TAB_GLOBAL,R.id.frame);
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return Product_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Product_list.size();
    }


    public int ManageProducts_filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            Product_list.clear();
            if (charText.length() == 0) {
                Product_list.addAll(arraylist);
            } else {
                for (SubCategoriesModel wp : arraylist) {
                    if (wp.getCategory_name().toLowerCase().contains(charText)) {
                        Product_list.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        } catch (Exception ex) {
        }
        return Product_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productimage;

        public ViewHolder(View view) {
            super(view);

            productimage = (ImageView) view.findViewById(R.id.productimage);

        }
    }
}