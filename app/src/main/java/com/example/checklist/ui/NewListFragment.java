package com.example.checklist.ui;

import android.os.Bundle;

import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.checklist.Lists;
import com.example.checklist.R;
import com.example.checklist.Throttler;
import com.example.checklist.databinding.FragmentHomeBinding;
import com.example.checklist.databinding.FragmentNewListBinding;
import com.example.checklist.viewmodels.ListsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NewListFragment extends Fragment {
    FragmentNewListBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        binding = FragmentNewListBinding.inflate(inflater,container,false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailField = auth.getCurrentUser().getEmail();
        NavController controller = NavHostFragment.findNavController(this);
        binding.deletebutton.setVisibility(View.GONE);

        EditText NameField = binding.ItemName;

        ListsViewModel viewModel =  new ViewModelProvider(getActivity()).get(ListsViewModel.class);
        viewModel.getCurrentList().observe(getViewLifecycleOwner(),(item) ->{

            if(item!=null) {
                binding.deletebutton.setVisibility(View.VISIBLE);
                NameField.setText(item.getName());
            }



            Throttler savethrottler = new Throttler(() ->{
                if(item!=null) {
                    item.setName(NameField.getText().toString());
                    viewModel.updateList(item);
                }
                else {
                    viewModel.saveList(emailField, NameField.getText().toString());

                }

                controller.navigate(R.id.action_newListFragment_to_listFragment);
            },2000);
            binding.save.setOnClickListener(view2 -> {
                if(NameField.getText().toString().isEmpty()){
                    TextView userinfo = binding.info3;
                    userinfo.setText("missing name of item");
                }
                else {
                    savethrottler.execute();
                }
            });
            binding.deletebutton.setOnClickListener(view -> {
                viewModel.removeList(item);

                controller.navigate(R.id.action_newListFragment_to_listFragment);
            });
        });

        return binding.getRoot();
    }

}