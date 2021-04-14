package com.example.inspiringpersons.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.inspiringpersons.R
import com.example.inspiringpersons.databinding.ActivityUpdatePersonBinding
import com.example.inspiringpersons.databinding.ContentUpdatePersonBinding
import com.example.inspiringpersons.dialogs.DATE_PICKER_DATE
import com.example.inspiringpersons.dialogs.DATE_PICKER_ID
import com.example.inspiringpersons.dialogs.DATE_PICKER_TITLE
import com.example.inspiringpersons.dialogs.DatePickerFragment
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.model.PersonWithQuotes
import com.example.inspiringpersons.viewmodels.UpdatePersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


private const val TAG = "UpdatePersonActivity"
private const val DIALOG_BIRTH_DATE = 1
private const val DIALOG_DEATH_DATE = 2

@AndroidEntryPoint
class UpdatePersonActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var activityUpdatePersonBinding: ActivityUpdatePersonBinding
    private lateinit var contentUpdatePersonBinding: ContentUpdatePersonBinding

    private var birthDate: Long = 0L
    private var deathDate: Long = 0L

    private var personWithQuotes: PersonWithQuotes? = null

    private val updatePersonViewModel: UpdatePersonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityUpdatePersonBinding = ActivityUpdatePersonBinding.inflate(layoutInflater)
        val view = activityUpdatePersonBinding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))

        contentUpdatePersonBinding = ContentUpdatePersonBinding.bind(view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateListener = initializeDateListener()
        contentUpdatePersonBinding.etBirthDate.setOnClickListener(dateListener)
        contentUpdatePersonBinding.etDeathDate.setOnClickListener(dateListener)

        var quotes = ArrayList<String>()
        val adapter = ArrayAdapter(this, R.layout.item_quote, R.id.quote, quotes)
        contentUpdatePersonBinding.lvPersonQuotes.adapter = adapter

        contentUpdatePersonBinding.lvPersonQuotes.setOnItemClickListener {parent, view, position, id ->
            updatePersonViewModel.deleteQuote(position)
        }

        contentUpdatePersonBinding.btnAddQuote.setOnClickListener {
            val quote = contentUpdatePersonBinding.etQuote.text.toString()
            Log.d(TAG, "onCreate: adding quote with value $quote")
            if(quote.isNotEmpty()) {
                updatePersonViewModel.addQuote(quote)
            }
        }


        if (intent != null) {
            personWithQuotes = intent.extras?.getSerializable(PERSON_WITH_QUOTES_TRANSFER) as PersonWithQuotes

            Log.d(TAG, "onCreate: intent is not null person is $personWithQuotes")

            contentUpdatePersonBinding.etImageLink.setText(personWithQuotes!!.inspiringPerson.imageLink)
            contentUpdatePersonBinding.addeditDescription.setText(personWithQuotes!!.inspiringPerson.description)

            val dateFormat = DateFormat.getDateFormat(this@UpdatePersonActivity) // formats date depending on different parts of a world
            val userBirthDate = dateFormat.format(personWithQuotes?.inspiringPerson?.birthDate)
            val userDeathDate = dateFormat.format(personWithQuotes?.inspiringPerson?.deathDate)
            contentUpdatePersonBinding.etBirthDate.setText(userBirthDate)
            contentUpdatePersonBinding.etDeathDate.setText(userDeathDate)
            birthDate = personWithQuotes?.inspiringPerson?.birthDate!!
            deathDate = personWithQuotes?.inspiringPerson?.deathDate!!

        }


        updatePersonViewModel.birthDateLD.observe(this,
                {

                    val dateFormat = DateFormat.getDateFormat(this@UpdatePersonActivity) // formats date depending on different parts of a world
                    val userBirthDate = dateFormat.format(it)
                    contentUpdatePersonBinding.etBirthDate.setText(userBirthDate)
                    birthDate = it

                })

        updatePersonViewModel.deathDateLD.observe(this,
                {

                    val dateFormat = DateFormat.getDateFormat(this@UpdatePersonActivity) // formats date depending on different parts of a world
                    val userBirthDate = dateFormat.format(it)
                    contentUpdatePersonBinding.etDeathDate.setText(userBirthDate)
                    deathDate = it


                })


        updatePersonViewModel.currentQuotesLD.observe(this,
            {
                Log.d(TAG, "onCreate: observing quotes with values $it")
                quotes = it
                adapter.clear()
                adapter.addAll(quotes)
                adapter.notifyDataSetChanged()
                Log.d(TAG, "onCreate: observing quotes with adapter value ${adapter.count}")

            })


        contentUpdatePersonBinding.apply {

            btnUpdate.setOnClickListener {
                if(addeditDescription.text.isEmpty() || etImageLink.text.isEmpty()) {
                    Toast.makeText(this@UpdatePersonActivity, getString(R.string.toast_fill_all), Toast.LENGTH_SHORT).show()
                }
                else {
                    updatePersonViewModel.updatePerson(
                        InspiringPerson(personId = personWithQuotes?.inspiringPerson?.personId!!, imageLink = etImageLink.text.toString(),
                            description = addeditDescription.text.toString(), birthDate = birthDate,
                            deathDate = deathDate)
                    )
                }
            }
        }



    }

    private fun initializeDateListener(): View.OnClickListener {
        return  View.OnClickListener { v ->

            contentUpdatePersonBinding.apply {
                when (v) {
                    etBirthDate -> {
                        showDatePickerDialog(getString(R.string.home_title_birth_date), DIALOG_BIRTH_DATE)

                    }

                    etDeathDate -> {
                        showDatePickerDialog(getString(R.string.home_title_death_date), DIALOG_DEATH_DATE)
                    }
                }
            }
        }

    }

    private fun showDatePickerDialog(title: String, dialogId: Int) {
        Log.d(TAG, "showDatePickerDialog: starts with $title and $dialogId")
        val dialogFragment = DatePickerFragment()

        val arguments = Bundle()
        arguments.putInt(DATE_PICKER_ID, dialogId)
        arguments.putString(DATE_PICKER_TITLE, title)

        val date = Date()
        date.time = when(dialogId) {
            DIALOG_BIRTH_DATE -> {
                birthDate
            }

            DIALOG_DEATH_DATE -> {
                deathDate
            }
            else -> {
                GregorianCalendar(Locale.getDefault()).timeInMillis
            }
        }

        arguments.putSerializable(DATE_PICKER_DATE, date)

        dialogFragment.arguments = arguments
        dialogFragment.show(supportFragmentManager, "datePicker")

        Log.d(TAG, "showDatePickerDialog: ends")
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        Log.d(TAG, "onDateSet: starts")
        val calendar = GregorianCalendar()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)

        // Check the id, so we know what to do with the result
        val dialogId = view.tag as Int // we put dialog id in tag in datepickerfragment, views tag is general purpose slot where you can store things, we can store any object here
        when (dialogId) {
            DIALOG_BIRTH_DATE -> {
                updatePersonViewModel.updatePersonBirth(calendar.timeInMillis)
            }
            DIALOG_DEATH_DATE -> {

                updatePersonViewModel.updatePersonDeath(calendar.timeInMillis)

            }
            else -> throw IllegalArgumentException("Invalid mode when receiving DatePickerDialog result")
        }
    }

}