package hoang.nguyenminh.smartexam.ui.exam.submit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.smartexam.databinding.ItemQuestionIndexBinding
import hoang.nguyenminh.smartexam.model.exam.Choice

class ChoiceIndexAdapter :
    ListAdapter<Choice, ChoiceIndexAdapter.ViewHolder>(ChoiceIndexDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ItemQuestionIndexBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Choice) {
            binding.apply {
                lblContent.text = item.index.identity
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQuestionIndexBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ChoiceIndexDiffCallback : DiffUtil.ItemCallback<Choice>() {

    override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean =
        oldItem == newItem
}