package com.example.estoriassemhapp.model;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.CommentsActivity;
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

public class CommentsViewModel extends ViewModel {
    String id;
    MutableLiveData<List<Comment>> commentPost;

    public CommentsViewModel(String id) {
        this.id = id;
    }

    public LiveData<List<Comment>> getComments() {
        if (commentPost==null) {
            commentPost = new MutableLiveData<List<Comment>>();
            loadComments();
        }

        return commentPost;
    }

    public void refreshComments() {
        loadComments();
    }

    void loadComments() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                List<Comment> commentsList = new ArrayList<>();
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "comments/get_comments.php", "GET", "UTF-8");
                httpRequest.addParam("id_story", id);

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
                            JSONObject jComments = jsonArray.getJSONObject(i);

                            String idcoment = jComments.getString("idcoment");
                            String idusu = jComments.getString("idusuario");
                            String nomusu = jComments.getString("nomusuario");
                            String comentaro = jComments.getString("dsccorpocoment");

                            Comment comment = new Comment(idcoment, idusu, nomusu, comentaro);
                            commentsList.add(comment);
                        }
                        commentPost.postValue(commentsList);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendComment(String iduser, String comment) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "comments/create_comment.php", "POST", "UTF-8");
                httpRequest.addParam("comment", comment);
                httpRequest.addParam("idusuario", iduser);
                httpRequest.addParam("idhist", id);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);

                    refreshComments();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static public class CommentsViewModelFactory implements ViewModelProvider.Factory {

        String id;

        public CommentsViewModelFactory(String id) {
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CommentsViewModel(id);
        }
    }

}
