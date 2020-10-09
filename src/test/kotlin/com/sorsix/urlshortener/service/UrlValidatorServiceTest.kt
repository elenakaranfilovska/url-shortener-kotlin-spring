package com.sorsix.urlshortener.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UrlValidatorServiceTest {

    private val service: UrlValidatorService = UrlValidatorService()

    @Test
    fun `isValidUrl should return true for valid URL`() {
        val url = "https://www.google.com/"

        val actual = service.isValidUrl(url)

        assertEquals(true, actual)
    }

    @Test
    fun `isValidUrl should return false for invalid URL`() {
        val url = "twitter.com"

        val actual = service.isValidUrl(url)

        assertEquals(false, actual)
    }
}