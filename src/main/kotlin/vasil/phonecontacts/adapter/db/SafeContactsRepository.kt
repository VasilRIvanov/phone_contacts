package vasil.phonecontacts.adapter.db

import vasil.phonecontacts.core.Contact
import vasil.phonecontacts.core.ContactNameAlreadyExistsException
import vasil.phonecontacts.core.ContactsRepository

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

class SafeContactsRepository(private val origin: ContactsRepository) : ContactsRepository {
    override fun save(contact: Contact) {
        val possibleDuplicate = origin.findByName(contact.name)
        if (possibleDuplicate.isPresent) throw ContactNameAlreadyExistsException()

        origin.save(contact)
    }

    override fun getAll() = origin.getAll()

    override fun findByName(name: String) = origin.findByName(name)
}