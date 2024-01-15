package com.example.fundamentalapp.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fundamentalapp.databinding.ItemRowUserBinding
import com.example.fundamentalapp.response.ItemsItem
import com.example.fundamentalapp.ui.DetailActivity.Companion.DETAIL_USER

class ListUserAdapter : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserAdapter.MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    class MyViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userName: ItemsItem) {
            Log.d(TAG, "Data Found $userName")
            binding.tvItemName.text = userName.login
            Glide.with(itemView.context).load(userName.avatarUrl).into(binding.imgItemPhoto)

            //  make click in item recycle view
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DETAIL_USER, userName.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }

        private const val TAG = "UserAdapter"
    }
}
