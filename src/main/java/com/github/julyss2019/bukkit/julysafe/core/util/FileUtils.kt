package com.github.julyss2019.bukkit.julysafe.core.util

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {
    fun writeInputStreamToFile(input: InputStream, file: File, overwrite: Boolean) {
        if (!overwrite && file.exists()) {
            return
        }

        file.parentFile.let {
            if (!it.exists() && !it.mkdirs()) {
                throw RuntimeException("mkdirs failed: ${it.absolutePath}")
            }
        }

        val out = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var read: Int

        while (input.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }
}