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
            db.deleteAllData();
            db.createUser("Username");
            db.save();
            // Make sure it persists
            db = new UserDatabase();
            User user = db.login("Username");
            assertEquals(user.getProfile().getUsername(), "Username");
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testSetLocation() {
        try {
            UserDatabase db = new UserDatabase();
            db.deleteAllData();
            User user = db.createUser("Username");
            try {
                user.getProfile().setLocation("Edmonton");
            } catch (IllegalArgumentException e) {
            }
            db.save();
            // Make sure it persists
            db = new UserDatabase();
            user = db.login("Username");
            assertTrue(user.getProfile().getLocation().equals("Edmonton"));
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testSetEmail() {
        try {
            UserDatabase db = new UserDatabase();
            db.deleteAllData();
            User user = db.createUser("Username");
            try {
                user.getProfile().setEmail("apersonsname@awebsite.com");
            } catch (IllegalArgumentException e) {
            }
            db.save();
            // Make sure it persists
            db = new UserDatabase();
            user = db.login("Username");
            assertEquals(user.getProfile().getEmail(), "apersonsname@awebsite.com");
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testFieldsTooLong() {
        try {
            UserDatabase db = new UserDatabase();
            db.deleteAllData();
            User user = db.createUser("Username");
            try {
                user.getProfile().setEmail("apersonsname@awebsite.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
                assertTrue(false); // if we got here, the exception didn't happen
            } catch (IllegalArgumentException e) {
            }
            try {
                user = db.createUser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahahahahhahahahahahhahahahahahaha");
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
        db.deleteAllData();
        User user = db.createUser("Username");
        Image avatar = new Image("hello.jpeg");
        Image avatar2 = new Image("hello.jpg");

        //avatar should be empty to begin with
        assertNull(user.getProfile().getAvatar());

        //test setting avatar
        user.getProfile().setAvatar(avatar);
        assertEquals(user.getProfile().getAvatar(), avatar);

        //test changing avatar
        user.getProfile().setAvatar(avatar2);
        assertEquals(user.getProfile().getAvatar(), avatar2);

        //test deleting avatar
        user.getProfile().deleteAvatar();
        assertTrue(user.getProfile().getAvatar() instanceof NullImage);

    }

    public void testSetDownloadImages() {
        try {
            UserDatabase db = new UserDatabase();
            db.deleteAllData();
            User user = db.createUser("Username");

            user.getProfile().setShouldDownloadImages(true);
            db.save();
            // Make sure it persists
            db = new UserDatabase();
            user = db.login("Username");
            assertTrue(user.getProfile().getShouldDownloadImages());
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }
}
