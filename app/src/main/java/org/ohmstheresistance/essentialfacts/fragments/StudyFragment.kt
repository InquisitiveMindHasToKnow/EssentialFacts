package org.ohmstheresistance.essentialfacts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.network.EssentialFactsInfoService
import org.ohmstheresistance.essentialfacts.network.EssentialFactsInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StudyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.study_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val service = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EssentialFactsInfoService::class.java)

        service.retrieveEssentialFactsInformation("InquisitiveMindHasToKnow")
            .enqueue(object : Callback<List<EssentialFactsInfo>> {
                override fun onResponse(call: Call<List<EssentialFactsInfo>>, response: Response<List<EssentialFactsInfo>>) {
                    response.body()?.forEach { println("FACTS_: $it") }
                }

                override fun onFailure(call: Call<List<EssentialFactsInfo>>, t: Throwable) = t.printStackTrace()
            })
    }
    }


