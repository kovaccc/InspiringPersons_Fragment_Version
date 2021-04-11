package com.example.inspiringpersons.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.activity.viewModels
import com.example.inspiringpersons.R
import com.example.inspiringpersons.databinding.ActivityMainBinding
import com.example.inspiringpersons.databinding.ContentMainBinding
import com.example.inspiringpersons.dialogs.DATE_PICKER_ID
import com.example.inspiringpersons.dialogs.DATE_PICKER_TITLE
import com.example.inspiringpersons.dialogs.DatePickerFragment
import com.example.inspiringpersons.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.text.format.DateFormat
import android.widget.AdapterView
import android.widget.Toast
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.dialogs.DATE_PICKER_DATE


private const val TAG = "MainActivity"
private const val DIALOG_BIRTH_DATE = 1
private const val DIALOG_DEATH_DATE = 2


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding


    private val mainViewModel: MainViewModel by viewModels()

    private var birthDate: Long = 0L
    private var deathDate: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))

        contentMainBinding = ContentMainBinding.bind(view) // content main is merged layout

        
        val dateListener = initializeDateListener()
        contentMainBinding.etBirthDate.setOnClickListener(dateListener)
        contentMainBinding.etDeathDate.setOnClickListener(dateListener)

        var quotes = ArrayList<String>()
        val adapter = ArrayAdapter(this, R.layout.item_quote, R.id.quote, quotes)
        contentMainBinding.lvPersonQuotes.adapter = adapter


        contentMainBinding.lvPersonQuotes.setOnItemClickListener {parent, view, position, id ->
            mainViewModel.deleteQuote(position)
        }


        contentMainBinding.apply {

            btnSave.setOnClickListener {
                if(addeditDescription.text.isEmpty() || etImageLink.text.isEmpty()) {
                    Toast.makeText(this@MainActivity, getString(R.string.toast_fill_all), Toast.LENGTH_SHORT).show()
                }
                else {
                    mainViewModel.saveInspiringPerson(
                        InspiringPerson(imageLink = etImageLink.text.toString(),
                        description = addeditDescription.text.toString(), birthDate = birthDate,
                        deathDate = deathDate)
                    )
                }
            }
        }



        mainViewModel.currentQuotesLD.observe(this,
            {
                Log.d(TAG, "onCreate: observing quotes with values $it")
                quotes = it
                adapter.clear()
                adapter.addAll(quotes)
                adapter.notifyDataSetChanged()
                Log.d(TAG, "onCreate: observing quotes with adapter value ${adapter.count}")

            })

        mainViewModel.birthDateLD.observe(this,
            {

                val dateFormat = DateFormat.getDateFormat(this) // formats date depending on different parts of a world
                val userDate = dateFormat.format(it)
                birthDate = it
                contentMainBinding.etBirthDate.setText(userDate)
            })

        mainViewModel.deathDateLD.observe(this,
            {
                val dateFormat = DateFormat.getDateFormat(this)
                val userDate = dateFormat.format(it)
                deathDate = it
                contentMainBinding.etDeathDate.setText(userDate)
            })


        contentMainBinding.btnAddQuote.setOnClickListener {
            val quote = contentMainBinding.etQuote.text.toString()
            Log.d(TAG, "onCreate: adding quote with value $quote")
            if(quote.isNotEmpty()) {
                mainViewModel.addQuote(quote)
            }
        }



        Log.d(TAG, "onCreate: ends")
    }

    private fun initializeDateListener(): View.OnClickListener {
        return  View.OnClickListener { v ->

            contentMainBinding.apply {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       when (item.itemId) {
            R.id.menumain_showInspiringPersons -> startActivity(Intent(this, InspiringPersonsActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = GregorianCalendar()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)

        // Check the id, so we know what to do with the result
        val dialogId = view.tag as Int // we put dialog id in tag in datepickerfragment, views tag is general purpose slot where you can store things, we can store any object here
        when (dialogId) {
            DIALOG_BIRTH_DATE -> {
                mainViewModel.updatePersonBirth(calendar.timeInMillis)
            }
            DIALOG_DEATH_DATE -> {

                mainViewModel.updatePersonDeath(calendar.timeInMillis)

            }
            else -> throw IllegalArgumentException("Invalid mode when receiving DatePickerDialog result")
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: starts")
        super.onDestroy()
        Log.d(TAG, "onDestroy: ends")
    }
}