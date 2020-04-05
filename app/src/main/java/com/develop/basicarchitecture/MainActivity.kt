package com.develop.basicarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.develop.basicarchitecture.network.Repository
import com.develop.basicarchitecture.network.dataclasses.SearchResponse
import com.develop.basicarchitecture.network.utility.UtilFunctions
import com.develop.basicarchitecture.ui.main.MainFragment
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

//        GlobalScope.launch {
//            testSearchApi()
//        }
    }

//    private suspend fun testSearchApi() {
//        val response = Repository().runSaerchQuery("t")
//        Log.i("testApi", "response?.code() : "+response?.code())
//
//
//        if (response?.isSuccessful == true) {
//            val searchQueryResponse = Gson().fromJson(response.body(), SearchResponse::class.java)
//            Log.i("testApi", searchQueryResponse.toString())
//
//        } else {
//            UtilFunctions.toast(response?.errorBody()?.string() ?: "Error")
//        }
//    }

}
