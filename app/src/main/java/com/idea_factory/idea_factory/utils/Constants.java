package com.idea_factory.idea_factory.utils;

import com.idea_factory.idea_factory.model.ProductModel;
import com.idea_factory.idea_factory.model.SubCategoriesModel;

import java.util.ArrayList;

/**
 * Created by Admin on 26-07-2017.
 */

public class Constants {

    public static final String TAB_GLOBAL = "global";
    public static final String TAB_HOME = "home";
    public static String BASE_URL = "http://idea-factory.in/api/";
    public static ArrayList<ProductModel> arrayListProduct;
    public static ArrayList<SubCategoriesModel> arrayListSubCategory;
    public static String IMAGE_URL = "http://idea-factory.in/images/";
    public static String PDF_URL = "http://idea-factory.in/Idea.pdf";


    public static enum REQUESTS {
        GET, POST, PUT
    };


}
