package hoang.nguyenminh.smartexam.ui.exam.submit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.databinding.ItemQuestionAnswerBinding
import hoang.nguyenminh.smartexam.model.exam.AnswerModel

class QuestionAnswerAdapter :
    ListAdapter<AnswerModel, QuestionAnswerAdapter.ViewHolder>(QuestionAnswerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ItemQuestionAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = ChoiceIndexAdapter()

        fun bind(item: AnswerModel) {
            binding.apply {
                label.text = itemView.context.getString(R.string.format_question_index, item.id)
                recChoices.adapter = adapter
                adapter.submitList(item.choices)
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQuestionAnswerBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class QuestionAnswerDiffCallback : DiffUtil.ItemCallback<AnswerModel>() {

    override fun areItemsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean =
        oldItem == newItem
}
