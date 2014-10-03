package com.khunglong.xanh.utils;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.example.sonnt_commonandroid.utils.PreferenceUtil;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.json.DragonData;

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
            dragonData.setIcon(R.drawable.drawer_gai);
        }

        if (page.equals(resource.getListPageResource().get(1).getFbName())) {
            dragonData = resource.getKlxData();
            dragonData.setIcon(R.drawable.drawer_klx);
        }

        if (page.equals(resource.getListPageResource().get(2).getFbName())) {
            dragonData = resource.getGtgData();
            dragonData.setIcon(R.drawable.drawer_haivl);
        }

        if (page.equals(resource.getListPageResource().get(3).getFbName())) {
            dragonData = resource.getHaivlData();
            dragonData.setIcon(R.drawable.drawer_haivl);
        }
        if (page.equals(resource.getListPageResource().get(4).getFbName())) {
            dragonData = resource.getNghiemtucvlData();
            dragonData.setIcon(R.drawable.drawer_nghiemtucvl);
        }

        return dragonData;
    }

    public static void resetNews(String page) {
        ResourceManager resource = ResourceManager.getInstance();
        ;
        if (page.equals(resource.getListPageResource().get(0).getFbName())) {
            PreferenceUtil.setPreference(resource.getContext(), MsConstant.KEY_NEW_1, MsConstant.DEFAULT);
        }

        if (page.equals(resource.getListPageResource().get(1).getFbName())) {
            PreferenceUtil.setPreference(resource.getContext(), MsConstant.KEY_NEW_2, MsConstant.DEFAULT);
        }

        if (page.equals(resource.getListPageResource().get(2).getFbName())) {
            PreferenceUtil.setPreference(resource.getContext(), MsConstant.KEY_NEW_3, MsConstant.DEFAULT);
        }

        if (page.equals(resource.getListPageResource().get(3).getFbName())) {
            PreferenceUtil.setPreference(resource.getContext(), MsConstant.KEY_NEW_4, MsConstant.DEFAULT);
        }
        if (page.equals(resource.getListPageResource().get(4).getFbName())) {
            PreferenceUtil.setPreference(resource.getContext(), MsConstant.KEY_NEW_5, MsConstant.DEFAULT);
        }

    }

    public static void sendEmail(Context context, String receiver, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { receiver });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        try {
            context.startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }
}
