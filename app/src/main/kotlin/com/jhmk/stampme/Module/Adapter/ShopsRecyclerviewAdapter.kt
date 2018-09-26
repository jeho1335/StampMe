package com.jhmk.stampme.Module.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jhmk.stampme.Model.Shops
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_item_shops.view.*

class ShopsRecyclerviewAdapter(context: Context, items: MutableList<Shops?>, listener: ShopsRecyclerviewAdapter.IClickListener) : RecyclerView.Adapter<ShopsRecyclerviewAdapter.ViewHolder>() {
    val TAG = this.javaClass.simpleName

    private var mItems: MutableList<Shops?> = items
    private var mListener: IClickListener = listener
    private val mContext = context

    interface IClickListener {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(viewGropu: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGropu.context).inflate(R.layout.layout_item_shops, viewGropu, false)
        return ViewHolder(v)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "##### onBindViewHolder position : $position")
        val item: Shops? = mItems.get(position)
        if(mItems.get(position)!!.isFront) {
            holder.itemView.layout_item_shops_front.visibility = View.VISIBLE
            holder.itemView.layout_item_shops_back.visibility = View.GONE
            Glide
                    .with(mContext)
                    .load(item?.shopImageUrl)
                    .into(holder.itemView.img_item_shops)
            holder.itemView.txt_name_item_shops.text = item?.shopName
            holder.itemView.txt_subname_item_shops.text = item?.shopAddress
            holder.itemView.txt_distance_item_shops.text = item?.shopDistance
        }else{
            holder.itemView.layout_item_shops_front.visibility = View.GONE
            holder.itemView.layout_item_shops_back.visibility = View.VISIBLE
            holder.itemView.txt_subtitle_back_item_shops.text = item?.shopTargetBehavior
        }
    }

    override fun getItemCount(): Int {
        return this.mItems.size
    }

    fun refreshItem(pos : Int){
        Log.d(TAG, "##### refreshITem ##### pos : $pos")
        mItems.get(pos)!!.isFront = !mItems.get(pos)!!.isFront
        notifyItemChanged(pos)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { v -> mListener.onClick(adapterPosition)}
        }
    }
}