package com.example.estoriassemhapp.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.estoriassemhapp.util.HttpRequest;
import com.example.estoriassemhapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {
    MutableLiveData<List<Story>> stories;

    public LiveData<List<Story>> getStories() {
        if (stories==null) {
            stories = new MutableLiveData<List<Story>>();
            loadStories();
        }

        return stories;
    }

    public void refreshStories() {
        loadStories();
    }

    void loadStories() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Story> storiesList = new ArrayList<>();
                HttpRequest httpRequest = new HttpRequest("https://estorias-sem-h-crud.herokuapp.com/stories/get_stories.php", "GET", "UTF-8");

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
