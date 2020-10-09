package com.sorsix.urlshortener.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "urls")
data class Url(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        val id: Long,

        @Column(name = "long_url")
        @JsonProperty("original_url")
        val longUrl: String,

        @Column(name = "short_url")
        @JsonProperty("short_url")
        val shortUrl: String
)