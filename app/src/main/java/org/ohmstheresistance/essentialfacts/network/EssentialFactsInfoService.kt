package org.ohmstheresistance.essentialfacts.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

 interface EssentialFactsInfoService {
    @GET("{user}/111ad76df23f89ee2a0cb6489c68ed7f/raw/0ffae60f6fa40fbd9e913d431905e06ff405148e/EssentialFactsStudyGuide")
    fun retrieveEssentialFactsInformation(@Path("user") user: String): Call<List<EssentialFactsInfo>>
}