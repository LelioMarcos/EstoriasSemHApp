package com.example.estoriassemhapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.CommentsActivity;
import com.example.estoriassemhapp.activity.LoginActivity;
import com.example.estoriassemhapp.activity.StoryActivity;
import com.example.estoriassemhapp.adapter.StoryAdapter;
import com.example.estoriassemhapp.model.MainViewModel;
import com.example.estoriassemhapp.model.ProfileModel;
import com.example.estoriassemhapp.model.Story;
import com.example.estoriassemhapp.model.StoryViewModel;
import com.example.estoriassemhapp.util.Config;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView rvStory = getView().findViewById(R.id.rvStories);
        rvStory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvStory.setLayoutManager(layoutManager);

        //Configuração do Adapter (parte do recycler view)
        ProfileModel profileModel = new ViewModelProvider(getActivity(), new ProfileModel.ProfileModelFactory(Config.getId(getContext()))).get(ProfileModel.class);
        LiveData<List<Story>> stories = profileModel.getStories();
        stories.observe(getViewLifecycleOwner(), new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> stories) {
                StoryAdapter storyAdapter = new StoryAdapter(getContext(), stories);
                rvStory.setAdapter(storyAdapter);
            }
        });

        ImageButton btnLogout = getView().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Deseja mesmo sair?");
                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Config.setLogin(getContext(), "", "");
                        Config.setPassword(getContext(), "");
                        Intent in = new Intent(getContext(), LoginActivity.class);
                        startActivity(in);
                    }
                });
                alertDialog.setNegativeButton("Não", null);
                alertDialog.show();
            }
        });

    }
}