package com.example.estoriassemhapp.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

public class ProfileModel extends ViewModel {
    String id;
    MutableLiveData<List<Story>> stories;
    MutableLiveData<User> userPost;

    public ProfileModel(String id) {
        this.id = id;
    }

    public LiveData<List<Story>> getStories() {
        if (stories==null) {
            stories = new MutableLiveData<List<Story>>();
            loadStories();
        }

        return stories;
    }

    public LiveData<User> getUser() {
        if (userPost == null) {
            userPost = new MutableLiveData<User>();
            loadUser();
        }

        return userPost;
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

                httpRequest.addParam("id_user", id);

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
                        stories.postValue(storiesList);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void loadUser() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "users/get_user.php", "GET", "UTF-8");
                httpRequest.addParam("id", id);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);

                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        String nome = jsonObject.getString("nomusuario");
                        String bio = jsonObject.getString("dscbiousuario");

                        String foto64 = jsonObject.getString("linkfotousuario");
                        String pureBase64Encoded = foto64.substring(foto64.indexOf(",")+1);
                        Bitmap foto = Util.base642Bitmap(pureBase64Encoded);

                        User user = new User(id, nome, bio, foto);
                        userPost.postValue(user);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static public class ProfileModelFactory implements ViewModelProvider.Factory {

        String id;

        public ProfileModelFactory(String id) {
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProfileModel(id);
        }
    }
}
