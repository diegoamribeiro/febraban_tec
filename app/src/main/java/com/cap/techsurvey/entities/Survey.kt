package com.cap.techsurvey.entities

import java.io.Serializable

data class Survey(
    val id: Int,
    val user: User,
    val questions: List<Question>,
    val url: String
) : Serializable

data class Question(
    val id: Int,
    val text: String,
    val options: List<Option>,
    val score: Int? = null
) : Serializable

data class Option(
    val id: Int,
    val text: String,
    val answer: String? = null,
    var isSelected: Boolean = false
) : Serializable

data class User(
    val name: String,
    val email: String? = null,
    val company: String,
    val role: String,
    val phone: String? = null,
    val area: String? = null
) : Serializable
