package com.techand.sampletest.ui.albumDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.techand.sampletest.data.models.Album
import com.techand.sampletest.databinding.AlbumDetailFragmentBinding

class AlbumDetail : Fragment() {

    private lateinit var viewDataBinding: AlbumDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideTitle()
        setData()
    }

    private fun hideTitle() {
        val toolbar = (requireActivity() as AppCompatActivity).supportActionBar
        toolbar?.hide()
    }


    private fun setData() {
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        if (arguments != null) {
            val album = arguments?.getSerializable("detail") as Album
            viewDataBinding.album = album
        }
    }

}