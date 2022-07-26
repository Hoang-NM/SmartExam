package hoang.nguyenminh.smartexam.ui.exam.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatSelected
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatVisibility
import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.databinding.ItemExamListBinding
import hoang.nguyenminh.smartexam.model.exam.ExamModel

class ExamListAdapter(private val onItemClick: (ExamModel) -> Unit) :
    ListAdapter<ExamModel, ExamListAdapter.ViewHolder>(ExamModelDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class ViewHolder private constructor(val binding: ItemExamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExamModel, onItemClick: (ExamModel) -> Unit) {
            binding.apply {
                lblName.text = item.name
                lblDate.text = itemView.context.getString(
                    R.string.format_exam_time_limit, item.timeLimit
                )
                lblResult.apply {
                    viewCompatVisibility(item.result.isNotEmpty())
                    text = item.result
                }
                root.setOnSafeClickListener {
                    onItemClick(item)
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

class ExamModelDiffCallback : DiffUtil.ItemCallback<ExamModel>() {

    override fun areItemsTheSame(oldItem: ExamModel, newItem: ExamModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExamModel, newItem: ExamModel): Boolean =
        oldItem == newItem
}