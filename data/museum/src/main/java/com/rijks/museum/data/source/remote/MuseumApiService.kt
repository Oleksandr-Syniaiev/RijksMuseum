package com.rijks.museum.data.source.remote

import com.rijks.museum.data.source.remote.model.ListOfArtsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MuseumApiService {

    @GET("api/{culture}/collection")
    suspend fun getListOfArts(
        @Path("culture") culture: String,
        @Query("p") page: Int,
        @Query("ps") pageSize: Int,
        @Query("s") sorting: String = "artist",
        @Query("imgonly") imageOnly: Boolean = true
    ): ListOfArtsResponse
}
