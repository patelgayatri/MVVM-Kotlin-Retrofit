package com.techand.sampletest.ui.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.techand.sampletest.R
import com.techand.sampletest.data.models.User
import com.techand.sampletest.databinding.RecyclerviewLayoutBinding
import com.techand.sampletest.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfo : Fragment(), UserInfoAdapter.Listener {

    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var viewDataBinding: RecyclerviewLayoutBinding
    private lateinit var adapter: UserInfoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = RecyclerviewLayoutBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setTitle()
        setUpListAdapter()
        setData()

    }

    private fun setTitle() {
        val toolbar = (requireActivity() as AppCompatActivity).supportActionBar
        toolbar?.title = getString(R.string.user_info)
        toolbar?.show()
    }

    private fun setUpListAdapter() {
        val recyclerView = viewDataBinding.recyclerlist
        recyclerView.layoutManager = LinearLayoutManager(this.requireActivity())
        adapter = UserInfoAdapter(arrayListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun setData() {
        userViewModel.loader.observe(this as LifecycleOwner, { loading ->
            when (loading) {
                true -> viewDataBinding.progressBar.visibility = View.VISIBLE
                else -> viewDataBinding.progressBar.visibility = View.GONE
            }
        })
        userViewModel.getUser().observe(viewLifecycleOwner, {
            if (it.getOrNull() != null) {
                retrieveList(it.getOrNull())
            } else {
                Snackbar.make(
                    viewDataBinding.progressBar,
                    it.exceptionOrNull()?.message ?: "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        })
    }

    private fun retrieveList(users: List<User>?) {
        adapter.apply {
            users?.let { addUsers(it) }
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(user: User, position: Int) {
        val action = UserInfoDirections.actionUserInfoFragmentToAlbumFragment(user.id.toString())
        findNavController().navigate(action)
    }

}