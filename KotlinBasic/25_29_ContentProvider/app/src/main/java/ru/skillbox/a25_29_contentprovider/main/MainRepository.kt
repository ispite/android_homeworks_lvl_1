package ru.skillbox.a25_29_contentprovider.main

import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.provider.ContactsContract.RawContacts
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.a25_29_contentprovider.data.Contact
import ru.skillbox.a25_29_contentprovider.data.ContactInfo


class MainRepository(private val context: Context) {

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null,
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    suspend fun getContact(id: Long): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            "${ContactsContract.Contacts._ID} = ?",
            arrayOf(id.toString()),
            null,
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val name = cursor.getString(nameIndex).orEmpty()
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)

            list.add(Contact(id = id, name = name, phones = emptyList()))
        } while (cursor.moveToNext())
        return list
    }


    suspend fun getPhonesAndEmailsForContact(id: Long): List<ContactInfo> =
        withContext(Dispatchers.IO) {
            val list = mutableListOf<ContactInfo>()
            list.addAll(getPhonesForContact(id))
            list.addAll(getEmailsForContact(id))
            list
        }

    private fun getPhonesForContact(contactId: Long): List<ContactInfo.Phone> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =  ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getEmailsForContact(contactId: Long): List<ContactInfo.Email> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getEmailsFromCursor(it)
        }.orEmpty()
    }

    private fun getEmailsFromCursor(cursor: Cursor): List<ContactInfo.Email> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<ContactInfo.Email>()
        do {
            val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
            val email = cursor.getString(emailIndex)
            list.add(ContactInfo.Email(cursor.position, email))
        } while (cursor.moveToNext())
        return list
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<ContactInfo.Phone> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<ContactInfo.Phone>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            list.add(ContactInfo.Phone(cursor.position, number))
        } while (cursor.moveToNext())
        return list
    }

    suspend fun removeContact(contactId: Long) = withContext(Dispatchers.IO) {
        /*context.contentResolver.delete(
            ContactsContract.Contacts.CONTENT_URI,
            "${ContactsContract.Contacts._ID} = ?",
            arrayOf((contactId.toString()))
        )*/
        val modifiedURI =
            Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId.toString())
        //val modifiedURI2 = Uri.withAppendedPath(ContactsContract.RawContacts.CONTENT_URI, contactId.toString())

        context.contentResolver.delete(
            modifiedURI,
            null,
            null
        )

/*        context.contentResolver.query(
            modifiedURI2,
            null,
            null,
            null,
            null
        )?.use {
            if (it.moveToFirst()) {
                context.contentResolver.delete(modifiedURI2, null, null)
            }
        }*/
    }

    suspend fun saveContact(firstName: String, lastName: String, phone: String, email: String?) =
        withContext(Dispatchers.IO) {

            //val contactId = saveRawContact()
            saveContactName(firstName, lastName)
            val contactId = getLastContactID()
            saveContactPhone(contactId, phone)
            email?.let { saveContactEmail(contactId, email) }
        }

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        Log.d("MainRepository", "saveRawContact uri:$uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw contact")
    }

    private suspend fun getLastContactID(): Long = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts._ID + " DESC"
        )?.use {
            getLastIDFromCursor(it)
        } ?: throw Exception("Content resolver error")

    }

    private fun getLastIDFromCursor(cursor: Cursor): Long {
        if (cursor.moveToFirst().not()) throw Exception("Cursor can not reach ID")
        val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
        return cursor.getLong(idIndex)
    }

    private fun saveContactName(firstName: String, lastName: String) {
        val operationList = ArrayList<ContentProviderOperation>()

        operationList.add(
            ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.GIVEN_NAME, firstName)
                .withValue(StructuredName.FAMILY_NAME, lastName)
                .build()
        )

        try {
            context.contentResolver.applyBatch(
                ContactsContract.AUTHORITY,
                operationList as java.util.ArrayList<ContentProviderOperation>
            )
        } catch (e: Exception) {
            Log.d("MainRepository", "saveContactName:", e)
        }
    }

    private fun saveContactPhone(contactId: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactEmail(contactId: Long, Email: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Email.DATA, Email)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }


    suspend fun saveContactWithArgs() {
        withContext(Dispatchers.IO) {

            val op_list = ArrayList<ContentProviderOperation>()
            op_list.add(
                ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                    .withValue(RawContacts.ACCOUNT_TYPE, null)
                    .withValue(
                        RawContacts.ACCOUNT_NAME,
                        null
                    ) //.withValue(RawContacts.AGGREGATION_MODE, RawContacts.AGGREGATION_MODE_DEFAULT)
                    .build()
            )

            // first and last names

            // first and last names
            op_list.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(StructuredName.GIVEN_NAME, "Second Name")
                    .withValue(StructuredName.FAMILY_NAME, "First Name")
                    .build()
            )

            op_list.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    )
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "09876543210")
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME
                    )
                    .build()
            )
            op_list.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                    )
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, "abc@xyz.com")
                    .withValue(
                        ContactsContract.CommonDataKinds.Email.TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK
                    )
                    .build()
            )

            try {
                val results: Array<ContentProviderResult> =
                    context.contentResolver.applyBatch(ContactsContract.AUTHORITY, op_list)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}