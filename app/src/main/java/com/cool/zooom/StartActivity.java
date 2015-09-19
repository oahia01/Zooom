package com.cool.zooom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends Activity {

    private static final String TAG = StartActivity.class.getSimpleName();
    private String distanceMatrixRequestAddress =
            "https://maps.googleapis.com/maps/api/distancematrix/json?parameters";
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

    private void postToServer(final Map<String, String> params) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    "address",
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                final String HttpResponseStr = response.getString("status");
//                                handleNetworkResponse(HttpResponseStr);
                            } catch (JSONException e) {
                                Log.e(TAG, "Error:" + e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("charset", "utf-8");
                    return headers;
                }
            };

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            jsonObjectRequest.setTag(TAG);

            requestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            Log.e(TAG, "Could not perform successful HttpPost");
        }
    }

    public void onClickButtonGo(View v) {
//        Button go_button = (Button) findViewById(R.id.button_go_search);
//        go_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
        if (v == (Button) findViewById(R.id.button_go_search)) {
            EditText editText_startingPoint = (EditText) findViewById(R.id.editText_startingPoint);
            EditText editText_destination = (EditText) findViewById(R.id.editText_destination);
            if (editText_startingPoint != null
                    && editText_destination != null) {
                if (editText_startingPoint.getText().toString().equals("")
                        || editText_destination.getText().toString().equals("")) {
                    showToast("Please enter both starting and destination points", Toast.LENGTH_SHORT);
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("origin", editText_startingPoint.getText().toString());
                    params.put("destination", editText_destination.getText().toString());
                    postToServer(params);
                }
            }
//            }
//        });
        }
    }

    private void showToast(String text, int duration) {
        Toast.makeText(getApplicationContext(), text, duration).show();
    }

    private void startResultsActivity(String response) {
        Intent startResultsActivity = new Intent(StartActivity.this, ResultsActivity.class);
//        startResultsActivity.putExtra()
        startActivity(startResultsActivity);
    }
}
