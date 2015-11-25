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
/**
 * DESCRIPTION: This class is going to be dedicated to making it so that we have more functionality
 * on the notification class. This is a subclass of the notification class that allows us to have
 * methods to get the name, descrption or image.
 *
 * Realize though that image is going to be an int, despite this being called stringable. The
 * other two are, as makes sense, strings.
 */
/*
 * Intended to make this for polymorphism on ListAdapter
 * Success?
 */
public abstract class Stringeable extends Notification {
    abstract String getName();
    abstract String getDescription();
    abstract int getImage();
}
