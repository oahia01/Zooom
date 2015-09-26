package com.cool.zooom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;

public class StartActivity extends Activity {

    private static final String TAG = StartActivity.class.getSimpleName();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    private void postToServer(final String transportParams) {
        try {
            String requestAddress = "http://ec2-52-27-250-72.us-west-2.compute.amazonaws.com/getvar.php" + transportParams;
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    requestAddress,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //confirm response is valid
                                if (response.length() > 0 && response.startsWith("[") && response.endsWith("]")) {
                                    startResultsActivity(response);
                                } else {
                                    Log.e(TAG, "Response from server could be invalid. Response: " + response);
                                    showToast("Could not find suitable routes for your request. Please try again", Toast.LENGTH_SHORT);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Error:" + e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error);
                }
            });

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            stringRequest.setTag(TAG);

            requestQueue.add(stringRequest);

        } catch (Exception e) {
            Log.e(TAG, "Could not perform successful HttpPost. Error: " + e);
        }
    }

    public void onClickButtonGo(View v) {
        if (v == (Button) findViewById(R.id.button_go_search)) {
            EditText editText_startingPoint = (EditText) findViewById(R.id.editText_startingPoint);
            EditText editText_destination = (EditText) findViewById(R.id.editText_destination);

//            startResultsActivity("[12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]");

            if (editText_startingPoint != null
                    && editText_destination != null) {
                if (editText_startingPoint.getText().toString().equals("")
                        || editText_destination.getText().toString().equals("")) {
                    showToast("Please enter both starting and destination points", Toast.LENGTH_SHORT);
                } else if (editText_startingPoint.getText().toString().equals(editText_destination.getText().toString())) {
                    showToast("Please enter different starting and destination points", Toast.LENGTH_SHORT);
                } else {
                    String params = "?O=" + editText_startingPoint.getText().toString() + "&D=" + editText_destination.getText().toString();
                    params = params.replace(" ", "_");
                    postToServer(params);
                }
            }
        }
    }

    private void showToast(String text, int duration) {
        Toast.makeText(getApplicationContext(), text, duration).show();
    }

    private void startResultsActivity(String response) {
        Intent startResultsActivity = new Intent(StartActivity.this, ResultsActivity.class);
        Log.e(TAG, response);
        String[] repsonseArray = response.split(", ", -1);
        startResultsActivity.putExtra("transportData", repsonseArray);
        startActivity(startResultsActivity);
    }

    // Source: http://stackoverflow.com/questions/14351870/send-txt-file-document-file-to-the-server-in-android


}
