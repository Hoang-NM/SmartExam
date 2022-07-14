package hoang.nguyenminh.smartexam.ui.exam.detail.adapter

import android.annotation.SuppressLint
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: ItemQuestionAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = AnswerChoiceAdapter()

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: AnswerModel) {
            binding.apply {
                label.text = root.resources.getString(R.string.format_question_index, item.id)
                recChoices.adapter = childAdapter
                childAdapter.submitList(item.choices)
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