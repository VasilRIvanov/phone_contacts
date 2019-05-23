package vasil.phonecontacts.adapter.db

import com.google.gson.GsonBuilder
import vasil.phonecontacts.core.Contact
import vasil.phonecontacts.core.ContactsRepository
import java.io.File
import java.util.*

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

class LocalContactsRepository(private val file: File) : ContactsRepository {

    private val gson = GsonBuilder().create()

    override fun save(contact: Contact) {
        val contacts = file.getContacts()

        if (contacts.any { it.id == contact.id }) {
            contacts.removeIf { it.id == contact.id }
        }

        contacts.add(contact)

        file.saveContacts(contacts)
    }

    override fun getAll() = file.getContacts().sortedBy { it.name }

    override fun findByName(name: String): Optional<Contact> {
        val contacts = file.getContacts()
        val foundContact = contacts.find { it.name.equals(name, true) }

        return if (foundContact == null) Optional.empty() else Optional.of(foundContact)
    }

    private fun File.getContacts(): MutableList<Contact> {
        val content = this.readText()
        return if (content.isBlank()) {
            mutableListOf()
        } else {
            gson
                .fromJson(content, ContactsContainer::class.java)
                .contacts
                .toMutableList()
        }
    }

    private fun File.saveContacts(contacts: List<Contact>) {
        val container = ContactsContainer(contacts)
        this.writeText(gson.toJson(container))
    }

    data class ContactsContainer(val contacts: List<Contact>)
}
