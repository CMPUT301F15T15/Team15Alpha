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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class UserDatabase {
    private User currentUser;
    private List<User> users;
    private List<Trade> trades;
    private List<Skill> skillz;
    private ChangeList toBePushed;
    private Elastic elastic;
    private Local local;

    UserDatabase() {
        users = new ArrayList<User>();
        trades = new ArrayList<Trade>();
        skillz = new ArrayList<Skill>();
        toBePushed = new ChangeList();

        // Persistence API
        // Via ElasticSearch(Internet)
        elastic = new Elastic("http://cmput301.softwareprocess.es:8080/cmput301f15t15/");
        // Via SD Card(Local)
        //TODO: Get Permissions/Figure Out what's wrong with Local
        local = new Local();

        LocalPersistentObject lpo = local.getLocalData();
        if (lpo != null) {
            currentUser = lpo.getCurrentUser();
        } else {
            currentUser = null;
        }
    }

    public User createUser(String username) throws UserAlreadyExistsException {
        if (getAccountByUsername(username) != null)
            throw new UserAlreadyExistsException();

        User u = new User(username);
        users.add(u);
        // You wouldn't be creating a user if you already had one
        currentUser = u;
        getChangeList().add(u.getFriendsList());
        try {
            elastic.addDocument("user", username, u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u;
    }

    public User login(String username) {
        User u = getAccountByUsername(username);
        currentUser = u;
        getChangeList().add(u.getFriendsList());
        return u;
    }

    public void deleteAllData() {
        try {
            elastic.deleteDocument("user", "");
            elastic.deleteDocument("skill", "");
            elastic.deleteDocument("trade", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pullUsers() {
        for (int i = 0; i < users.size(); i++) {
            users.set(i, getOnlineAccountByUsername(users.get(i).getProfile().getUsername()));
        }
    }

    public void save() {
        toBePushed.push(this);
        // Saves locally and pushes changes if connected to the internet
    }

    public ChangeList getChangeList() {
        return toBePushed;
    }

    /*
     * The internet API
     */
    public Elastic getElastic() {
        return elastic;
    }

    /*
     * The Local API
     */
    public Local getLocal() {
        return local;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getAccountByUsername(String username) {
        for (User u : users) {
            if (u.getProfile().getUsername().equals(username)) {
                return u;
            }
        }
        return getOnlineAccountByUsername(username);
    }

    private User getOnlineAccountByUsername(String username) {
        //TODO Maybe this should throw an exception instead of returning null.
        User u = null;
        try {
            System.out.println(username);
            u = elastic.getDocumentUser(username);
            if (u != null)
                users.add(u);
        } catch (IOException e) {
        }
        return u;
    }

    public User getAccountByUserID(ID id) {
        for (User u : users)
            if (u.getUserID().equals(id))
                return u;
        return null;
    }

    public Trade getTradeByID(ID id) {
        for (Trade t : trades)
            if (t.getTradeID().equals(id))
                return t;
        return null;
    }

    public Skill getSkillByID(ID id) {
        for (Skill s : skillz)
            if (s.getSkillID().equals(id))
                return s;
        return null;
    }

    public void addSkill(Skill s) {
        skillz.add(s);
        // New Skill
        getChangeList().add(s);
    }

    public void addTrade(Trade t) {
        trades.add(t);
        // New Trade
        getChangeList().add(t);
    }
}
