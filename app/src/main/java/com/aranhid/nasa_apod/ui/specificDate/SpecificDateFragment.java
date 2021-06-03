package com.aranhid.nasa_apod.ui.specificDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aranhid.nasa_apod.R;

public class SpecificDateFragment extends Fragment {

    private SpecificDateViewModel specificDateViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        specificDateViewModel =
                new ViewModelProvider(this).get(SpecificDateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_specific_date, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        specificDateViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}