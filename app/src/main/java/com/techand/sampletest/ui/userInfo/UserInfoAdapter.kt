package com.techand.sampletest.ui.userInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techand.sampletest.data.models.User
import com.techand.sampletest.databinding.RawUserInfoBinding

class UserInfoAdapter(private val users: ArrayList<User>,private val listener: Listener) :
    RecyclerView.Adapter<UserInfoAdapter.DataViewHolder>() {

    class DataViewHolder(val binding: RawUserInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User,listener: Listener,position: Int) {
            binding.user = user
            binding.root.setOnClickListener { listener.onItemClick(user, position) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RawUserInfoBinding.inflate(inflater)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position],listener,position)
    }

    fun addUsers(users: List<User>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }
    interface Listener {
        fun onItemClick(user: User, position: Int)
    }
}