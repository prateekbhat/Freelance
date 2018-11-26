package com.idea_factory.idea_factory.model;

public class SubCategoriesModel {
    private String id;

    private String sub_category_icon;

    private String seo_description;

    private String category_name;

    private String status;

    private String sub_category_image;

    private String sub_category_name;

    private String seo_keywords;

    private String seo_content;

    private String seo_title;

    private String search_title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_category_icon() {
        return sub_category_icon;
    }

    public void setSub_category_icon(String sub_category_icon) {
        this.sub_category_icon = sub_category_icon;
    }

    public String getSeo_description() {
        return seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub_category_image() {
        return sub_category_image;
    }

    public void setSub_category_image(String sub_category_image) {
        this.sub_category_image = sub_category_image;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getSeo_keywords() {
        return seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_content() {
        return seo_content;
    }

    public void setSeo_content(String seo_content) {
        this.seo_content = seo_content;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getSearch_title() {
        return search_title;
    }

    public void setSearch_title(String search_title) {
        this.search_title = search_title;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", sub_category_icon = " + sub_category_icon + ", seo_description = " + seo_description + ", category_name = " + category_name + ", status = " + status + ", sub_category_image = " + sub_category_image + ", sub_category_name = " + sub_category_name + ", seo_keywords = " + seo_keywords + ", seo_content = " + seo_content + ", seo_title = " + seo_title + ", search_title = " + search_title + "]";
    }
}


