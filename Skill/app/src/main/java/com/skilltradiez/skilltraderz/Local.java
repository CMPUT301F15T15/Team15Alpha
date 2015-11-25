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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Collection;

/**~~DESCRIPTION:
 * In our application it is going to be essential for local saves to be incorporated through
 * some sort of mecahnsm, this class, the local class is going to be what we're actually
 * going to utilize in order to achieve this end.
 *
 * ~~ACCESS:
 * This is public meaning any part of this application can indeed somehow create instantiations
 * of this object and that any part of the application can also use any methods that are
 * associated with any of these objects at whim. Allowing flexibility and a lot of potential
 * areas of use! GLORIOUS! :)
 *
 * ~~CONSTRUCTOR:
 * This class is going to only have a single constructor that will take into itself no params
 * but when it is instantiated it will attempt to create a save_object (attribute) which is
 * going to be an attempted readfrom file. If this fails then we throw an exception mindyou!
 * SAFTEY FIRST! :D
 *
 * ~~ATTRIBUTES/METHODS:
 *
 * 1: SAVE_OBJECT:
 *     This is going to be the attribute associated with what the constructor read from the file
 *     and this is going to allow easy and effecient caching of all of the file that we desire
 *     to know about and interact with.
 *
 *
 * 2: SAVE_FILE:
 *     This is something set by us to set a path of where exactly we're going to be putting
 *     together all of this locally saved data once we're at the point of saving the things.
 *     This is hard coded.
 *
 * ~~MISC METHODS:
 *     I'm going to isolate the methods here for clarity's sake, please forgive me for breaking
 *     the regular formatting of comments.
 *
 * 1: SAVETOFILE:
 *     THIS IS AN OVERLOADED METHOD! OVERLOADED METHOD! TWO VERIONS!
 *
 *     OVERLOAD 1:
 *     PARAMETERS: NONE (NONE! NO PARAMETERS HERE!)
 *     When this method is called we're going to do the very critical and obviously essential
 *     thing of saving the current file into the local device. This when called will allow us to
 *     do this process. SUPER CRITICAL. No point to any of this without this method.
 *
 *     OVERLOAD 2:
 *     PARAMETERS: User me, List<User> friends, List<Skill> skillz List<Trade> trades,
 *                   List<Notification> notifications
 *     This is going to be our initial save to the storage on the device, we therefore
 *     need to specify and send a whole bunch of parameters into the local device's storage, this
 *     will allow the application to actually know what the heck it is putting into the local
 *     storage on the device.
 *
 *
 *
 * 2: GETLOCALDATA:
 *     This is going to return the ENTIRE saved object from the local device's storage and
 *     inform the application of all of it as a LocalPersistentObject object.
 *
 * 3: READFROMFILE:
 *     When we actually are going to take the storage of the application we're going to actually
 *     be looking at the information that is currently stored within the application and then
 *     we'll see what is going on through this method and then we'll see through this all
 *     being returned as a LocalPersistentObject.
 *
 */



public class Local {
    LocalPersistentObject save_object = null;
    private static final String SAVE_FILE = "/sdcard/save_file.sav";

    Local() {
        try {
            readFromFile();
        } catch (IOException e) {
            // Not sure what this means
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // method to get Locally Saved Data
    public LocalPersistentObject getLocalData() {
        return save_object;
    }

    public void saveToFile(User me, Collection<User> friends, Collection<Skill> skillz,
                           Collection<Trade> trades) {
        //Collection<Trade> trades, List<Notification> notifications) {
        save_object.setCurrentUser(me);
        save_object.setUsers(friends);
        save_object.setSkillz(skillz);
        save_object.setTrades(trades);
        //save_object.setNotifications(notifications);

        try {
            saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("this died in Local");
        }
    }

    // method to save LocalPersistentObject to local file
    public void saveToFile() throws IOException {
        FileOutputStream fop = null;
        File file = new File(SAVE_FILE);

        // create file if doesn't exist
        if (!file.exists()) file.createNewFile();
        fop = new FileOutputStream(file);
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fop));
        Gson gson = new Gson();
        gson.toJson(save_object, output);
        output.flush();
        fop.close();
    }

    /* method to read LocalPersistentObject from local file
     * returns what is read as a LocalPersistentObject*/
    public LocalPersistentObject readFromFile() throws IOException {
        FileInputStream fip = null;
        File file = new File(SAVE_FILE);

        // create file if doesn't exist
        if (!file.exists()) file.createNewFile();
        fip = new FileInputStream(file);
        BufferedReader in = new BufferedReader(new InputStreamReader(fip));
        Gson gson = new Gson();

        try {
            Type lpo_type = new TypeToken<LocalPersistentObject>(){}.getType();
            save_object = gson.fromJson(in, lpo_type);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (save_object == null) save_object = new LocalPersistentObject();
        in.close();
        return save_object;
    }

    public void deleteFile() throws IOException {
        File file = new File(SAVE_FILE);
        file.delete();
    }
}