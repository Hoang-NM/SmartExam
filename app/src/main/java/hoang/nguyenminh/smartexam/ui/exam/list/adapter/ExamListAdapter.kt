package hoang.nguyenminh.smartexam.ui.exam.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.databinding.ItemExamListBinding
import hoang.nguyenminh.smartexam.model.exam.Exam

class ExamListAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Exam, ExamListAdapter.ViewHolder>(ExamListDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class ViewHolder private constructor(val binding: ItemExamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Exam, onItemClick: (Int) -> Unit) {
            binding.apply {
                lblName.text = item.name
                lblDate.text = item.createdAt
                root.setOnSafeClickListener {
                    onItemClick(item.id)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemExamListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ExamListDiffCallback : DiffUtil.ItemCallback<Exam>() {

    override fun areItemsTheSame(oldItem: Exam, newItem: Exam): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Exam, newItem: Exam): Boolean =
        oldItem == newItem
}