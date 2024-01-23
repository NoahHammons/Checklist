package com.example.checklist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.checklist.R;
import com.example.checklist.databinding.FragmentHomeBinding;
import com.example.checklist.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment {

    FragmentSignupBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        binding = FragmentSignupBinding.inflate(inflater,container,false);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText emailField = binding.editEmail;
        EditText passwordField = binding.editPassword;
        EditText CemailField = binding.editCEmail;
        EditText CpasswordField = binding.editCPassword;
        NavController controller = NavHostFragment.findNavController(this);
        //login
        binding.signUpButton.setOnClickListener(view2 -> {
            if(emailField.getText().toString().isEmpty() || CemailField.getText().toString().isEmpty()){
                TextView userinfo = binding.info2;
                userinfo.setText("missing email");
            }
            else if (passwordField.getText().toString().equals("") || CpasswordField.getText().toString().equals(""))
            {
                TextView userinfo =  binding.info2;
                userinfo.setText("missing password");
            }
            else if(!emailField.getText().toString().equals(CemailField.getText().toString())){
                TextView userinfo =  binding.info2;
                userinfo.setText("emails did not match");
            }
            else if (!passwordField.getText().toString().equals(CpasswordField.getText().toString())){
                TextView userinfo =  binding.info2;
                userinfo.setText("Passwords did not match");
            }
            else{
                auth.createUserWithEmailAndPassword(
                        emailField.getText().toString(),
                        passwordField.getText().toString()
                ).addOnCompleteListener((task)->{
                    if(task.isSuccessful()){
                        controller.navigate(R.id.action_signupFragment_to_homeFragment);
                    }
                    else{
                        TextView userinfo =  binding.info2;
                        userinfo.setText(task.getException().getMessage());
                    }
                });
            }

        });
        return binding.getRoot();
    }
}