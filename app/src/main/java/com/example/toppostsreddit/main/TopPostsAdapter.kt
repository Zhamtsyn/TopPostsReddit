package com.example.toppostsreddit.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toppostsreddit.data.models.Children
import com.example.toppostsreddit.databinding.ItemPostBinding

class TopPostsAdapter : RecyclerView.Adapter<TopPostsAdapter.TopPostsViewHolder>() {
    class TopPostsViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Children) {
            with(binding) {
                tvAuthor.text = post.data.author
                tvComments.text = "Comments:${post.data.num_comments}"
                tvText.text = post.data.title
                if (post.data.thumbnail == "default") {
                    iv.isVisible = false
                } else {
                    iv.isVisible=true
                    Glide.with(root).load(post.data.thumbnail).into(iv)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPostsViewHolder {
        val itemBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopPostsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TopPostsViewHolder, position: Int) {
        val post = dataList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Children>() {
        override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var dataList: List<Children>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }
}