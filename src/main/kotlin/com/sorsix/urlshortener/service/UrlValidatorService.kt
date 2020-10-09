package com.sorsix.urlshortener.service

import org.springframework.stereotype.Service
import java.net.URL

@Service
class UrlValidatorService {

    fun isValidUrl(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: Exception) {
            false
        }
    }
}