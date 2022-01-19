package com.example.estoriassemhapp.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.estoriassemhapp.R;
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

public class MainViewModel extends ViewModel {
    MutableLiveData<List<Story>> stories;
    int navigationOpSelected = R.id.homeViewOp; // Utilizar a visualização em grid por padrão.

    public LiveData<List<Story>> getStories() {
        if (stories==null) {
            stories = new MutableLiveData<List<Story>>();
            loadStories();
        }

        return stories;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
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
                            String classificacao = jProduct.getString("classificacao");

                            Story story = new Story(id, title, text, classificacao, "a");
                            storiesList.add(story);
                        }
                    }

                    stories.postValue(storiesList);

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
