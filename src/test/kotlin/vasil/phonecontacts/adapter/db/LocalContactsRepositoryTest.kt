package vasil.phonecontacts.adapter.db

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import vasil.phonecontacts.core.Contact
import vasil.phonecontacts.core.ContactCategory
import vasil.phonecontacts.core.ContactsRepository
import java.util.*

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

class LocalContactsRepositoryTest {

    @Rule
    @JvmField
    val tempDir = TemporaryFolder()

    private lateinit var repository: ContactsRepository

    @Before
    fun setUp() {
        val file = tempDir.newFile("local_db.json")
        repository = LocalContactsRepository(file)
    }

    @Test
    fun happyPath() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)
        repository.save(contact)

        assertThat(repository.findByName("Vasil"), `is`(Optional.of(contact)))
    }

    @Test
    fun overwriteContact() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)
        val updatedContact = contact.copy(name = "Georgi")

        repository.save(contact)
        repository.save(updatedContact)

        assertThat(repository.getAll(), `is`(listOf(updatedContact)))
    }

    @Test
    fun fetchAllContactsSortedByName() {
        val contactsList = listOf(
            Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY),
            Contact("2", "Peter", "08822222", "", ContactCategory.FAMILY),
            Contact("3", "Martin", "08822222", "", ContactCategory.FAMILY),
            Contact("4", "George", "08822222", "", ContactCategory.FAMILY),
            Contact("5", "Stanimir", "08822222", "", ContactCategory.FAMILY)
        )

        contactsList.forEach { repository.save(it) }

        assertThat(repository.getAll(), `is`(contactsList.sortedBy { it.name }))
    }

    @Test
    fun findByNameIgnoringCase() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)
        repository.save(contact)

        assertThat(repository.findByName("vasil"), `is`(Optional.of(contact)))
    }

    @Test
    fun unableToFindContactByName() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)
        repository.save(contact)

        assertFalse(repository.findByName("Georgi").isPresent)
    }
}