package com.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prashant.patel on 9/4/2017.
 */

public class SCRInningsMod {

    @SerializedName("innings_id")
    public String innings_id;

    @SerializedName("bat_team_id")
    public String bat_team_id;


    @SerializedName("bat_team_name")
    public String bat_team_name;


    @SerializedName("score")
    public String score;


    @SerializedName("wkts")
    public String wkts;


    @SerializedName("ovr")
    public String ovr;




    @SerializedName("next_batsman_label")
    public String next_batsman_label;



    @SerializedName("batsmen")
    public ArrayList<SCRBatsmenMod> arrayListSCRBatsmenMod;


    @SerializedName("bowlers")
    public ArrayList<SCRBowlersMod> arrayListSCRBowlersMod;


    @SerializedName("extras")
    public SCRExtrasMod scrExtrasMod;

}
