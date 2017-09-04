package com.api.response;

import com.api.model.SCRInningsMod;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prashant.patel on 9/4/2017.
 */

public class ScorecardRes extends CommonRes {

    @SerializedName("Innings")
    public ArrayList<SCRInningsMod> arrayListSCRInningsMod;

}
