package com.crickbit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.api.ApiService;
import com.api.model.SCRInningsMod;
import com.api.response.ScorecardRes;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.demo.ActPdf;
import com.google.gson.Gson;
import com.utils.AppFlags;
import com.utils.CustomProgressDialog;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActScoreCard extends BaseActivity {

    String TAG = "=ActScoreCard=";
    ApiService apiService;
    Call callApiMethod;
    CustomProgressDialog customProgressDialog;

    String strFrom = "", strTitle = "Score Card", strMatchId = "";
    ArrayList<SCRInningsMod> arrayListSCRInningsMod = new ArrayList<>();

    TextView tvName, tvDetail;

    MaterialRefreshLayout materialRefreshLayout;


    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup.inflate(this, R.layout.act_score_card, llContainerSub);
        try {
            getIntentData();
            initialization();
            setApiData();
            setClickEvent();
            if (App.isInternetAvail(ActScoreCard.this)) {
                //111 open api call for listing
                asyncGetNotificationList();

            } else {

                App.showSnackBar(tvTitle, getString(R.string.strNetError));
            }
        } catch (Exception e) {
            // TODO: handle exceptione.
            e.printStackTrace();
        }
    }

    private void initialization() {
        try {
            rlBack.setVisibility(View.VISIBLE);
            setEnableDrawer(false);
            tvTitle.setText(strTitle);

            tvName = (TextView) findViewById(R.id.tvName);
            tvDetail = (TextView) findViewById(R.id.tvDetail);

            materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
            materialRefreshLayout.setIsOverLay(true);
            materialRefreshLayout.setWaveShow(true);
            materialRefreshLayout.setWaveColor(0x55ffffff);

            materialRefreshLayout.setLoadMore(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setApiData() {
        try {
            apiService = App.getJsonRetrofit(App.API_BASE_URL_3).create(ApiService.class);
            customProgressDialog = new CustomProgressDialog(ActScoreCard.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(AppFlags.tagTitle) != null && bundle.getString(AppFlags.tagTitle).length() > 0) {
                strTitle = bundle.getString(AppFlags.tagTitle);
            }
            if (bundle.getString(AppFlags.tagFrom) != null && bundle.getString(AppFlags.tagFrom).length() > 0) {
                strFrom = bundle.getString(AppFlags.tagFrom);
            }
            if (bundle.getString(AppFlags.tagData) != null && bundle.getString(AppFlags.tagData).length() > 0) {
                strMatchId = bundle.getString(AppFlags.tagData);
            }
        }

    }


    private void setClickEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //refreshing...
                if (App.isInternetAvail(ActScoreCard.this)) {

                    asyncGetNotificationList();

                  /*  Intent intent = new Intent(ActScoreCard.this, ActPdf.class);
                    startActivity(intent);
*/
                } else {
                    App.showSnackBar(tvTitle, getString(R.string.strNetError));

                }
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                try {

                    App.showSnackBar(rlMenu, "No more match found.");
                    materialRefreshLayout.finishRefresh();
                    // load more refresh complete
                    materialRefreshLayout.finishRefreshLoadMore();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //http://54.72.90.40/route_is_mine/ws.halacab.php?op=noti_list&uid=116&user_type=0
    public void asyncGetNotificationList() {

        try {
            customProgressDialog.show();
            callApiMethod = apiService.getScoreCard(strMatchId);

            App.showLogApi("data pass---strMatchId==" + strMatchId);

            callApiMethod.enqueue(new Callback<ScorecardRes>() {
                @Override
                public void onResponse(Call<ScorecardRes> call, Response<ScorecardRes> response) {
                    try {
                        customProgressDialog.dismiss();

                        materialRefreshLayout.finishRefresh();
                        // load more refresh complete
                        materialRefreshLayout.finishRefreshLoadMore();



                        ScorecardRes model = response.body();
                        if (model == null) {
                            //404 or the response cannot be converted to User.
                            App.showLog("---null response--", "==Something wrong=");
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                try {
                                    App.showLog("---error-", "" + responseBody.string());
                                    App.showSnackBar(tvTitle, getString(R.string.strSomethingWentwrong));


                                    //tv.setText("responseBody = "+responseBody.string());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //200 sucess
                            App.showLog("===Response== " + response.body().toString());
                            App.showLog("==**==Success==**==asyncGetNotificationList==> ", new Gson().toJson(response.body()));

                            if (model.arrayListSCRInningsMod != null && model.arrayListSCRInningsMod.size() > 0) {

                                arrayListSCRInningsMod = model.arrayListSCRInningsMod;

                                setDetailData();


                            } else {
                                App.showLog("=====model.arrayListMatchesMod====Null model=");
                                App.showSnackBar(tvTitle, getString(R.string.strSomethingWentwrong));
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        customProgressDialog.dismiss();

                        materialRefreshLayout.finishRefresh();
                        // load more refresh complete
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }

                @Override
                public void onFailure(Call<ScorecardRes> call, Throwable t) {

                    t.printStackTrace();
                    customProgressDialog.dismiss();

                    materialRefreshLayout.finishRefresh();
                    // load more refresh complete
                    materialRefreshLayout.finishRefreshLoadMore();
                    App.showSnackBar(tvTitle, getString(R.string.strSomethingWentwrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

            customProgressDialog.dismiss();

            materialRefreshLayout.finishRefresh();
            // load more refresh complete
            materialRefreshLayout.finishRefreshLoadMore();
            App.showSnackBar(tvTitle, getString(R.string.strSomethingWentwrong));
        }
    }


    private void setDetailData() {
        try {
            tvName.setText(strTitle);
            tvDetail.setText("");

            String strData = "\n";

            if (arrayListSCRInningsMod != null && arrayListSCRInningsMod.size() > 0) {
                for (int i = 0; i < arrayListSCRInningsMod.size(); i++) {


                    strData = strData + "bating team : " + arrayListSCRInningsMod.get(i).bat_team_name + "\n";
                    strData = strData + "score : " + arrayListSCRInningsMod.get(i).score + "\n";
                    strData = strData + "wicket : " + arrayListSCRInningsMod.get(i).wkts + "\n";
                    strData = strData + "over : " + arrayListSCRInningsMod.get(i).ovr + "\n";

                    if (arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod != null && arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.size() > 0) {
                        strData = strData + "\n \n  ========BATSMEN=======\n \n";

                        for (int j = 0; j < arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.size(); j++) {
                            strData = strData + "\n";
                            strData = strData + "out_desc : " + arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.get(j).out_desc + "\n";
                            strData = strData + "run : " + arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.get(j).r + "\n";
                            strData = strData + "ball : " + arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.get(j).b + "\n";
                            strData = strData + "4s : " + arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.get(j)._4s + "\n";
                            strData = strData + "6s : " + arrayListSCRInningsMod.get(i).arrayListSCRBatsmenMod.get(j)._6s + "\n";
                        }

                    }

                    if (arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod != null && arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.size() > 0) {
                        strData = strData + "\n \n  ========BOWLERS=======\n \n";

                        for (int j = 0; j < arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.size(); j++) {
                            strData = strData + "\n";
                            strData = strData + "over : " + arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.get(j).o + "\n";
                            strData = strData + "m : " + arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.get(j).m + "\n";
                            strData = strData + "run : " + arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.get(j).r + "\n";
                            strData = strData + "wicket : " + arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.get(j).w + "\n";
                            strData = strData + "no ball : " + arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.get(j).n + "\n";
                            strData = strData + "wide ball : " + arrayListSCRInningsMod.get(i).arrayListSCRBowlersMod.get(j).wd + "\n";
                        }
                    }

                    if (arrayListSCRInningsMod.get(i).scrExtrasMod != null) {
                        strData = strData + "\n \n  ========EXTRAS=======\n \n";
                        strData = strData + "Total Extra : " + arrayListSCRInningsMod.get(i).scrExtrasMod.t + "\n";
                        strData = strData + "ball : " + arrayListSCRInningsMod.get(i).scrExtrasMod.b + "\n";
                        strData = strData + "lb : " + arrayListSCRInningsMod.get(i).scrExtrasMod.lb + "\n";
                        strData = strData + "wide ball : " + arrayListSCRInningsMod.get(i).scrExtrasMod.wd + "\n";
                        strData = strData + "no ball : " + arrayListSCRInningsMod.get(i).scrExtrasMod.nb + "\n";
                        strData = strData + "p : " + arrayListSCRInningsMod.get(i).scrExtrasMod.p + "\n";

                    }

                    strData = strData + "\n \n ";

                }


            }


            tvDetail.setText(strData);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        animFinishActivity();
    }

}