package com.example.inspiringpersons.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inspiringpersons.R
import com.example.inspiringpersons.adapters.InspiringPersonsAdapter
import com.example.inspiringpersons.databinding.FragmentPersonListBinding
import com.example.inspiringpersons.model.PersonWithQuotes
import com.example.inspiringpersons.viewmodels.PersonsViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "PersonListFragment"

@AndroidEntryPoint
class PersonListFragment : Fragment(), InspiringPersonsAdapter.OnPersonClickListener {


    interface OnPersonEdit {
        fun onPersonEdit (personWithQuotes: PersonWithQuotes)
    }

    private lateinit var fragmentPersonListBinding: FragmentPersonListBinding

    private val personsViewModel: PersonsViewModel by viewModels()

    private lateinit var personsAdapter: InspiringPersonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        personsViewModel.personWithQuotes.observe(this, {
                        Log.d(TAG, "onCreate: observing personsWithQuotes with values $it")
                        personsAdapter.loadNewPersons(it)
                        Log.d(TAG, "onCreate: observing personsWithQuotes with adapter value ${personsAdapter.itemCount}")
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentPersonListBinding = FragmentPersonListBinding.inflate(inflater, container, false)

        personsAdapter = InspiringPersonsAdapter(emptyList(), this)

        fragmentPersonListBinding.rvInspiringPersons.adapter = personsAdapter


        return fragmentPersonListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentPersonListBinding.rvInspiringPersons.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )

    }

    override fun onItemClick(personWithQuotes: PersonWithQuotes) {
        Log.d(TAG, "onItemClick: starts with $personWithQuotes")
        val randomQuote = if(personWithQuotes.personQuotes.isEmpty()) getString(R.string.toast_empty_quote) else personWithQuotes.personQuotes.shuffled().random().quoteText
        Toast.makeText(context, randomQuote, Toast.LENGTH_SHORT).show()

    }

    override fun onEditClick (
            personWithQuotes: PersonWithQuotes,
            viewHolder: RecyclerView.ViewHolder
    ) {
        Log.d(TAG, "onEditClick: starts with $personWithQuotes and $viewHolder")
        (activity as OnPersonEdit).onPersonEdit(personWithQuotes)
    }

    override fun onDeleteClick(
            personWithQuotes: PersonWithQuotes,
            viewHolder: RecyclerView.ViewHolder
    ) {
        Log.d(TAG, "onDeleteClick: starts with $personWithQuotes and $viewHolder")
        personsViewModel.deletePerson(personWithQuotes.inspiringPerson)
    }


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach: called")
        super.onAttach(context)

        if (context !is OnPersonEdit) {
            throw RuntimeException("$context must implement OnPersonEdit")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = PersonListFragment()
    }


}