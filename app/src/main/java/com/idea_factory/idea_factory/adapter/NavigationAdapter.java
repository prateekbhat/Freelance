package com.idea_factory.idea_factory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.activities.DashBoardListenerInteractor;
import com.idea_factory.idea_factory.model.SubCategoriesModel;

import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyViewHolder> {

    private List<SubCategoriesModel> subCategoriesModelList;
    private DashBoardListenerInteractor mDashBoardListenerInteractor;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RelativeLayout item;
        public ImageView imageViewIcon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.lblListItem);
            item = (RelativeLayout) view.findViewById(R.id.rel);
            imageViewIcon = (ImageView) view.findViewById(R.id.ic_image);


        }
    }


    public NavigationAdapter(List<SubCategoriesModel> subCategoriesModelList, DashBoardListenerInteractor mDashBoardListenerInteractor
    , Context context) {
        if (this.subCategoriesModelList != null && subCategoriesModelList.size() > 0) {
            this.subCategoriesModelList.clear();
        }
        this.subCategoriesModelList = subCategoriesModelList;
        this.mDashBoardListenerInteractor = mDashBoardListenerInteractor;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group_child, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SubCategoriesModel menuName = subCategoriesModelList.get(position);
        holder.title.setText(menuName.getSub_category_name().replace("&amp;", "&"));

        Glide.with(context)
                .load("http://idea-factory.in/images/" + menuName.getSub_category_icon())
                .into(holder.imageViewIcon);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDashBoardListenerInteractor.hideNavigation(menuName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoriesModelList.size();
    }
}
