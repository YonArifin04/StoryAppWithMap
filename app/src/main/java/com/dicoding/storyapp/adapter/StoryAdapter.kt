    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.paging.PagingDataAdapter
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.dicoding.storyapp.R
    import com.dicoding.storyapp.data.ListStoryItems
    import com.dicoding.storyapp.data.StoryResponseItem
    import com.dicoding.storyapp.databinding.ItemStoryBinding
    import com.dicoding.storyapp.response.ListStoryItem

    class StoryAdapter : PagingDataAdapter<ListStoryItems, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {
        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

        interface OnItemClickCallback {
            fun onItemClicked(detailStory: ListStoryItem?)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
            val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StoryViewHolder(binding, onItemClickCallback)
        }

        override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
            val story = getItem(position)
            if (story != null) {
                holder.bind(story)
            }
        }

        class StoryViewHolder(private val binding: ItemStoryBinding,private val onItemClickCallback: OnItemClickCallback?) : RecyclerView.ViewHolder(binding.root) {
            fun bind(story: ListStoryItems) {
                binding.apply {
                    yourName.text = story.name
                    Glide.with(itemView.context)
                        .load(story.photoUrl)
                        .into(imageLogo)

                    itemView.setOnClickListener {
                        val storyItem = ListStoryItem(
                            photoUrl = story.photoUrl,
                            name = story.name,
                            createdAt = story.createdAt,
                            description = story.description,
                            id = story.id,
                        )
                        onItemClickCallback?.onItemClicked(storyItem)
                    }
                }
            }
        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItems>() {
                override fun areItemsTheSame(oldItem: ListStoryItems, newItem: ListStoryItems): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: ListStoryItems, newItem: ListStoryItems): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }