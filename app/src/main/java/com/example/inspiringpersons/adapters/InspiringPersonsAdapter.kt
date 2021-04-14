package com.example.inspiringpersons.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.inspiringpersons.model.PersonWithQuotes
import com.example.inspiringpersons.databinding.ItemInspiringPersonBinding

private const val TAG = "InspiringPersonsAdap"

class InspiringPersonsAdapter (private var personWithQuotes: List<PersonWithQuotes>, private val listener: OnPersonClickListener)
    : RecyclerView.Adapter<InspiringPersonsViewHolder>(){
    interface OnPersonClickListener {
        fun onItemClick(personWithQuotes: PersonWithQuotes)
        fun onEditClick(personWithQuotes: PersonWithQuotes, viewHolder: RecyclerView.ViewHolder)
        fun onDeleteClick(personWithQuotes: PersonWithQuotes, viewHolder: RecyclerView.ViewHolder)
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
        viewBinderHelper.bind(binding.swipeLayout, personWithQuotes[position].toString()) // string that identifies viewHolder item
        viewBinderHelper.closeLayout(personWithQuotes[position].toString())

        holder.bind(personWithQuotes[position], listener)
    }

    override fun getItemCount(): Int {
        return personWithQuotes.size
    }

    fun loadNewPersons(newPersons: List<PersonWithQuotes>) {
        Log.d(TAG, "loadNewPersons: starts with $newPersons and old persons are $personWithQuotes")
        personWithQuotes = newPersons
        notifyDataSetChanged()
    }

}