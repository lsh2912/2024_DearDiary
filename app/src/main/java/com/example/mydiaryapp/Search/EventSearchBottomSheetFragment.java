/*
package com.example.mydiaryapp.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiaryapp.Event.Event;
import com.example.mydiaryapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class EventSearchBottomSheetFragment extends BottomSheetDialogFragment {

    private List<Event> eventList;

    public static EventSearchBottomSheetFragment newInstance(List<Event> events) {
        EventSearchBottomSheetFragment fragment = new EventSearchBottomSheetFragment();
        fragment.eventList = events;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_search_bottom_sheet, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new SearchEventAdapter(eventList));

        return view;
    }
}
*/
