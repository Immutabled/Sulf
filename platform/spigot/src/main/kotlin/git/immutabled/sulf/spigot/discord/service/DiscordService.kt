package git.immutabled.sulf.spigot.discord.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscordService {

    @GET("guilds/{id}")
    fun getGuildInfo(
        @Path("id") id: Long,
        @Query("with_counts") counts: Boolean = true
    ): Call<Map<String, Any>>

}