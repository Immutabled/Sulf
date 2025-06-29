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

abstract class SingleFileRepository<T>(clazz: Class<T>,val file: File) {

    private var adapter = MoshiUtil.instance.adapter<List<T>>(Types.newParameterizedType(List::class.java,clazz))

    init {

        if (!this.file.exists()) {
            this.update(listOf())
        }

    }

    open fun findAll():List<T> {
        return this.adapter.fromJson(JsonReader.of(FileSystem.SYSTEM.source(this.file).buffer())) ?: listOf()
    }

    open fun update(values: List<T>) {

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