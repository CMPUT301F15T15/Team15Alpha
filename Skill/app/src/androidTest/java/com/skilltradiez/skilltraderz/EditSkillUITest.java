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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by nweninge on 11/21/15.
 */
public class EditSkillUITest  extends ActivityInstrumentationTestCase2 {
    public EditSkillUITest() {
        super(EditSkillActivity.class);
    }

    public void testStart() throws Exception {
         EditSkillActivity activity = (EditSkillActivity)getActivity();
        activity.finish();
    }

    public void testCreateSkill() throws Exception {
        EditSkillActivity activity = (EditSkillActivity)getActivity();
        final EditText nameField = activity.getSkillName();
        final EditText descField = activity.getSkillDescription();
        final Spinner categoryField = activity.getSkillCategory();
        final CheckBox visibilityField = activity.getSkillVisible();
        final Button addButton = activity.getAddSkillToDB();

        MasterController masterController = new MasterController();

        masterController.initializeController();


        DatabaseController.deleteAllData();
        DatabaseController.createNewUser("User", "Email");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameField.setText("Skill name");
                descField.setText("Skill description");
                //categoryField.setText("Skill category");
                visibilityField.setChecked(true);
            }
        });
        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Skill skill = (Skill) activity.getMasterController().getAllSkillz().toArray()[0];
        assertEquals("Skill name", skill.getName());
        assertEquals("Skill description", skill.getDescription());
        //assertEquals("Skill category", skill.getCategory());
        assertEquals(true, skill.isVisible());

        activity.finish();
    }
}
