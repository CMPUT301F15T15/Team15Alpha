package com.skilltradiez.skilltraderz;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Activities should ideally never need to interact with the model directly.
 * This acts as a controller that facilitates the communication from the views to the models that
 * we possess.
 *
 * Assuming direct control.
 * --Sovereign, mass effect
 */
public final class MasterController implements CControllerInterface{
    private static UserDatabase userDB;
    private static CDatabaseController databaseController;

    /** DATABASE RELATED **/
    //Initialize the master controller.
    //Absolutely core, this sets up the entire system of controllers.
    public void initializeController(){

        userDB = new UserDatabase();
        databaseController = new CDatabaseController();


    }

    //Return the database object! Only avaliable to other controller objects.
    public static UserDatabase getUserDB(){
        return userDB;
    }





    /** USER RELATED **/
    //If we probe for the USER that is currently on the app... returns the USER object of that user.
    //NOT just the name. USER object.
    public User getCurrentUser(){
        return userDB.getCurrentUser();
    }

    //Give the current username from the database.
    public String getCurrentUserUsername(){
        return userDB.getCurrentUser().getProfile().getUsername();
    }

    public String getCurrentUserEmail(){
        return userDB.getCurrentUser().getProfile().getEmail();
    }



    //Given a profile name... can we please return THE PROFILE OBJECT?! (Yes. Yes we can.)
    public User getUserByName(String userProfileName){
        return CDatabaseController.getAccountByUsername(userProfileName);
    }

    public User getUserByID(ID userID){
        return CDatabaseController.getAccountByUserID(userID);
    }

    /**FRIEND RELATED **/
    //Do they have THIS friend in particular.
    public boolean currentUserHasFriend(User currentUser){
        return currentUser.getFriendsList().hasFriend(userDB.getCurrentUser());
    }

    public void addANewFriend(User currentUser){
        userDB.getCurrentUser().getFriendsList().addFriend(currentUser);
    }

    public void removeThisFriend(User currentUser){
        userDB.getCurrentUser().getFriendsList().removeFriend(currentUser);
    }



    /** SKILLZ RELATED FUNCTIONS **/

    //Clear the current List<Skill> of skillz!
    // this seems unnecessary
    public void clearSkillzList(List<Skill> skillz){
        skillz.clear();
    }

    //Obtain from the user database all of the current skills!
    public Set<Skill> getAllSkillz(){
        return userDB.getSkills();
    }

    public Set<User> getAllUserz(){
        return userDB.getUsers();
    }

    public Set<Trade> getAllTradez() {return userDB.getTrades();}

    public void makeNewSkill(String name, String category, String description, boolean isVisible, Image image){
        userDB.getCurrentUser().getInventory().add(new Skill(userDB, name, category, description, isVisible, image));
        CDatabaseController.save();
    }

    /** SkillDescriptionActivity methods **/



    public void removeCurrentSkill(Skill currentSkill){
        userDB.getCurrentUser().getInventory().remove(currentSkill.getSkillID());
        currentSkill.removeOwner(userDB.getCurrentUser().getUserID());
    }

    public void addCurrentSkill(Skill currentSkill){
        userDB.getCurrentUser().getInventory().add(currentSkill);
        currentSkill.addOwner(userDB.getCurrentUser().getUserID());
    }

    /** TradeRequestActivity methods **/

    //Given the ID of a trade, we will now RETURN a TRADE OBJECT to the caller of this method.
    public Trade getCurrentTradeByID(ID identifier){
        return CDatabaseController.getTradeByID(identifier);
    }

    //ACCEPT THE TRADE
    public void acceptTheCurrentTrade(Trade trade){
        trade.getHalfForUser(userDB.getCurrentUser()).setAccepted(true);
    }
}
