package com.cap.techsurvey.entities

import java.io.Serializable

data class Survey(
    var id: String,
    val user: User,
    val questions: List<Question>? = null,
    val url: String? = null
) : Serializable

data class Question(
    val id: String? = null,
    val text: String? = null,
    var option: Option? = null,
    val weight: Int? = null,
    var score: Double? = null
) : Serializable

data class Option(
    val id: String? = null,
    val text: String? = null,
    val answer: String? = null,
    var isSelected: Boolean = false,
    val score: Int? = null
) : Serializable

data class User(
    val name: String,
    val email: String? = null,
    val company: String,
    val role: String,
    val phone: String? = null,
    val area: String? = null
) : Serializable
