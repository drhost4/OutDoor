package com.example.outdoor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.outdoor.models.IncludedData
import com.example.outdoor.models.RentalListData
import com.example.outdoor.models.RentalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RentalViewModel : ViewModel() {
    private val rentalListData = MutableLiveData<List<RentalListData>>()
    private val repoRetriever = RentalAPI()

    private val callback = object : Callback<RentalResponse> {
        override fun onFailure(call: Call<RentalResponse>?, t: Throwable?) {
//            loadingIndicator.postValue(false)
//            if (!ConnectivityUtility.isConnected(connectivityManager)) {
//                displayDialog.postValue(
//                    DialogInfo(
//                        R.string.no_internet_title,
//                        R.string.no_internet_text,
//                        R.string.no_internet_positive,
//                        true,
//                        R.string.no_internet_negative
//                    )
//                )
//            } else {
//                displayDialog.postValue(
//                    DialogInfo(
//                        R.string.no_restaurants_title,
//                        R.string.no_restaurants_text,
//                        R.string.no_restaurants_positive,
//                        false, 0
//                    )
//                )
//            }
//
            Log.e("MainActivity", "Problem calling Restaurants API}")
        }

        override fun onResponse(
            call: Call<RentalResponse>?,
            response: Response<RentalResponse>?
        ) {
//            loadingIndicator.postValue(false)
            response?.isSuccessful.let {
                val rentalResponse = response?.body()
                if (rentalResponse?.rentalData?.size!! > 0) {
                    val rentalData = ArrayList<RentalListData>()
                    for (rentalResponseData in rentalResponse.rentalData) {
                        rentalData.add(RentalListData(rentalResponseData.attributes.rentalCompanyName,
                            getImageUrl(rentalResponseData.relationships.primaryImage.imageData.imageId, rentalResponse.includedData)
                            ))
                    }

                    rentalListData.postValue(rentalData)

//                    restaurantsData.postValue(restaurants)
//                    displayDialog.postValue(
//                        DialogInfo(
//                            R.string.no_restaurants_title,
//                            R.string.no_restaurants_text,
//                            R.string.no_restaurants_positive,
//                            false, 0
//                        )
//                    )
                } else {
//                    restaurantsData.postValue(response?.body())
//                    restaurants = response?.body() as ArrayList<Restaurant>
                }
            }
        }
    }

    fun loadData(searchString: String) {
//        val rentalData = rentalListData.value
//        if (rentalData?.size!! > 0) {
//            rentalListData.postValue(rentalData)
//        } else {
//            loadingIndicator.postValue(true)
            repoRetriever.getRentals(searchString, callback)
 //       }
    }

    private fun getImageUrl(imageId: String, includedData: ArrayList<IncludedData>): String {
        for (included in includedData) {
            if (included.includedId == imageId) {
                return included.includedAttributes.imageUrl
            }
        }

        return ""
    }
}