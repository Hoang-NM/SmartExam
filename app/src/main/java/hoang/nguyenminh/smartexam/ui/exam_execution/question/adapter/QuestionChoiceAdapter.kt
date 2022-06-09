package hoang.nguyenminh.smartexam.ui.exam_execution.question.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.smartexam.databinding.ItemQuestionChoiceBinding
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.util.BindingAdapters.viewCompatSelected

class QuestionChoiceAdapter(private val onSelectChoice: (Int, Choice) -> Unit) :
    ListAdapter<Choice, QuestionChoiceAdapter.ViewHolder>(QuestionChoiceDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onSelectChoice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: ItemQuestionChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Choice, clickListener: (Int, Choice) -> Unit) {
            binding.apply {
                lblContent.text = item.content
                lblContent.viewCompatSelected(item.isSelected)
                lblContent.setOnClickListener {
                    item.isSelected = !item.isSelected
                    clickListener(adapterPosition, item)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQuestionChoiceBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class QuestionChoiceDiffCallback : DiffUtil.ItemCallback<Choice>() {

    override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean =
        oldItem == newItem
}