package com.example.inspiringpersons.ui

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.inspiringpersons.R
import com.example.inspiringpersons.databinding.FragmentEditPersonBinding
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.viewmodels.SavePersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


private const val TAG = "SavePersonFragment"

@AndroidEntryPoint
class SavePersonFragment : Fragment() {

    interface OnDateClicked {
        fun onBirthDatePicked(date: Long)
        fun onDeathDatePicked(date: Long)
    }

    private lateinit var binding: FragmentEditPersonBinding

    private var quotes = ArrayList<String>()

    private var adapter: ArrayAdapter<String> ?= null

    private var birthDate: Long = 0L
    private var deathDate: Long = 0L

    private val savePersonViewModel: SavePersonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: stars")
        super.onCreate(savedInstanceState)


        savePersonViewModel.currentQuotesLD.observe(this,
            {
                Log.d(TAG, "onCreate: observing quotes with values $it")
                quotes = it
                adapter?.let { adapter ->
                    adapter.clear()
                    adapter.addAll(quotes)
                    adapter.notifyDataSetChanged()
                }

                Log.d(TAG, "onCreate: observing quotes with adapter value ${adapter?.count}")

            })

        savePersonViewModel.birthDateLD.observe(this,
            {

                val dateFormat = DateFormat.getDateFormat(context) // formats date depending on different parts of a world
                val userDate = dateFormat.format(it)
                birthDate = it
                binding.etBirthDate.setText(userDate)
            })

        savePersonViewModel.deathDateLD.observe(this,
            {
                val dateFormat = DateFormat.getDateFormat(context)
                val userDate = dateFormat.format(it)
                deathDate = it
                binding.etDeathDate.setText(userDate)
            })
        Log.d(TAG, "onCreate: ends")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(TAG, "onCreateView: stars")

        binding = FragmentEditPersonBinding.inflate(inflater, container, false)


        binding.lvPersonQuotes.setOnItemClickListener {parent, view, position, id ->
            savePersonViewModel.deleteQuote(position)
        }


        binding.btnAddQuote.setOnClickListener {
            val quote = binding.etQuote.text.toString()
            Log.d(TAG, "onCreate: adding quote with value $quote")
            if(quote.isNotEmpty()) {
                savePersonViewModel.addQuote(quote)
            }
        }

        binding.apply {
            btnSave.setOnClickListener {
                if(addeditDescription.text.isEmpty() || etImageLink.text.isEmpty()) {
                    Toast.makeText(context, getString(R.string.toast_fill_all), Toast.LENGTH_SHORT).show()
                }
                else {
                    savePersonViewModel.saveInspiringPerson(
                            InspiringPerson(imageLink = etImageLink.text.toString(),
                                    description = addeditDescription.text.toString(), birthDate = birthDate,
                                    deathDate = deathDate)
                    )
                }
            }
        }

        adapter = ArrayAdapter(requireContext(), R.layout.item_quote, R.id.quote, quotes)

        val dateListener = initializeDateListener()
        binding.etBirthDate.setOnClickListener(dateListener)
        binding.etDeathDate.setOnClickListener(dateListener)

        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ends")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: stars")
        super.onViewCreated(view, savedInstanceState)

        binding.lvPersonQuotes.adapter = adapter

        Log.d(TAG, "onViewCreated: ends")

    }

    private fun initializeDateListener(): View.OnClickListener {
        return  View.OnClickListener { v ->

            binding.apply {
                when (v) {
                    etBirthDate -> {
                        (activity as OnDateClicked?)?.onBirthDatePicked(birthDate)
                    }

                    etDeathDate -> {
                        (activity as OnDateClicked?)?.onDeathDatePicked(deathDate)
                    }
                }
            }
        }

    }


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach: called")
        super.onAttach(context)

        if (context !is OnDateClicked) {
            throw RuntimeException("$context must implement OnDateClicked")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SavePersonFragment()
    }
}