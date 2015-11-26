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

public class ProfileTests extends ActivityInstrumentationTestCase2 {

    public ProfileTests() {
        super(com.skilltradiez.skilltraderz.Profile.class);
    }
    // tests for ConfigureProfileDetails

    public void testSetUsername() {
        try {
            UserDatabase db = new UserDatabase();
            DatabaseController.deleteAllData();
            DatabaseController.createUser("Username");
            DatabaseController.save();
            // Make sure it persists
            db = new UserDatabase();
            User user = DatabaseController.login("Username");
            assertEquals(user.getProfile().getUsername(), "Username");
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testSetLocation() {
        try {
            UserDatabase db = new UserDatabase();
            DatabaseController.deleteAllData();
            User user = DatabaseController.createUser("Username");
            try {
                user.getProfile().setLocation("Edmonton");
            } catch (IllegalArgumentException e) {
            }
            DatabaseController.save();
            // Make sure it persists
            //artificated garbage: db = new UserDatabase();
            user = DatabaseController.login("Username");
            assertTrue(user.getProfile().getLocation().equals("Edmonton"));
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testSetEmail() {
        try {
            UserDatabase db = new UserDatabase();
            DatabaseController.deleteAllData();
            User user = DatabaseController.createUser("Username");
            try {
                user.getProfile().setEmail("apersonsname@awebsite.com");
            } catch (IllegalArgumentException e) {
            }
            DatabaseController.save();
            // Make sure it persists
            //antiquidated garbage: db = new UserDatabase();
            user = DatabaseController.login("Username");
            assertEquals(user.getProfile().getEmail(), "apersonsname@awebsite.com");
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testFieldsTooLong() {
        try {
            UserDatabase db = new UserDatabase();
            DatabaseController.deleteAllData();
            User user = DatabaseController.createUser("Username");
            try {
                user.getProfile().setEmail("apersonsname@awebsite.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
            try {
                user = DatabaseController.createUser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
            try {
                user.getProfile().setLocation("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testAvatar() throws UserAlreadyExistsException {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();
        User user = DatabaseController.createUser("Username");
        Image avatar = new Image("hello.jpeg");
        Image avatar2 = new Image("hello.jpg");

        //avatar should be empty to begin with
        assertNull(user.getProfile().getAvatar());

        //test setting avatar
        user.getProfile().setAvatar(avatar.getInt());
        assertEquals(user.getProfile().getAvatar(), avatar);

        //test changing avatar
        user.getProfile().setAvatar(avatar2.getInt());
        assertEquals(user.getProfile().getAvatar(), avatar2);

        //test deleting avatar
        user.getProfile().deleteAvatar();
        assertTrue(user.getProfile().getAvatar() == new NullImage().getInt());

    }

    public void testSetDownloadImages() {
        try {
            UserDatabase db = new UserDatabase();
            DatabaseController.deleteAllData();
            User user = DatabaseController.createUser("Username");

            user.getProfile().setShouldDownloadImages(true);
            DatabaseController.save();
            // Make sure it persists
            //antiquidated garbage: b = new UserDatabase();
            user = DatabaseController.login("Username");
            assertTrue(user.getProfile().getShouldDownloadImages());
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }
}
