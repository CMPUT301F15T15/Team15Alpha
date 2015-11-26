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

public class FriendListTests extends ActivityInstrumentationTestCase2 {
    public FriendListTests() {
        super(com.skilltradiez.skilltraderz.FriendListTests.class);
    }

    public void testAddFriend() throws UserAlreadyExistsException {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();

        User user1 = DatabaseController.createUser("user1");
        User user2 = DatabaseController.createUser("user2");

        user1.getFriendsList().addFriend(user2);
        DatabaseController.save();
        assertTrue(user1.getFriendsList().hasFriend(user2));
        assertTrue(user2.getFriendsList().hasFriend(user1));
    }

    public void testRemoveFriend() throws UserAlreadyExistsException {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();

        User user1 = DatabaseController.createUser("user1");
        User user2 = DatabaseController.createUser("user2");

        user1.getFriendsList().addFriend(user2);
        DatabaseController.save();
        assertTrue(user1.getFriendsList().hasFriend(user2));
        assertTrue(user2.getFriendsList().hasFriend(user1));

        user1.getFriendsList().removeFriend(user2);
        DatabaseController.save();
        assertFalse(user1.getFriendsList().hasFriend(user2));
        assertFalse(user2.getFriendsList().hasFriend(user1));
    }
}
