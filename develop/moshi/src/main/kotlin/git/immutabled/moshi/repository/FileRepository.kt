package git.immutabled.moshi.repository

import com.squareup.moshi.JsonReader
import git.immutabled.moshi.MoshiUtil
import git.immutabled.moshi.setPrettyPrinting
import okhttp3.internal.io.FileSystem
import okio.buffer
import java.io.File
import java.io.FileWriter
import java.io.IOException

abstract class FileRepository<T>(clazz: Class<T>,val container: File) {

    private var adapter = MoshiUtil.instance.adapter(clazz)

    init {
        this.container.mkdir()
    }

    abstract fun getFile(type: T):File

    open fun findAll():List<T> {
        return (this.container.listFiles() ?: arrayOf())
            .filterNotNull()
            .mapNotNull{JsonReader.of(FileSystem.SYSTEM.source(it).buffer())}
            .mapNotNull{this.adapter.setPrettyPrinting().fromJson(it)}
    }

    open fun updateById(type: T) {

        val writer = FileWriter(this.getFile(type))

        try {
            writer.write(this.adapter.setPrettyPrinting().toJson(type))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return
        }

        writer.close()
    }

    open fun deleteById(type: T) {

        val file = this.getFile(type)

        if (!file.exists()) {
            return
        }

        file.delete()
    }

}