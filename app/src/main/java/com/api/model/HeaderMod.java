package com.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prashant.patel on 8/28/2017.
 */

public class HeaderMod
{
    @SerializedName("start_time")
    public String start_time;

    @SerializedName("end_time")
    public String end_time;


    @SerializedName("state")
    public String state;


    @SerializedName("match_desc")
    public String match_desc;


    @SerializedName("type")
    public String type;


    @SerializedName("state_title")
    public String state_title;

    @SerializedName("toss")
    public String toss;

    @SerializedName("status")
    public String status;

}
