package com.example.toppostsreddit.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toppostsreddit.data.database.Post
import com.example.toppostsreddit.databinding.ItemPostBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class TopPostsAdapter(
    private val onClickListener: (String) -> Unit,
    private val onTextViewClickListener: (String) -> Unit
) : RecyclerView.Adapter<TopPostsAdapter.TopPostsViewHolder>() {
    inner class TopPostsViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            val time = getHours(post.created_utc.toDouble().toLong(), "HH")
            with(binding) {
                tvAuthor.text = post.author
                tvComments.text = "Comments: ${post.num_comments}"
                tvText.text = post.title
                tvHoursAgo.text = "$time hours ago"
                if (post.thumbnail == "default") {
                    iv.isVisible = false
                    tvSave.isVisible = false
                } else {
                    iv.isVisible = true
                    tvSave.isVisible = true
                    Glide.with(root).load(post.thumbnail).into(iv)
                }
                iv.setOnClickListener {
                    onClickListener.invoke(post.url)
                }
                tvSave.setOnClickListener {
                    onTextViewClickListener.invoke(post.url)
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
        val post = dataList[position]


            holder.bind(post)

    }

    override fun getItemCount(): Int {
return dataList.size
    }

    fun getHours(secondsToAdd: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val netDate = Date(secondsToAdd * 1000)
        val time = sdf.format(netDate)
        val minuteTime = SimpleDateFormat("mm").format(netDate)

        val c = Calendar.getInstance()

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val res1 = ((time.toInt() * 60 + minuteTime.toInt()) / 60.0).roundToInt()
        val res2 = ((hour * 60 + minute) / 60.0).roundToInt()

        return if (time.toInt() >= hour) (24 - res1 + res2).toString() else (res2 - res1).toString()
    }

        val differCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    val differ = AsyncListDiffer(this, differCallback)

    var dataList: List<Post>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }
}