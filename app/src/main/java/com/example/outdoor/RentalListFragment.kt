package com.example.outdoor

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outdoor.models.DialogInfo
import com.example.outdoor.models.RentalListData
import kotlinx.android.synthetic.main.rental_list_fragment.*

class RentalListFragment : Fragment() {

    private val rentalListAdapter = RentalListAdapter()
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.rental_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(RentalViewModel::class.java)
        viewModel.loadingIndicator.observe(this, Observer<Boolean> { display ->
            displayLoadingIndicator(display)
        })

        viewModel.rentalListData.observe(this, Observer { rentalList ->  displaySearchList(rentalList)})
        viewModel.loadData("trailer")
        rentalRecycler.layoutManager = LinearLayoutManager(context)
        val emptyRentalListData = ArrayList<RentalListData>()
        rentalListAdapter.setRentals(emptyRentalListData)
        rentalRecycler.adapter = rentalListAdapter
    }

    private fun displaySearchList(rentalList: List<RentalListData>) {
        rentalListAdapter.setRentals(rentalList)
        rentalListAdapter.notifyDataSetChanged()
    }

    private fun displayLoadingIndicator(display: Boolean) {
        listLoading.visibility = if (display) View.VISIBLE else View.GONE
    }

    private fun displayDialog(dialogInfo: DialogInfo) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(dialogInfo.notification)
            .setCancelable(false)
            .setPositiveButton(dialogInfo.positiveText) { dialog, id ->
                dialog.dismiss()
            }

        dialog = dialogBuilder.create()
        dialog.setTitle(dialogInfo.title)
        dialog.show()
    }
}