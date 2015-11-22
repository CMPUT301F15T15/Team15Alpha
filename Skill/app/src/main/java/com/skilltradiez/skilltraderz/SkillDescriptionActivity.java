package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire skill description process that the user will need to interact
 * with through our application.
 *
 * ~~ACCESS:
 * This may seem redundant but for formatting purposes... this is a "public" class, meaning that
 * we can have this class actually be accessed technically anywhere in the application that
 * calls it. But since this is an activity it may seem a bit strange to refer to instantiating
 * an instance of the "EditTradeActivity" object.
 *
 * Instead what is happening is that we are having this activity be called by the onCreate() method
 * as is traditionally done in the android studio framework for android applications. In this
 * instance we're going to create this activity and then we'll have an onstart() method following
 * this which is going to make it so that we have this activate a cascade of events that are all
 * interelated with the main primary goal of allowing us to have a screen where we edit the
 * trading activity!
 *
 *~~CONSTRUCTOR:
 * Upon calling the method onCreate() for this activity the android studio framework will
 * cause the android application to create an instance of this actvity and display it to the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: SETSKILLTITLE:
 *     This is more of a UI element but this will allow us to actually set the value of the title
 *     for this particular skill description for the particular application.
 * 2: SETSKILLDESCRIPTION:
 *     Suppose we want to have the user modify the skill description, through the UI this
 *     method will be invoked that will allow the user to actually set their skill description
 *     for the rest of the application.
 *
 * 3: ADDREMOVESKILL:
 *     This is going to be the entire process of adding or removing a skill from a skill description
 *     when we actually have the user interact with the UI to call this method that will
 *     allow them to add or remove a damn skill.
 *
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 *    Team15Alpha
 *    AppName: SkillTradiez (Subject to change)
 *    Copyright (C) 2015  Stephen Andersen, Falon Scheers, Elyse Hill, Noah Weninger, Cole Evans
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class SkillDescriptionActivity extends GeneralMenuActivity {
    private Skill currentSkill;
    private Button addRemoveSkill;
    private TextView skillTitle;
    private TextView skillDescription;
    private Context skillDescripContext = this;
    private Boolean hasSkill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);
        masterController = new MasterController();

        currentSkill = CDatabaseController.getSkillByID((ID) getIntent().getExtras().get("skill_id"));

        addRemoveSkill = (Button) findViewById(R.id.add_remove_skill);
        skillDescription = (TextView) findViewById(R.id.skill_description);
        skillTitle = (TextView) findViewById(R.id.skillTitle);

        setSkillTitle(currentSkill.getName());
        setSkillDescription(currentSkill.getDescription());

        User user = masterController.getCurrentUser();
        Inventory inv = user.getInventory();

        hasSkill = inv.hasSkill(currentSkill);
        if (hasSkill)
            addRemoveSkill.setText("Remove Skill");
        // It's initially set to "Add Skill"
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    /**
     * @ TODO:?
     */
    public void setSkillTitle(String text){
        //skillTitle = title of the skill we're looking at
        skillTitle.setText(text);
    }

    /**
     * @ TODO:?
     */
    public void setSkillDescription(String text){
        //skillDescription = description of the skill we're looking at
        skillDescription.setText(text);
    }

    /**
     * Adds or removes a skill from a user's list or trade request
     */
    public void addRemoveSkill(View v){
        //notify the user that the skill has been added to their profile or trade request depending
        //on what context we're given ie: trade request vs skill search
        if (hasSkill) {
            //Set text to the OPPOSITE of what is happening.
            addRemoveSkill.setText("Add Skill");
            //Evoke controller to REMOVE skill.
            masterController.removeCurrentSkill(currentSkill);
        } else {
            //Set text yet again to the OPPOSITE of what the function is.
            addRemoveSkill.setText("Remove Skill");
            //Evoke controller to ADD skill.
            masterController.addCurrentSkill(currentSkill);
        }
        hasSkill = !hasSkill;
        CDatabaseController.save();
    }

    public Button getAddRemoveSkill(){
        return addRemoveSkill;
    }

    public void editSkill(View view){
        Intent intent = new Intent(skillDescripContext, EditSkillActivity.class);
        //intent.getExtras(currentSkill.getName().toString(),);
        startActivity(intent);
    }

}
