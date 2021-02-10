package db

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

class PinnwandGuild(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PinnwandGuild>(PinnwandGuilds)

    var commandPrefix by PinnwandGuilds.commandPrefix
    var firstJoined by PinnwandGuilds.firstJoined
    var pinboardChannel by PinnwandGuilds.pinboardChannel

    override fun toString(): String {
        return "PinnwandGuild(commandPrefix='$commandPrefix', firstJoined=$firstJoined, pinboardChannel=$pinboardChannel)"
    }
}

object PinnwandGuilds : LongIdTable("pinnwand_guild") {
    val commandPrefix = varchar("command_prefix", 32).default("*")
    val firstJoined = datetime("first_joined")
    val pinboardChannel = long("pinboard").nullable().default(null)
}