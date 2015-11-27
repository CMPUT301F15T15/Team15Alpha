package com.skilltradiez.skilltraderz;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Stephen on 2015-11-26.
 *
 * An owner of a Rating can be either a User(Profile) or a Skill
 *  - Skill Ratings are how difficult/good a skill is
 *  - User Ratings are a measurement of their personal Skill Teaching Ability
 */
public class Rating extends Notification {
    private String type, id;
    private HashMap<String, Integer> rating;
    Rating(String type, String id) {
        rating = new HashMap<String, Integer>();
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    /**
     * Calculates the average of all ratings
     */
    public int getRating() {
        if (rating.isEmpty()) return 0;
        int total = 0, count = 0;
        for (Integer rate:rating.values()) {
            total += rate;
            count++;
        }
        return total / count;
    }

    /**
     * Changes a users rating of the owner
     *
     * If the user has rated the owner before,
     * the user's previous rating will be replaced
     */
    public void changeRating(String username, Integer rate)  {
        if (rating.containsKey(username))
            rating.remove(username);
        rating.put(username, rate);
    }

    @Override
    boolean commit(UserDatabase userDB) {
        //TODO commit this to it's user/skill
        try {
            if (type.equals("skill")) {
                DatabaseController.updateDocuments(type, id, this, "rating");
            } else {
                DatabaseController.updateDocuments(type, id, this, "profile/" + "rating");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
