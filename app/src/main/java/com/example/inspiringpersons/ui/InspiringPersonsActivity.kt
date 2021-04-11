package com.example.inspiringpersons.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inspiringpersons.R
import com.example.inspiringpersons.adapters.InspiringPersonsAdapter
import com.example.inspiringpersons.model.PersonsWithQuotes
import com.example.inspiringpersons.databinding.ActivityInspiringPersonsBinding
import com.example.inspiringpersons.databinding.ContentInspiringPersonsBinding
import com.example.inspiringpersons.viewmodels.PersonsViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "InspiringPersonsActiv"

@AndroidEntryPoint
class InspiringPersonsActivity : AppCompatActivity(), InspiringPersonsAdapter.OnPersonClickListener {

    private lateinit var inspiringPersonsBinding: ActivityInspiringPersonsBinding
    private lateinit var contentInspiringPersonsBinding: ContentInspiringPersonsBinding


    private val personsViewModel: PersonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inspiringPersonsBinding = ActivityInspiringPersonsBinding.inflate(layoutInflater)
        val view = inspiringPersonsBinding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contentInspiringPersonsBinding = ContentInspiringPersonsBinding.bind(view)

        val personsAdapter = InspiringPersonsAdapter(emptyList(), this)

        contentInspiringPersonsBinding.rvInspiringPersons.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        contentInspiringPersonsBinding.rvInspiringPersons.adapter = personsAdapter

        personsViewModel.personsWithQuotes.observe(this,
            {
                Log.d(TAG, "onCreate: observing personsWithQuotes with values $it")
                personsAdapter.loadNewPersons(it)
                Log.d(TAG, "onCreate: observing personsWithQuotes with adapter value ${personsAdapter.itemCount}")
            })
    }

    override fun onItemClick(personWithQuotes: PersonsWithQuotes) {
        val randomQuote = personWithQuotes.personQuotes.shuffled().random().quoteText
        val showText = if(randomQuote.isEmpty()) getString(R.string.toast_empty_quote) else randomQuote

        Toast.makeText(this, showText, Toast.LENGTH_SHORT).show()

    }
}