package com.techand.sampletest.ui.albumList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techand.sampletest.data.models.Album
import com.techand.sampletest.databinding.RawAlbumBinding

class AlbumAdapter(private val albums: ArrayList<Album>, private val listener: Listener) :
    RecyclerView.Adapter<AlbumAdapter.DataViewHolder>() {


    interface Listener {
        fun onItemClick(album: Album, position: Int)
    }

    class DataViewHolder(val binding: RawAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, listener: Listener, position: Int) {
            binding.album = album
            binding.root.setOnClickListener { listener.onItemClick(album, position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RawAlbumBinding.inflate(inflater)
        return DataViewHolder(binding)
    }


    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(albums[position], listener, position)
    }

    fun addPhotos(albums: List<Album>) {
        this.albums.apply {
            clear()
            addAll(albums)
        }

    }


}