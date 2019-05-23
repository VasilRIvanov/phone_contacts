package vasil.phonecontacts.core

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

data class Contact(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val description: String = "",
    val category: ContactCategory = ContactCategory.KNOWN
)