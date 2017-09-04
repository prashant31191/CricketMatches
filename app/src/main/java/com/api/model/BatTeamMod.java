package com.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prashant.patel on 8/28/2017.
 */

public class BatTeamMod {

    @SerializedName("id")
    public String id;

    @SerializedName("innings")
    public ArrayList<InningsMod> arrayListInningsMod;

}
