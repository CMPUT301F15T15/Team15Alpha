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
 * 1: MAKESEARCHTHREAD:
 *     This is going to grant the user the ability to make a search thread for searching
 *     through various things through our application such as profiles, friends, skills,
 *     inventories.
 *
 * 2: NEWUSER:
 *     This is going to be how we actually add a new user to our application and our application
 *     database. Without this UI element and method tied to the UI we would no doubt have
 *     a complete and utter failure of this application.
 *
 *
 * 3: BEGINALLSEARCH:
 *     This is going to be how we're going to actually begin all of the potential searches
 *     within this application. We let it begin a potential search options from this UI option
 *     which invokes this UI method.
 *
 * 4: BEGINREFINEDSEARCH:
 *     Suppose the user of the application wants a furhter refined search with more speficic options
 *     that are tied to the search process. This is going to be the method called and then we will
 *     provide all of the search options to the various users.
 *
 * 5: SHOWPROFILE
 *     This is going to be the method called when the user clicks on the UI where we will
 *     successfully have all of the profile of what they clicked on through this method.
 *
 * 6: CREATENEWSKILL:
 *     Suppose we have the user wanting to create a new skill for our application. This is going
 *     to be the method that is called through the UI when the UI is clicked that will prompt
 *     for the entire cascade of what is going to be produced through the entire cascaded process.
 *
 *
 *
 *
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends GeneralMenuActivity {
    private Context mainContext = this;

    //Main screen
    private Button searchButton;
    private Button searchAllSkillzButton;
    private Button searchAllUsersButton;
    private Button goToProfile;
    private EditText searchField;
    private String searchDatabase;

    //First time user screen
    private EditText newUserName;
    private EditText newUserEmail;
    private Button makeNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        masterController = new MasterController();
        masterController.initializeController();

        //TODO HACK
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (CDatabaseController.isLoggedIn()) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.first_time_user);
        }

        // main
        searchButton = (Button) findViewById(R.id.search_button);
        searchAllSkillzButton = (Button) findViewById(R.id.browse_skillz);
        searchAllUsersButton = (Button) findViewById(R.id.browse_users);
        goToProfile = (Button) findViewById(R.id.go_to_profile);
        searchField = (EditText) findViewById(R.id.search_bar);

        // first_time
        newUserName = (EditText) findViewById(R.id.usernameField);
        newUserEmail = (EditText) findViewById(R.id.emailField);
        makeNewUser = (Button) findViewById(R.id.beginApp);

        searchDatabase = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //masterController.initializeArrayListForSkills();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);
        return true;
    }

    /**
     * onClick listeners and buttons go here
     */
    @Override
    public void onStart(){
        super.onStart();
    }

    /**
     * Make a thread with a string. Used to search the database for a specific user or skill.
     * @param
     */
    public void makeSearchThread(String stringToSearch){
        /*
        Like in the lab Get a string and search
        SearchThread thread = new SearchThread(string);
        thread.start();*/
        //TODO: Use this or remove this
    }

    /**
     * Create a new user when you first open up the app.
     *
     */
    private static User new_guy = null;
    public void newUser(View view){
        final Context context = getApplicationContext();
        final String username;

        if(newUserName.getText().toString().isEmpty()){
            Toast.makeText(context, "You need a name!", Toast.LENGTH_SHORT).show();
        }else {
            username = newUserName.getText().toString();

            //Used for error checking
            // If null, then it failed
            new_guy = CDatabaseController.createNewUser(username, newUserEmail.getText().toString());
            CDatabaseController.save();

/*            try {
                new_guy = userDB.createUser(username);
            } catch (UserAlreadyExistsException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                Context context_exception = this;
                // this makes a pop-up alert with a dismiss button.
                // source credit: http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
                AlertDialog.Builder alert = new AlertDialog.Builder(context_exception);
                alert.setMessage("UserName too long!\n");
                alert.setCancelable(true);
                alert.setPositiveButton("retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog tooBig_alert = alert.create();
                tooBig_alert.show();
                return;

            }*/

            if (new_guy != null) {
                Toast.makeText(context, "Welcome, " + username, Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_main);
            } else {
                Toast.makeText(context, username + " Already Exists!", Toast.LENGTH_SHORT).show();
                // Do nothing
            }
        }
    }

    /**
     * Browse all skillz or users in the database. Might be able to combine this with refined search
     * for better code reuse. EEDIT
     * @param view
     */
    public void beginAllSearch(View view){
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        if(view.getId() == R.id.browse_skillz){
            intent.putExtra("All_search", 0);
        } else {
            intent.putExtra("All_search", 1);
        }
        startActivity(intent);
    }

    /**
     * Begin a refined search of the database from user input
     * @param view
     */
    public void beginRefinedSearch(View view){
        Intent intent = new Intent(mainContext, SearchScreenActivity.class);
        startActivity(intent);
    }

    /**
     * Take user to their own profile when "Your Profile" button is pressed
     * @param view
     */
    public void showProfile(View view){
        Intent intent = new Intent(mainContext, ProfileActivity.class);
        intent.putExtra("user_name_for_profile", masterController.getCurrentUserUsername());
        startActivity(intent);
    }

    /**
     * Sends user to the EditSkill activity to make a new skill
     * @param view
     */
    public void createNewSkill(View view){
        Intent intent = new Intent(mainContext, EditSkillActivity.class);
        startActivity(intent);
    }

    public void deleteDatabase(View view) {
        CDatabaseController.deleteAllData();
        Toast.makeText(getApplicationContext(), "Complete online database has been deleted!!!!", Toast.LENGTH_SHORT).show();
    }

    public EditText getNameField() {
        return newUserName;
    }

    public EditText getEmailField() {
        return newUserEmail;
    }

    public Button getLoginButton() {
        return makeNewUser;
    }

    public void clearFields() {
        runOnUiThread(new Runnable() {
            public void run() {
                newUserName.setText("");
                newUserEmail.setText("");
            }
        });
    }

    /* method to check if connected to internet to be called when app opens and also before anytime online activity is needed
       source: http://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
       returns true if connectivity is available*/
    public boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
