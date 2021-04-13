package com.example.inspiringpersons.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.inspiringpersons.model.PersonsWithQuotes
import com.example.inspiringpersons.databinding.ItemInspiringPersonBinding

private const val TAG = "InspiringPersonsAdap"

class InspiringPersonsAdapter (private var personsWithQuotes: List<PersonsWithQuotes>, private val listener: OnPersonClickListener)
    : RecyclerView.Adapter<InspiringPersonsViewHolder>(){
    interface OnPersonClickListener {
        fun onItemClick(personWithQuotes: PersonsWithQuotes)
        fun onEditClick(personWithQuotes: PersonsWithQuotes, viewHolder: RecyclerView.ViewHolder)
        fun onDeleteClick(personWithQuotes: PersonsWithQuotes, viewHolder: RecyclerView.ViewHolder)
    }

    private val viewBinderHelper = ViewBinderHelper()

    private lateinit var binding: ItemInspiringPersonBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspiringPersonsViewHolder {
        binding = ItemInspiringPersonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return InspiringPersonsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InspiringPersonsViewHolder, position: Int) {

        //swipe functionality
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(binding.swipeLayout, personsWithQuotes[position].toString()) // string that identifies viewHolder item
        viewBinderHelper.closeLayout(personsWithQuotes[position].toString())

        holder.bind(personsWithQuotes[position], listener)
    }

    override fun getItemCount(): Int {
        return personsWithQuotes.size
    }

    fun loadNewPersons(newPersons: List<PersonsWithQuotes>) {
        Log.d(TAG, "loadNewPersons: starts with $newPersons and old persons are $personsWithQuotes")
        personsWithQuotes = newPersons
        notifyDataSetChanged()
    }

}