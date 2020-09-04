package com.example.outdoor

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
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

        viewModel.showKeyboard.observe(this, Observer { hide -> displayKeyboard(hide) })
        viewModel.displayDialog.observe(this, Observer { dialogInfo -> displayDialog(dialogInfo) })
        viewModel.rentalListData.observe(this, Observer { rentalList ->  displaySearchList(rentalList)})
        rentalRecycler.layoutManager = LinearLayoutManager(context)
        val emptyRentalListData = ArrayList<RentalListData>()
        rentalListAdapter.setRentals(emptyRentalListData)
        rentalRecycler.adapter = rentalListAdapter

        searchIcon.setOnClickListener { viewModel.processSearchIconSelection() }
        searchString.setOnClickListener { viewModel.processSearchFieldSelection() }
        searchString.setOnKeyListener { _, keyCode: Int, event: KeyEvent ->
            viewModel.processEnteredKey(keyCode, event, searchString.text.toString())
        }
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

    fun displayKeyboard(showKeyboard: Boolean) {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (showKeyboard) {
            inputMethodManager.showSoftInput(searchString, InputMethodManager.SHOW_FORCED)
            searchString.isFocusable = true
        } else {
            inputMethodManager.hideSoftInputFromWindow(
                searchString.windowToken,
                0
            )
        }
    }

}