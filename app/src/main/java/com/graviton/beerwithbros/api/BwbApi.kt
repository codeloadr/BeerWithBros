package com.graviton.beerwithbros.api

import com.graviton.beerwithbros.model.Beer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface BwbApi {
    companion object {
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }

    @GET("beers")
    suspend fun getBeers(
        @Query("abv_gt") abvMoreThan: Int? = null,
        @Query("name") name: String? = null,
        @Query("brewed_before") brewedBefore: Date? = null,
        @Query("brewed_after") brewedAfter: Date? = null,
        @Query("hops") hops: String? = null,
        @Query("malt") malt: String? = null,
        @Query("food") foodMatch: String? = null,
        @Query("page") page: Int?= null,
        @Query("per_page") pageSize: Int?= null,
    ): List<Beer>

    @GET("beers/random")
    suspend fun getRandomBeer(): Beer
}