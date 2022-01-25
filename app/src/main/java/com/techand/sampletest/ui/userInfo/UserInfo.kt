package com.techand.sampletest.ui.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.techand.sampletest.R
import com.techand.sampletest.data.models.User
import com.techand.sampletest.databinding.RecyclerviewLayoutBinding
import com.techand.sampletest.ui.UserViewModel
import com.techand.sampletest.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

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
        userViewModel.getUser().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        resource.data?.let { users ->
                            retrieveList(users)
                            viewDataBinding.progressBar.visibility = View.GONE
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        viewDataBinding.progressBar.visibility = View.GONE
                    }
                    Resource.Status.LOADING -> {
                        viewDataBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: Response<List<User>>) {
        adapter.apply {
            addUsers(users.body()!!)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(user: User, position: Int) {
        val action = UserInfoDirections.actionUserInfoFragmentToAlbumFragment(user.id.toString())
        findNavController().navigate(action)
    }

}