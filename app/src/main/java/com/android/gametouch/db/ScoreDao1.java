package com.android.gametouch.db;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface ScoreDao1 {

    // below method is use to
    // add data to database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ScoreModal1 model);

    @Insert
    void insertAll(ScoreModal1... modal);

    // below method is use to update
    // the data in our database.
    @Update
    void update(ScoreModal1 model);

    // below line is use to delete a
    // specific course in our database.
    @Delete
    void delete(ScoreModal1 model);

    @Query("DELETE FROM scoreTable1")
    void deleteAllCourses();

    @Query("SELECT * FROM scoreTable1")
    LiveData<List<ScoreModal1>> getAllScores();

    @Query("SELECT * FROM scoreTable1 WHERE username = :name")
    LiveData<ScoreModal1> findbyUsername(String name);

    /*todo order by top scorer*/
    /*TOP 25*/
    @Query("SELECT * FROM scoreTable1 WHERE level = :level ORDER BY CAST(score AS INT) DESC LIMIT 25")
    LiveData<List<ScoreModal1>> findbyLevel(String level);

}

