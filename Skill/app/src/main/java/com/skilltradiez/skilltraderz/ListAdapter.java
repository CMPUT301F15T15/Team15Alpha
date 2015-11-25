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
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * List adapter is going to be a subclass if the stringable superclass, where the core purpose
 * of this class is to grant us a constructor and various methods for dealing with viewing the
 * data presented. This is achieved by handing this class a particular activity of interest and a
 * list of potential items to be processed by this list.
 *
 * When used, this class will be allowing us to obtain view details to be used in displaying
 * the information on the given activity through it's two methods.
 */
public class ListAdapter extends ArrayAdapter<Stringeable> {
    private final Activity context;
    private final List<Stringeable> items;

    public ListAdapter(Activity context, List<Stringeable> itemList) {
        super(context, R.layout.list_item, itemList);
        this.context = context;
        this.items = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        /* TODO FIGURE THIS OUT
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        Skill here = skillz.get(position);

        txtTitle.setText(here.getName());//.toString());

        // If we have received an array of ints
        //imageView.setImageResource(imageId[position]);
        // or If Drawable has been set by constructor
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.iconshape));
        // or a better If Drawable has been set by constructor
        imageView.setImageResource(here.getImage());
        return rowView;*/

        Stringeable item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.desc = (TextView) view.findViewById(R.id.desc);

            //viewHolder.img = view.findViewById(R.id.img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(item.getName());
        viewHolder.desc.setText(item.getDescription());
        //TODO IMAGES
        // viewHolder.img.setImageResource(item.getImage());
        //viewHolder.img.setBackground(item.getImage());
        // Return the completed view to render on screen
        return view;
    }

    // Acting As A Struct
    public class ViewHolder {
        TextView name;
        TextView desc;
        View img;
    }
}