package com.sorsix.urlshortener.service

import com.sorsix.urlshortener.domain.Url
import com.sorsix.urlshortener.repository.UrlRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UrlService(val repository: UrlRepository,
                 val validatorService: UrlValidatorService,
                 val urlConverterService: UrlConverterService) {

    val logger: Logger = LoggerFactory.getLogger(UrlService::class.java)

    fun convertToShortUrl(longUrl: String): Url? {
        if (!validatorService.isValidUrl(longUrl)) {
            logger.warn("Invalid url [{}]", longUrl)
            return null
        }
        return repository.findByLongUrl(longUrl) ?: let {
            val entity = repository.save(Url(0, longUrl, ""))
            val shortUrl = urlConverterService.encodeUrl(entity.id)
            val url = Url(entity.id, longUrl, shortUrl)
            logger.info("New url saved [{}]", url)
            repository.save(url)
        }
    }

    fun getLongUrl(shortUrl: String): String? {
        val id = urlConverterService.decode(shortUrl)
        val entity = repository.findById(id).orElseGet {
            logger.warn("Invalid short URL [{}]", shortUrl)
            null
        }
        return entity?.longUrl
    }
}