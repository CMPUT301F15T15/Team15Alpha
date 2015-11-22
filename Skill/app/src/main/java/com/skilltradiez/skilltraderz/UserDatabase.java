package com.skilltradiez.skilltraderz;
/**~~DESCRPTION:
 * A user database is going to be the object that is going to be a massive force in this
 * application. This is the massive heaving beast of a class that will be burdened with a great
 * deal of functionality associated directly with the database itself. In order for this to
 * properly function we will be making use of the popular database ElasticSearch.
 *
 * Now suppose that we have a bunch of users, we want to be able to successfully manage
 * all of the user's individually, all the users within a list like format, and then a list of the
 * modifications that we want to successfully commit to the databae. Databae, we love it.
 *
 * This class will have the ability to pull various things from the database in which case
 * the application will pull relevant information that the applicaiton desires at that current
 * point of time.
 *
 * ~~ACCESS:
 * This class is a public class, meaning that at any point in time during the application with
 * any class in the application we can instnatiate a new copy of this object, and that it's methods
 * are fully accessible throughout the entire applicaiton at any given point in time!
 *
 * ~~CONSTRUCTORS:
 * This class is going to be instantiated in which case we have the default constructor.
 * This takes in nothing, and just makes a user database. Have fun!
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: CURRENTUSER:
 *     This is going to be the attribute that is going to have the identifier for the current
 *     user using this application!
 *
 *     WE PROVIDE THE METHODS TO:
 *
 *
 * 2: USERS:
 *     This is going to be the full list of all of the user's that is pulled from the database.
 *     This list is going to likely be comprehensive, however keep in mind that we have a
 *     large variety of methods that can actually allow the current user/application of the
 *     current online user to choose what sort of list of the entire range of users that the
 *     current user actually desires to see at any given point in time.
 *
 *
 *      WE PROVIDE THE METHODS TO:
 *          -Pull users, granting us the entire list of users       --pullUsers
 *          -Get the user by their username                         --getAccountByUsername
 *          -Get the users by accordance to their user ID           --getAccountByUserID
 *          -Get the user's by how their nickname is in relations!  --getAccountByNickname
 *
 *
 *  3: TOBEPUSHED:
 *      This is going to be a large list of all of the pending information that is related to the
 *      users in which we have NOT YET AT ALL updated the database yet with the
 *      information of the users that we currently have stored within this very interesting
 *      list of potential users and then we will actually have the thingies update once we make an
 *      update request to the database through the method save()
 *
 * ~~MISC METHODS:
 *
 * 1: CREATEUSER:
 *     This method is going to be the method that will take into itself the parameters
 *     of the user's string of their username, the string of the user's pasword and then will create
 *     a brand new user in the database associated with the username and apssword that is given
 *     to the user. And then we forever have this! YAY! :)
 *
 * 2: LOGIN:
 *     Suppose we already have a database of useres (wow, what a coincidence!!!!) and now we want
 *     to actually be able to confirm if the user that tried to login to the application was authentic or not...
 *     now I realize strongly that we don't need to supply a username or a password to the database to
 *     confirm that the user is authentic....
 *     we have no cryptography systems or anything for this application and so we actually are
 *     just going off of the idea and fact that the assignment... will just let us....
 *     have any random user login to the application using a username and then will
 *     just log that user into the application and then
 *     we wil grant them full acess to that profile. Regardless... we have a password that is
 *     associated with each user...
 *
 *     WE PROVIDE THE METHODS TO:
 *         -log into the application           --login
 *
 *
 *
 * 3: SAVE:
 *     Where we have the application itself actually involve itself with the database.
 *     When this method is actually called by the user or the class we will actually take
 *     the entire funnel of information that the applicaiton currently
 *     has locked within itself and then the database will be updated with the infromation.
 *     This will be the master function that is called in order to call the database and then fill
 *     the database with all the information.
 *
 *
 *      WE PROVIDE THE METHODS TO:
 *          -save the information to the databae --save
 *
 */


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

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private User currentUser;
    private Set<User> users;
    private Set<Trade> trades;
    private Set<Skill> skillz;
    private ChangeList toBePushed;
    private Elastic elastic;
    private Local local;

    UserDatabase() {
        users = new HashSet<User>();
        trades = new HashSet<Trade>();
        skillz = new HashSet<Skill>();
        toBePushed = new ChangeList();

        // Persistence API
        // Via ElasticSearch(Internet)
        elastic = new Elastic("http://cmput301.softwareprocess.es:8080/cmput301f15t15/");
        // Via SD Card(Local)
        local = new Local();

        LocalPersistentObject lpo = local.getLocalData();
        if (lpo != null) {
            if (lpo.getCurrentUser() != null) {
                MasterController.getUserDB().setCurrentUser(lpo.getCurrentUser());
            } else {
                currentUser = null;
            }
            users.addAll(lpo.getUsers());
            skillz.addAll(lpo.getSkillz());
            trades.addAll(lpo.getTrades());
            //getChangeList().getNotifications().addAll(lpo.getNotifications());
        } else {
            currentUser = null;
        }
    }


    /** Model here only has the simple getter/setter methods that allow the controller to function
     * without some insane level of complexity.
     *
     * All of those huge functions from before were just outright modified and put into the
     * controller itself. Those don't belong in a true model class following MVC.
     */
    public void setCurrentUserToNull(){ currentUser = null; }

    public Set<Skill> getSkillz(){ return skillz; }

    public ChangeList getChangeList() { return toBePushed; }

    public Elastic getElastic() { return elastic; }

    public Local getLocal() { return local; }

    public User getCurrentUser() { return currentUser; }

    public Set<User> getUsers() { return users; }

    public Set<Skill> getSkills() { return skillz; }

    public Set<Trade> getTrades() { return trades;}

    public void setCurrentUserVal(User currentUserFromFn) { this.currentUser = currentUserFromFn;}





    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        getChangeList().add(currentUser.getFriendsList());
        getChangeList().add(currentUser.getTradeList());
        getChangeList().add(currentUser.getProfile());
        getChangeList().add(currentUser.getInventory());
    }

}
