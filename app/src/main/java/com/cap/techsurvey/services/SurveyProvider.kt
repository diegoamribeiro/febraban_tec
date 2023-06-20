package com.cap.techsurvey.services

import com.cap.techsurvey.entities.Survey
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SurveyProvider {

    private val db = Firebase.firestore.collection("Surveys")

    fun create(survey: Survey): Task<DocumentReference> {
        return db.add(survey)
    }

    fun update(survey: Survey): Task<Void> {
        return db.document(survey.id).set(survey, SetOptions.merge())
    }

    fun getNewDocumentReference(): DocumentReference {
        return db.document()
    }
}