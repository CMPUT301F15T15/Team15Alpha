package com.skilltradiez.skilltraderz;
/**~~DESCRIPTION:
 * This is going to be the class where we have the inventory and all of it's associated functions
 * that a user will have all clumped together into one coherent object.
 * This object will have a set of attributes that are related to the inventory such as an
 * arraylist of skills. (And that's it.... for now... dun dun dunnnnn!)
 * This object will be method centric, where most of the object will be dealing with manipulating
 * a truckload of functionality throughout the rest of the objects present within the application.
 *
 * ~~ACCESS:
 * This is a public class, meaning that any other class or part of the application can call,
 * instantiate, and utilize the methods associated
 *
 * ~~CONSTRUCTOR:
 *
 * This class is going to only have one constructor associated with it. This constructor will take
 * in no parameters and will simply instantiate a new instance of this class and then within
 * this object it will instantiate a new ArrayList if ID's. This allows this to have a
 * unique collection of things.
 *
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: SKILLZ:
 *     We want an inventory to be populated with a ton of ID's that are going to represent various
 *     skillZ that the user has. Without this we couldn't possibly piece together what a user
 *     actually has to offer/can offer to someone else. AND SO.... we make this lovely arraylist
 *     and pop values of ID's into there and this also has the function of allowing us to have
 *     a unique PRIMARY KEY ESQUE way of accessing the database. It is glorious, it is fun, it is
 *     great. Enjoy!
 *
 * ~~MISC METHODS:
 *     Let me state here that the only reason I am putting the things here is because it will
 *     get hella convoluted if I try and maintain the exact rigid format as before.
 *
 *  1: GET:
 *      THIS IS AN OVERLOADED METHOD! OVERLOADED METHOD!
 *      Meaning that depending on the parameters given to this particular function we will
 *      have the function do a dramatically different function!
 *
 *      OVERLOADED ONE:
 *          PARAMETERS:   UserDatabase userDB, Integer index
 *          This is going to be given a userdatabase, and then an index value. This is going to
 *          retrieve for us the particular user's inventory from the database and give us a skill
 *          back as a skill.
 *
 *      OVERLOADED TWO:
 *          PARAMETERS: Integer index
 *          Given the value of a particular index we will return a particular ID that is related
 *          to that index.
 *
 *
 *  2: ADD:
 *      This is going to be the method that will allow us to add a skillz to the inventory list.
 *      Think about populating an inventory, is it not absolutely maddening to imagine
 *      having an empty inventory...? No...? No...? Just me?
 *      FINE!?
 *      BUT WE WILL LET USERS POPULATE THEIR INVENTORY. Because obviously if they have nothing
 *      in their inventory then they actually unfortunately have nothing to trade and then
 *      things are good.
 *
 *
 * 3: REMOVE:
 *     This is going to be the method that will be used in order to remove a particular skillZ
 *     from the inventory that is related to a particualr user of our program. Upon removal
 *     we will ideally update the database to inform all parties of the fact that this has been
 *     permanently removed from the user's inventory.
 *
 *
 * 4: SIZE:
 *     Suppose we want to know the size of a user's inventory. Maybe the user has one skill,
 *     maybe the user has 0 skills and therefore is on our app to.... mooch? I dunno. Maybe
 *     they have a billion skills and are stroking their ego and walking around using our
 *     application going "Ooh look at me, I am so skilled and your mother smells of elderberries!"
 *     Either way, upon invoking this method we will return to the user the current size of the
 *     inventory for that particular user.
 *
 * 5: FINDBYNAME:
 *     Given users use our application, and that there are more than one user's... would it not
 *     be considered essential to find the various inventories of users by their name? Yes? No?
 *     Well this is going to resolve that particular problem, and so now we're going to actually
 *     have the database query the database and get all of these inventories by their name.
 *
 *
 * 6: FINDBYCATEGORY:
 *     So we have categories in our application that differentiate skills that a user may have,
 *     the categories are broad stroking categories that the users may or may not opt to use.
 *     Ideally though if we have Fred the baker offering to cook cat shaped cookies we will have
 *     the skill fall into the cookie category (for instance) opposed to falling into the pets
 *     category (in which we do questionable things with cats perchance?).
 *
 *     Point being we select a category, send it into the database and query the database for all
 *     of the results that are related to this query, upon obtaining this value we've now obtained
 *     a full list of all of the things from the query that the application can now fully utilize
 *     with ease.
 *
 *
 * 7: ORDERBYNAME:
 *     Suppose we have a massive database query result and it's just... a henious massive beast
 *     that is present in this person's inventory. Let's say Igor the burly bearded man has
 *     like 10,000,000 skills (yeah, he's got dat) and then we want to somehow get through this
 *     list and find his ability to chop wood (because we're going to assume burly bearded men
 *     are all going to cut wood and wear plaid for whatever reason) and then we will have from
 *     this result the ability to scroll through the result alphabetically and then give to the
 *     application itself this result. Allowing the user on the other end of the application
 *     (As in the user using the app and choosing this option) to actually have something tangible
 *     and useful to read that they can properly interact with opposed to a hodge-podge mess of
 *     just.. the stuff of nightmares.
 *
 * 8: ORDERBYCATEGORY:
 *     Suppose now we want to ask Igor the burly bearded man to give us all of his skills for pets,
 *     lets say he is like a princess and has all the woodland creatures flock to him (including
 *     kittens)... his list of 10,000,000 skills is obviously oppressive and now we have the problem
 *     of wanting to see the pet skills.
 *
 *     So what do we do? The answer is that we take all of the categories that our application
 *     offers and now we arrange the results that the user on the other ends sees by these category
 *     groupings! TADA! BEAUTIFUL! ...Right? Right! Confidence, that is right!
 *
 *
 * 9: CLONESKILLZ:
 *     We're in the business of trading skills. Might our application need sometimes the ability
 *     to clone a list of skills? Probably, and so this is the means to which we will achieve that
 *     particular end! HORAAAY! Right!? RIGHT! So we're going to iterate throughout the list
 *     we want to copy and then place all of the items, item by item O(n) time and then we will
 *     stuff these items into a new list that we can utilize throughout the application.
 *
 *     Much fun, much chaos, much order. Somehow both.
 *
 *
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An inventory contains the the skills held by a user.
 */
