package hoang.nguyenminh.smartexam.ui.exam.execution.navigation_dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatSelected
import hoang.nguyenminh.smartexam.databinding.ItemQuestionIndexBinding
import hoang.nguyenminh.smartexam.model.exam.QuestionIndex

class ExamQuestionIndexAdapter(val listener: (QuestionIndex) -> Unit) :
    ListAdapter<QuestionIndex, ExamQuestionIndexAdapter.ViewHolder>(QuestionIndexDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: ItemQuestionIndexBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuestionIndex, clickListener: (QuestionIndex) -> Unit) {
            binding.apply {
                lblContent.text = item.index.toString()
                lblContent.viewCompatSelected(item.isSelected)
                lblContent.setOnClickListener {
                    clickListener(item)
                }
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

class QuestionIndexDiffCallback : DiffUtil.ItemCallback<QuestionIndex>() {

    override fun areItemsTheSame(oldItem: QuestionIndex, newItem: QuestionIndex): Boolean =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: QuestionIndex, newItem: QuestionIndex): Boolean =
        oldItem == newItem
}