package com.sorsix.urlshortener.repository

import com.sorsix.urlshortener.domain.Url
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : JpaRepository<Url, Long> {
    fun findByLongUrl(longUrl: String): Url?
}