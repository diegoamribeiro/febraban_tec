package com.cap.techsurvey.entities

data class Survey(
    val id: Int,
    val name: String,
    val email: String,
    val company: String,
    val questions: List<Question>,
    val url: String
)

data class Question(
    val id: Int,
    val text: String,
    val options: List<Option>
)

data class Option(
    val id: Int,
    val text: String,
    val answer: String? = null,
    var isSelected: Boolean = false
)
