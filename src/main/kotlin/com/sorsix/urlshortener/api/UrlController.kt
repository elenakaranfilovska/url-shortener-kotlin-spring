package com.sorsix.urlshortener.api

import com.sorsix.urlshortener.service.UrlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping
class UrlController(val service: UrlService) {

    @PostMapping("/api/shorturl/new")
    fun getShortUrl(@RequestBody longUrl: String): Any {
        return service.convertToShortUrl(longUrl)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.badRequest().body(mapOf("error" to "invalid URL"))
    }

    @GetMapping("/{shortUrl}")
    fun getLongUrl(@PathVariable shortUrl: String, response: HttpServletResponse): Any {
        return service.getLongUrl(shortUrl)?.let {
            response.sendRedirect(it)
        } ?: ResponseEntity.badRequest().body(mapOf("error" to "invalid short URL"))
    }


}