package com.crickbit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.utils.AppFlags;
import com.utils.PreferencesKeys;

import java.util.ArrayList;

public class BaseActivity extends FragmentActivity {


    protected View headerview;
    protected ListView lvMenuList;
    protected BaseMenuAdapter baseMenuAdapter;
    //protected  NavigationView navigationView;

    protected ImageView ivNotificationBase;
    protected TextView tvUsernameBase, tvUseremailBase, tvReqCounter;
    protected ImageView ivUserPhotoBase;
    protected RelativeLayout rlBaseMenuHeader;
    protected RelativeLayout rlLogout;
    protected SimpleDraweeView draweeView;


    String TAG = "==my- BaseActivity==";

    // for the Base full screen view and with sub screen view with header.
    protected LinearLayout llContainerMain, llContainerSub;

    // for the menu and back arrow
    protected RelativeLayout rlBaseMainHeader, rlMenu, rlBack;

    // for the text view header of the screen
    protected TextView tvTitle, tvTitle2;
    protected ImageView tvTitleImage;
    protected TextView tvSubTitle /*, tvExitMessage, tvCancel, tvOK*/;

    // for the main data in drawer layouts
    RelativeLayout left_drawer;
    // for the drawer layout
    protected DrawerLayout drawer;


    // for the Menu icons show at right side side
    protected RelativeLayout rlMenu4, rlMenu3, rlMenu2, rlMenu1;
    // for the menu images icons
    protected ImageView ivMenu4, ivMenu3, ivMenu2, ivMenu1;

