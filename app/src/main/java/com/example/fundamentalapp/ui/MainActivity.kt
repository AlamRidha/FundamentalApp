package com.example.fundamentalapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalapp.R
import com.example.fundamentalapp.databinding.ActivityMainBinding
import com.example.fundamentalapp.response.ItemsItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //  call data from viewModel
        mainModel.dataUser.observe(this) { user ->
            setDataUser(user)
        }

        // layoutManager for recycleview
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        binding.rvGithub.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)

        //  call loading from viewModel
        mainModel.isLoading.observe(this) {
            showLoading(it)
        }

        //  searchbar
        with(binding) {
            searchview.setupWithSearchBar(searchbar)
            searchview.editText.setOnEditorActionListener { textView, actionId, event ->
                //  if error in this line downgrade the dependencies
                searchbar.text = searchview.text
                searchview.hide()
                Log.d("MainActivity", "Data search is ${searchview.text.toString()}")
                mainModel.dataUser(searchview.text.toString())
                false
            }
        }

    }


    //  make function to set data
    private fun setDataUser(dataUser: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(dataUser)
        binding.rvGithub.adapter = adapter
    }

    //make function to set loading
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}