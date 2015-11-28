package com.skilltradiez.skilltraderz;

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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * ~~TYPE: MODEL + CONTROLLER
 * <p/>
 * ~~DESCRIPTION:
 * Our application is based off of the notion of facilitating the entire process of one user
 * being able to offer another user a skill. They make a trade from one user to another user
 * through our application.
 * <p/>
 * That being said, how possibly could the ability to edit a skill (THE thing offered during trades)
 * not be one of our core functionalities?!
 * <p/>
 * This class will be all about editing the skills that users offer, storing relevant values into
 * a swath of attributes and then giving other classes the methods to interact with this!
 * <p/>
 * ~~ACCESS:
 * This class is a public class, meaning that any other part of the application (class, etc) has
 * complete access to this class and it's functions once it is instantiated into an object. For
 * the ability to edit skills, this allows the ability to actually edit skills to be pervasive
 * throughout our entire application. Meaning that this is not locked down to one single class.
 * Flexibility is critical.
 * <p/>
 * However this is likely a large misnomer of the word "class" , as this is actually an activity
 * (more UI related) as well as utilizing the android framework to make all of our classes
 * come to life.
 * <p/>
 * <p/>
 * ~~CONSTRUCTOR:
 * As is typical with android activities used in applications, this activity once invoked will
 * be made with the onCreate() method followed by anything present in the onStart() method.
 * <p/>
 * ~~ATTRIBUTES/METHODS:
 * 1: SKILLNAME:
 * This is going to be extremely relevant on this part of the application, when we have the
 * users actually go around trying to trade their skills is it not considered critical to have
 * some sort of label for it? I mean in an idealistic world we could always just assign the
 * name an arbitrary value that blank and let users read descriptions (as to not be swayed
 * by potentially flawed name choices!)... but alas we do not live in such a world filled with
 * boundless memes.
 * <p/>
 * WE PROVIDE THE METHODS TO:
 * Nothing. This is created during the onStart() method.
 * <p/>
 * 2: SKILLDESCRIPTION:
 * This is going to be the ever importaint location where users may stroke their ego.
 * "Oh I may be a dog trainer but I truly won 1337 poodle olympics, and I can cut a poodles
 * hair into all sorts of shapes and da'aling it is just absolutely gaaawgeous."-- or something
 * absolutely mortifying like this.
 * <p/>
 * Pretty much without this we couldn't let people uniquely identify their skill. Why would
 * I want Big Bertha's cat grooming business over that of the lovely Anastasia? The differences
 * in their description. But keep in mind we don't actually have a verification method so
 * I am certain if one wanted they could say they are offering the skill of flying people
 * to the moon on a baboon while singing a tune about a forlorn swoon.
 * <p/>
 * WE PROVIDE THE METHODS TO:
 * Nothing. This is, similar to above, created during the onStart() method.
 * <p/>
 * 3: SKILLCATEGORY:
 * We can all pretend we don't like our categories, we can go onto (some random webpage)
 * and I am confident in my claim that I can somehow offend someone by saying a tomato
 * is a fruit. "BUT NO IT IS A VEGETABLE!!!1!!1!1!". ALAS-- we are using categories to help
 * us define general types for users to interact with.
 * <p/>
 * Allow me to demonstrate through example:
 * CATEGORY: Cats
 * Skill 1 (from random user 1): Cat bathing (Very daring.)
 * Skill 2: Cat petting (Very loving.)
 * Skill 3: Cat grooming (Much wow.)
 * Skill 4: Teaching a cat how to duel with light sabers because I am really REALLY hyped
 * for a particular movie coming out soon. Lightsabers. Cats. Nothing could go wrong.
 * <p/>
 * So this example hopefully demonstrates that given a "cat" CATegory (oh my, how punny) we
 * will actually be able to let users pin point a finer granularity in their searches.
 * <p/>
 * WE PROVIDE THE METHODS TO:
 * Nothing. This is created during the onStart() method.
 */

public class EditSkillActivity extends CameraActivity {
    static String ID_PARAM = "skill_id";
    private Skill skillToEdit;

    private ListView imageList;
    private EditText skillName, skillDescription;
    private Spinner skillCategory;
    private CheckBox skillVisible;
    private Button addSkillToDB;

    private ImageAdapter imageAdapter;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        masterController = new MasterController();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skill);

        skillName = (EditText) findViewById(R.id.new_skill_name);
        skillDescription = (EditText) findViewById(R.id.new_skill_description);
        addSkillToDB = (Button) findViewById(R.id.add_skill_to_database);
        skillCategory = (Spinner) findViewById(R.id.category_spinner);
        skillVisible = (CheckBox) findViewById(R.id.is_visible);
        imageList = (ListView) findViewById(R.id.imageList);

        // Images Setup
        imageAdapter = new ImageAdapter(this, getImages());
        imageList.setAdapter(imageAdapter);

        //Android Developers
        // http://developer.android.com/guide/topics/ui/controls/spinner.html
        adapter = ArrayAdapter.createFromResource(this,
                R.array.category_spinner_strings, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillCategory.setAdapter(adapter);
    }

    public void onStart() {
        super.onStart();

        // We need to be able to edit an existing skill
        if (getIntent().hasExtra(ID_PARAM)) {
            skillToEdit = DatabaseController.getSkillByID((ID) getIntent().getExtras().get(ID_PARAM));
            skillName.setText(skillToEdit.getName());
            skillDescription.setText(skillToEdit.getDescription());
            skillCategory.setSelection(adapter.getPosition(skillToEdit.getCategory()));
            setImages(skillToEdit.getImages());
            skillVisible.setChecked(skillToEdit.isVisible());
            addSkillToDB.setText("Save changes");
        }
        imageAdapter.notifyDataSetChanged();
    }

    public void addNewImage(View view) {
        addNewImage(view);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteImage(View view, Image toBeRemoved) {
        super.deleteImage(view, toBeRemoved);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void retakeImage(View view, Image toBeRemoved) {
        super.retakeImage(view, toBeRemoved);
        imageAdapter.notifyDataSetChanged();
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

    public CheckBox getSkillVisible() {
        return skillVisible;
    }

    /**
     * add skill to the database
     */
    public void addNewSkill(View view){
        String name = skillName.getText().toString();
        String description = skillDescription.getText().toString();
        String category = skillCategory.getSelectedItem().toString();
        boolean isVisible = skillVisible.isChecked();

        if (name.length() == 0 || description.length() == 0) {
            // this makes a pop-up alert with a dismiss button.
            // source credit: http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
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

        if (skillToEdit == null) { // if we are creating a new skill
            //Make a new skill through the controller.
            masterController.makeNewSkill(name, category, description, isVisible, getImages());
            DatabaseController.save();

            //Toasty
            Context context = getApplicationContext();
            Toast.makeText(context, "You made a skill!", Toast.LENGTH_SHORT).show();

            skillName.setText("");
            skillDescription.setText("");
        } else { // if we are editing an existing skill
            skillToEdit.setName(name);
            skillToEdit.setDescription(description);
            skillToEdit.setCategory(category);
            skillToEdit.setImages(getImages());
            skillToEdit.setVisible(isVisible);
            DatabaseController.save();

            Context context = getApplicationContext();
            Toast.makeText(context, "Skill saved!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
