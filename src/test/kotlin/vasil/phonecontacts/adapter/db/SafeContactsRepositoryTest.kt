package vasil.phonecontacts.adapter.db

import org.hamcrest.CoreMatchers.`is`
import org.jmock.Expectations
import org.jmock.integration.junit4.JUnitRuleMockery
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import vasil.phonecontacts.core.Contact
import vasil.phonecontacts.core.ContactCategory
import vasil.phonecontacts.core.ContactNameAlreadyExistsException
import vasil.phonecontacts.core.ContactsRepository
import java.util.*

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

class SafeContactsRepositoryTest {

    @Rule
    @JvmField
    val context = JUnitRuleMockery()

    private val origin = context.mock(ContactsRepository::class.java)
    private val safeRepository = SafeContactsRepository(origin)

    @Test
    fun saveContact() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)

        context.checking(object : Expectations() {
            init {
                oneOf(origin).findByName("Vasil")
                will(returnValue(Optional.empty<Contact>()))

                oneOf(origin).save(contact)
            }
        })

        safeRepository.save(contact)
    }

    @Test
    fun getContacts() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)

        context.checking(object : Expectations() {
            init {
                oneOf(origin).getAll()
                will(returnValue(listOf(contact)))
            }
        })

        assertThat(safeRepository.getAll(), `is`(listOf(contact)))
    }

    @Test(expected = ContactNameAlreadyExistsException::class)
    fun contactWithSameNameAlreadyExists() {
        val contact = Contact("1", "Vasil", "08822222", "", ContactCategory.FAMILY)

        context.checking(object : Expectations() {
            init {
                oneOf(origin).findByName("Vasil")
                will(returnValue(Optional.of(Contact())))

                never(origin).save(contact)
            }
        })

        safeRepository.save(contact)
    }
}