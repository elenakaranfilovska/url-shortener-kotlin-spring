package com.sorsix.urlshortener.service

import com.sorsix.urlshortener.domain.Url
import com.sorsix.urlshortener.repository.UrlRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


internal class UrlServiceTest {

    @RelaxedMockK
    lateinit var repository: UrlRepository

    @RelaxedMockK
    lateinit var serviceConverter: UrlConverterService

    @RelaxedMockK
    lateinit var serviceValidator: UrlValidatorService

    private lateinit var service: UrlService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        service = UrlService(repository, serviceValidator, serviceConverter)
    }

    @Test
    fun `convertToShortUrl should return null if url is invalid`() {
        val url = "some url"
        every { serviceValidator.isValidUrl(url) } returns false

        val actual = service.convertToShortUrl(url)

        assertEquals(null, actual)
    }

    @Test
    fun `convertToShortUrl should return encoded short url`() {
        val longUrl = "some url"
        val id = 2L
        val expected = Url(id, longUrl, "c")
        every { serviceValidator.isValidUrl(longUrl) } returns true
        every { repository.findByLongUrl(longUrl) } returns null
        every { serviceConverter.encodeUrl(id) } returns "c"
        every { repository.save(Url(0, longUrl, "")) } returns expected
        every { repository.save(expected) } returns expected

        val actual = service.convertToShortUrl(longUrl)

        assertEquals(expected, actual)
    }

    @Test
    fun `convertToShortUrl should return short url without encoding again`() {
        val url = Url(2, "long url", "short url")
        val longUrl = "long url"
        every { serviceValidator.isValidUrl(longUrl) } returns true
        every { repository.findByLongUrl(longUrl) } returns url

        val actual = service.convertToShortUrl(longUrl)

        assertEquals(url, actual)
    }

    @Test
    fun `getLongUrl should return null when short url doesn't exist`() {
        val shortUrl = "short url"
        val id = 1L
        every { serviceConverter.decode(shortUrl) } returns id
        every { repository.findById(id) } returns Optional.empty()

        val actual = service.getLongUrl(shortUrl)

        assertEquals(null, actual)
    }

    @Test
    fun `getLongUrl should return long url when short url is valid`() {
        val shortUrl = "short url"
        val id = 1L
        val longUrl = "long url"
        val url = Url(id, longUrl, shortUrl)
        every { serviceConverter.decode(shortUrl) } returns id
        every { repository.findById(1) } returns Optional.of(url)

        val actual = service.getLongUrl(shortUrl)

        assertEquals(longUrl, actual)
    }

}