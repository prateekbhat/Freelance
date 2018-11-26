package com.idea_factory.idea_factory.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.activities.ImagePreviewActivity;
import com.idea_factory.idea_factory.adapter.ProductAdapter;
import com.idea_factory.idea_factory.decorder.SpacesItemDecoration;
import com.idea_factory.idea_factory.model.ProductModel;

import java.util.ArrayList;
import java.util.Locale;

import static com.idea_factory.idea_factory.utils.Constants.IMAGE_URL;
import static com.idea_factory.idea_factory.utils.Constants.arrayListProduct;
import static com.idea_factory.idea_factory.utils.GeneralUtils.OnClickShare;

public class ProductFragment extends Fragment implements View.OnClickListener {

    View view;

    Context context;

    ImageButton gridSelectBtn, singleSelectBtn, shareBtn;

    RecyclerView recyclerView;

    ProductAdapter categoryAdapter;

    GridLayoutManager gridLayoutManager;

    ArrayList<ProductModel> listSelectedItems = new ArrayList<>();

    ArrayList<View> listSelectedViews = new ArrayList<>();

    ArrayList<ImageView> listSelectedImageViews = new ArrayList<>();
    ArrayList arrayListCategory = new ArrayList<ProductModel>();

    boolean isMultiSelect = false;

    EditText editSearch;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        String categoryName = getArguments().getString("data");


        TextView textViewTitle = getActivity().findViewById(R.id.trending);
        textViewTitle.setText(categoryName.replace("&amp;", "&"));

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_product, container, false);
        }

        context = getActivity();

        //((DashBoardActivity) getActivity()).setActionBarTitle(categoryName);


        /*Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_dashboard));
        getActivity().setActionBar(toolbar);*/


        gridSelectBtn = view.findViewById(R.id.gridSelected);
        singleSelectBtn = view.findViewById(R.id.singleSelected);
        editSearch = view.findViewById(R.id.search);
        shareBtn = view.findViewById(R.id.share);


        gridSelectBtn.setEnabled(false);

        gridSelectBtn.setOnClickListener(this);
        singleSelectBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);


        ArrayList<ProductModel> tempProductArrayList = new ArrayList<>();


        for (int i = 0; i < arrayListProduct.size(); i++) {
            if (arrayListProduct.get(i).getSub_category_name().equals(categoryName)) {
                tempProductArrayList.add(arrayListProduct.get(i));
            }
        }

        categoryAdapter = new ProductAdapter(getActivity(), ProductFragment.this, tempProductArrayList);
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(categoryAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);

        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, 2));

        // Capture Text in EditText
        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                categoryAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void gridSelectFunction() {
        if (gridSelectBtn.isEnabled()) {
            gridSelectBtn.setEnabled(false);
            singleSelectBtn.setEnabled(true);
            gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);


            categoryAdapter.updateCreateViewHolder(2);
            categoryAdapter.notifyDataSetChanged();
            //categoryAdapter.resetAdapter(listSelectedItems);

        } else {
            gridSelectBtn.setEnabled(true);
            singleSelectBtn.setEnabled(false);
        }

    }

    public void singleSelectFunction() {
        if (singleSelectBtn.isEnabled()) {
            singleSelectBtn.setEnabled(false);
            gridSelectBtn.setEnabled(true);
            gridLayoutManager = new GridLayoutManager(context, 1);
            recyclerView.setLayoutManager(gridLayoutManager);


            categoryAdapter.updateCreateViewHolder(1);
            categoryAdapter.notifyDataSetChanged();
            //categoryAdapter.resetAdapter(listSelectedItems);


        } else {
            singleSelectBtn.setEnabled(true);
            gridSelectBtn.setEnabled(false);
        }
    }

    public void onSingleClick(View view, ProductModel productModel, int position) {
        if (!isMultiSelect) {
            startActivity(new Intent(context, ImagePreviewActivity.class).putExtra("url",
                    (IMAGE_URL + productModel.getProduct_image()))
                    .putExtra("title", productModel.getSeo_title()));
        } else {
            multiSelect(view, productModel, position);
        }
    }

    public void onLongClick(View view, ProductModel productModel, int position) {
        if (!isMultiSelect) {
            listSelectedItems.clear();
            listSelectedViews.clear();
            listSelectedImageViews.clear();
            isMultiSelect = true;
            multiSelect(view, productModel, position);
        } else {
           /* for (View views : listSelectedViews) {
                ImageView imageView = views.findViewById(R.id.imgChecked);
                imageView.setVisibility(View.GONE);
            }
            listSelectedItems.clear();
            listSelectedViews.clear();
            isMultiSelect = false;*/
        }


    }


    public void multiSelect(View view, ProductModel productModel, int position) {

        ImageView imageView = view.findViewById(R.id.imgChecked);
        RelativeLayout selected_greyRel = view.findViewById(R.id.selected_greyRel);

        if (listSelectedItems.contains(productModel)) {
            listSelectedItems.remove(productModel);
            listSelectedViews.remove(view);
            listSelectedImageViews.remove(view);
            listSelectedImageViews.remove(imageView);
            listSelectedImageViews.remove(selected_greyRel);
            productModel.setVisibleStatus(false);
            imageView.setVisibility(View.GONE);
            selected_greyRel.setVisibility(View.GONE);
            if (listSelectedItems.size() == 0) {
                isMultiSelect = false;
            }
        } else {
            ImageView MainimageView = view.findViewById(R.id.itemImage);
            listSelectedItems.add(productModel);
            listSelectedViews.add(view);
            listSelectedImageViews.add(MainimageView);
            productModel.setVisibleStatus(true);
            imageView.setVisibility(View.VISIBLE);
            selected_greyRel.setVisibility(View.VISIBLE);
        }

    }

    public void shareFunction() {
        if (listSelectedItems.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(view, getString(R.string.share_error), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }else{
            OnClickShare(getActivity(),listSelectedImageViews);


        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.gridSelected:

                gridSelectFunction();

                break;

            case R.id.singleSelected:

                singleSelectFunction();

                break;

            case R.id.share:

                shareFunction();

                break;

        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
