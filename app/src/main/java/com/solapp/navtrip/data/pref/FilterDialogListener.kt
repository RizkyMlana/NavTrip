package com.solapp.navtrip.data.pref

interface FilterDialogListener {
    fun onFilterApplied(
        search: String,
        minDistance: Int?,
        maxDistance: Int?,
        categories: List<String>
    )
}
