package com.skilltradiez.skilltraderz;

/**~~DESCRIPTION:
 * We want an android framework that will support the ability for the user to interact
 * with our application in a very logical and easy way. So we're going to create an activity
 * that is associated with just the activities with the user. This activity is going to be
 * associated purely with the entire trade request process that the user will need to interact with
 * through our application.
 *
 * ~~ACCESS:
 * This may seem redundant but for formatting purposes... this is a "public" class, meaning that
 * we can have this class actually be accessed technically anywhere in the application that
 * calls it. But since this is an activity it may seem a bit strange to refer to instantiating
 * an instance of the "EditTradeActivity" object.
 *
 * Instead what is happening is that we are having this activity be called by the onCreate() method
 * as is traditionally done in the android studio framework for android applications. In this
 * instance we're going to create this activity and then we'll have an onstart() method following
 * this which is going to make it so that we have this activate a cascade of events that are all
 * interelated with the main primary goal of allowing us to have a screen where we edit the
 * trading activity!
 *
 *~~CONSTRUCTOR:
 * Upon calling the method onCreate() for this activity the android studio framework will
 * cause the android application to create an instance of this actvity and display it to the user.
 *
 * ~~ATTRIBUTES/METHODS:
 * 1: ACCEPTTRADE:
 *     This is going to be the method called when the user interacts with the UI where the user
 *     actually is going to accept a trade that is presented to them. When this is hit
 *     a series of steps will be activated in which the trade will actually be accepted by the user.
 *
 *
 *  2: DELETEREQUEST:
 *      When the user actually wants to DELETE a request they can interact with the UI and then
 *      this method will be called. This will outright remove the request of trading from the
 *      user's application permanently. Fabulous.
 *
 * 3: COUNTEROFFER:
 *     Suppose a user isn't happy with what they currently are seeing/offering for a particular
 *     trade in our application, interacting with the UI will allow us to have a counteroffer
 *     method called (this method) that will allow the user to edit their trade agreement in
 *     the application in a glorious and beautiful fashion!
 *     MAJESTIC! AS! HECK!
 *
 * 4: SETTRADEDETAILS:
 *     When a user interacts with their application, it is stupidly essential to give the user
 *     the DETAILS of a particular trade that they are performing on. This method is called when
 *     the user interacts with the UI in a fashion that allows the user to view the details
 *     of a given trade.
 *
 *
 * 5: SETTRADENOTES:
 *     This is going to obtain the trade notes from the user and then will present them to the
 *     user of the application once the application framework is prompted to actually go
 *     through these series of steps!
 *
 *
 * 6: SETTRADETITLE:
 *     This is going to be more "UI related" but it also has the effect that we now have some
 *     sort of special unique identifier (aside from ID) that the user can relate to when they are
 *     dealing with the entire trade process. This gives the user that power to set the
 *     title of the trade! GLORIOUS!
 *
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

public class TradeRequestActivity extends GeneralMenuActivity {
    private Context tradeContext = this;

    private Button acceptRequest;
    private Button deleteRequest;
    private Button counterOffer;
    private TextView tradeTitle;
    private TextView tradeDescription;
    private ListView skillsInTrade;
    private Trade trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request);
        //Initiate master controller object here.

        masterController = new MasterController();

        Bundle extras = getIntent().getExtras();
        //Use the ID of the trade to get the trade OBJECT and assign it to trade variable.
        trade = masterController.getCurrentTradeByID((ID) extras.get("trade_id"));
    }

    @Override
    public void onStart(){
        super.onStart();

        acceptRequest = (Button) findViewById(R.id.acceptTrade);
        deleteRequest = (Button) findViewById(R.id.deleteTrade);
        counterOffer = (Button) findViewById(R.id.counterTrade);
        tradeDescription = (TextView) findViewById(R.id.trade_description);
        skillsInTrade = (ListView) findViewById(R.id.skillListInTrade);
        tradeTitle = (TextView) findViewById(R.id.trading_with);
    }

    /**
     * Accept a trade. Sends a message to the user making the request that you are happy with the
     * terms of the request. Will add the trade to your history
     */
    public void acceptTrade(){
        //Alert dialog that you have accepted the request. Sends a notification to the requester
        //Add the trade to your history
        Toast toast = Toast.makeText(tradeContext, "Accepted a Trade", Toast.LENGTH_SHORT);
        toast.show();

        //Evoke the masterController to set the trade to be ACCEPTED == TRUE.
        masterController.acceptTheCurrentTrade(trade);

    }

    /**
     * Delete a request.
     * @ TODO:
     */
    public void deleteRequest(){
        // alert dialogue for making sure that you want to delete the request. Confirmation.
        Toast toast = Toast.makeText(tradeContext, "Deleted a Request", Toast.LENGTH_SHORT);
        toast.show();
        //TODO the model doesn't support this yet.
    }

    /**
     * counter offer. Takes you to the Edit Trade Request screen
     * @ TODO:
     */
    public void counterOffer(View view){
        Intent intent = new Intent(tradeContext, EditTradeActivity.class);
        intent.putExtra("trade_id", trade.getTradeID());
        startActivity(intent);
        //TODO: Shouldn't we set the Extra?
        intent.getExtras().get("counter_offer");
    }

    /**
     * Set the details of the trade offer.
     * View
     * @ TODO: setTrade Details?
     */
    public void setTradeDetails(){
        //need a list of skills relevant to the trade and then populate the view
        //skillsInTrade = something based on other things
    }

    /**
     * Set the notes of the trade as given by the trade requester
     * View
     * @ TODO: Trade Notes?
     */
    public void setTradeNotes(){
        //need to get the notes from the requester to populate this field
        // tradeDescription = something
    }

    /**
     * Who you're trading with
     * View
     * @ TODO:
     */
    public void setTradeTitle(){
        //tradeTitle = who you're trading with
        UserDatabase db = masterController.getUserDB();
        User otherUser = CDatabaseController.getAccountByUserID(trade.getOppositeHalf(db.getCurrentUser()).getUser());
        tradeTitle.setText(otherUser.getProfile().getUsername());
    }
}
