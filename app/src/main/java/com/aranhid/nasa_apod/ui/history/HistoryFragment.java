package com.aranhid.nasa_apod.ui.history;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aranhid.nasa_apod.HistoryListAdapter;
import com.aranhid.nasa_apod.R;
import com.aranhid.nasa_apod.SearchHistory;
import com.aranhid.nasa_apod.SearchHistoryDB;
import com.aranhid.nasa_apod.SearchHistoryItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    ListView listView;
    HistoryListAdapter adapter;

    SearchHistoryDB db;
    ArrayList<SearchHistoryItem> historyItems;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        listView = root.findViewById(R.id.history_list);
        historyItems = new ArrayList<>();
        adapter = new HistoryListAdapter(getContext(), historyItems);
        listView.setAdapter(adapter);
        db = SearchHistoryDB.create(this.getContext(), false);
        getHistory();

        return root;
    }

    public void updateList(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void getHistory() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SearchHistory searchHistory = db.searchHistory();
                historyItems.clear();
                historyItems.addAll(searchHistory.selectAll());
                updateList();
                Log.d("TAG", historyItems.toString());
            }
        });
    }
}

