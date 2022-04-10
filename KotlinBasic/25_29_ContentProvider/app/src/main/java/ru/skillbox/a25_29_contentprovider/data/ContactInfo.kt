package ru.skillbox.a25_29_contentprovider.data

sealed class ContactInfo {
    data class Phone(
        val id: Int,
        val phone: String
    ) : ContactInfo()

    data class Email(
        val id: Int,
        val email: String
    ) : ContactInfo()
}