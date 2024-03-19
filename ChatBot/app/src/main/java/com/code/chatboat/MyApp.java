package com.code.chatboat;

import android.app.Application;
import android.util.Log;

import com.code.chatboat.model.ResponseApiItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyApp extends Application {

    public static ArrayList<ResponseApiItem> questionAnswerList = new ArrayList<ResponseApiItem>();
    public static ArrayList<ResponseApiItem> questionAnswerGujList = new ArrayList<ResponseApiItem>();

    @Override
    public void onCreate() {
        super.onCreate();
        getQNAList();
        getQNAGujList();
    }

    private void getQNAList() {
        String responseData = loadJSONFromAsset("eng/englishData");
        Log.e("TAG", "getQNAList: " + responseData);
        Type collectionType = new TypeToken<List<ResponseApiItem>>() {
        }.getType();
        Collection<ResponseApiItem> enums = new Gson().fromJson(responseData, collectionType);
        questionAnswerList.addAll(enums);
    }

    private void getQNAGujList() {
        String responseData = loadJSONFromAsset("guj/gujData");
        Type collectionType = new TypeToken<List<ResponseApiItem>>() {
        }.getType();
        Collection<ResponseApiItem> enums = new Gson().fromJson(responseData, collectionType);
        questionAnswerGujList.addAll(enums);
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
