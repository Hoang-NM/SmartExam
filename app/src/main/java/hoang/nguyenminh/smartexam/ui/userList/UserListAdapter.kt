package hoang.nguyenminh.smartexam.ui.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyenminh.smartexam.databinding.ItemUsersListBinding
import hoang.nguyenminh.smartexam.model.user.UserListItem

class UsersListAdapter(private val onRequestDetail: (UserListItem) -> Unit) :
    ListAdapter<UserListItem, UsersListAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onRequestDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    class ViewHolder private constructor(private val binding: ItemUsersListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserListItem, clickListener: (UserListItem) -> Unit) {
            binding.data = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUsersListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class UsersListDiffCallback : DiffUtil.ItemCallback<UserListItem>() {

    override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean =
        oldItem == newItem
}