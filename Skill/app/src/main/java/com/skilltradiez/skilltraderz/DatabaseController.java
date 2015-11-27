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
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**~~TYPE: CONTROLLER
 *
 * DESCRPTION: Following MVC styling to the absolute core, this is taking out of the UserDatabase
 * anything but the most bare minimal methods. We're maintaing the model as the model, and we're
 * making all the other functionality be housed here.
 *
 * This area will take care of anything and everything the rest of the program may possibly want
 * to do with the database.
 */
public final class DatabaseController implements ControllerInterface{
    private static UserDatabase userDB;

    DatabaseController() {
        userDB = new UserDatabase();
    }

    public UserDatabase getUserDB() {
        return userDB;
    }

    //This refresh method damn well belongs in the controller.
    /**
     * Downloads all online data into a local cache
     * TODO: debug why local changes offline don't persist once back online... not sure if problem is here
     */
    public static void refresh() {
        save();
        Elastic elastic = MasterController.getUserDB().getElastic();
        User currentUser = MasterController.getUserDB().getCurrentUser();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        ChangeList changes = MasterController.getUserDB().getChangeList();

        try {
            List<User> onlineUsers = elastic.getAllUsers();
            for (User u : onlineUsers) {
                if (u.equals(currentUser)) MasterController.getUserDB().setCurrentUser(u);
                users.remove(u);
                users.add(u);
            }
            //TODO CATCH IOExceptions //Done?
            List<Skill> skills = elastic.getAllSkills();
            for (Skill s : skills) {
                skillz.remove(s);
                skillz.add(s);
                changes.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        ChangeList toBePushed = MasterController.getUserDB().getChangeList();
        Local local = MasterController.getUserDB().getLocal();
        toBePushed.push(MasterController.getUserDB());
        User currentUser = MasterController.getUserDB().getCurrentUser();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Set<Trade> trades = MasterController.getUserDB().getTrades();

        local.saveToFile(currentUser, users, skillz, trades, toBePushed);
        if (MainActivity.connected) {
            toBePushed.push(MasterController.getUserDB());
        }
    }

    public static void deleteAllData() {
        ChangeList toBePushed = MasterController.getUserDB().getChangeList();
        Local local = MasterController.getUserDB().getLocal();
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        User currentUser = MasterController.getUserDB().getCurrentUser();

        try {
            elastic.deleteDocument("example", "");
            elastic.deleteDocument("user", "");
            elastic.deleteDocument("skill", "");
            elastic.deleteDocument("trade", "");
            local.deleteFile();
            MasterController.getUserDB().setCurrentUserToNull();
            users.clear();
            skillz.clear();
            trades.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Add a trade
    public static void addTrade(Trade t) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        trades.add(t);
        // New Trade
        MasterController.getUserDB().getChangeList().add(t);
        try {
            MasterController.getUserDB().getElastic().addDocument("trade", t.getTradeID().toString(), t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //When we have a new user... we call upon the controller here to interact with the database
    //in order to create a brand new user. Returns this brand new user!
    public static User createNewUser(String usernameGiven, String emailGiven){
        User new_guy = null;
        try {
            new_guy = createUser(usernameGiven);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        new_guy.getProfile().setEmail(emailGiven);
        DatabaseController.save();

        return new_guy;
    }

    public static User getAccountByUserID(ID id) {
        Set<User> users = MasterController.getUserDB().getUsers();
        for (User u : users)
            if (u.getUserID().equals(id))
                return u;
        return null;
    }

    //Moved create user functionality to this new area
    public static User createUser(String username) throws UserAlreadyExistsException {
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();

        if (getAccountByUsername(username) != null)
            throw new UserAlreadyExistsException();

        User u = new User(username);
        users.add(u);
        // You wouldn't be creating a user if you already had one
        MasterController.getUserDB().setCurrentUser(u);
        try {
            elastic.addDocument("user", username, u);
        } catch (IOException e) {
            // No internet, no registration
            throw new RuntimeException();
        }
        return u;
    }

    //Login functionality
    public static User login(String username) {
        User u = getAccountByUsername(username);
        MasterController.getUserDB().setCurrentUser(u);
        return u;
    }

    public static boolean isLoggedIn() {
        User currentUser = MasterController.getUserDB().getCurrentUser();
        return currentUser != null;
    }

    public static User getAccountByUsername(String username) {
        Set<User> users = MasterController.getUserDB().getUsers();
        for (User u : users)
            if (u.getProfile().getUsername().equals(username))
                return u;
        return getOnlineAccountByUsername(username);
    }

    private static User getOnlineAccountByUsername(String username){
        Elastic elastic = MasterController.getUserDB().getElastic();
        Set<User> users = MasterController.getUserDB().getUsers();
        User u = null;
        try {
            //System.out.println(username);
            u = elastic.getDocumentUser(username);
            if (u != null) users.add(u);
        } catch (IOException e) {
            System.err.println("Issue reading user from database." + e.getMessage());
        }

        return u;
    }

    /** Get by ID functions
     * Each public method is paired with a private method that goes in depth.
     * **/

    public static Trade getTradeByID(ID id) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        for (Trade t : trades)
            if (t.getTradeID().equals(id))
                return t;
        return getOnlineTradeByID(id);
    }

    //Associated with above function.
    private static Trade getOnlineTradeByID(ID id) {
        Set<Trade> trades = MasterController.getUserDB().getTrades();
        Elastic elastic = MasterController.getUserDB().getElastic();
        Trade t = null;
        try {
            t = elastic.getDocumentTrade(id.toString());
            if (t != null)
                trades.add(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static Skill getSkillByID(ID id) {
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        for (Skill s : skillz)
            if (s.getSkillID().equals(id))
                return s;
        return getOnlineSkillByID(id);
    }

    private static Skill getOnlineSkillByID(ID id) {
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        Elastic elastic = MasterController.getUserDB().getElastic();
        Skill s = null;
        try {
            s = elastic.getDocumentSkill(id.toString());
            if (s != null) {
                skillz.add(s);
                MasterController.getUserDB().getChangeList().add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**Adding a skill **/
    public static void addSkill(Skill s) {
        Set<Skill> skillz = MasterController.getUserDB().getSkills();
        ChangeList changeList = MasterController.getUserDB().getChangeList();
        Elastic elastic = MasterController.getUserDB().getElastic();

        skillz.add(s);
        // New Skill
        changeList.add(s);
        try {
            elastic.addDocument("skill", s.getSkillID().toString(), s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Delete Document block of functions. These look WAY more controller-like than model **/
    public static void deleteDocumentUser(User user) {
        deleteDocumentUser(user.getUserID());
    }

    public static void deleteDocumentSkill(Skill skill) {
        deleteDocumentSkill(skill.getSkillID());
    }

    public static void deleteDocumentTrade(Trade trade) {
        deleteDocumentTrade(trade.getTradeID());
    }

    public static void deleteDocumentUser(ID userID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("user", userID.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDocumentSkill(ID skillID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("skill", skillID.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDocumentTrade(ID tradeID) {
        Elastic elastic = MasterController.getUserDB().getElastic();
        try {
            elastic.deleteDocument("trade", tradeID.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static <T> void updateDocuments(String type, String id, T data, String path) throws IOException{
        userDB.getElastic().updateDocument(type, id, data, path);
        //MasterController.getUserDB().getElastic().updateDocument(type, id, this, "rating");
    }
}
