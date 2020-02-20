package org.ohmstheresistance.essentialfacts.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

 interface EssentialFactsInfoService {
    @GET("{user}/111ad76df23f89ee2a0cb6489c68ed7f/raw/8c9f7a9fd3caeda6f9e4f964d05a5824739830fa/EssentialFactsStudyGuide")
    fun retrieveEssentialFactsInformation(@Path("user") user: String): Call<List<EssentialFactsInfo>>
}