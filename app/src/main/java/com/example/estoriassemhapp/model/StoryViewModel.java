package com.example.estoriassemhapp.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.estoriassemhapp.activity.StoryActivity;
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

public class StoryViewModel extends ViewModel {
    String id;
    MutableLiveData<Story> storyPost;

    public StoryViewModel(String id) {
        this.id = id;
    }

    public LiveData<Story> getStory() {
        if (storyPost==null) {
            storyPost = new MutableLiveData<Story>();
            loadStory();
        }

        return storyPost;
    }

    public void refreshStories() {
        loadStory();
    }

    void loadStory() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "stories/get_story.php", "GET", "UTF-8");
                httpRequest.addParam("id", id);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);

                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        String title = jsonObject.getString("nomhist");
                        String text = jsonObject.getString("dsccorpohist");
                        String autor = jsonObject.getString("nome");

                        Story story = new Story(id, title, text, autor);

                        storyPost.postValue(story);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static public class StoryViewModelFactory implements ViewModelProvider.Factory {

        String id;

        public StoryViewModelFactory(String id) {
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new StoryViewModel(id);
        }
    }
}

