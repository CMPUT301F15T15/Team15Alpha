package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the profile process that the user will need to interact
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
 * 1: VIEWEDUSER:
 *     Suppose we view a user, we want to keep track of this, and so we are going to store
 *     this as a very basic User object class. This is assigned early on in the creation
 *     of this activity.
 *
 *
 * 2: VIEWINGUSER:
 *     When we are currently viewing a user we will maintain a record of that user and this is
 *     going to be stored as a User attribute where we maintain the user that we're currently
 *     ordering the UI to actually make us look at.
 *
 * 3: SETUSERDESCRIPTION:
 *     This is the method that will be called when the user interacts with the UI in order
 *     to change their description of the user. This will activate a series of statements
 *     that will grant the ability for the user to modify their user description.
 *
 * 4: CHECKINVENTORY:
 *     This is going to be the UI method called that when the user wants to check the inventory
 *     of another user they will then have the inventory of the other user appear on their screen.
 *     And then we have things update and modify themselves and change around.
 *
 * 5: STARTTRADE:
 *     This is the method called when the user clicks in the userinterface for the goal of starting
 *     a trade with the other user. This is going to invoke a cascade of methods that will
 *     then initiate a trade request.
 *
 *
 * 6: ADDFRIEND:
 *     This is going to be the method called when the user is on another user's profile
 *     and they want to add them as a friend and then this will make a series of functional
 *     calls where the user who's profile we're currently on is going to be udpdated on the
 *     friends list with a friend request.
 *
 *     Of course until this is accepted you are NOT the other user's friend.
 *     Mean girls pls.
 *
 *  7:REMOVEFRIEND:
 *      This is how we interact with the other user's profile to actualyl remove the user from
 *      our friendslist. This is going to be located on the profile of an ALREADY ADDED/FRIENDED
 *      user and then we will have it so that the friendlists are updated with the mutual
 *      removal of each user from eachothers friends lists.
 *
 *
 *  8: BLOCKUNBLOCKEDUSER:
 *      This is going to be a mutual method where we have the ability to both
 *      block and unblock a particular user. This is going to update our block lists and when
 *      the user is already blocked we have this method UNblock the user and when the user is NOT YET
 *      blocked and we click this we then block the user.
 *
 *
 *
 *  9: CHECKUSER:
 *     This is going to return the ID of the user that we're looking at. Simple as that, allowing
 *     us to see just who this particular user is!
 *
 *
 *
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class ProfileActivity extends GeneralMenuActivity {
    private Bundle profileExtras;
    private String userProfileName;

    private User currentUser;
    private Boolean hasFriend;

    private Context profileContext = this;

    private Button addRemoveFriend;
    private Button startTrade;
    private Button viewInventory;
    private TextView userContactInfo;
    private TextView profileTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        masterController = new MasterController();
        profileExtras = getIntent().getExtras();
        userProfileName = profileExtras.getString("user_name_for_profile");

        addRemoveFriend = (Button) findViewById(R.id.add_friend);
        startTrade = (Button) findViewById(R.id.maketrade);
        viewInventory = (Button) findViewById(R.id.inventory);
        userContactInfo = (TextView) findViewById(R.id.user_description);
        profileTitle = (TextView) findViewById(R.id.user_name);

        populateProfile();
        profileTitle.setText(userProfileName);
        userContactInfo.setText(currentUser.getProfile().getEmail());

        if (masterController.getUserDB().getCurrentUser().getFriendsList().hasFriend(currentUser)) {
            addRemoveFriend.setText(R.string.remove_friend);
            hasFriend = true;
        } else {
            addRemoveFriend.setText(R.string.add_friend);
            hasFriend = false;
        }

        // You can't be friends with yourself, go get some real friends
        if (masterController.getUserDB().getCurrentUser().equals(currentUser))
            addRemoveFriend.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);
        return true;
    }

    @Override
    public void onStart(){
        super.onStart();
        populateProfile();
    }

    /**
     * When you click on a profile it gets data something something @todo make this sound nicer
     */
    public void populateProfile() {
        currentUser = masterController.getUserByName(userProfileName);
        hasFriend = masterController.currentUserHasFriend(currentUser);
    }

    /**
     * Begins the inventory activity, and shows the inventory specific to the user whose profile
     * you are browsing
     * @param view
     */
    public void checkInventory(View view){
        Intent intent = new Intent(profileContext, InventoryActivity.class);
        intent.putExtra("user_id", currentUser.getUserID());
        startActivity(intent);
    }

    /**
     *
     * @param view
     * @ TODO: 11/4/2015
     */
    public void startTrade(View view){
        //check if user you are making a trade with is your friend
        //do not show start trade button
        Intent intent = new Intent(profileContext, EditTradeActivity.class);
        startActivity(intent);
    }

    public void addRemoveFriend(View view){
        if(hasFriend){
            removeFriend();
        }else{
            addFriend();
        }
        hasFriend = !hasFriend;
    }

    /**
     * Add a user as a friend.
     */
    public void addFriend(){
        //Function call to the master controller to deal with all this!
        masterController.addANewFriend(currentUser);

        CDatabaseController.save();

        //Toasties!
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Added "+currentUser.getProfile().getUsername()+" as a friend", Toast.LENGTH_SHORT);
        toast.show();

        //Modify the displayed text to remove friend option.
        addRemoveFriend.setText(R.string.remove_friend);
    }

    /**
     * Remove friend from user's friendlist
     */
    public void removeFriend(){
        masterController.removeThisFriend(currentUser);

        CDatabaseController.save();

        //Toasties!!!!!!
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Removed "+currentUser.getProfile().getUsername()+" from FriendsList", Toast.LENGTH_SHORT);
        toast.show();

        //Modify the displayed text the user sees to keep them aware of their choices.
        addRemoveFriend.setText(R.string.add_friend);
    }
}
