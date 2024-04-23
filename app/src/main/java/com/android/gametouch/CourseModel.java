package com.android.gametouch;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

public class CourseModel {

    // string course_name for storing course_name
    // and imgid for storing image id.
    private String course_name;


    public CourseModel(String course_name) {
        this.course_name = course_name;
    }


    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}