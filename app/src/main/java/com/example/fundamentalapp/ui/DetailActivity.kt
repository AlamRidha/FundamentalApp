package com.example.fundamentalapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.fundamentalapp.R
import com.example.fundamentalapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  get data then aplly data to detail activity
        val userName = intent.getStringExtra(DETAIL_USER)
        if (userName != null) {
            detailModel.detailUser(userName)
        }

        //  apply data to layout
        detailModel.detailUser.observe(this) { userData ->
            binding.tvNameuser.text = userData.login
            binding.tvFollowers.text = userData.followers.toString()
            binding.tvFollowing.text = userData.following.toString()
            Glide.with(this@DetailActivity).load(userData.avatarUrl).into(binding.ivDetailimg)
            Log.d(TAG, "Data name is ${userData.login}")
        }

        //  call loading from detailviewmodel
        detailModel.isLoading.observe(this) {
            showLoading(it)
        }

        //  setting for tablayout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        if (userName != null) {
            sectionsPagerAdapter.userName = userName
        }
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    //make function to set loading
    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val TAG = "DetailActivity"
        const val DETAIL_USER = "detail user"

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}