public class Inventory extends Notification {
    private ArrayList<ID> skillz;
    private ID user;

    public Inventory(ID user) {
        skillz = new ArrayList<ID>();
        this.user = user;
    }

    /**
     * Gets a skill at a given index
     *
     * @param index the index
     * @return the skill
     */
    public Skill get(UserDatabase userDB, Integer index) {
        if (index < skillz.size())
            return CDatabaseController.getSkillByID(skillz.get(index));
        return null;
    }

    public ID get(Integer index) {
        if (index < skillz.size())
            return skillz.get(index);
        return null;
    }

    /**
     * Adds a new skill to the end of the list
     *
     * @param new_skill the skill to add.
     * @return false if the skill was already in the list
     */
    public Boolean add(Skill new_skill) {
        if (skillz.contains(new_skill.getSkillID())) return false;
        skillz.add(new_skill.getSkillID());
        notifyDB();
        return true;
    }

    /**
     * Removes a skill from the list
     *
     * @param skill the skill to remove
     */
    public void remove(ID skill) {
        skillz.remove(skill);
        notifyDB();
    }

    /**
     * Gets the number of skills on the list
     */
    public Integer size() {
        return skillz.size();
    }

    /**
     * Finds all skills with a particular name.
     *
     * @param name The skill name to search for
     * @return a list of all skills matching the given name.
     */
    public List<Skill> findByName(UserDatabase userDB, String name) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        Skill temp;
        for (ID s : skillz) {
            temp = CDatabaseController.getSkillByID(s);
            if (temp.getName().contains(name) && temp.isVisible()) matching.add(temp);
        }
        return matching;
    }

    /**
     * Finds all skills with a particular category.
     *
     * @param category The skill category to search for
     * @return a list of all skills matching the given category.
     */
    public List<Skill> findByCategory(UserDatabase userDB, String category) {
        ArrayList<Skill> matching = new ArrayList<Skill>();
        Skill temp;
        for (ID s : skillz) {
            temp = CDatabaseController.getSkillByID(s);
            if (temp.getCategory().contains(category) &&
                    (temp.isVisible() || CDatabaseController.getAccountByUserID(user).getInventory().hasSkill(temp)))
                matching.add(temp);
        }
        return matching;
    }

    /**
     * Returns a copy of the list of skills, sorted ascending by name.
     */
    public ArrayList<Skill> orderByName(UserDatabase userDB) {
        ArrayList<Skill> sorted = cloneSkillz(userDB);
        Collections.sort(sorted, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return sorted;
    }

    /**
     * Returns a copy of the list of skills, sorted ascending by name.
     */
    public ArrayList<Skill> orderByCategory(UserDatabase userDB) {
        ArrayList<Skill> sorted = cloneSkillz(userDB);
        Collections.sort(sorted, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        return sorted;
    }

    public Boolean hasSkill(Skill skill) {
        for (ID s:skillz)
            if (skill.getSkillID().equals(s))
                return true;
        return false;
    }

    public ArrayList<Skill> cloneSkillz(UserDatabase userDB) {
        ArrayList<Skill> newList = new ArrayList<Skill>();
        for (ID id:skillz)
            newList.add(CDatabaseController.getSkillByID(id));
        return newList;
    }

    @Override
    boolean commit(UserDatabase userDB) {
        System.out.println("Inventory commit!");
        try {
            userDB.getElastic().updateDocument("user", CDatabaseController.getAccountByUserID(user).getProfile().getUsername(), this, "inventory");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //Save Locally
        try {
            userDB.getLocal().saveToFile();
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
}
