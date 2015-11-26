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

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.mock.MockContext;
import android.widget.Button;


/**
 * Created by nweninge on 11/21/15.
 */
public class SkillDescriptionUITest extends ActivityInstrumentationTestCase2 {
    public SkillDescriptionUITest() {
        super(SkillDescriptionActivity.class);
    }

    public void testStart() throws Exception {
        SkillDescriptionActivity activity = (SkillDescriptionActivity)getActivity();
        activity.finish();
    }

    public void testAddRemoveSkill() throws Exception {
        MasterController mc = new MasterController();
        mc.initializeController();

        DatabaseController.deleteAllData();

        DatabaseController.createNewUser("User", "Email");
        mc.makeNewSkill("Skill1Test", "testing", "see if can add new skill", true, new NullImage());
        Skill skill = ((Skill)(mc.getAllSkillz().toArray()[0]));
        // see if skill added
        assertTrue(mc.getCurrentUser().getInventory().hasSkill(skill));

        //now remove the skill and test if that works
        MockContext mockContext = new MockContext();
        Intent intenti = new Intent(mockContext, SkillDescriptionActivity.class);
        intenti.putExtra("skill_id", skill.getSkillID());
        setActivityIntent(intenti);
        SkillDescriptionActivity activity = (SkillDescriptionActivity)getActivity();
        final Button addButton = activity.getAddRemoveSkill();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        // check to make sure skill removed
        assertFalse(mc.getCurrentUser().getInventory().hasSkill(skill));

        activity.finish();
    }
}
