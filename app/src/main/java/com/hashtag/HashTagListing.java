package com.hashtag;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Deepak on 10/26/2016.
 */

public class HashTagListing extends AppCompatActivity {

    private static final int REQUEST_LOCATION_SERVICE = 999;
    private static final String TAG = "HashTagListing";
    private RecyclerView recycler_view;
    ArrayList<GetterSetteList> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashlisting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            popup();
        }
        else
            new Delivery_Boy_List_Async().execute();

    }


    private void popup() {
        if (!requestPermission()) {
            return;
        }
    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED )
        {
            new Delivery_Boy_List_Async().execute();
            return true;
        }

        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_WIFI_STATE)
                && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
        {
            Snackbar.make(recycler_view, "Please provide permission", Snackbar.LENGTH_INDEFINITE)
                    .setAction("UNDO", new View.OnClickListener()
                    {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v)
                        {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_SERVICE);
                        }
                    }).show();
            Toast.makeText(HashTagListing.this, getResources().getString(R.string.error_permission), Toast.LENGTH_LONG).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_SERVICE);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG, " grantResults  "+grantResults[0]+ "  length   " +grantResults.length);
        if (requestCode == REQUEST_LOCATION_SERVICE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestPermission())
            {
                new Delivery_Boy_List_Async().execute();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Delivery_Boy_List_Async extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String resposne = "";
            try {
                getHashTag();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e(TAG, "Delivery_Boy_List_Async  response.............." + resposne);
            return resposne;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getHashTag(){
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("MfjXyHXNrCTiHAiOXeETiJON3");//consumerKey);
        builder.setOAuthConsumerSecret("4WMz1rLz7KovkVtOYeWLxadzC3tyFGB5ZH7lNFylWxPyOicL3M");//SecretKet);
        builder.setOAuthAccessToken("0mtOSCIzYjMqIgu3rBUMck3IPpuCdMmx9YQckNFs");
        builder.setOAuthAccessTokenSecret("O5uG4KjLLic2BaG1a88mowOUTc0LXFSMD554mnV8fnueo");
        builder.setJSONStoreEnabled(true);


        AccessToken newAcc = new AccessToken("350498745-0mtOSCIzYjMqIgu3rBUMck3IPpuCdMmx9YQckNFs",
                "O5uG4KjLLic2BaG1a88mowOUTc0LXFSMD554mnV8fnueo");//getoken, getokensecret);
        Twitter twitter = new TwitterFactory(builder.build())
                .getInstance(newAcc);

        try {
            List<Status> statuslist = new ArrayList<Status>();
            //23 for hash #
            Query AA = new Query("" + "#weather");//HASH_TAG_string);
//            Query{query='%23twitter', lang='null', locale='null', maxId=-1, rpp=-1, page=-1, since='null', sinceId=-1, geocode='null', until='null', resultType='null'}
            System.out.println("AA : " + AA);
            AA.setCount(20);
//            AA.setSinceId(24012619984051000L);
//            AA.setMaxId(250126199840518145L);
//            AA.setResultType("mix");
            //if (!statusid.equals("")) {
            //long l = Long.parseLong(statusid);
            //AA.setMaxId(l - 1);
            //}

            QueryResult result = null;
            try {
                try {
                    result = twitter.search(AA);
                    statuslist = result.getTweets();
                } catch (twitter4j.TwitterException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            long id = result.getMaxId();

            System.out.println("Count : result " + result.getCount());
            System.out.println("Count : result " + result);

            mList = new ArrayList<GetterSetteList>();
            for (twitter4j.Status status : statuslist) {
                GetterSetteList obj = new GetterSetteList();
                String sts = status.getText();
                obj.setStatusID("" + status.getId());
                Log.d("STATE", sts);
                System.out.println("=======ID=====" + status.getId());

                User u = status.getUser();

                obj.setImageURL(u.getProfileImageURLHttps());
                obj.setmStatus(status.getText());
                Date date = status.getCreatedAt();
                obj.setStatustime(date.toString());

                obj.setUser_id(u.getScreenName());
                obj.setUsername(u.getName());
                mList.add(obj);
            }
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
//            flag = 1;
            Log.v("ERROR", "GetHastTagdata" + e.toString());
            e.printStackTrace();
        }
    }
}
