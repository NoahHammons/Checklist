package com.example.checklist.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checklist.Items;
import com.example.checklist.ItemsAdapter;
import com.example.checklist.R;
import com.example.checklist.databinding.FragmentHomeBinding;
import com.example.checklist.databinding.FragmentLoginBinding;
import com.example.checklist.viewmodels.ItemsViewModel;
import com.example.checklist.viewmodels.ListsViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        NavController controller = NavHostFragment.findNavController(this);
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        binding.bannerAd.loadAd(
                new AdRequest.Builder().build()
        );
        FirebaseAuth auth = FirebaseAuth.getInstance();
        ListsViewModel ListviewModel = new ViewModelProvider(getActivity()).get(ListsViewModel.class);
        ItemsViewModel viewModel = new ViewModelProvider(getActivity()).get(ItemsViewModel.class);
        binding.setViewmodel(viewModel);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        binding.checkedswitch.setOnClickListener((task) ->{
            viewModel.loadItems();
        });
        viewModel.loadItems();
        viewModel.getItems().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Items>>() {
            @Override
            public void onChanged(ObservableList<Items> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<Items> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Items> sender, int positionStart, int itemCount) {

                ObservableList items = new ObservableArrayList();

                for (Items item : sender) {
                    if(!item.getList().equals(ListviewModel.getCurrentList())) {
                        if (binding.checkedswitch.isChecked()) {
                            if(!item.getchecked()){
                                items.add(item);
                            }
                        }
                        else
                        items.add(item);
                    }

                }
                recyclerView.setAdapter(new ItemsAdapter(items, item -> {
                    viewModel.setCurrentItem(item);
                    if(binding.editswitch.isChecked()){
                        controller.navigate(R.id.action_homeFragment_to_addItemFragment);
                    }
                    else{
                        viewModel.updateItem(item);
                    }
                }));
            }

            @Override
            public void onItemRangeMoved(ObservableList<Items> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Items> sender, int positionStart, int itemCount) {

            }
        });



        binding.item.setOnClickListener(view1 -> {
            viewModel.setCurrentItem(null);
            controller.navigate(R.id.action_homeFragment_to_addItemFragment);

        });
        binding.ListPage.setOnClickListener(view1 -> {
            viewModel.setCurrentItem(null);
            controller.navigate(R.id.action_homeFragment_to_listFragment);

        });
        binding.logoutButton.setOnClickListener(view2 -> {
            auth.signOut();
            controller.navigate(R.id.action_homeFragment_to_LoginFragment);
        });
        return binding.getRoot();
    }
}