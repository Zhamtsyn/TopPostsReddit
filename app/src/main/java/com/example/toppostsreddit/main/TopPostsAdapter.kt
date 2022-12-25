package com.example.toppostsreddit.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toppostsreddit.data.models.Children
import com.example.toppostsreddit.databinding.ItemPostBinding

class TopPostsAdapter(
    private val onClickListener: (String) -> Unit,
    private val onTextViewClickListener: (String) -> Unit
) : PagingDataAdapter<Children, TopPostsAdapter.TopPostsViewHolder>(differCallback) {
    inner class TopPostsViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Children) {
            with(binding) {
                tvAuthor.text = post.data.author
                tvComments.text = "Comments:${post.data.num_comments}"
                tvText.text = post.data.title
                if (post.data.thumbnail == "default") {
                    iv.isVisible = false
                    tvSave.isVisible = false
                } else {
                    iv.isVisible = true
                    tvSave.isVisible = true
                    Glide.with(root).load(post.data.thumbnail).into(iv)
                }
                iv.setOnClickListener {
                    onClickListener.invoke(post.data.url)
                }
                tvSave.setOnClickListener {
                    onTextViewClickListener.invoke(post.data.url)
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
        holder.setIsRecyclable(false)
        val post = getItem(position)
        if (post != null) {
            holder.bind(post)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Children>() {
            override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
                return oldItem == newItem
            }
        }
    }
}