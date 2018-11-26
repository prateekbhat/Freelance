package com.idea_factory.idea_factory.activities;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.idea_factory.idea_factory.R;
import com.idea_factory.idea_factory.adapter.ExpandableListAdapter;
import com.idea_factory.idea_factory.adapter.NavigationAdapter;
import com.idea_factory.idea_factory.fragments.HomeFragment;
import com.idea_factory.idea_factory.fragments.ProductFragment;
import com.idea_factory.idea_factory.model.MenuModel;
import com.idea_factory.idea_factory.model.SubCategoriesModel;
import com.idea_factory.idea_factory.utils.Constants;
import com.idea_factory.idea_factory.utils.GeneralUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.idea_factory.idea_factory.API.Api.GetProductList;
import static com.idea_factory.idea_factory.API.Api.GetSubCategories;
import static com.idea_factory.idea_factory.utils.Constants.PDF_URL;
import static com.idea_factory.idea_factory.utils.NetworkUtils.isNetworkConnected;

public class DashBoardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashBoardListenerInteractor {
    private static final String TAG = "DashBoardActivity";
    ExpandableListAdapter expandableListAdapter;
    RecyclerView mRecyclerView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private GeneralUtils mGeneralUtils;
    private NavigationAdapter mNavigationAdapter;
    ImageView download_catelogue, refresh;
    Toolbar toolbar;
    ImageView home;
    File directory, mypath;
    Context context;
    boolean isDownloading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGeneralUtils = new GeneralUtils(this);
        mRecyclerView = findViewById(R.id.nav_recyler);

        context = this;


        setUpNavRecyler();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        // starts opening
                        setIcon(true);
                    } else {
                        // closing drawer
                        setIcon(false);
                    }
                    invalidateOptionsMenu();
                }
            }
        };


        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    // home.setImageDrawable(getResources().getDrawable(R.drawable.nav_ic));
                    home.setBackground(getResources().getDrawable(R.drawable.nav_ic));
                } else {
                    drawer.openDrawer(GravityCompat.START);
                    // home.setImageDrawable(getResources().getDrawable(R.drawable.nav_back_ic));
                    home.setBackground(getResources().getDrawable(R.drawable.nav_back_ic));
                }

            }
        });

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Clicked", "hii");
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mGeneralUtils.moveFragmentForDashBoard(new HomeFragment(), null, R.id.frame);

        if (isNetworkConnected(DashBoardActivity.this)) {
            GetProductList(this, DashBoardActivity.this);
        } else {
            Toast.makeText(DashBoardActivity.this, "No Internet Connection.", Toast.LENGTH_LONG).show();
            finish();
        }

        download_catelogue = (ImageView) findViewById(R.id.download_catelogue);
        download_catelogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected(DashBoardActivity.this)) {
                    String fileName = getString(R.string.app_name) + ".pdf";

                    directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                    mypath = new File(directory, "/ideaFactoryPdf/" + "/E-Catalogue_" + fileName);

                    if (mypath.exists()) {

                        showPdf();

                    } else {
                        if (!isDownloading) {
                            downloadPDF(fileName, PDF_URL);
                        }else {
                            Toast.makeText(DashBoardActivity.this, "Please wait while we prepare your file for download...", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(DashBoardActivity.this, "No Internet Connection.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected(DashBoardActivity.this)) {
                    GetSubCategories(DashBoardActivity.this, DashBoardActivity.this, null);
                } else {
                    Toast.makeText(DashBoardActivity.this, "No Internet Connection.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setIcon(boolean b) {
        if (b) {
            home.setBackground(getResources().getDrawable(R.drawable.nav_ic));
        } else {
            home.setBackground(getResources().getDrawable(R.drawable.nav_back_ic));
        }
    }

    private void setUpNavRecyler() {
        if (Constants.arrayListSubCategory != null && Constants.arrayListSubCategory.size() > 0) {
            mNavigationAdapter = new NavigationAdapter(Constants.arrayListSubCategory, this, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mNavigationAdapter);
        } else {
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item1) {
            // Handle the camera action
        } else if (id == R.id.item2) {

        } else if (id == R.id.item3) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void downloadPDF(String fileName, String pdfUrl) {
        isDownloading = true;

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(pdfUrl);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Pdf Downloading...");
        request.setDescription("PDF " + fileName);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ideaFactoryPdf/" + "/E-Catalogue_" + fileName);

        final long refid = downloadManager.enqueue(request);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(refid);

        BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isDownloading = false;
                //check if the broadcast message is for our enqueued download
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (referenceId == refid) {
                    showAlert("E-Catalogue Download Completed", "Downloaded", DashBoardActivity.this);
                }
            }
        };

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);
    }

    public void showPdf() {
        GeneralUtils.PDF_viewer(mypath, DashBoardActivity.this);
    }



   /* private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Category 1", true, false, getResources().getDrawable(R.mipmap.ic_pen)); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Category 2", true, true, getResources().getDrawable(R.drawable.ic_menu_gallery)); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Product 1", false, false, getResources().getDrawable(R.mipmap.ic_pen));
        childModelsList.add(childModel);

        childModel = new MenuModel("Product 2", false, false, getResources().getDrawable(R.mipmap.ic_pen));
        childModelsList.add(childModel);

        childModel = new MenuModel("Product 3", false, false, getResources().getDrawable(R.mipmap.ic_pen));
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123", "here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Category 3", true, true, getResources().getDrawable(R.drawable.ic_menu_gallery)); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, getResources().getDrawable(R.mipmap.ic_pen));
        childModelsList.add(childModel);

        childModel = new MenuModel("Python Fractions", false, false, getResources().getDrawable(R.mipmap.ic_pen));
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }*/

   /* private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                       *//* WebView webView = findViewById(R.id.webView);
                        webView.loadUrl(headerList.get(groupPosition).url);
                        onBackPressed();*//*
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    onBackPressed();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", model.menuName);
                    HomeFragment mHomeFragment = new HomeFragment();
                    mHomeFragment.setArguments(bundle);
                    mGeneralUtils.moveFragmentForDashBoard(mHomeFragment, Constants.TAB_GLOBAL, R.id.frame);
                }

                return false;
            }
        });
    }*/


    @Override
    public void hideNavigation(SubCategoriesModel menuName) {
        onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString("data", menuName.getSub_category_name());
        ProductFragment mProductFragment = new ProductFragment();
        mProductFragment.setArguments(bundle);
        mGeneralUtils.moveFragmentForDashBoard(mProductFragment, Constants.TAB_GLOBAL, R.id.frame);
    }


}
