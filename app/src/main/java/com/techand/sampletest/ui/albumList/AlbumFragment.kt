package com.techand.sampletest.ui.albumList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.techand.sampletest.R
import com.techand.sampletest.data.models.Album
import com.techand.sampletest.databinding.RecyclerviewLayoutBinding
import com.techand.sampletest.ui.UserViewModel
import com.techand.sampletest.ui.userInfo.UserInfoArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumFragment : Fragment(), AlbumAdapter.Listener {

    private val viewModel by viewModels<UserViewModel>()
    private lateinit var viewDataBinding: RecyclerviewLayoutBinding
    private lateinit var adapter: AlbumAdapter
    var albumId: Int = 0
    val args: UserInfoArgs by navArgs()

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
        albumId = args.abc.toInt()
        val toolbar = (requireActivity() as AppCompatActivity).supportActionBar
        toolbar?.title = "Album ID: $albumId"
        toolbar?.show()
    }

    private fun setUpListAdapter() {
        val recyclerView = viewDataBinding.recyclerlist
        recyclerView.layoutManager = LinearLayoutManager(this.requireActivity())
        adapter = AlbumAdapter(arrayListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun setData() {
        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when (loading) {
                true -> viewDataBinding.progressBar.visibility = View.VISIBLE
                else -> viewDataBinding.progressBar.visibility = View.GONE
            }
        })
        viewModel.getPhotos(albumId).observe(viewLifecycleOwner, {
            if (it.getOrNull() != null)
                retrieveList(it.getOrNull()!!)
            else
                Snackbar.make(
                    viewDataBinding.progressBar,
                    it.exceptionOrNull()?.message ?: "Error",
                    Snackbar.LENGTH_LONG
                ).show()
        })
    }

    private fun retrieveList(albums: List<Album>) {
        adapter.apply {
            addPhotos(albums)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(album: Album, position: Int) {
        val bundle = bundleOf(Pair("detail", album))
        findNavController().navigate(R.id.action_Album_to_Deatil, bundle)
    }
}