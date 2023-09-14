package com.rajibul.musicplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajibul.musicplayer.databinding.RecyclerViewBinding
import com.rajibul.musicplayer.MusicContent


interface MusicClick{
    fun OnSongPlayClick(musicContent: MusicContent)
}

class RecyclerAdapter(var musicClick : MusicClick): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var musicContent: ArrayList<MusicContent> = arrayListOf()
    class ViewHolder(var view:RecyclerViewBinding):RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view=RecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() =musicContent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.musicContent = musicContent[position]
        holder.view.musicClick = musicClick
    }
    fun updateList( musicContent: ArrayList<MusicContent>){
        this.musicContent.clear()
        this.musicContent.addAll(musicContent)
        notifyDataSetChanged()
    }
}