package org.vincentzhang.photomanagerandroid.model;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

/**
 * Created by VincentZhang on 11/28/2017.
 */

public class ImageURLLoader implements Response.Listener<JSONObject> {
    private String uri_prefix = "http://10.86.48.54:8000/img/";
    private int loadedPage = -1;
    private int pageSize = 10;

    public void getNextPage() {
        int curLoadingPage = loadedPage + 1;
        // 1. Get date meta data. Every 10 days as a page.
        JsonObjectRequest stringRequest = new JsonObjectRequest(GET, uri_prefix + "dateinfo?pagenumber=" + curLoadingPage + "&pagesize=" + pageSize, null, this, null);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("ImgURLLoader", response.toString());
    }
}
