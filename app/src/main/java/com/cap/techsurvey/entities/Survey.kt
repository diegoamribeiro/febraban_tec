package com.cap.techsurvey.entities

data class Survey(
    val id: Int,
    val user: User,
    val questions: List<Question>,
    val url: String
)

data class Question(
    val id: Int,
    val text: String,
    val options: List<Option>,
    val score: Int? = null
)

data class Option(
    val id: Int,
    val text: String,
    val answer: String? = null,
    var isSelected: Boolean = false
)

data class User(
    val name: String,
    val email: String,
    val company: String,
    val area: String,
    val role: String,
    val phone: String
)
