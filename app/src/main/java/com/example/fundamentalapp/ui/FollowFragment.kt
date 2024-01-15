package com.example.fundamentalapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalapp.R
import com.example.fundamentalapp.databinding.FragmentFollowBinding
import com.example.fundamentalapp.response.ItemsItem

class FollowFragment : Fragment() {

    private val detailViewModel by viewModels<DetailViewModel>()

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private var position = 0
    private var username: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvFollow.layoutManager = layoutManager
        binding.rvFollow.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            username?.let {
                detailViewModel.getFollowers(it)
            }
            detailViewModel.listFollowUser.observe(viewLifecycleOwner) { listFollowers ->
                if (!listFollowers.isNullOrEmpty()) {
                    Log.d(TAG, "Data followers $listFollowers")
                    setUserFollow(listFollowers)
                } else {
                    Log.d(TAG, "Data empty")
                }
            }
        } else {
            username?.let {
                detailViewModel.getFollowing(it)
            }
            detailViewModel.listFollowUser.observe(viewLifecycleOwner) { listFollowing ->
                if (!listFollowing.isNullOrEmpty()) {
                    Log.d(TAG, "Data following $listFollowing")
                    setUserFollow(listFollowing)
                } else {
                    Log.d(TAG, "Data empty")
                }
            }
        }

    }

    private fun setUserFollow(userName: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(userName)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "user_name"
        const val TAG = "fragment"
    }

}