package com.example.checklist.ui;

import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;

//import com.example.checklist.Notes;
import com.example.checklist.R;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.checklist.databinding.FragmentHomeBinding;
import com.example.checklist.databinding.FragmentLoginBinding;
//import com.example.checklist.viewmodels.NotesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText emailField = binding.emailField;
        EditText passwordField = binding.passwordField;
        NavController controller = NavHostFragment.findNavController(this);


        if(auth.getCurrentUser() != null){
            controller.navigate(R.id.action_LoginFragment_to_listFragment);
        }
        //login
        binding.login.setOnClickListener(view2 -> {

            if(emailField.getText().toString().isEmpty()){
                TextView userinfo = binding.info;
                userinfo.setText("missing email");
            }
            else if (passwordField.getText().toString().isEmpty())
            {
                TextView userinfo = binding.info;
                userinfo.setText("missing password");
            }
            else {
                auth.signInWithEmailAndPassword(
                        emailField.getText().toString(),
                        passwordField.getText().toString()
                ).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        controller.navigate(R.id.action_LoginFragment_to_listFragment);

                    } else {
                        TextView userinfo = binding.info;
                        userinfo.setText(task.getException().getMessage());
                    }
                });
            }
        });

        binding.signup.setOnClickListener(view2 -> {
            controller.navigate(R.id.action_LoginFragment_to_signupFragment);
        });



        return binding.getRoot();
    }
}