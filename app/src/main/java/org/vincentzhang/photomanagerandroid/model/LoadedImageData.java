package org.vincentzhang.photomanagerandroid.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VincentZhang on 11/28/2017.
 */

public class LoadedImageData {
    private Map<Date, DateImageList> dateToImagesMap = new HashMap<>();
    private List<DateImageList> viewData = new ArrayList<>();

    public Map<Date, DateImageList> getDateToImagesMap() {
        return dateToImagesMap;
    }

    public void setDateToImagesMap(Map<Date, DateImageList> dateToImagesMap) {
        this.dateToImagesMap = dateToImagesMap;
    }

    public List<DateImageList> getViewData() {
        return viewData;
    }

    public void setViewData(List<DateImageList> viewData) {
        this.viewData = viewData;
    }
}
