package ru.skillbox.a25_29_contentprovider.data

sealed class ContactInfo {
    data class Phone(
        val id: Long,
        val phone: String
    ) : ContactInfo()

    data class Email(
        val id: Long,
        val email: String
    ) : ContactInfo()
}