package hoang.nguyenminh.smartexam.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.databinding.ItemCardMenuBinding
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.ui.exam.list.adapter.ExamModelDiffCallback

class TodoExamAdapter(private val onItemClick: (ExamModel) -> Unit) :
    ListAdapter<ExamModel, TodoExamAdapter.ViewHolder>(ExamModelDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class ViewHolder private constructor(val binding: ItemCardMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExamModel, onItemClick: (ExamModel) -> Unit) {
            binding.apply {
                label.text = item.name
                root.setOnSafeClickListener {
                    onItemClick(item)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCardMenuBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}