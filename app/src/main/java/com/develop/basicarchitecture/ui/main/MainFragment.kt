package com.develop.basicarchitecture.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.basicarchitecture.R
import com.develop.basicarchitecture.ui.main.adapter.RestaurantsAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val restaurantsAdapter = RestaurantsAdapter()

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
        searchEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                Log.i("testApi","onTextChanged : " +s.toString())
                viewModel.search(s.toString())
                observeLiveData()

//                tvSample.setText("Text in EditText : "+s)
            }
        })
    }


//    private fun updateAdapter(
//        config: PagedList.Config,
//        pagedListAdapter: PagedListAdapter<SearchResponse.Restaurant, RecyclerView.ViewHolder>
//    ) {
//        val turnoverDataSourceFactory = ItemSourceFactory(Dispatchers.IO)
//
//        val items = PagedList.Builder(turnoverDataSourceFactory.create(), config)
//            .setNotifyExecutor(Executor { view?.post(it) })
//            .setFetchExecutor(Executors.newSingleThreadExecutor())
//            .build()
//
//        pagedListAdapter.submitList(items)
//    }

    private fun observeLiveData() {
        //observe live data emitted by view model
        viewModel.getRestaurantLiveData()?.observe(viewLifecycleOwner, Observer {
            restaurantsAdapter.submitList(it)
        })
    }

    private fun initializeList() {
        resultsRv.layoutManager = LinearLayoutManager(context)
        resultsRv.adapter = restaurantsAdapter
    }

}
