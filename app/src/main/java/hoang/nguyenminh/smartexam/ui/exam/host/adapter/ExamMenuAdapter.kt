package hoang.nguyenminh.smartexam.ui.exam.host.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.databinding.ItemCardMenuBinding
import hoang.nguyenminh.smartexam.model.main.MenuItem

class ExamMenuAdapter(private val onItemSelected: (Int, MenuItem) -> Unit) :
    ListAdapter<MenuItem, ExamMenuAdapter.ViewHolder>(ExamMenuDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, onItemSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: ItemCardMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: MenuItem, position: Int, clickListener: (Int, MenuItem) -> Unit) {
            binding.apply {
                label.text = root.resources.getString(item.label)
                cardIcon.setImageResource(item.icon)
                cardView.setOnSafeClickListener {
                    clickListener(position, item)
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

class ExamMenuDiffCallback : DiffUtil.ItemCallback<MenuItem>() {

    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
        oldItem == newItem
}