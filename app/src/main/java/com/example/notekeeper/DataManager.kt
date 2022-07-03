package com.example.notekeeper

object DataManager {
    val courses = HashMap<String,CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }



    private fun initializeCourses() {
        var course = CourseInfo("Android_Intents","Android Programming with Intents")
        courses.set(course.courseId,course)

        course = CourseInfo(courseId = "Android_Async", title = "Android Async Programming and Services")
        courses.set(course.courseId,course)

        course = CourseInfo(title = "Java Fundamentals: The Java Language", courseId = "Java_lang")
        courses.set(course.courseId,course)

        course = CourseInfo("Java_Core","Java Fundamnetals: The Core Platform")
        courses.set(course.courseId,course)
    }

    private fun initializeNotes() {
        var note = NoteInfo(CourseInfo("Android_Intents","Android Programming with Intents"),
            "Intents",
            "An Intent is a messaging object you can use to request an action from another app component.")
        notes.add(note)

        note = NoteInfo(CourseInfo("Android_Intents","Android Programming with Intents"),
            "Intent Filters",
            "An intent filter is an expression in an app's manifest file that specifies the type of intents that the component would like to receive.")
        notes.add(note)

        note = NoteInfo(CourseInfo("Android_Async", "Android Async Programming and Services"),
            "Asynchronous Task",
            "An asynchronous task is defined by a computation that runs on a background thread and whose result is published on the UI thread")
        notes.add(note)

        note = NoteInfo(CourseInfo( "Java_lang","Java Fundamentals: The Java Language"),
            "static in Java",
            "Static means one per class, not one for each object no matter how many instance of a class might exist")
        notes.add(note)

        note = NoteInfo(CourseInfo("Java_Core","Java Fundamnetals: The Core Platform"),
            "Core java",
            "Core Java is the part of Java programming language that is used for creating or developing a general-purpose application.")
        notes.add(note)

        note = NoteInfo(CourseInfo("Android_Intents","Android Programming with Intents"),
            "explicit intent",
            "An explicit intent is one that you use to launch a specific app component.")
        notes.add(note)

    }
}