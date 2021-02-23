package com.husnul23.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListCityAdapter(val listCity: ArrayList<Weather>) : RecyclerView.Adapter<ListCityAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(
        onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_city_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val city = listCity[position]
        holder.bind(city, onItemClickCallback)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Weather)
    }

    inner class ListViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        var cityName: TextView = itemView.findViewById(R.id.cityName)
        var detailBtn: Button = itemView.findViewById(R.id.buttonDetail)


        fun bind(city: Weather, clickListener: OnItemClickCallback) {
            detailBtn.setOnClickListener {
                clickListener.onItemClick(city)
            }

            cityName.text = city.city
        }

    }
}