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

public class UserInventoryTests extends ActivityInstrumentationTestCase2 {
    public UserInventoryTests() {
        super(com.skilltradiez.skilltraderz.Inventory.class);
    }

    public void testAddSkill() {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new NullImage());

        // Testing adding a skill to inventory
        inv.add(skill);
        // test for getting skill from inventory as well
        assertTrue(inv.get(db, 0).equals(skill));
    }

    public void testRemoveSkill() {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new NullImage());
        inv.add(skill);

        // Testing removing a skill from inventory
        inv.remove(skill.getSkillID());
        assertTrue(inv.size()==0);
    }


    public void testSetSkillProperties() {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new NullImage());
        inv.add(skill);

        // Testing modifying a skill in inventory
        Image dog = new Image("dog chasing it's tail");
        Skill held_skill = inv.get(db, 0);
        held_skill.setDescription("I jumped and then got really tired");
        held_skill.setImage(dog.getInt());
        held_skill.setVisible(false);
        assertTrue(dog.equals(held_skill.getImage()));
        assertTrue(held_skill.getDescription().equals("I jumped and then got really tired"));
        assertTrue(!held_skill.isVisible());
    }

    public void testSkillSorting() {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new NullImage());
        Skill skill2 = new Skill(db, "Skill Naem", "dategory", "desc", true, new NullImage());
        ArrayList<Skill> list1 = new ArrayList<Skill>();

        list1.add(skill);
        list1.add(skill2);

        inv.add(skill2);
        inv.add(skill);

        assertEquals(inv.orderByCategory(db), list1);
    }

    public void testSearchSkills() {
        UserDatabase db = new UserDatabase();
        DatabaseController.deleteAllData();
        User owner = null;
        try {
            owner = DatabaseController.createUser("Billy");
        } catch (UserAlreadyExistsException e) {
            // can't happen
        }
        Inventory inv = owner.getInventory();
        Skill skill = new Skill(db, "Skill Name", "category", "desc", true, new NullImage());
        Skill skill2 = new Skill(db, "Skil Naem", "dategory", "desc", true, new NullImage());
        ArrayList<Skill> list1 = new ArrayList<Skill>();
        ArrayList<Skill> list2 = new ArrayList<Skill>();

        list1.add(skill);
        list2.add(skill2);

        inv.add(skill);
        inv.add(skill2);

        assertEquals(inv.findByName(db, "Name"), list1);
        assertEquals(inv.findByName(db, "Naem"), list2);
        assertEquals(inv.findByCategory(db, "category"), list1);
        assertEquals(inv.findByCategory(db, "dategory"), list2);
        list1.add(skill2);
        assertEquals(inv.findByCategory(db, "gory"), list1);
    }
}
