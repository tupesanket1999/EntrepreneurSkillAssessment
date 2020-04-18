package com.nabha.EntrepreneurSkillAssessment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnsStorage {

    public List<Integer> marks=new ArrayList<Integer>();
    public String time;

    public AnsStorage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AnsStorage(int [] ans){
        for (int i =0 ; i < ans.length ; i++){
            marks.add(ans[i]);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        time = new SimpleDateFormat("dd-MM-yyyy--HH-mm").format(timestamp);
    }
}
