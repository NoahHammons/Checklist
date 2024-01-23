package com.example.checklist.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.checklist.Items;
import com.example.checklist.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ItemsViewModel extends ViewModel {
    ObservableArrayList<Items> items = new ObservableArrayList<>();
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<Items> currentitem = new MutableLiveData<>();

    public ItemsViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public ObservableArrayList<Items> getItems() {
        return items;
    }


    public void updateItem (Items item){
        Items newItem = new Items(auth.getCurrentUser().getEmail(), item.getName(), item.getchecked(), item.getId(), item.getList() );
        db.collection("Items").document(item.getId()).set(newItem).addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                currentitem.postValue(newItem);
                Log.d("__FIREBASE", "update succsessful");
            }
            else {
                Log.d("__FIREBASE", "update failed");
            }
        }));
    }

    public void saveItem (String email, String name, Lists list){

        Items newItems = new Items  (email,name, false, "", list );
        db.collection("Items").add(newItems).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                task.getResult().getId();
            }

        });

    }
    public void removeItem(Items item){
        Items newItem = new Items(auth.getCurrentUser().getEmail(), item.getName(), item.getchecked(), item.getId(), item.getList());
        db.collection("Items").document(item.getId()).delete().addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                currentitem.postValue(newItem);
                Log.d("__FIREBASE", "update succsessful");
            }
            else {
                Log.d("__FIREBASE", "update failed");
            }
        }));
    }
    public void loadItems(){
        items.clear();
        db.collection("Items")
                .orderBy("name")
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()){
                        QuerySnapshot collection = task.getResult();
                        items.addAll(collection.toObjects(Items.class));
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

    public void setCurrentItem(Items currentItem){this.currentitem.setValue(currentItem);}

    public MutableLiveData<Items> getCurrentItem() {
        return currentitem;
    }

    public String getName(){return auth.getCurrentUser().getEmail();}
}