    //for the app common class
    protected App app;
    protected PowerManager.WakeLock mWakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (App.blnFullscreenActvitity == true) {

           /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
*/
            /*


            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            View decorView = getWindow().getDecorView();

            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);*/

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            App.blnFullscreenActvitity = false;
        }


        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.act_baseactivity);

            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

           /* size = App.sharePrefrences.getArraySize(getApplicationContext(), PreferencesKeys.arrayAert);
            helper = new DatabaseHelper(this);*/

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            initialization();
            //setBaseLabelLanguage();
            setDrawerListAdapter();
            setBaseClickEvents();
            setFonts();

            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFonts() {
        tvTitle.setTypeface(App.getFont_Regular());
        tvTitle2.setTypeface(App.getFont_Regular());
        tvUsernameBase.setTypeface(App.getFont_Regular());
        tvUseremailBase.setTypeface(App.getFont_Regular());
        tvReqCounter.setTypeface(App.getFont_Regular());
    }


    private void setBaseClickEvents() {
        try {
            rlMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    } else {
                        drawer.openDrawer(left_drawer);
                    }
                }
            });
            rlBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            llContainerSub.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    System.out.println("===on touch=2=");
                    if (v instanceof EditText) {
                        System.out.println("=touch no hide edittext==2=");
                    } else {
                        System.out.println("===on touch hide=2=");
                        app.hideKeyBoard(v);
                    }
                    return true;
                }
            });
            rlBaseMenuHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BaseActivity.this, Main2Activity.class);
                            intent.putExtra(AppFlags.tagFrom, "BaseActivity");
                            startActivity(intent);
                            animStartActivity();
                        }
                    }, 280);
                }
            });

            ivNotificationBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BaseActivity.this, ActLiveMatchList.class);
                            intent.putExtra(AppFlags.tagFrom, "BaseActivity");
                            startActivity(intent);
                            animStartActivity();
                        }
                    }, 280);
                }
            });
            rlLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // for the logout click


                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initialization() {
        try {
            app = (App) getApplicationContext();

            lvMenuList = (ListView) findViewById(R.id.lvMenuList);
            LayoutInflater inflater = getLayoutInflater();
            headerview = inflater.inflate(R.layout.nav_header_act_home, null, false);
            lvMenuList.addHeaderView(headerview, null, false);

            rlBaseMainHeader = (RelativeLayout) findViewById(R.id.rlBaseMainHeader);
            llContainerMain = (LinearLayout) findViewById(R.id.llContainerMain);
            llContainerSub = (LinearLayout) findViewById(R.id.llContainerSub);
            rlMenu = (RelativeLayout) findViewById(R.id.rlMenu);

            rlBack = (RelativeLayout) findViewById(R.id.rlBack);
            tvTitle = (TextView) findViewById(R.id.tvTitle);
            tvTitle2 = (TextView) findViewById(R.id.tvTitle2);
            tvTitleImage = (ImageView) findViewById(R.id.tvTitleImage);
            tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
            // for the handel right side menu click 
            rlMenu4 = (RelativeLayout) findViewById(R.id.rlMenu4);
            rlMenu3 = (RelativeLayout) findViewById(R.id.rlMenu3);
            rlMenu2 = (RelativeLayout) findViewById(R.id.rlMenu2);
            rlMenu1 = (RelativeLayout) findViewById(R.id.rlMenu1);
            // for the right side menu images
            ivMenu4 = (ImageView) findViewById(R.id.ivMenu4);
            ivMenu3 = (ImageView) findViewById(R.id.ivMenu3);
            ivMenu2 = (ImageView) findViewById(R.id.ivMenu2);
            ivMenu1 = (ImageView) findViewById(R.id.ivMenu1);

            tvReqCounter = (TextView) findViewById(R.id.tvReqCounter);

            left_drawer = (RelativeLayout) findViewById(R.id.left_drawer);
            rlLogout = (RelativeLayout) findViewById(R.id.rlLogout);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            rlBaseMenuHeader = (RelativeLayout) headerview.findViewById(R.id.rlBaseMenuHeader);
            ivUserPhotoBase = (ImageView) headerview.findViewById(R.id.ivUserPhotoBase);
            tvUsernameBase = (TextView) headerview.findViewById(R.id.tvUsernameBase);
            tvUseremailBase = (TextView) headerview.findViewById(R.id.tvUseremailBase);

            ivNotificationBase = (ImageView) headerview.findViewById(R.id.ivNotificationBase);
            draweeView  = (SimpleDraweeView) headerview.findViewById(R.id.my_image_view);

            initDrawer();
            setDrawerListAdapter();

            tvTitle.setText(R.string.app_name);
            setBaseMenuDrawerHeaderData();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    protected void setDrawerListAdapter() {
        try {

            baseMenuAdapter = new BaseMenuAdapter(BaseActivity.this, getBaseMenuListData());
            lvMenuList.setAdapter(baseMenuAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setBaseMenuDrawerHeaderData() {
        try {

            Uri uri = Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-bG4elU8Vq8Ne1oREtQ_k5IGhy_Hk0jQikZ1kBqPrrdly8FImLg");

            draweeView.setImageURI(uri);



            if (tvUsernameBase != null && tvUseremailBase != null) {
                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName) != null
                        && App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName).length() > 1) {
                    String strFullName = App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName);
                    tvUsernameBase.setText(strFullName);
                }
                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserEmail) != null && App.sharePrefrences.getStringPref(PreferencesKeys.strUserEmail).length() > 1) {
                    String strEmailId = App.sharePrefrences.getStringPref(PreferencesKeys.strUserEmail);
                    tvUseremailBase.setText(strEmailId);
                }
            }

            if (ivUserPhotoBase != null) {
                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserImage) != null
                        && App.sharePrefrences.getStringPref(PreferencesKeys.strUserImage).length() > 1) {


                   /* Picasso.with(getApplicationContext())
                            .load(App.strBaseUploadedPicUrl + App.sharePrefrences.getStringPref(PreferencesKeys.strUserImage))
                            .fit().centerCrop()
                            .into(ivUserPhotoBase);

                    ivUserPhotoBase.setBorderWidth(2);
                    //ivUserPhotoBase.setBorderColor(Color.WHITE);
                    ivUserPhotoBase.setBorderColor(Color.GRAY);
                    //    ivUserPhotoBase.setShadow("#000000");
*/


                    //Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");


                }
            }


            /*if (tvUsernameBase != null && ivUserPhotoBase != null) {
                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName) != null && App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName).length() > 1) {
                    String strFullName = App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName);
                    tvUsernameBase.setText(strFullName);
                }
                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserImageurl) != null && App.sharePrefrences.getStringPref(PreferencesKeys.strUserImageurl).length() > 1) {
                    String strUserImageurl = App.sharePrefrences.getStringPref(PreferencesKeys.strUserImageurl);
                    Picasso.with(getApplicationContext())
                            .load(strUserImageurl)
                            .placeholder(R.drawable.profile_ph)
                            .fit().centerCrop()
                            .into(ivUserPhotoBase);
                }
            }*/
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    private void initDrawer() {
        try {
            left_drawer.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {// TODO Auto-generated method stub
                    // TODO Auto-generated method stub
                    App.showLog("==base menu act==", "===on touch==");

                    if (v instanceof EditText) {
                        App.showLog("==base menu act==", "=touch no hide edittext==2=");
                    } else {
                        App.showLog("==base menu act==", "===on touch hide=2=");
                        //   v.clearFocus();
                        App.hideSoftKeyboardMy(BaseActivity.this);
                    }
                    return true;
                }
            });


            drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                }

                @Override
                public void onDrawerOpened(View drawerView) {

                    try {

                        App.showLog("==onDrawerOpened===");

                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }


                }

                @Override
                public void onDrawerClosed(View drawerView) {

                    App.showLog("==onDrawerClosed===");

                    if (drawerView instanceof EditText) {
                        App.showLog("==base menu act==", "=touch no hide edittext==2=");
                    } else {
                        App.showLog("==base menu act==", "===on touch hide=2=");
                        //   v.clearFocus();
                        App.hideSoftKeyboardMy(BaseActivity.this);
                    }
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setEnableDrawer(boolean blnEnable) {
        if (blnEnable == true) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }


    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
            }
        } catch (Exception e) {
        }
    }


    public ArrayList<MenuItems> getBaseMenuListData() {
        ArrayList<MenuItems> arrayListMenuItems = new ArrayList<>();

        try {


            arrayListMenuItems.add(new MenuItems("Dashboard", R.drawable.ic_arrow_right, 0));
            arrayListMenuItems.add(new MenuItems("LiveMatch", R.drawable.ic_arrow_right, 1));
            arrayListMenuItems.add(new MenuItems("Cricbuzz", R.drawable.ic_arrow_right, 2));
            arrayListMenuItems.add(new MenuItems("Espn cricinfo", R.drawable.ic_arrow_right, 3));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayListMenuItems;
    }


    public class MenuItems {
        String strMenuName;
        int intImageDrawable, intMenuId;

        public MenuItems(String name, int id) {
            strMenuName = name;
            intMenuId = id;
        }

        public MenuItems(String name, int image, int id) {
            strMenuName = name;
            intImageDrawable = image;
            intMenuId = id;
        }
    }


    public class BaseMenuAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<MenuItems> mArrayListMenuItems;
        LayoutInflater inflater;

        public BaseMenuAdapter(Context context, ArrayList<MenuItems> arrayListMenuItems) {
            inflater = LayoutInflater.from(context);
            mArrayListMenuItems = arrayListMenuItems;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mArrayListMenuItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mArrayListMenuItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mArrayListMenuItems.get(i).intMenuId;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            View rowView = convertView;
            final ViewHolder viewHolder;
            // reuse views
            if (rowView == null) {
                viewHolder = new ViewHolder();
                rowView = inflater.inflate(R.layout.raw_menulist, null);
                // configure view holder


                viewHolder.ivMenuIcon = (ImageView) rowView.findViewById(R.id.ivMenuIcon);
                viewHolder.tvMenuItem = (TextView) rowView.findViewById(R.id.tvMenuItem);
                viewHolder.rlMenuItem = (RelativeLayout) rowView.findViewById(R.id.rlMenuItem);

                viewHolder.tvMenuItem.setTypeface(App.getFont_Regular());

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }


            final String menu_selected_id = App.sharePrefrences.getStringPref(PreferencesKeys.strMenuSelectedId);
            if (menu_selected_id != null && menu_selected_id.length() > 0) {
              /*  try {

                    int intMenu = 0;
                    intMenu = Integer.parseInt(menu_selected_id);

                    if (mArrayListMenuItems.get(i).intMenuId == intMenu) {
                        viewHolder.ivMenuIcon.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                        viewHolder.tvMenuItem.setTextColor(0xffffffff);
                    } else {
                        viewHolder.ivMenuIcon.setColorFilter(Color.argb(255, 255, 219, 000)); // Yellow Tint
                        viewHolder.tvMenuItem.setTextColor(0x40ffffff);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            } else {
                App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "0");
            }

            viewHolder.ivMenuIcon.setImageResource(mArrayListMenuItems.get(i).intImageDrawable);
            viewHolder.tvMenuItem.setText(mArrayListMenuItems.get(i).strMenuName);

            viewHolder.rlMenuItem.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View view) {

                    //viewHolder.ivMenuIcon.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint


                    drawer.closeDrawer(GravityCompat.START);
                    App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "" + mArrayListMenuItems.get(i).intMenuId);

                    if (mArrayListMenuItems.get(i).intMenuId == 0) {
                        if (!menu_selected_id.equalsIgnoreCase("0")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(BaseActivity.this, ActDashboard.class);
                                    intent.putExtra(AppFlags.tagFrom, "BaseActivity");
                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    }
                    else if (mArrayListMenuItems.get(i).intMenuId == 1) {
                        if (!menu_selected_id.equalsIgnoreCase("1")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    //Intent intent = new Intent(BaseActivity.this, WebviewContentActivity.class);
                                    Intent intent = new Intent(BaseActivity.this, ActLiveMatchList.class);
                                    intent.putExtra(AppFlags.tagFrom, "BaseActivity");
                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    }
                    else if (mArrayListMenuItems.get(i).intMenuId == 2) {
                        if (!menu_selected_id.equalsIgnoreCase("2")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(BaseActivity.this, ActWebUrls.class);
                                    intent.putExtra(AppFlags.tagFrom, "BaseActivity");
                                    intent.putExtra(AppFlags.tagTitle, "Cricbuzz");
                                    intent.putExtra(AppFlags.tagUrl, "http://www.cricbuzz.com");


                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    } else if (mArrayListMenuItems.get(i).intMenuId == 3) {
                        if (!menu_selected_id.equalsIgnoreCase("3")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(BaseActivity.this, ActWebUrls.class);
                                    intent.putExtra(AppFlags.tagFrom, "BaseActivity");
                                    intent.putExtra(AppFlags.tagTitle, "Espn cricinfo");
                                    intent.putExtra(AppFlags.tagUrl, "http://www.espncricinfo.com/ci/engine/match/index.html");


                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    }  else {
                        App.showLog("=======Menu item not found====");
                    }

                }
            });
            return rowView;
        }

        public class ViewHolder {
            ImageView ivMenuIcon;
            TextView tvMenuItem;
            RelativeLayout rlMenuItem;
        }
    }




/*

    private void openBottomSheetShare() {

        String strTagShareIntent = "Share";
        String strTagFacebook = "Facebook";
        String strTagTwitter = "Twitter";
        String strTagOther = "Other";

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet_share_intent, null, false);

        TextView header = (TextView) view.findViewById(R.id.header);
        header.setText(strTagShareIntent);

        TextView tvFacebook = (TextView) view.findViewById(R.id.tvFacebook);
        TextView tvTwitter = (TextView) view.findViewById(R.id.tvTwitter);
        TextView tvOther = (TextView) view.findViewById(R.id.tvOther);

        tvFacebook.setText(strTagFacebook);
        tvTwitter.setText(strTagTwitter);
        tvOther.setText(strTagOther);

        header.setTypeface(App.getFont_Regular());
        tvFacebook.setTypeface(App.getFont_Regular());
        tvTwitter.setTypeface(App.getFont_Regular());
        tvOther.setTypeface(App.getFont_Regular());

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(BaseActivity.this, R.style.BottomSheetDialog);
        mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);

        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();

                Intent i1 = new Intent(BaseActivity.this, ActShareIntent.class);
                i1.putExtra(AppFlags.tagFrom, "Facebook");
                startActivity(i1);
            }
        });

        tvTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent i1 = new Intent(BaseActivity.this, ActShareIntent.class);
                i1.putExtra(AppFlags.tagFrom, "Twitter");
                startActivity(i1);
            }
        });

        tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();

                Intent i1 = new Intent(BaseActivity.this, ActShareIntent.class);
                i1.putExtra(AppFlags.tagFrom, "other");
                startActivity(i1);
            }
        });
    }

*/


    public void animStartActivity() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void animFinishActivity() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        try {

            if (AppFlags.isEditProfileBase == true) {
                App.showLog("==base onResume==isEditProfileBase=");
                AppFlags.isEditProfileBase = false;
                setBaseMenuDrawerHeaderData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        //	setCloseDrawerMenu(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llContainerMain.getWindowToken(), 0);
        super.onPause();
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        System.out.println("=====Base onStop====");
        //	setCloseDrawerMenu(true);
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        System.out.println("=====Base onDestroy====");
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            try {
                super.onBackPressed();
                animFinishActivity();
            } catch (Exception e) {
                e.printStackTrace();
                App.showLog("==Exception on base back click====");
            }
        }
    }


    ////// COMMON EXIT DIALOG FOR ALL ACTIVITY //////
    public void showExitDialog() {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            final Dialog dialog = new Dialog(BaseActivity.this);
            // Include dialog.xml file
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.setContentView(R.layout.popup_exit);

            // set values for custom dialog components - text, image and button
            TextView tvExitMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
            TextView tvOK = (TextView) dialog.findViewById(R.id.tvOk);

            String strAlertMessageExit = "Are you sure you want to exit ?";

            String strYes = "YES";

            String strNo = "NO";

            App.showLog("==al-msg====strAlertMessageExit====" + strAlertMessageExit);
            App.showLog("==al-0=====strYes===" + strYes);
            App.showLog("==al-1====strNo====" + strNo);

            tvExitMessage.setText(strAlertMessageExit);
            tvCancel.setText(strNo);
            tvOK.setText(strYes);

            tvExitMessage.setTypeface(App.getFont_Regular());
            tvCancel.setTypeface(App.getFont_Regular());
            tvOK.setTypeface(App.getFont_Regular());

            dialog.show();


            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 800);
                }
            });

            tvOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            App.myFinishActivity(BaseActivity.this);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            } else {
                                finish();
                            }
                            onBackPressed();
                            return;
                        }
                    }, 800);

                }
            });
        }
    }
}
