package com.skilltradiez.skilltraderz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

public class Hodor extends ActionBarActivity {

    ExpandableListView expandedListView;
    ExpandableListAdapter listAdapter;
    List<String> listOne;
    //HashMap<String> listTwo;



    String[] gameOfThrones = {"Renly", "Arya"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodor);

        //connect UI to variable
        expandedListView = (ExpandableListView) findViewById(R.id.expandableListView);

        //run data
        doTheDataExample();

        //listAdapter = new ExpandableListAdapter(this, listOne, listTwo);




    }

    //Method to actually populate it.
    public void doTheDataExample(){

    }




}
