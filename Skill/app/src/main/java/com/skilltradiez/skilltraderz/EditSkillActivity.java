package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * Our application is based off of the notion of facilitating the entire process of one user
 * being able to offer another user a skill. They make a trade from one user to another user
 * through our application.
 *
 * That being said, how possibly could the ability to edit a skill (THE thing offered during trades)
 * not be one of our core functionalities?!
 *
 * This class will be all about editing the skills that users offer, storing relevant values into
 * a swath of attributes and then giving other classes the methods to interact with this!
 *
 * ~~ACCESS:
 * This class is a public class, meaning that any other part of the application (class, etc) has
 * complete access to this class and it's functions once it is instantiated into an object. For
 * the ability to edit skills, this allows the ability to actually edit skills to be pervasive
 * throughout our entire application. Meaning that this is not locked down to one single class.
 * Flexibility is critical.
 *
 * However this is likely a large misnomer of the word "class" , as this is actually an activity
 * (more UI related) as well as utilizing the android framework to make all of our classes
 * come to life.
 *
 *
 * ~~CONSTRUCTOR:
 * As is typical with android activities used in applications, this activity once invoked will
 * be made with the onCreate() method followed by anything present in the onStart() method.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: SKILLNAME:
 *     This is going to be extremely relevant on this part of the application, when we have the
 *     users actually go around trying to trade their skills is it not considered critical to have
 *     some sort of label for it? I mean in an idealistic world we could always just assign the
 *     name an arbitrary value that blank and let users read descriptions (as to not be swayed
 *     by potentially flawed name choices!)... but alas we do not live in such a world filled with
 *     boundless memes.
 *
 *     WE PROVIDE THE METHODS TO:
 *         Nothing. This is created during the onStart() method.
 *
 * 2: SKILLDESCRIPTION:
 *     This is going to be the ever importaint location where users may stroke their ego.
 *     "Oh I may be a dog trainer but I truly won 1337 poodle olympics, and I can cut a poodles
 *     hair into all sorts of shapes and da'aling it is just absolutely gaaawgeous."-- or something
 *     absolutely mortifying like this.
 *
 *     Pretty much without this we couldn't let people uniquely identify their skill. Why would
 *     I want Big Bertha's cat grooming business over that of the lovely Anastasia? The differences
 *     in their description. But keep in mind we don't actually have a verification method so
 *     I am certain if one wanted they could say they are offering the skill of flying people
 *     to the moon on a baboon while singing a tune about a forlorn swoon.
 *
 *     WE PROVIDE THE METHODS TO:
 *         Nothing. This is, similar to above, created during the onStart() method.
 *
 * 3: SKILLCATEGORY:
 *     We can all pretend we don't like our categories, we can go onto (some random webpage)
 *     and I am confident in my claim that I can somehow offend someone by saying a tomato
 *     is a fruit. "BUT NO IT IS A VEGETABLE!!!1!!1!1!". ALAS-- we are using categories to help
 *     us define general types for users to interact with.
 *
 *     Allow me to demonstrate through example:
 *     CATEGORY: Cats
 *     Skill 1 (from random user 1): Cat bathing (Very daring.)
 *     Skill 2: Cat petting (Very loving.)
 *     Skill 3: Cat grooming (Much wow.)
 *     Skill 4: Teaching a cat how to duel with light sabers because I am really REALLY hyped
 *     for a particular movie coming out soon. Lightsabers. Cats. Nothing could go wrong.
 *
 *     So this example hopefully demonstrates that given a "cat" CATegory (oh my, how punny) we
 *     will actually be able to let users pin point a finer granularity in their searches.
 *
 *     WE PROVIDE THE METHODS TO:
 *         Nothing. This is created during the onStart() method.
 *
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class EditSkillActivity extends GeneralMenuActivity {
    private Skill newSkill;
    private EditText skillName;
    private EditText skillDescription;
    private Spinner skillCategory;
    private Button addSkillToDB;
    private CheckBox visible;
    private Context edSkillContext = this;
    private Bundle editSkillExtras;

    private String editName;
    private String editDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editSkillExtras = getIntent().getExtras();
        masterController = new MasterController();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skill);

        skillName = (EditText) findViewById(R.id.new_skill_name);
        skillDescription = (EditText) findViewById(R.id.new_skill_description);
        addSkillToDB = (Button) findViewById(R.id.add_skill_to_database);
        skillCategory = (Spinner) findViewById(R.id.category_spinner);
        visible = (CheckBox) findViewById(R.id.is_visible);

        //Android Developers
        // http://developer.android.com/guide/topics/ui/controls/spinner.html
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_spinner_strings, android.R.layout.simple_spinner_item);
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillCategory.setAdapter(adapter);

    }

    public EditText getSkillName() {
        return skillName;
    }

    public EditText getSkillDescription() {
        return skillDescription;
    }

    public Spinner getSkillCategory() {
        return skillCategory;
    }

    public Button getAddSkillToDB() {
        return addSkillToDB;
    }

    public CheckBox getVisible() {
        return visible;
    }



    public void onStart(){
        //TODO this should optionally take a skill ID via intent to edit, instead of creating a new one.
        super.onStart();


        //@todo: get extras as provided from the skilldescription activity and set the skill name to the extras
        // We need to be able to edit an existing skill
        if(editSkillExtras != null){
            editName = editSkillExtras.getString("skillName");
            editDescription = editSkillExtras.getString("skillDescription");
            skillName.setText("editName");
            skillDescription.setText("editDescription");
        }

        //@todo: change the "add skill" button to "Save changes" button if you're editing an existing skill
        //@todo: make sure that you have actually made changes and then set. if no change just return to inventory
    }

    /**
     * add skill to the database
     */
    public void addNewSkill(View view){
        //Character limit of skill name set to 40 characters
        String name = skillName.getText().toString();
        String description = skillDescription.getText().toString();
        boolean isVisible;
        isVisible = visible.isChecked();

        if (name.length() == 0 || description.length() == 0) {
            // this makes a pop-up alert with a dismiss button.
            // source credit: http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
            AlertDialog.Builder alert = new AlertDialog.Builder(edSkillContext);
            alert.setMessage("Please make sure all fields are filled!\n");
            alert.setCancelable(true);
            alert.setPositiveButton("retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog toolong_alert = alert.create();
            toolong_alert.show();
            return;
        }

        //Make a new skill through the controller.
        masterController.makeNewSkill(name, skillCategory.getSelectedItem().toString(), description, isVisible, new NullImage());
        CDatabaseController.save();

        //Toasty
        Context context = getApplicationContext();
        Toast.makeText(context, "You made a skill!", Toast.LENGTH_SHORT).show();

        skillName.setText("");
        skillDescription.setText("");

    }
}
