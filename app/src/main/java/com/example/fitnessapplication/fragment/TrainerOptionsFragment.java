package com.example.fitnessapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.utils.FragmentNavigation;

public class TrainerOptionsFragment extends Fragment {

    public static final String TAG = TrainerOptionsFragment.class.getSimpleName();

    private View view;
    private Button btn_upload, btn_my_content, btn_settings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainer_options, container, false);

        initializeViewElements(view);
        btn_my_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyContentClicked();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUploadClicked();
            }
        });


        return view;
    }

    private void initializeViewElements(View view) {
        btn_my_content = view.findViewById(R.id.button_option_trainer_my_content);
        btn_settings = view.findViewById(R.id.button_option_trainer_settings);
        btn_upload = view.findViewById(R.id.button_option_trainer_upload);
    }

    private void onMyContentClicked(){
        FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerMyContentFragment(),R.id.content_fragment);
    }

    private void onUploadClicked(){
        FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerUploadFragment(),R.id.content_fragment);
    }
}
