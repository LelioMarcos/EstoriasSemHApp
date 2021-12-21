package com.example.estoriassemhapp.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.estoriassemhapp.util.Config;
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

public class SearchViewModel extends ViewModel {
    String query;
    MutableLiveData<List<Story>> stories;

    public SearchViewModel(String query) {
        this.query = query;
    }

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
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "stories/get_stories.php", "GET", "UTF-8");

                httpRequest.addParam("search", query);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);

                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jProduct = jsonArray.getJSONObject(i);

                            String id = jProduct.getString("idhist");
                            String title = jProduct.getString("nomhist");
                            String text = jProduct.getString("dscsinopsehist");

                            Story story = new Story(id, title, text);
                            storiesList.add(story);
                        }
                        stories.postValue(storiesList);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static public class SearchViewModelFactory implements ViewModelProvider.Factory {

        String query;

        public SearchViewModelFactory(String query) {
            this.query = query;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SearchViewModel(query);
        }
    }
}
