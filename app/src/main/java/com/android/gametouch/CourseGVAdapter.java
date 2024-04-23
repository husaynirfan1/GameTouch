package com.android.gametouch;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Collections;

public class CourseGVAdapter extends ArrayAdapter<CourseModel> {

    ArrayList<CourseModel> courseModelArrayList;
    int rand;
    int random;
    int tapcount = 0;
    Context context;
    int box;
    boolean isClicked = true;
    MediaPlayer mp;

    public CourseGVAdapter(@NonNull Context context, ArrayList<CourseModel> courseModelArrayList, int random, int box) {
        super(context, 0, courseModelArrayList);
        this.courseModelArrayList = courseModelArrayList;
        this.rand = random;
        this.context = context;
        this.box = box;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        float tenDp = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 10,
                context.getResources().getDisplayMetrics() );

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        CourseModel courseModel = getItem(position);

        CardView cardView = listitemView.findViewById(R.id.cardID);
        TextView tapcounttv = listitemView.findViewById(R.id.tapcounttv);

        Log.d("rand", String.valueOf(rand));
        if (courseModel.getCourse_name().equals(String.valueOf(rand))) {
            cardView.setCardBackgroundColor(Color.RED);
            cardView.setRadius(tenDp);
        }
        ArrayList<Integer> number = new ArrayList<Integer>();
        for (int i = 0; i < box; ++i) number.add(i);

        int var;
        ColorStateList bg = cardView.getCardBackgroundColor();
        if (bg instanceof ColorStateList) {
            var = ((ColorStateList) bg).getDefaultColor();
            if (var == Color.RED) {
                if (tapcount == 0) {
                    tapcounttv.setVisibility(View.VISIBLE);
                    tapcounttv.setText("Tap!");
                }
            }
        }

        cardView.setOnClickListener(v -> {

            int color;
            ColorStateList background = cardView.getCardBackgroundColor();
            if (background instanceof ColorStateList) {
                color = ((ColorStateList) background).getDefaultColor();
                if (color == Color.RED) {

                    tapcounttv.setVisibility(View.INVISIBLE);

                    playSound(R.raw.stop);
                    Collections.shuffle(number);
                    random = number.get(position);
                    Log.d("position", "Position : " + String.valueOf(position) + "| rand :  " + random);

                    tapcount++;

                    cardView.setCardBackgroundColor(Color.WHITE);
                    Log.d("randnum", String.valueOf(random));

                    Log.d("tappp", String.valueOf(tapcount));

                    ViewGroup parentss = (ViewGroup) parent.getChildAt(random);
                    View card = parent.getChildAt(random);

                    if (card instanceof CardView){
                        ((CardView) card).setRadius(tenDp);
                        ((CardView) card).setCardBackgroundColor(Color.RED);
                    }
                    Log.d("cardChildCount", String.valueOf(parentss.getChildCount()));
                    View tv = parentss.findViewById(R.id.tapcounttv);

                    if (tv instanceof TextView) {
                        tv.setVisibility(View.VISIBLE);
                        ((TextView) tv).setText(String.valueOf(tapcount + 1));
                    }

                    Log.d("size", String.valueOf(courseModelArrayList.size()));

                    Intent intent = new Intent("custom-message");

                    intent.putExtra("tapCount", String.valueOf(tapcount));

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                }
            }


        });


        return listitemView;
    }

    public void playSound(int _id)
    {
        if(mp != null)
        {
            mp.stop();
            mp.release();
        }
        mp = MediaPlayer.create(context, _id);
        if(mp != null)
            mp.start();
    }
}
