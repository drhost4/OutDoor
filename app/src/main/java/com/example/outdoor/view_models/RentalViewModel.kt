package com.example.outdoor.view_models

import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.outdoor.R
import com.example.outdoor.api.RentalAPI
import com.example.outdoor.models.DialogInfo
import com.example.outdoor.models.api.IncludedData
import com.example.outdoor.models.RentalListData
import com.example.outdoor.models.api.RentalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RentalViewModel : ViewModel() {
    val rentalListData = MutableLiveData<List<RentalListData>>()
    val loadingIndicator = MutableLiveData<Boolean>()
    val displayDialog = MutableLiveData<DialogInfo>()
    val showKeyboard = MutableLiveData<Boolean>()

    private val repoRetriever = RentalAPI()
    private var inputSearchString = ""

    private val callback = object : Callback<RentalResponse> {
        override fun onFailure(call: Call<RentalResponse>?, t: Throwable?) {
            processResponseFailure()
        }

        override fun onResponse(
            call: Call<RentalResponse>?,
            response: Response<RentalResponse>?
        ) {
            processResponseSuccess(response)
        }
    }

    private fun processResponseFailure() {
        loadingIndicator.postValue(false)
        displayDialog.postValue(
            DialogInfo(
                R.string.no_results_title,
                R.string.no_results_description,
                R.string.no_results_positive
            ))
        Log.d(RentalViewModel::class.java.simpleName, "Failure in retrieving results")
    }

    private fun processResponseSuccess(response: Response<RentalResponse>?) {
        loadingIndicator.postValue(false)
        response?.isSuccessful.let {
            val rentalResponse = response?.body()
            if (rentalResponse?.rentalData?.size!! > 0) {
                val rentalData = ArrayList<RentalListData>()
                for (rentalResponseData in rentalResponse.rentalData) {
                    rentalData.add(
                        RentalListData(
                            rentalResponseData.attributes.rentalCompanyName,
                            getImageUrl(
                                rentalResponseData.relationships.primaryImage.imageData.imageId,
                                rentalResponse.includedData
                            )
                        )
                    )
                }

                rentalListData.postValue(rentalData)
            } else {
                displayDialog.postValue(
                    DialogInfo(
                        R.string.no_results_title,
                        R.string.no_results_description,
                        R.string.no_results_positive
                    ))
                Log.d(RentalViewModel::class.java.simpleName, "No results found for search: " + inputSearchString)
            }
        }
    }

    fun processSearchIconSelection() {
        showKeyboard.postValue(true)
    }

    fun processSearchFieldSelection() {
        showKeyboard.postValue(true)
    }

    fun processEnteredKey(keyCode: Int, event: KeyEvent, searchString: String): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            showKeyboard.postValue(true)
            if (searchString.isNotBlank()) {
                inputSearchString = searchString
                showKeyboard.postValue(false)
                loadingIndicator.postValue(true)
                repoRetriever.getRentals(searchString, callback)
            }
            return true
        }

        return false
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