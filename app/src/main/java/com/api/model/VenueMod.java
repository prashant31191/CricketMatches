package com.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prashant.patel on 8/28/2017.
 */

public class VenueMod {


    @SerializedName("name")
    public String name;

    @SerializedName("location")
    public String location;

    @SerializedName("timezone")
    public String timezone;

    @SerializedName("lat")
    public String lat;

    @SerializedName("long")
    public String _long;

}
