package com.khunglong.xanh.utils;

import org.json.JSONObject;

import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.json.DragonData;

import android.util.Log;

public class MsUtils {
    public static boolean iSJsonValueAvailable(JSONObject ja, String value) {
        if (ja.has(value)) {
            return true;
        } else {
            Log.e("", ">>>ERROR JSON parse value:" + value);
            return false;
        }
    }

    public static DragonData getDragonDataFrompage(String page) {
        ResourceManager resource = ResourceManager.getInstance();
        DragonData dragonData = resource.getChandaiData();
        ;
        if (page.equals(resource.getListPageResource().get(0).getFbName())) {
            dragonData = resource.getChandaiData();
        }

        if (page.equals(resource.getListPageResource().get(1).getFbName())) {
            dragonData = resource.getKlxData();
        }

        if (page.equals(resource.getListPageResource().get(2).getFbName())) {
            dragonData = resource.getGtgData();
        }

        if (page.equals(resource.getListPageResource().get(3).getFbName())) {
            dragonData = resource.getHaivlData();
        }
        if (page.equals(resource.getListPageResource().get(4).getFbName())) {
            dragonData = resource.getNghiemtucvlData();
        }

        return dragonData;
    }
}
