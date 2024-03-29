package com.example.fitnessapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.MainActivity;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.User;
import com.example.fitnessapplication.utils.Constant;
import com.example.fitnessapplication.utils.FragmentNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class RegistrationFragment extends Fragment {

    public static final String TAG = RegistrationFragment.class.getSimpleName();

    private View view;
    private EditText etName, etUsername, etPassword, etConfirmPassword;
    private Button btnRegistration;
    private RadioGroup rgTrainerTrainee;
    private String selectedUserType;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        if (checkIfInsertedDatasAreCorrect()) {
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean found = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.child(Constant.USERNAME).getValue().toString().equals(etUsername.getText().toString())) {
                            found = true;
                            Toast.makeText(getActivity(), R.string.reg_user_already_exists, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if (!found) {
                        String KEY = mRef.push().getKey();
                        if (selectedUserType.equals(Constant.TRAINER)) {
                            User user = new User(KEY, etName.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString(), true, false);
                            mRef.child(KEY).setValue(user);
                            Toast.makeText(getActivity(), R.string.reg_user_created, Toast.LENGTH_SHORT).show();
                        } else if (selectedUserType.equals(Constant.TRAINEE)) {
                            User user = new User(KEY, etName.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString(), false, true);
                            mRef.child(KEY).setValue(user);
                            Toast.makeText(getActivity(), R.string.reg_user_created, Toast.LENGTH_SHORT).show();
                        }
                        FragmentNavigation.getInstance(getContext()).onBackPressed((MainActivity) getActivity());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i(Constant.FIREBASE_ON_CANCELLED, Constant.FIREBASE_ON_CANCELLED);
                }
            });
        }
    }

    //username should contain 6 characters
    //password should contain 6 characters
    //name should contain 6 characters
    private boolean checkNameAndUsernameAndPasswordLength(String username, String password, String name) {
        return username.length() >= 6 && password.length() >= 6 && name.length() >= 6
                && username.length() <= 20 && password.length() <= 20 && name.length() <= 20;
    }

    private boolean checkEnteredPasswordMatch(String password, String confirm_password) {
        return password.equals(confirm_password);
    }

    private boolean checkIfInsertedDatasAreCorrect() {
        if (selectedUserType == null) {
            Toast.makeText(getContext(), R.string.reg_select_user_type, Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean passwordsMatch = checkEnteredPasswordMatch(etPassword.getText().toString(), etConfirmPassword.getText().toString());
        boolean usernamePasswordLength = checkNameAndUsernameAndPasswordLength(etUsername.getText().toString(), etPassword.getText().toString(), etName.getText().toString());
        if (!passwordsMatch) {
            Toast.makeText(getContext(), R.string.reg_password_dont_match, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!usernamePasswordLength) {
            Toast.makeText(getContext(), R.string.reg_characters_fail, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initializeViewElements(View view) {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.USERS);
        etName = view.findViewById(R.id.editText_registration_name);
        etUsername = view.findViewById(R.id.editText_registration_username);
        etPassword = view.findViewById(R.id.editText_registration_password);
        etConfirmPassword = view.findViewById(R.id.editText_registration_confirm_password);
        btnRegistration = view.findViewById(R.id.button_registration);
        rgTrainerTrainee = view.findViewById(R.id.radioGroup_registration_user_type);
        //Create radio buttons into radiogroup
        final List<String> userTypes = Arrays.asList(getResources().getStringArray(R.array.registration_user_types));
        for (int i = 0; i < userTypes.size(); ++i) {
            RadioButton rb = new RadioButton(getActivity());
            rb.setText(userTypes.get(i));
            rb.setId(i);
            //What happens when we choose one of them
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        MainActivity ma = (MainActivity) getActivity();
                        selectedUserType = compoundButton.getText().toString();
                    } else {
                        selectedUserType = "";
                    }
                }
            });
            rgTrainerTrainee.addView(rb);
        }
    }
}
