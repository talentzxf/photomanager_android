package org.vincentzhang.photomanagerandroid;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by VincentZhang on 11/28/2017.
 */

public class RecyclerImageViewAdapter extends RecyclerView.Adapter {
    // Arrange by image creation time.
    // List of map from date string to image list
    private List<Map<Date, List<String>>> viewData;
    private int lastLoadedPage = -1;

    public void loadPage(){

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
