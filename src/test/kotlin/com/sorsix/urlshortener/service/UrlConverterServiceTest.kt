package com.sorsix.urlshortener.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UrlConverterServiceTest {
    private val service: UrlConverterService = UrlConverterService()

    @Test
    fun `decode should return id from short URL`() {
        val id = 5L
        val shortUrl = service.encodeUrl(id)

        val actual = service.decode(shortUrl)

        assertEquals(id, actual)
    }
}