package hoang.nguyenminh.smartexam.ui.exam.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.databinding.ItemExamHistoryBinding
import hoang.nguyenminh.smartexam.model.exam.ExamHistory

class ExamHistoryAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<ExamHistory, ExamHistoryAdapter.ViewHolder>(ExamHistoryDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class ViewHolder private constructor(val binding: ItemExamHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExamHistory, onItemClick: (Int) -> Unit) {
            binding.apply {
                lblName.text = item.name
                lblDate.text = item.executedDate
                root.setOnSafeClickListener {
                    onItemClick(item.id)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemExamHistoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ExamHistoryDiffCallback : DiffUtil.ItemCallback<ExamHistory>() {

    override fun areItemsTheSame(oldItem: ExamHistory, newItem: ExamHistory): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExamHistory, newItem: ExamHistory): Boolean =
        oldItem == newItem
}