package vasil.phonecontacts

import vasil.phonecontacts.adapter.db.LocalContactsRepository
import vasil.phonecontacts.adapter.db.SafeContactsRepository
import java.io.File

/**
 * @author Vasil Ivanov (browzeff@gmail.com)
 */

fun main(args: Array<String>) {
    val file = File("local_db.json")
    file.createNewFile()

    val repository = SafeContactsRepository(LocalContactsRepository(file))

    //todo
}