package com.crickbit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.ApiService;
import com.api.model.HeaderMod;
import com.api.model.MatchesMod;
import com.api.model.VenueMod;
import com.api.response.LivematchesRes;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.utils.AppFlags;
import com.utils.CustomProgressDialog;
import com.utils.PreferencesKeys;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActLiveMatchList extends BaseActivity {

    String TAG = "=ActLiveMatchList=";
    RecyclerView recyclerView;
    MaterialRefreshLayout materialRefreshLayout;
    NotificationAdapter notificationAdapter;
    TextView tvNodataTag;
    LinearLayout llNodataTag;


    ApiService apiService;
    Call callApiMethod;
    CustomProgressDialog customProgressDialog;

    String strFrom = "", strTitle = "Live Matches";
    int page = 0;
    String strTotalResult = "0";
    public ArrayList<MatchesMod> arrayListAllMatchesMod;

    private Paint p = new Paint();


    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup.inflate(this, R.layout.act_livematch_list, llContainerSub);

        try {
            App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "1");
            getIntentData();
            initialization();
            setApiData();
            setClickEvent();


            //setData();
            if (App.isInternetAvail(ActLiveMatchList.this)) {

                page = 0;
                arrayListAllMatchesMod = new ArrayList<>();
                //111 open api call for listing
                asyncGetNotificationList();

            } else {

                App.showSnackBar(tvTitle, getString(R.string.strNetError));
            }
            tvNodataTag.setTypeface(App.getFont_Regular());

        } catch (Exception e) {
            // TODO: handle exceptione.
            e.printStackTrace();
        }
    }

    private void initialization() {
        try {
            rlMenu.setVisibility(View.VISIBLE);
            tvTitle.setText(strTitle);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            tvNodataTag = (TextView) findViewById(R.id.tvNodataTag);
            llNodataTag = (LinearLayout) findViewById(R.id.llNodataTag);
            materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
            materialRefreshLayout.setIsOverLay(true);
            materialRefreshLayout.setWaveShow(true);
            materialRefreshLayout.setWaveColor(0x55ffffff);

            //tvNodataTag.setVisibility(View.GONE);
            llNodataTag.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            //materialRefreshLayout.setLoadMore(true);
            materialRefreshLayout.setLoadMore(false);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActLiveMatchList.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);


            initSwipe();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    notificationAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {


                        /*p.setColor(Color.RED);
                        c.drawRect(background,p);*/

                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        p.setColor(Color.GRAY);
                        p.setTextSize(35);
                        c.drawText("will be removed", background.centerX(), background.centerY(), p);
                        //versionViewHolder.tvName.setTypeface(App.getFont_Regular());

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void setApiData() {
        try {
            apiService = App.getJsonRetrofit(App.API_BASE_URL_3).create(ApiService.class);
            customProgressDialog = new CustomProgressDialog(ActLiveMatchList.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
           /* if (bundle.getString(AppFlags.tagTitle) != null && bundle.getString(AppFlags.tagTitle).length() > 0) {
                strTitle = bundle.getString(AppFlags.tagTitle);
            }*/
            if (bundle.getString(AppFlags.tagFrom) != null && bundle.getString(AppFlags.tagFrom).length() > 0) {
                strFrom = bundle.getString(AppFlags.tagFrom);
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
                if (App.isInternetAvail(ActLiveMatchList.this)) {
                    //asyncGetNotificationList();

                    page = 0;
                    arrayListAllMatchesMod = new ArrayList<>();

                    asyncGetNotificationList();

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


                  /*  if (App.isInternetAvail(ActLiveMatchList.this)) {
                        if (arrayListAllMatchesMod != null && strTotalResult.equalsIgnoreCase("" + arrayListAllMatchesMod.size())) {
                            if (arrayListAllMatchesMod.size() >= 20) {

                                App.showSnackBar(rlMenu, "No more matched found.");

                            }
                            materialRefreshLayout.finishRefresh();
                            // load more refresh complete
                            materialRefreshLayout.finishRefreshLoadMore();
                        } else {
                            page = page + 1;
                            asyncGetNotificationList();
                        }
                    } else {

                        App.showSnackBar(tvTitle, getString(R.string.strNetError));

                        materialRefreshLayout.finishRefresh();
                        // load more refresh complete
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                    */

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
            callApiMethod = apiService.getLivematches();

            App.showLogApi("No data pass");

            callApiMethod.enqueue(new Callback<LivematchesRes>() {
                @Override
                public void onResponse(Call<LivematchesRes> call, Response<LivematchesRes> response) {
                    try {
                        customProgressDialog.dismiss();
                        // refresh complete
                        materialRefreshLayout.finishRefresh();
                        // load more refresh complete
                        materialRefreshLayout.finishRefreshLoadMore();


                        LivematchesRes model = response.body();
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

                            if (model.arrayListMatchesMod != null && model.arrayListMatchesMod.size() > 0) {
                                arrayListAllMatchesMod.addAll(model.arrayListMatchesMod);


                                if (page == 0) {
                                    notificationAdapter = new NotificationAdapter(ActLiveMatchList.this, arrayListAllMatchesMod);
                                    recyclerView.setAdapter(notificationAdapter);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setVisibility(View.VISIBLE);
                                    //tvNodataTag.setVisibility(View.GONE);
                                    llNodataTag.setVisibility(View.GONE);
                                } else {
                                    if (notificationAdapter != null) {
                                        notificationAdapter.notifyDataSetChanged();
                                    }
                                }

                            } else {
                                llNodataTag.setVisibility(View.VISIBLE);
                                App.showLog("=====model.arrayListMatchesMod====Null model=");
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        customProgressDialog.dismiss();
                        // refresh complete
                        materialRefreshLayout.finishRefresh();
                        // load more refresh complete
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }

                @Override
                public void onFailure(Call<LivematchesRes> call, Throwable t) {

                    t.printStackTrace();
                    customProgressDialog.dismiss();
                    // refresh complete
                    materialRefreshLayout.finishRefresh();
                    // load more refresh complete
                    materialRefreshLayout.finishRefreshLoadMore();

                    App.showSnackBar(tvTitle, getString(R.string.strSomethingWentwrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VersionViewHolder> {
        ArrayList<MatchesMod> mArrListMatchesMod;
        Context mContext;


        public NotificationAdapter(Context context, ArrayList<MatchesMod> arrayListFollowers) {
            mArrListMatchesMod = arrayListFollowers;
            mContext = context;
        }

        @Override
        public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_livematch, viewGroup, false);
            VersionViewHolder viewHolder = new VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
            try {
                MatchesMod matchesMod = mArrListMatchesMod.get(i);

                if (matchesMod.series_name != null) {
                    versionViewHolder.tvName.setText(matchesMod.series_name);
                }

                versionViewHolder.cardItemLayout.setAlpha(1f);

                if (matchesMod.headerMod != null) {

                    HeaderMod mHeaderMod = matchesMod.headerMod;
                    String strDetails =
                            "type : " + mHeaderMod.type +
                                    // "\nstate : "+mHeaderMod.state +
                                    "\nstate: " + mHeaderMod.state_title +
                                    "\nmatch: " + mHeaderMod.match_desc +
                                    "\ntoss : " + mHeaderMod.toss +
                                    "\nstatus : " + mHeaderMod.status;


                    if (mHeaderMod.state.equalsIgnoreCase("lunch")) {
                        versionViewHolder.vStatus.setBackgroundColor(0xFFd81c1c);
                        versionViewHolder.tvName.setTypeface(App.getFont_Bold());
                    } else if (mHeaderMod.state.equalsIgnoreCase("inprogress")) {
                        versionViewHolder.vStatus.setBackgroundColor(0xFF196d2b);
                        versionViewHolder.tvName.setTypeface(App.getFont_Bold());
                    } else {
                        versionViewHolder.vStatus.setBackgroundColor(0xFFBCBCBC);
                        versionViewHolder.tvName.setTypeface(App.getFont_Regular());
                        versionViewHolder.cardItemLayout.setAlpha(0.6f);
                    }

                    versionViewHolder.tvDetail.setText(strDetails);
                }


                versionViewHolder.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {


                            App.showLog("==data_path===" + mArrListMatchesMod.get(i).data_path);

                            if (mArrListMatchesMod.get(i).venueMod != null) {

                                try {
                                    VenueMod mVenueMod = mArrListMatchesMod.get(i).venueMod;

                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String strLocation = "geo:0,0?q=" + (mVenueMod.lat + "," + mVenueMod._long);
                                    intent.setData(Uri.parse(strLocation));
                                    startActivity(intent);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                versionViewHolder.cardItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            App.showLog("==data_path===" + mArrListMatchesMod.get(i).data_path);

                            if(mArrListMatchesMod.get(i).match_id !=null) {
                                Intent intente = new Intent(ActLiveMatchList.this, ActScoreCard.class);
                                intente.putExtra(AppFlags.tagFrom, "ActLiveMatchList");
                                intente.putExtra(AppFlags.tagTitle, mArrListMatchesMod.get(i).series_name);
                                intente.putExtra(AppFlags.tagData, mArrListMatchesMod.get(i).match_id);
                                startActivity(intente);
                                animStartActivity();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mArrListMatchesMod.size();
        }


        public void removeItem(int position) {
            mArrListMatchesMod.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mArrListMatchesMod.size());
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {
            CardView cardItemLayout;
            TextView tvName, tvDetail;
            ImageView ivUserPhoto;
            RelativeLayout rlMain;
            View vDividerLine, vStatus;

            public VersionViewHolder(View itemView) {
                super(itemView);

                cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
                rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                ivUserPhoto = (ImageView) itemView.findViewById(R.id.ivUserPhoto);
                tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
                vDividerLine = itemView.findViewById(R.id.vDividerLine);
                vStatus = (View) itemView.findViewById(R.id.vStatus);



                tvDetail.setTypeface(App.getFont_Regular());
            }

        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        showExitDialog();
        /*super.onBackPressed();
        animFinishActivity();*/
    }

}