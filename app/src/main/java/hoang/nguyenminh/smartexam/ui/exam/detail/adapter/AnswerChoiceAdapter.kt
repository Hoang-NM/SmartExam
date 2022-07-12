package hoang.nguyenminh.smartexam.ui.exam.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatSelected
import hoang.nguyenminh.smartexam.databinding.ItemQuestionIndexBinding
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.ui.exam.execution.question.adapter.QuestionChoiceDiffCallback

class AnswerChoiceAdapter :
    ListAdapter<Choice, AnswerChoiceAdapter.ViewHolder>(QuestionChoiceDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: ItemQuestionIndexBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Choice) {
            binding.apply {
                lblContent.text = item.index.identity
                lblContent.viewCompatSelected(item.isSelected)
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