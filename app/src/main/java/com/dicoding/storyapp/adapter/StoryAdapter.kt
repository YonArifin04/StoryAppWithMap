    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.paging.PagingDataAdapter
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.dicoding.storyapp.R
    import com.dicoding.storyapp.data.StoryResponseItem
    import com.dicoding.storyapp.databinding.ItemStoryBinding

    class StoryAdapter : PagingDataAdapter<StoryResponseItem, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
            val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StoryViewHolder(binding)
        }

        override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
            val story = getItem(position)
            if (story != null) {
                holder.bind(story)
            }
        }

        class StoryViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(story: StoryResponseItem) {
                binding.apply {
                    yourName.text = story.listStory[0].name
                    Glide.with(itemView.context)
                        .load(story.listStory[0].photoUrl)
                        .into(imageLogo)

                }
            }
        }

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponseItem>() {
                override fun areItemsTheSame(oldItem: StoryResponseItem, newItem: StoryResponseItem): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: StoryResponseItem, newItem: StoryResponseItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }