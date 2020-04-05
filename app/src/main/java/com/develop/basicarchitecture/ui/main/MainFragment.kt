package com.develop.basicarchitecture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.develop.basicarchitecture.R
import com.develop.basicarchitecture.network.dataclasses.ListItem
import com.develop.basicarchitecture.ui.main.adapter.RestaurantsAdapter
import com.develop.basicarchitecture.utility.UtilFunctions
import com.develop.basicarchitecture.utility.UtilFunctions.Companion.hideKeyboard
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val restaurantsAdapter = RestaurantsAdapter()
    private var searchResultObserver: LiveData<PagedList<ListItem>>? = null
    private var lastItemCountReceived = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setTextChangeListener()
        initializeList()
    }

    private fun setTextChangeListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                view?.let { context?.hideKeyboard(it) }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(UtilFunctions.isNetworkAvailable()) {
                    viewModel.search(query.toString())
                    observeSearchResultLiveData()
                    if (query.isNullOrEmpty()) {
                        searchView.queryHint = getString(R.string.type_to_filter)
                    }
                }else{
                    placeHolderPb.visibility = View.GONE
                    UtilFunctions.toast(getString(R.string.no_internet_available))
                }
                return true
            }
        })
    }

    private fun observeSearchResultLiveData() {
        searchResultObserver?.removeObservers(viewLifecycleOwner)
        searchResultObserver = viewModel.getRestaurantLiveData()

        restaurantsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (itemCount != 0 && lastItemCountReceived != itemCount) {
                    hideRecycleView(false)
                }
                if (itemCount == 0 && itemCount == 0) {
                    hideRecycleView(true)
                }
                lastItemCountReceived = itemCount
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                hideRecycleView(true)
            }
        })

        searchResultObserver?.observe(viewLifecycleOwner, Observer {
            restaurantsAdapter.submitList(it)
        })
    }

    private fun hideRecycleView(show: Boolean) {
        placeHolderIv.visibility = View.GONE
        if (show) {
            placeHolderPb.visibility = View.VISIBLE
            resultsRv.visibility = View.GONE
        } else {
            placeHolderPb.visibility = View.GONE
            resultsRv.visibility = View.VISIBLE
        }
    }

    private fun initializeList() {
        resultsRv.layoutManager = LinearLayoutManager(context)
        resultsRv.adapter = restaurantsAdapter
    }

}
