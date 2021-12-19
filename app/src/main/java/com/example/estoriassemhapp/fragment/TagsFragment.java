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
import com.example.estoriassemhapp.adapter.StoryAdapter;
import com.example.estoriassemhapp.adapter.TagsAdapter;
import com.example.estoriassemhapp.model.MainViewModel;
import com.example.estoriassemhapp.model.Story;
import com.example.estoriassemhapp.model.Tag;
import com.example.estoriassemhapp.model.TagsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TagsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TagsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public TagsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TagsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TagsFragment newInstance() {
        TagsFragment fragment = new TagsFragment();
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
        return inflater.inflate(R.layout.fragment_tags, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView rvTags = getView().findViewById(R.id.rvTags);
        rvTags.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTags.setLayoutManager(layoutManager);

        //Configuração do Adapter (parte do recycler view)
        TagsViewModel tagsViewModel = new ViewModelProvider(getActivity()).get(TagsViewModel.class);
        LiveData<List<Tag>> tags = tagsViewModel.getTags();
        tags.observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags1) {
                TagsAdapter tagsAdapter = new TagsAdapter(getContext(), tags1);
                rvTags.setAdapter(tagsAdapter);
            }
        });
    }
}