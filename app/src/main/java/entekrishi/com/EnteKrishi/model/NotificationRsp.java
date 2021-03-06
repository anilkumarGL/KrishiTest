package entekrishi.com.EnteKrishi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Santhosh.Joseph on 08-08-2015.
 */
public class NotificationRsp extends ModelClassMapper  {
    public String type;
    public String msg;
    public String count;
    public String unread;
    @SerializedName("new")
    public int isNew;
    @SerializedName("items")
    public ArrayList<Product> listofProducts;
    //public ProductListing listofProducts;
    //public String items;
}
