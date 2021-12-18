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

public class TagsViewModel extends ViewModel {

    MutableLiveData<List<Tag>> tags;
    int navigationOpSelected = R.id.homeViewOp; // Utilizar a visualização em grid por padrão.

    public LiveData<List<Tag>> getTags() {
        if (tags==null) {
            tags = new MutableLiveData<List<Tag>>();
            loadTags();
        }

        return tags;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void refreshTags() {
        loadTags();
    }

    void loadTags() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Tag> tagsList = new ArrayList<>();
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "genders/get_genders.php", "GET", "UTF-8");

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
                            JSONObject jTags = jsonArray.getJSONObject(i);

                            String id = jTags.getString("idgenero");
                            String genero = jTags.getString("dscgenero");

                            Tag tag = new Tag(id, genero);
                            tagsList.add(tag);
                        }
                        tags.postValue(tagsList);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
