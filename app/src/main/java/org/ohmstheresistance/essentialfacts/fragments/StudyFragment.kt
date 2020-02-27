package org.ohmstheresistance.essentialfacts.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.StudyFragmentBinding
import org.ohmstheresistance.essentialfacts.data.EssentialFactsInfo
import org.ohmstheresistance.essentialfacts.network.EssentialFactsInfoService
import org.ohmstheresistance.essentialfacts.recyclerview.EssentialFactsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StudyFragment : Fragment() {

    private var snackbar: Snackbar? = null
    val studyGuideList = ArrayList<EssentialFactsInfo>()
    val essentialFactsAdapter = EssentialFactsAdapter(studyGuideList)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<StudyFragmentBinding>(inflater, R.layout.study_fragment, container, false)

        binding.studyGuideRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        binding.studyGuideRecyclerView.adapter = essentialFactsAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetConnection()

    }

    private fun getInfo() {

        val service = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EssentialFactsInfoService::class.java)

        service.retrieveEssentialFactsInformation("InquisitiveMindHasToKnow")
            .enqueue(object : Callback<List<EssentialFactsInfo>> {
                override fun onResponse(
                    call: Call<List<EssentialFactsInfo>>,
                    response: Response<List<EssentialFactsInfo>>
                ) {
                    response.body()?.forEach { println("FACTS_: $it") }

                    response.body()?.let { studyGuideList.addAll(it) }
                    studyGuideList.shuffle()

                        essentialFactsAdapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<List<EssentialFactsInfo>>, t: Throwable) =
                    t.printStackTrace()
            })
    }

    private fun showSnackBar(view: View){

        snackbar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
        val snackbarView = snackbar!!.view

        snackbarView.setBackgroundColor(resources.getColor(R.color.backGroundColor))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        textView.textSize = 18f
        snackbar!!.show()
    }

    private fun checkInternetConnection(): Boolean {

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo?
            activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

        if (context?.let { isNetworkAvailable(it) }!!) {
            getInfo()
        } else {
            view?.let { showSnackBar(it) }
        }

return true
    }


    override fun onDestroy() {
        super.onDestroy()

        snackbar?.dismiss()
    }
}

