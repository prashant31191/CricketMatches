package com.api.response;

import com.api.model.MatchesMod;
import com.api.model.SrsCategoryMod;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prashant.patel on 8/28/2017.
 */

public class LivematchesRes extends CommonRes
{

    @SerializedName("matches")
    public ArrayList<MatchesMod> arrayListMatchesMod;


    @SerializedName("srs_category")
    public ArrayList<SrsCategoryMod> arrayListSrsCategoryMod;
}
