package db

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

class PinboardMessage(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PinboardMessage>(PinboardMessages)

    var guild by PinnwandGuild referencedOn PinboardMessages.guild
    var channel by PinboardMessages.channel
    var message by DiscordMessage referencedOn PinboardMessages.message

    override fun toString(): String {
        return "PinboardMessage(id=$id, guild=$guild, channel=$channel, message=$message)"
    }


}

object PinboardMessages : LongIdTable("pinboard_message") {
    val guild = reference("guild", PinnwandGuilds)
    val channel = long("channel")
    val message = reference("message", DiscordMessages).uniqueIndex()
}