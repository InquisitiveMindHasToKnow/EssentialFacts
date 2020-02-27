package org.ohmstheresistance.essentialfacts.network

import org.ohmstheresistance.essentialfacts.data.EssentialFactsInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

 interface EssentialFactsInfoService {
    @GET("{user}/111ad76df23f89ee2a0cb6489c68ed7f/raw/8df8376178f3c28c68977e3cd841198b5bccef29/EssentialFactsStudyGuide")
    fun retrieveEssentialFactsInformation(@Path("user") user: String): Call<List<EssentialFactsInfo>>
}