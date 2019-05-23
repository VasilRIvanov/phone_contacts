package vasil.phonecontacts.core

import java.util.*

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

interface ContactsRepository {
    /**
     * Persist a [Contact]. If a contact with the same id is saved twice,
     * the first one will be overwritten. This functionality can be used
     * for updating a contact
     *
     * @throws [ContactNameAlreadyExistsException]
     */
    fun save(contact: Contact)

    /**
     * Gets all available contacts sorted by name
     */
    fun getAll(): List<Contact>

    /**
     * Finds contact by its name
     */
    fun findByName(name: String): Optional<Contact>
}