package com.anioncode.websocket.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessagesViewModel extends ViewModel {


    //this is the data that we will fetch asynchronously
    public MutableLiveData<List<String>> ItemList;
    private ArrayList<String> datas = new ArrayList<>();

    //we will call this method to get the data
    public LiveData<List<String>> getHeroes() {
        //if the list is null
        if (ItemList == null) {
            ItemList = new MutableLiveData<List<String>>();
            //we will load it asynchronously from server in this method
            // loadHeroes();
        }

        //finally we will return the list
        return ItemList;
    }

    public void addItem(String modelmess) {
        //if the list is null
        datas.add(modelmess);
        //finally we will return the list

    }
    public void setItemList() {
        //if the list is null
        ItemList.setValue(datas);
        //finally we will return the list

    }
    public void SetItem(List<String> datas2) {
        //if the list is null

        ItemList.setValue(datas2);
        //finally we will return the list

    }
}