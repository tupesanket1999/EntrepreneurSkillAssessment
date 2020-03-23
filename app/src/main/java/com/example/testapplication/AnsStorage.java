package com.example.testapplication;

import java.util.ArrayList;
import java.util.List;

public class AnsStorage {

    public List<Integer> marks=new ArrayList<Integer>();

    public AnsStorage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AnsStorage(int [] ans){
        for (int i =0 ; i < ans.length ; i++){
            marks.add(ans[i]);
        }
    }
}
