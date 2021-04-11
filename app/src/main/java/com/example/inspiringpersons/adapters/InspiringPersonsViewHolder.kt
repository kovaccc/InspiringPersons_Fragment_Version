package com.example.inspiringpersons.adapters

import android.text.format.DateFormat
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inspiringpersons.R
import com.example.inspiringpersons.model.PersonsWithQuotes
import com.example.inspiringpersons.databinding.ItemInspiringPersonBinding

private const val TAG = "InspiringPersonsView"

class InspiringPersonsViewHolder(private val binding: ItemInspiringPersonBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(personWithQuotes: PersonsWithQuotes, listener: InspiringPersonsAdapter.OnPersonClickListener ) {

        Log.d(TAG, "bind: starts with $personWithQuotes and listener $listener")

        with(binding) {
            Glide.with(itemView.context)
                .load(personWithQuotes.inspiringPerson.imageLink) // image url
                .placeholder(R.drawable.placeholder) // any placeholder to load at start
                .error(R.drawable.placeholder)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .into(ivPerson)

            val dateFormat = DateFormat.getDateFormat(itemView.context)
            val personBirthDate = dateFormat.format(personWithQuotes.inspiringPerson.birthDate)
            val personDeathDate = dateFormat.format(personWithQuotes.inspiringPerson.deathDate)

            tvPersonDates.text = itemView.context.getString(R.string.textview_person_dates, personBirthDate, personDeathDate)
            tvPersonDescription.text = personWithQuotes.inspiringPerson.description
        }

        itemView.setOnClickListener {
            listener.onItemClick(personWithQuotes)
        }

        Log.d(TAG, "bind: ends with $personWithQuotes and listener $listener")
    }

}