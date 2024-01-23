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

import com.example.checklist.Items;
import com.example.checklist.R;
import com.example.checklist.Throttler;
import com.example.checklist.databinding.FragmentHomeBinding;
import com.example.checklist.databinding.FragmentAddItemBinding;
import com.example.checklist.viewmodels.ItemsViewModel;
import com.example.checklist.viewmodels.ListsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddItemFragment extends Fragment {
    FragmentAddItemBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        binding = FragmentAddItemBinding.inflate(inflater,container,false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailField = auth.getCurrentUser().getEmail();
        NavController controller = NavHostFragment.findNavController(this);
        binding.deletebutton.setVisibility(View.GONE);

        EditText NameField = binding.ItemName;

        ListsViewModel ListviewModel =  new ViewModelProvider(getActivity()).get(ListsViewModel.class);

        ItemsViewModel viewModel =  new ViewModelProvider(getActivity()).get(ItemsViewModel.class);
        viewModel.getCurrentItem().observe(getViewLifecycleOwner(),(item) ->{

            if(item!=null) {
                binding.deletebutton.setVisibility(View.VISIBLE);
                NameField.setText(item.getName());
            }



            Throttler savethrottler = new Throttler(() ->{
                if(item!=null) {
                    item.setName(NameField.getText().toString());
                    if(item.getchecked())
                    {
                        item.setChecked(false);
                    }
                    else{
                        item.setChecked(true);
                    }
                    viewModel.updateItem(item);
                }
                else {
                    ListviewModel.getCurrentList().observe(getViewLifecycleOwner(),(lists) ->{
                        viewModel.saveItem(emailField, NameField.getText().toString(), lists);
                    });
                    

                }

                controller.navigate(R.id.action_addItemFragment_to_homeFragment);
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
                viewModel.removeItem(item);

                controller.navigate(R.id.action_addItemFragment_to_homeFragment);
            });
        });

        return binding.getRoot();
    }

}