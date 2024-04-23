package com.android.gametouch;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gametouch.db.ListingAdapter;
import com.android.gametouch.db.ScoreModal1;
import com.android.gametouch.db.ViewModalScore;

import java.util.List;

public class DialogCustom {

    RecyclerView scoreRV;
    ListingAdapter adapter;

    public void showDialog(Activity activity, int lvl, List<ScoreModal1> scoreModal1List) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        scoreRV = dialog.findViewById(R.id.scoreRV);

        scoreRV.setLayoutManager(new LinearLayoutManager(activity));
        final ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory();

        adapter = new ListingAdapter();
        scoreRV.setAdapter(adapter);

        adapter.updateList(scoreModal1List);

        ImageButton closeBtn = dialog.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(v-> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
