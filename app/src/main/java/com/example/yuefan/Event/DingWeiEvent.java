package com.example.yuefan.Event;

import com.amap.api.maps.model.LatLng;

/**
 * Created by 67698 on 2018/8/25.
 */

public class DingWeiEvent {
    private LatLng latLng;
    public DingWeiEvent(LatLng latLng)
    {
        this.latLng=latLng;
    }

    public LatLng getLatLng()
    {
        return  latLng;
    }

    public void setLatLng(LatLng latLng)
    {
        this.latLng=latLng;
    }


}
