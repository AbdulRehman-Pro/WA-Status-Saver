package com.rehman.wasaver.Ui.Dashboard.ImagesFragment.Adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehman.wasaver.Models.WAStatusModels
import com.rehman.wasaver.R
import com.rehman.wasaver.Ui.Dashboard.ImagesFragment.ImageViewer.ViewImageActivity
import com.rehman.wasaver.Ui.Dashboard.VideosFragment.VideosViewer.ViewVideoActivity

class WAStatusAdapter(
    private val context: Context, private val list: ArrayList<WAStatusModels>,
    private val clickListner: (WAStatusModels) -> Unit
) : RecyclerView.Adapter<WAStatusAdapter.ViewHolder>() {

    private lateinit var currentItem: WAStatusModels

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val wpImage: ImageView = itemView.findViewById(R.id.wp_images)
        val wpDownload: TextView = itemView.findViewById(R.id.wp_download)
        val wpPlay: ImageView = itemView.findViewById(R.id.wp_play)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        currentItem = list[position]

        setupItemView(holder)

        itemCliclListner(holder)


    }

    private fun itemCliclListner(holder: WAStatusAdapter.ViewHolder) {
        holder.itemView.setOnClickListener {


            if (currentItem.fileUri.endsWith(".mp4")) {

                /*val intent = Intent(context,ViewVideoActivity::class.java)
                intent.putExtra("fileUri",currentItem.fileUri)
                 context.startActivity(intent)*/

                val intent = Intent(context, ViewVideoActivity::class.java)
                intent.putExtra("fileUri", currentItem.fileUri)
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    holder.itemView,
                    "ITEMS"
                )
                context.startActivity(intent, activityOptions.toBundle())


            } else {

                val intent = Intent(context, ViewImageActivity::class.java)
                intent.putExtra("fileUri", currentItem.fileUri)
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    holder.itemView,
                    "ITEMS"
                )
                context.startActivity(intent, activityOptions.toBundle())
            }
        }
    }

    private fun setupItemView(holder: WAStatusAdapter.ViewHolder) {
        if (currentItem.fileUri.endsWith(".mp4")) {
            holder.wpPlay.visibility = View.VISIBLE
        } else {
            holder.wpPlay.visibility = View.GONE
        }

        Glide.with(context.applicationContext).load(currentItem.fileUri).into(holder.wpImage)
        holder.wpDownload.setOnClickListener {
            clickListner(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}