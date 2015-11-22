package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * Trades are the core element of our application. Without the ability to create new trade
 * objects this application would become absolutely pointless. Trades are how the users interact
 * with one another and are the main driving and drawing appeal for the users to actually
 * USE our application.
 *
 * A trade object will possess attrbutes relating two users together as actors (actor1 and actor2)
 * and then will have a list of skills which are being offered for each user, and a boolean that
 * will maintain whether or not the trade is actually being accepted or not by the users!
 * As well as if it is already completed (and fully accepted) or not.
 *
 * In terms of the methods avaliable here, we have the various methods in which we will have the
 * ability to obtain the current actors in the trade (object of trade), get offers, set (new counter
 * offers) offers, set if accepted or declined or if it is even still active or not!
 *
 * CONSTRUCTOR:
 * This object is constructed from the songle constructor which is going to take in two user
 * objects, a user1 and a user 2. This constructor will assign the actor1 and actor2 attributes
 * in order to have a firm basis to have the entire trade change over!
 *
 * ATTRIBUTES/METHODS:
 * 1: actor1/actor2:
 *     These are going to be the two users that are actually trading. These attributes
 *     are going to keep a record of the two users which allows us to maintain integrity
 *     of who is going on and doing/trading what. This also allows us to pick apart what users
 *     can offer in a trade for skills, and other things associated with the users.
 *
 *     WE PROVIDE METHODS TO:
 *         -obtain the "first" actor        --getActor1
 *         -obtain the "second" actor       --geActor2
 *
 *  2: offer1/offer2:
 *      These are going to be the two offers that each of the users made, the entire trade is based
 *      upon these offers being made and modified to each user's liking and in which case we will
 *      actually have the offers between the two users being maintained and allow the modifications
 *      there. :)
 *         -get the offer (passed ANY user)     --getCurrentOffer
 *         -set a counteroffer                  --setCounterOffer
 *
 *
 *  3: accepted/active:
 *      These are two boolean values that maintain if the current trade is still active or
 *      not as well as if the user finishes or declines a trade then they will have this value
 *      modified.
 *
 *      WE PROVIDE THE METHODS TO:
 *          -Get if there is an active trade        --isActive
 *          -Get if the trade is accepted or not    --isAccepted
 *          -Decline the trade                      --decline
 *          -Set if trade is accepted               --setAccepted
 *          -
 *
 *~~MISC METHODS:
 * 1: COMMIT:
 *      So when we actually have one of these objects, since it is so critical and SO core
 *      to the application we're going to actually need to update the database itself with
 *      the object!
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
import java.util.List;

/**
 * Created by sja2 on 10/28/15.
 */
public class Trade extends Stringeable {
    private ID tradeID;
    private HalfTrade half1, half2;

    Trade(UserDatabase db, User user1, User user2) {
        tradeID = ID.generateRandomID();
        half1 = new HalfTrade(tradeID, user1.getUserID(), 1);
        half2 = new HalfTrade(tradeID, user2.getUserID(), 2);
        CDatabaseController.addTrade(this);
    }

    public HalfTrade getHalf1() {
        return half1;
    }

    public HalfTrade getHalf2() {
        return half2;
    }

    public HalfTrade getOppositeHalf(User user) {
        if (half1.getUser().equals(user.getUserID())) {
            return half2;
        } else if (half2.getUser().equals(user.getUserID())) {
            return half1;
        }
        return null;
    }

    /**
     * Gets the parts of this trade corresponding to one of the involved users.
     * @return The half corresponding to the user, or null if the user is not involved in the trade.
     */
    public HalfTrade getHalfForUser(User user) {
        if (half1.getUser().equals(user.getUserID())) {
            return half1;
        } else if (half2.getUser().equals(user.getUserID())) {
            return half2;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        return !(tradeID != null ? !tradeID.equals(trade.tradeID) : trade.tradeID != null);

    }

    @Override
    public int hashCode() {
        return tradeID != null ? tradeID.hashCode() : 0;
    }

    public ID getTradeID() {
        return tradeID;
    }

    /**
     * A trade is active if both parties have not yet accepted it.
     * @return
     */
    public boolean isActive() {
        return !(half1.isAccepted() && half2.isAccepted());
    }

    public boolean commit(UserDatabase userDB) {
        if (half1.hasChanged())
            if (!half1.commit(userDB))
                return false;
        if (half2.hasChanged())
            if (!half2.commit(userDB))
                return false;
        return true;
    }

    public String getName() {
        //TITLE
        return "TRADE: "+half1.getUser()+" -> "+half2.toString();
    }

    public String getDescription() {
        // SUBTITLE
        return "I should REALLY find the user's names";
    }

    public int getImage() {
        return 0;
    }

    @Override
    public String toString() {
        MasterController msCont = new MasterController();
        String string = half1.getUser().toString() + " " + half2.getUser().toString() + " ";

        // Skill Names: Might be a bit much
        for (ID skillID:half1.getOffer())
            string += CDatabaseController.getSkillByID(skillID).getName() + " ";
        for (ID skillID:half2.getOffer())
            string += CDatabaseController.getSkillByID(skillID).getName() + " ";

        return string;
    }
}
