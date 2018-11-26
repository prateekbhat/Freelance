package com.idea_factory.idea_factory.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.adapter.ProductSubCat_Adapter;

import static com.idea_factory.idea_factory.utils.Constants.arrayListSubCategory;


public class HomeFragment extends Fragment {
    View view;
    RecyclerView ProductList;
    ProductSubCat_Adapter _ProductAdapter;
    private static final AppCompatActivity ARG_PARAM1 = null;

    public HomeFragment() {
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment


        ProductList = (RecyclerView) view.findViewById(R.id.ProductList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ProductList.setLayoutManager(mLayoutManager);
        ProductList.setItemAnimator(new DefaultItemAnimator());


        if(arrayListSubCategory.size()!=0){
            _ProductAdapter = new ProductSubCat_Adapter(getActivity(),getActivity(), arrayListSubCategory);
            ProductList.setAdapter(_ProductAdapter);
        }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
