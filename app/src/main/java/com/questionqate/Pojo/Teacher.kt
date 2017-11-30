package com.questionqate.Pojo

import com.google.gson.JsonArray

/**
 * Created by CodeCrazy on 11/30/17.
 */
object Teacher {
    var  currentTeacher = teacherInfo(null,"",0)

    data class teacherInfo(val subjects: JsonArray?, val teacher_name: String, val teacher_id: Int)
}