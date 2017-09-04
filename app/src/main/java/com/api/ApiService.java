package com.api;

import com.api.response.LivematchesRes;
import com.api.response.ScorecardRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by prashant.patel on 8/28/2017.
 */

public interface ApiService {

   /* @GET("/users/{user}/repos")
    Call<LivematchesRes> reposForUser(
            @Path("user") String user
    );*/

   //http://mapps.cricbuzz.com/cbzios/match/livematches
    @GET("livematches")
    Call<LivematchesRes> getLivematches();

    //http://mapps.cricbuzz.com/cbzios/match/18376/scorecard
    @GET("{match_id}/scorecard")
    Call<ScorecardRes> getScoreCard(
            @Path("match_id") String match_id
    );
}