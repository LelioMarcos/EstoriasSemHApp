package com.example.estoriassemhapp.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.StoryActivity;
import com.example.estoriassemhapp.util.Config;
import com.example.estoriassemhapp.util.HttpRequest;
import com.example.estoriassemhapp.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WriteViewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WriteViewFragment newInstance() {
        WriteViewFragment fragment = new WriteViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnStory = getView().findViewById(R.id.btnSend);

        btnStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setEnabled(false); //Desabilitar botão

                //Verificação de preenchimento do campo titulo
                EditText etTitle = getView().findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(getContext(), "O campo de título não foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                //Verificação de preenchimento do campo tags
                EditText etTags = getView().findViewById(R.id.etTags);
                String tags = etTags.getText().toString();
                if (tags.isEmpty()) {
                    Toast.makeText(getContext(), "O campo tags não foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                //Verificação de preenchimento do campo classificação indicativa
                EditText etClass = getView().findViewById(R.id.etClass);
                String classif = etClass.getText().toString();
                if (classif.isEmpty()) {
                    Toast.makeText(getContext(), "O campo classificação indicativa não foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                //Verificação de preenchimento do campo história
                EditText etHist = getView().findViewById(R.id.tvWriteStory);
                String whist = etHist.getText().toString();
                if (whist.isEmpty()) {
                    Toast.makeText(getContext(), "O campo da história não foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                //Verificação de preenchimento do campo sinopse
                EditText etSinopse = getView().findViewById(R.id.etSinopse);
                String sinopse = etSinopse.getText().toString();
                if (sinopse.isEmpty()) {
                    Toast.makeText(getContext(), "O campo sinopse não foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                //Envio dos dados do novo produto
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "stories/create_story.php", "POST", "UTF-8");

                        httpRequest.addParam("titulo", title);
                        httpRequest.addParam("sinopse", sinopse);
                        httpRequest.addParam("corpo", whist);
                        httpRequest.addParam("idusuario", "3");
                        httpRequest.addParam("idcapa", "2");

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();
                            System.out.println(result);
                            Log.d("HTTP_REQUEST_RESULT", result);

                            JSONObject jsonObject = new JSONObject(result);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Produto adicionado com sucesso", Toast.LENGTH_LONG).show();
                                    getActivity().setResult(RESULT_OK);
                                    int id = 0;
                                    try {
                                        id = jsonObject.getInt("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Intent i = new Intent(getActivity(), StoryActivity.class);
                                    i.putExtra("idhist", Integer.toString(id));
                                    startActivity(i);
                                }
                            });
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}