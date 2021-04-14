package com.example.inspiringpersons.pagerAdapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.inspiringpersons.ui.SavePersonFragment
import com.example.inspiringpersons.ui.PersonListFragment
import java.util.ArrayList


private const val TAG = "MenuSlidePagerAdapter"

class MenuSlidePagerAdapter(fragmentActivity: FragmentActivity, private val nodes: ArrayList<String>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: starts ")
        return nodes.size
    }

    fun getTitle(position: Int): String {
        Log.d(TAG, "getTitle: starts ")
        return nodes[position]
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment: starts with position$position")
        val result = when(position) {
            0 -> SavePersonFragment.newInstance()
            1 -> PersonListFragment.newInstance()
            else -> SavePersonFragment.newInstance()
        }
        Log.d(TAG, "createFragment: ends with position $result")

        return result
    }

}
