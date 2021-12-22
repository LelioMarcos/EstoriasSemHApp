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

public class ClassificViewModel extends ViewModel {

    MutableLiveData<List<Classific>> classif;
    int navigationOpSelected = R.id.homeViewOp; // Utilizar a visualização em grid por padrão.

    public LiveData<List<Classific>> getClassif() {
        if (classif==null) {
            classif = new MutableLiveData<List<Classific>>();
            loadClassif();
        }

        return classif;
    }

    public void refreshClassif() {
        loadClassif();
    }

    void loadClassif() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Classific> classificList = new ArrayList<>();
                HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "classificacoes/get_classificacoes.php", "GET", "UTF-8");

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

                            String id = jTags.getString("idclassificacao");
                            String dscclassificacao = jTags.getString("dscclassificacao");

                            Classific classific = new Classific(id, dscclassificacao);
                            classificList.add(classific);
                        }
                        classif.postValue(classificList);
                    }

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
