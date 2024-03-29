package com.example.fitnessapplication.fragment.trainee;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.utils.Constant;
import com.example.fitnessapplication.utils.FragmentNavigation;

import java.util.Arrays;
import java.util.List;

public class TraineeMuscleGroupsFragment extends Fragment {

    public final static String TAG = TraineeMuscleGroupsFragment.class.getSimpleName();

    private View view;
    private LinearLayout linearLayoutTraineeMuscleGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_muscle_group, container, false);
        linearLayoutTraineeMuscleGroup = view.findViewById(R.id.linearLayout_trainee_muscle_groups);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButtons();
    }

    private void addButtons() {
        final List<String> buttonString = Arrays.asList(getResources().getStringArray(R.array.trainee_muscle_groups));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15, 15, 15, 15);
        for (int i = 0; i < buttonString.size(); ++i) {
            final int ii = i;
            Button btn = new Button(getContext());
            btn.setText(buttonString.get(i));
            btn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            btn.setPadding(10,10,10,10);
            btn.setTextColor(getResources().getColor(R.color.colorText));
            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constant.SELECTED_MUSCLE_GROUP = buttonString.get(ii);
                    FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeMuscleGroupExercisesFragment(), R.id.content_fragment);
                    //go to fragment trainee muscle group exercises
                    //passing somehow which muscle group should be shown
                }
            });
            linearLayoutTraineeMuscleGroup.addView(btn);
        }
    }
}
