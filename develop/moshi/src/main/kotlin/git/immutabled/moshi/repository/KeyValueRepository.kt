package git.immutabled.moshi.repository

import com.squareup.moshi.JsonReader
import com.squareup.moshi.Types
import git.immutabled.moshi.MoshiUtil
import git.immutabled.moshi.setPrettyPrinting
import okhttp3.internal.io.FileSystem
import okio.buffer
import java.io.File
import java.io.FileWriter
import java.io.IOException

abstract class KeyValueRepository<K,V>(key: Class<out K>,value: Class<out V>,val file: File) {

    private var adapter = MoshiUtil.instance.adapter<MutableMap<K,V>>(Types.newParameterizedType(MutableMap::class.java,key,value))

    init {

        if (!this.file.exists()) {
            this.update(mutableMapOf())
        }

    }

    open fun findAll():Map<K,V> {
        return this.adapter.fromJson(JsonReader.of(FileSystem.SYSTEM.source(this.file).buffer())) ?: mutableMapOf()
    }

    open fun update(values: MutableMap<K,V>) {

        val writer = FileWriter(this.file)

        try {
            writer.write(this.adapter.setPrettyPrinting().toJson(values))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return
        }

        writer.close()
    }

    open fun delete() {

        if (!this.file.exists()) {
            return
        }

        this.file.delete()
    }

}