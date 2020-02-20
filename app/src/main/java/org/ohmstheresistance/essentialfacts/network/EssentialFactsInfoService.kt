package org.ohmstheresistance.essentialfacts.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

 interface EssentialFactsInfoService {
    @GET("{user}/111ad76df23f89ee2a0cb6489c68ed7f/raw/ddb9234b3333189f5259a2acd83ebc79e50ddde2/EssentialFactsStudyGuide")
    fun retrieveEssentialFactsInformation(@Path("user") user: String): Call<List<EssentialFactsInfo>>
}