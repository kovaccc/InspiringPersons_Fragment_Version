package com.example.inspiringpersons.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.core.view.get
import com.example.inspiringpersons.R
import com.example.inspiringpersons.databinding.ActivityMainBinding
import com.example.inspiringpersons.databinding.ContentMainBinding
import com.example.inspiringpersons.dialogs.DATE_PICKER_ID
import com.example.inspiringpersons.dialogs.DATE_PICKER_TITLE
import com.example.inspiringpersons.dialogs.DatePickerFragment
import com.example.inspiringpersons.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.example.inspiringpersons.dialogs.DATE_PICKER_DATE
import com.example.inspiringpersons.model.PersonsWithQuotes
import com.example.inspiringpersons.pagerAdapter.MenuSlidePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


private const val TAG = "MainActivity"
private const val DIALOG_BIRTH_DATE = 1
private const val DIALOG_DEATH_DATE = 2


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,EditPersonFragment.OnDateClicked,PersonListFragment.OnPersonEdit {

    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))

        contentMainBinding = ContentMainBinding.bind(view) // content main is merged layout


        setUpPager()

        Log.d(TAG, "onCreate: ends")
    }



    private fun showDatePickerDialog(title: String, dialogId: Int, date: Long) {
        Log.d(TAG, "showDatePickerDialog: starts with $title and $dialogId and $date")
        val dialogFragment = DatePickerFragment()

        val arguments = Bundle()

        val dateDialog = Date()
        dateDialog.time = date

        arguments.apply {
            putInt(DATE_PICKER_ID, dialogId)
            putString(DATE_PICKER_TITLE, title)
            putSerializable(DATE_PICKER_DATE, dateDialog)
        }

        dialogFragment.arguments = arguments
        dialogFragment.show(supportFragmentManager, "datePicker")

        Log.d(TAG, "showDatePickerDialog: ends")
    }


    private fun setUpPager() {

        val menuNodesList = arrayListOf(
              "Edit", "List")

        val pagerAdapter = MenuSlidePagerAdapter(this, menuNodesList)
        contentMainBinding.viewPagerPersons.adapter = pagerAdapter

        TabLayoutMediator(mainActivityBinding.tabInspiringPerson, contentMainBinding.viewPagerPersons) { tab, position ->
            tab.text = pagerAdapter.getTitle(position)
        }.attach()

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

    override fun onBirthDatePicked(date: Long) {
        showDatePickerDialog(getString(R.string.home_title_birth_date), DIALOG_BIRTH_DATE, date)
    }

    override fun onDeathDatePicked(date: Long) {
        showDatePickerDialog(getString(R.string.home_title_death_date), DIALOG_DEATH_DATE, date)
    }

    override fun onPersonEdit(personWithQuotes: PersonsWithQuotes) {
        Log.d(TAG, "onPersonEdit: starts")

        val intent = Intent(this@MainActivity, FoodMenuActivity::class.java)
        intent.putExtra(MEAL_DATE_TRANSFER, mDate)
        intent.putExtra(CURRENT_USER_ID_TRANSFER, currentUser?.id)
        startActivity(intent)
    }
}