package com.devcommop.myapplication.ui.components.searchscreen

sealed class SearchScreenEvents{
    data class OnSearchQuerySubmit(val searchQuery: String): SearchScreenEvents()
}
