package com.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prashant.patel on 8/28/2017.
 */

public class MatchesMod {

    @SerializedName("match_id")
    public String match_id;

    @SerializedName("series_id")
    public String series_id;

    @SerializedName("series_name")
    public String series_name;

    @SerializedName("data_path")
    public String data_path;

    @SerializedName("header")
    public HeaderMod headerMod;

    @SerializedName("alerts")
    public String alerts;

    @SerializedName("venue")
    public VenueMod venueMod;


    @SerializedName("bat_team")
    public BatTeamMod batTeamMod;


    @SerializedName("bow_team")
    public BowTeamMod bowTeamMod;


    @SerializedName("batsman")
    public ArrayList<BatsmanMod> arrayListBatsmanMod;


    @SerializedName("bowler")
    public ArrayList<BowlerMod> arrayListBowlerMod;


    @SerializedName("team1")
    public TeamMod teamMod1;

    @SerializedName("team2")
    public TeamMod teamMod2;


    @SerializedName("srs_category")
    public String[] arr_srs_category;


}
