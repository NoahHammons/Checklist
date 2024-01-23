package com.example.checklist.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.checklist.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ListsViewModel extends ViewModel {
    ObservableArrayList<Lists> items = new ObservableArrayList<>();
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<Lists> currentitem = new MutableLiveData<>();

    public ListsViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public ObservableArrayList<Lists> getLists() {
        return items;
    }


    public void updateList (Lists item){
        Lists newList = new Lists(auth.getCurrentUser().getEmail(), item.getName(), item.getId() );
        db.collection("Lists").document(item.getId()).set(newList).addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                currentitem.postValue(newList);
                Log.d("__FIREBASE", "update succsessful");
            }
            else {
                Log.d("__FIREBASE", "update failed");
            }
        }));
    }

    public void saveList (String email, String name){

        Lists newLists = new Lists  (email,name, "");
        db.collection("Lists").add(newLists).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                task.getResult().getId();
            }

        });

    }
    public void removeList(Lists item){
        Lists newList = new Lists(auth.getCurrentUser().getEmail(), item.getName(), item.getId() );
        db.collection("Lists").document(item.getId()).delete().addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                currentitem.postValue(newList);
                Log.d("__FIREBASE", "update succsessful");
            }
            else {
                Log.d("__FIREBASE", "update failed");
            }
        }));
    }
    public void loadLists(){
        items.clear();
        db.collection("Lists")
                .orderBy("name")
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()){
                        QuerySnapshot collection = task.getResult();
                        items.addAll(collection.toObjects(Lists.class));
                        List<DocumentSnapshot> docs = collection.getDocuments();
                        for(int i = 0; i < docs.size(); i++){
                            items.get(i).setId(docs.get(i).getId());
                        }
                    }
                    else {
                        Log.d("__FIREBASE", "somthing went wrong");
                    }
                });
    }

    public void setCurrentList(Lists currentList){this.currentitem.setValue(currentList);}

    public MutableLiveData<Lists> getCurrentList() {
        return currentitem;
    }

    public String getName(){return auth.getCurrentUser().getEmail();}
}
