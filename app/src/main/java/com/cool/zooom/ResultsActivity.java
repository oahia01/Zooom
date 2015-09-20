package com.cool.zooom;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends Activity {

    private static final String TAG = ResultsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle transportDataBundle = getIntent().getExtras();
        String [] dataArr = null;
        if (transportDataBundle != null && transportDataBundle.getStringArray("transportData") != null) {
            dataArr = transportDataBundle.getStringArray("transportData");
        }
        if (dataArr != null) {
            Log.d(TAG, dataArr.toString());
            dataArr[0] = dataArr[0].replace("[","");
            dataArr[dataArr.length - 1] = dataArr[dataArr.length - 1].replace("]","");
            String[] uber = new String[3];
            String[] cab = new String[3];
            String[] bus = new String[3];
            String[] bike = new String[3];
            System.arraycopy(dataArr, 0, uber, 0, 3);
            System.arraycopy(dataArr, 3, cab, 0, 3);
            System.arraycopy(dataArr, 6, bus, 0, 3);
            System.arraycopy(dataArr, 9, bike, 0, 3);

            RecyclerView resultsRecyclerView = (RecyclerView) findViewById(R.id.resultsRecyclerView);
            resultsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            resultsRecyclerView.setLayoutManager(linearLayoutManager);
            List<TransportData> transportDataList = new ArrayList<>();
            transportDataList.add(
                    new TransportData("Uber", "$" + uber[2], "Walking: " + uber[0] + " minutes",
                    "Travel: " + uber[1] + " minutes", R.drawable.uber_icon));
            transportDataList.add(
                    new TransportData("Bus", "$" + bus[2], "Walking: " + bus[0] + " minutes",
                    "Travel: " + bus[1] + " minutes", R.drawable.bus_icon));
            transportDataList.add(
                    new TransportData("Cab", "$" + cab[2], "Walking: " + cab[1] + " minutes",
                    "Travel: " + cab[1] + " minutes", R.drawable.taxi));
            transportDataList.add(
                    new TransportData("Bike", "$" + bike[2], "Walking: " + bike[1] + " minutes",
                    "Travel: " + bike[1] + " minutes", R.drawable.bike));
            resultsRecyclerView.setAdapter(new RecyclerAdapter(transportDataList));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchUberApp(){
        try {
            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri =
                    "uber://?action=setPickup&pickup=my_location&client_id=GJ0pVpfOkSG-O1-54hrIS1X0ZtiimYt3";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // No Uber app! Open mobile website.
            String url = "https://m.uber.com/sign-up?client_id=GJ0pVpfOkSG-O1-54hrIS1X0ZtiimYt3";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }
}
