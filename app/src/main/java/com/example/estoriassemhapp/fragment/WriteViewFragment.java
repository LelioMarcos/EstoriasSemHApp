package com.example.estoriassemhapp.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.StoryActivity;
import com.example.estoriassemhapp.adapter.TagsAdapter;
import com.example.estoriassemhapp.model.Classific;
import com.example.estoriassemhapp.model.ClassificViewModel;
import com.example.estoriassemhapp.model.Comment;
import com.example.estoriassemhapp.model.Tag;
import com.example.estoriassemhapp.model.TagsViewModel;
import com.example.estoriassemhapp.util.Config;
import com.example.estoriassemhapp.util.HttpRequest;
import com.example.estoriassemhapp.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

    private void populateSpinner(Spinner spinner, List<String> list) {
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnStory = getView().findViewById(R.id.btnSend);

        TagsViewModel tagsViewModel = new ViewModelProvider(getActivity()).get(TagsViewModel.class);
        LiveData<List<Tag>> tags = tagsViewModel.getTags();

        ClassificViewModel classificViewModel = new ViewModelProvider(getActivity()).get(ClassificViewModel.class);
        LiveData<List<Classific>> classific = classificViewModel.getClassif();

        Spinner sp1 = getView().findViewById(R.id.spGender);
        Spinner sp2 = getView().findViewById(R.id.spClass);

        ArrayList<String> ids = new ArrayList<>();

        tags.observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags1) {
                ArrayList<String> genders = new ArrayList<>();

                for (Tag tab: tags1) {
                    genders.add(tab.getGenero());
                    ids.add(tab.getId());
                }

                populateSpinner(sp1, genders);
            }
        });



        classific.observe(getViewLifecycleOwner(), new Observer<List<Classific>>() {
            @Override
            public void onChanged(List<Classific> classifics) {
                ArrayList<String> nomes = new ArrayList<>();

                for (Classific classific1: classifics) {
                    nomes.add(classific1.getClassif());
                }

                populateSpinner(sp2, nomes);
            }
        });


        btnStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setEnabled(false); //Desabilitar bot??o

                String id = Config.getId(getContext());

                //Verifica????o de preenchimento do campo titulo
                EditText etTitle = getView().findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(getContext(), "O campo de t??tulo n??o foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                String gender = ids.get(sp1.getSelectedItemPosition());
                String classific = sp2.getSelectedItem().toString();

                //Verifica????o de preenchimento do campo hist??ria
                EditText etHist = getView().findViewById(R.id.tvWriteStory);
                String whist = etHist.getText().toString();
                if (whist.isEmpty()) {
                    Toast.makeText(getContext(), "O campo da hist??ria n??o foi preenchido", Toast.LENGTH_LONG).show();
                    view.setEnabled(true);
                    return;
                }

                //Verifica????o de preenchimento do campo sinopse
                EditText etSinopse = getView().findViewById(R.id.etSinopse);
                String sinopse = etSinopse.getText().toString();
                if (sinopse.isEmpty()) {
                    Toast.makeText(getContext(), "O campo sinopse n??o foi preenchido", Toast.LENGTH_LONG).show();
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
                        httpRequest.addParam("idusuario", id);
                        httpRequest.addParam("idcapa", "3");
                        httpRequest.addParam("nomclassificacao", classific);

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();
                            Log.d("HTTP_REQUEST_RESULT", result);

                            JSONObject jsonObject = new JSONObject(result);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Hist??ria adicionado com sucesso", Toast.LENGTH_LONG).show();
                                }
                            });

                            //getActivity().setResult(RESULT_OK);

                            int id = 0;
                            try {
                                id = jsonObject.getInt("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            HttpRequest httpRequest2 = new HttpRequest(Config.BD_APP_URl + "generohists/create_generohist_id.php", "POST", "UTF-8");

                            httpRequest2.addParam("id_story", Integer.toString(id));
                            httpRequest2.addParam("id_genero", gender);

                            is = httpRequest2.execute();
                            result = Util.inputStream2String(is, "UTF-8");
                            httpRequest2.finish();

                            Log.d("HTTP_REQUEST_RESULT", result);

                            Intent i = new Intent(getActivity(), StoryActivity.class);
                            i.putExtra("idhist", Integer.toString(id));
                            startActivity(i);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}