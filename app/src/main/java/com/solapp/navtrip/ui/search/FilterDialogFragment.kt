package com.solapp.navtrip.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.solapp.navtrip.R
import com.solapp.navtrip.data.pref.FilterDialogListener

class FilterDialogFragment : DialogFragment() {

    private lateinit var etSearch: EditText
    private lateinit var etMinDistance: EditText
    private lateinit var etMaxDistance: EditText
    private lateinit var cbFoods: CheckBox
    private lateinit var cbBeverages: CheckBox
    private lateinit var cbDestination: CheckBox
    private lateinit var btnReset: Button
    private lateinit var btnApply: Button
    private lateinit var btnClose: TextView
    var listener: FilterDialogListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE)) // Transparan background
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch = view.findViewById(R.id.etSearch)
        etMinDistance = view.findViewById(R.id.etMinDistance)
        etMaxDistance = view.findViewById(R.id.etMaxDistance)
        cbFoods = view.findViewById(R.id.cbFoods)
        cbBeverages = view.findViewById(R.id.cbBeverages)
        cbDestination = view.findViewById(R.id.cbDestination)
        btnReset = view.findViewById(R.id.btnReset)
        btnApply = view.findViewById(R.id.btnApply)
        btnClose = view.findViewById(R.id.btnClose)

        btnReset.setOnClickListener { resetForm() }
        btnApply.setOnClickListener {
            applyFilters()
            dismiss()
        }
        btnClose.setOnClickListener { dismiss() }
    }
    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    private fun resetForm() {
        etSearch.setText("")
        etMinDistance.setText("")
        etMaxDistance.setText("")
        cbFoods.isChecked = false
        cbBeverages.isChecked = false
        cbDestination.isChecked = false
    }

    private fun applyFilters() {
        val search = etSearch.text.toString()
        val minDistance = etMinDistance.text.toString().toIntOrNull()
        val maxDistance = etMaxDistance.text.toString().toIntOrNull()
        val categories = mutableListOf<String>()

        if (cbFoods.isChecked) categories.add("Foods")
        if (cbBeverages.isChecked) categories.add("Beverages")
        if (cbDestination.isChecked) categories.add("Destination")

        Log.d("Filter", "Search=$search, Distance=$minDistance-$maxDistance km, Categories=$categories")

        listener?.onFilterApplied(search, minDistance, maxDistance, categories)

        dismiss()
    }


}
