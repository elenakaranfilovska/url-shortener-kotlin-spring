package com.sorsix.urlshortener.service

import org.springframework.stereotype.Service
import kotlin.math.pow

@Service
class UrlConverterService {
    private final val allowedString =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val base = allowedString.length

    fun encodeUrl(id: Long): String {
        val encodedUrl = StringBuilder()
        if (id == 0L)
            return allowedString[0].toString()
        var ind = id
        while (ind > 0) {
            encodedUrl.append(allowedString[((ind % base).toInt())])
            ind /= base
        }
        return encodedUrl.reversed().toString()
    }

    fun decode(shortUrl: String): Long {
        val length = shortUrl.length
        var id = 0L
        var counter = 1
        for (i in 0 until length) {
            id += (allowedString.indexOf(shortUrl[i]) * base.toDouble().pow(length - counter)).toLong()
            counter++
        }
        return id
    }
}