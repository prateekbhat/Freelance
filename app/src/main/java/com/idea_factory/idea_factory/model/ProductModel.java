package com.idea_factory.idea_factory.model;

public class ProductModel {
    private String product_searching;

    private String status;

    private String product_price;

    private String sub_category_name;

    private String product_desc;

    private String seo_title;

    private String collection_name;

    private String id;

    private String seo_description;

    private String arrival_product;

    private String category_name;

    private String product_code;

    private String seo_keywords;

    private String product_image;

    private boolean visibleStatus = false;


    public boolean isVisibleStatus() {
        return visibleStatus;
    }

    public void setVisibleStatus(boolean visibleStatus) {
        this.visibleStatus = visibleStatus;
    }

    public String getProduct_searching ()
    {
        return product_searching;
    }

    public void setProduct_searching (String product_searching)
    {
        this.product_searching = product_searching;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getProduct_price ()
    {
        return product_price;
    }

    public void setProduct_price (String product_price)
    {
        this.product_price = product_price;
    }

    public String getSub_category_name ()
    {
        return sub_category_name;
    }

    public void setSub_category_name (String sub_category_name)
    {
        this.sub_category_name = sub_category_name;
    }

    public String getProduct_desc ()
    {
        return product_desc;
    }

    public void setProduct_desc (String product_desc)
    {
        this.product_desc = product_desc;
    }

    public String getSeo_title ()
    {
        return seo_title;
    }

    public void setSeo_title (String seo_title)
    {
        this.seo_title = seo_title;
    }

    public String getCollection_name ()
    {
        return collection_name;
    }

    public void setCollection_name (String collection_name)
    {
        this.collection_name = collection_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getSeo_description ()
    {
        return seo_description;
    }

    public void setSeo_description (String seo_description)
    {
        this.seo_description = seo_description;
    }

    public String getArrival_product ()
    {
        return arrival_product;
    }

    public void setArrival_product (String arrival_product)
    {
        this.arrival_product = arrival_product;
    }

    public String getCategory_name ()
    {
        return category_name;
    }

    public void setCategory_name (String category_name)
    {
        this.category_name = category_name;
    }

    public String getProduct_code ()
    {
        return product_code;
    }

    public void setProduct_code (String product_code)
    {
        this.product_code = product_code;
    }

    public String getSeo_keywords ()
    {
        return seo_keywords;
    }

    public void setSeo_keywords (String seo_keywords)
    {
        this.seo_keywords = seo_keywords;
    }

    public String getProduct_image ()
    {
        return product_image;
    }

    public void setProduct_image (String product_image)
    {
        this.product_image = product_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [product_searching = "+product_searching+", status = "+status+", product_price = "+product_price+", sub_category_name = "+sub_category_name+", product_desc = "+product_desc+", seo_title = "+seo_title+", collection_name = "+collection_name+", id = "+id+", seo_description = "+seo_description+", arrival_product = "+arrival_product+", category_name = "+category_name+", product_code = "+product_code+", seo_keywords = "+seo_keywords+", product_image = "+product_image+"]";
    }
}

