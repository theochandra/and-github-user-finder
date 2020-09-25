package com.android.githubuserfinder.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.domain.model.User
import com.android.githubuserfinder.databinding.ItemLoadingBinding
import com.android.githubuserfinder.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserListAdapter(
    userList: ObservableList<User>
) : ObservableRecyclerViewAdapter<User, ViewHolder>(userList) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                ItemViewHolder(
                    ItemUserBinding.inflate(LayoutInflater
                        .from(parent.context), parent, false
                    )
                )
            }
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(
                    ItemLoadingBinding.inflate(LayoutInflater
                        .from(parent.context), parent, false
                    )
                )
            }
            else -> {
                ItemViewHolder(
                    ItemUserBinding.inflate(LayoutInflater
                        .from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(getItem(position))
            }
            is LoadingViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    class ItemViewHolder(
        private val binding: ItemUserBinding
    ) : ViewHolder(binding.root) {

        private lateinit var user: User

        fun bind(user: User) {
            this.user = user

            Picasso.get().load(user.avatarUrl)
                .resize(50, 50)
                .into(binding.ivAvatar)
            binding.tvLoginName.text = user.login
        }

    }

    class LoadingViewHolder(
        private val binding: ItemLoadingBinding
    ) : ViewHolder(binding.root) {

        fun bind() {
            binding.progressBar.visibility = View.VISIBLE
        }

    }

}