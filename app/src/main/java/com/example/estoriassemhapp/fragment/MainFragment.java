package com.example.estoriassemhapp.fragment;

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

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.MainActivity;
import com.example.estoriassemhapp.adapter.StoryAdapter;
import com.example.estoriassemhapp.model.MainViewModel;
import com.example.estoriassemhapp.model.Story;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Configuração do recycler view
        RecyclerView rvStory = getView().findViewById(R.id.rvStories);
        rvStory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvStory.setLayoutManager(layoutManager);

        //Configuração do Adapter (parte do recycler view)
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        LiveData<List<Story>> stories = mainViewModel.getStories();
        stories.observe(getViewLifecycleOwner(), new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> stories) {
                StoryAdapter storyAdapter = new StoryAdapter(getContext(), stories);
                rvStory.setAdapter(storyAdapter);
            }
        });



    }
}