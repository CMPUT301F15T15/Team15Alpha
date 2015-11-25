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
import java.util.List;

/**
 * DESCRIPTION: When a user is going to utilize a search we are going to want to have some sort
 * of reply back to the application, this class is purely dedicated to achieving the amount of
 * total, successful and failed attempts.
 *
 * This is tightly bound to the elastic search class. But there is a twist, this may be a
 * class called UserSearchResponse-- but it has two miniature classes located within itself. This
 * is going to be the Hits and hit class-- these are going to be useful in picking apart particular
 * returns from the elasticsearch database as the user searches.
 *
 * This can be useful in telling us how many times the search was done, whether it worked or failed.
 *
 */
public class UserSearchResponse {
    private int took;
    private boolean timed_out;
    private Shards _shards;
    class Shards {
        int total;
        int sucessful;
        int failed;
    }
    public Hits hits;
    class Hits {
        int total;
        float max_score;
        List<Hit> hits;
    }
    class Hit {
        String _index;
        String _type;
        String _id;
        float _score;
        User _source;
    }
}
