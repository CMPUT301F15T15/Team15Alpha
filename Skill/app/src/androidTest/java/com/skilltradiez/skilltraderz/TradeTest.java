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

public class TradeTest extends ActivityInstrumentationTestCase2 {
    public TradeTest() {
        super(com.skilltradiez.skilltraderz.Trade.class);
    }

    public void testInitTrade() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user = DatabaseController.createUser("u");
            User user2 = DatabaseController.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill(db, "illlllll", "LLLLLLLLLLLLLLLL", "desc", true, new ArrayList<Image>()));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer, offer);
            user.getTradeList().addTrade(MasterController.getUserDB(), trade);
            user2.getTradeList().addTrade(MasterController.getUserDB(), trade);

            assertEquals(user.getTradeList().getMostRecentTrade(db), trade);
            assertEquals(user2.getTradeList().getMostRecentTrade(db), trade);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testAcceptTradeRequest() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user = DatabaseController.createUser("u");
            User user2 = DatabaseController.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill(db, "illlllll", "LLLLLLLLLLLLLLLL", "desc", true, new ArrayList<Image>()));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            trade.getHalfForUser(user2).setAccepted(true);
            assertTrue(!trade.isActive());
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testRefuseTradeRequest() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user = DatabaseController.createUser("u");
            User user2 = DatabaseController.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill(db, "illlllll", "LLLLLLLLLLLLLLLL", "desc", true, new ArrayList<Image>()));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            // decline the trade
            trade.getHalfForUser(user2).setAccepted(false);
            DatabaseController.save();
            assertTrue(trade.isActive());
            // delete the trade
            mc.deleteTrade(trade);
            assertTrue(user.getTradeList().getActiveTrades(db).size() == 0);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testCounterOfferTradeRequest() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User user = DatabaseController.createUser("u");
            User user2 = DatabaseController.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();
            List<Skill> counterOffer = new ArrayList<Skill>();

            offer.add(new Skill(db,"illlllll", "LLLLLLLLLLLLLLLL", "desc", true, new ArrayList<Image>()));
            counterOffer.add(new Skill(db, "Counter skill", "meta", "desc", true, new ArrayList<Image>()));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            trade.getHalfForUser(user2).setAccepted(false);
            trade.getHalfForUser(user2).setOffer(counterOffer);
            List<ID> ids = new ArrayList<ID>();
            for (Skill skill : counterOffer) {
                ids.add(skill.getSkillID());
            }
            assertEquals(trade.getHalfForUser(user2).getOffer(), ids);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testModifyActiveTrade() throws NoInternetException {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        try {
            User bob = DatabaseController.createUser("Bob");
            User joel = DatabaseController.createUser("Joel");

            List<Skill> skillz1 = new ArrayList<Skill>(), skillz2 = new ArrayList<Skill>();
            skillz1.add(new Skill(db, "...YEP", "FOO", "desc", true, new ArrayList<Image>()));

            TradeList tl = bob.getTradeList();
            tl.createTrade(db, bob, joel, skillz2);

            Trade t = tl.getMostRecentTrade(db);

            // Modify An Active Trade
            t.getHalfForUser(bob).setOffer(skillz1);
            List<ID> ids = new ArrayList<ID>();
            for (Skill skill : skillz1) {
                ids.add(skill.getSkillID());
            }
            assertEquals(t.getHalfForUser(bob).getOffer(), ids);

            // Delete An Active Trade
            tl.delete(t);
            assertTrue(tl.getActiveTrades(db).size() == 0);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }
}
