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


import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseTests extends ActivityInstrumentationTestCase2 {
    public UserDatabaseTests() {
        super(com.skilltradiez.skilltraderz.UserDatabase.class);
    }

    public void testCreateAccount() {
        UserDatabase db = MasterController.getUserDB();
        DatabaseController.deleteAllData();
        User user;

        try {
            user = DatabaseController.createUser("Username");

            assertEquals(DatabaseController.getAccountByUsername("Username"), user);
        } catch (UserAlreadyExistsException e) {
        }
    }

    public void testCreateAccountFailCase() {
        UserDatabase db = MasterController.getUserDB();
        DatabaseController.deleteAllData();
        User user;

        try {
            user = DatabaseController.createUser("Username");
        } catch (UserAlreadyExistsException e) {

        }

        try {
            User user2 = DatabaseController.createUser("Username");
            assertTrue(false);
        } catch (UserAlreadyExistsException e) {
            assertTrue(true);
        }
    }

    public void testLogin() {
        UserDatabase db = MasterController.getUserDB();
        DatabaseController.deleteAllData();
        User user;

        try {
            user = DatabaseController.createUser("Username");
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);

        }

        assertTrue(DatabaseController.login("Username") != null);
    }

    public void testDatabasePersistence() {
        UserDatabase db = MasterController.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user = DatabaseController.createUser("Username");
            DatabaseController.save();

            // The new database should contain all the previous changes
            db = new UserDatabase();
            assertTrue(DatabaseController.getAccountByUsername("Username").equals(user));
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testTradelistPersistence() {
        UserDatabase db = MasterController.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user1 = DatabaseController.createUser("Username1"),
                    user2 = DatabaseController.createUser("Username2");

            TradeList tl = user1.getTradeList();
            tl.createTrade(db, user1, user2, new ArrayList<Skill>());
            DatabaseController.save();

            // The new database should contain all the previous changes
            db = new UserDatabase();
            user1 = DatabaseController.getAccountByUsername("Username1");
            user2 = DatabaseController.getAccountByUsername("Username2");

            tl = user1.getTradeList();
            assertEquals(tl.getMostRecentTrade(db).getHalfForUser(user2).getUser(), user2.getUserID());
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testFriendListPersistence() {
        UserDatabase db = MasterController.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user1 = DatabaseController.createUser("Username1"),
                    user2 = DatabaseController.createUser("Username2");

            user1.getFriendsList().addFriend(user2);
            DatabaseController.save();

            // The new database should contain all the previous changes
            db = new UserDatabase();
            user1 = DatabaseController.getAccountByUsername("Username1");
            user2 = DatabaseController.getAccountByUsername("Username2");

            assertTrue(user1.getFriendsList().hasFriend(user2));
            assertTrue(user2.getFriendsList().hasFriend(user1));
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }
}
