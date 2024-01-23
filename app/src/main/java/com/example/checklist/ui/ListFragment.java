package com.example.checklist.ui;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checklist.Lists;
import com.example.checklist.ListsAdapter;
import com.example.checklist.R;
import com.example.checklist.databinding.FragmentListBinding;

import com.example.checklist.viewmodels.ListsViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    FragmentListBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        NavController controller = NavHostFragment.findNavController(this);
        binding = FragmentListBinding.inflate(inflater,container,false);

        binding.bannerAd.loadAd(
                new AdRequest.Builder().build()
        );
        FirebaseAuth auth = FirebaseAuth.getInstance();
        ListsViewModel viewModel = new ViewModelProvider(getActivity()).get(ListsViewModel.class);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        viewModel.loadLists();
        viewModel.getLists().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Lists>>() {
            @Override
            public void onChanged(ObservableList<Lists> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<Lists> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Lists> sender, int positionStart, int itemCount) {
                String user = viewModel.getName();
                ObservableList lists = new ObservableArrayList();

                for ( Lists list : sender) {
                    if(list.getEmail().equals(user)) {
                        lists.add(list);
                        }
                }
                recyclerView.setAdapter(new ListsAdapter(lists, list -> {
                    viewModel.setCurrentList(list);
                    if(binding.editswitch.isChecked()){
                        controller.navigate(R.id.action_listFragment_to_newListFragment);
                    }
                    else{
                        controller.navigate(R.id.action_listFragment_to_homeFragment);
                    }
                }));
            }

            @Override
            public void onItemRangeMoved(ObservableList<Lists> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Lists> sender, int positionStart, int itemCount) {

            }
        });



        binding.item.setOnClickListener(view1 -> {
            viewModel.setCurrentList(null);
            controller.navigate(R.id.action_listFragment_to_newListFragment);

        });

        binding.logoutButton.setOnClickListener(view2 -> {
            auth.signOut();
            controller.navigate(R.id.action_listFragment_to_homeFragment);
        });
        return binding.getRoot();
    }
}