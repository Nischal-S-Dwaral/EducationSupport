package com.example.educationsupport.fragment.educator

import android.app.SearchManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.adapters.learner.CourseCardAdapter
import com.example.educationsupport.adapters.learner.LearnerSearchAdapter
import com.example.educationsupport.constants.CourseListConstants
import com.example.educationsupport.constants.LearnerListConstants
import android.widget.ArrayAdapter



class AddNewLearnerFragment : Fragment() {

    // creating variables for listview
    lateinit var programmingLanguagesLV: ListView

    // creating array adapter for listview
    lateinit var listAdapter: ArrayAdapter<String>

    // creating array list for listview
    lateinit var programmingLanguagesList: ArrayList<String>;

    // creating variable for searchview
    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val learnerList = LearnerListConstants.getLearnerList()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.educator_add_learner, container, false)

        /**
         * Search view filtering
         */

        // initializing variables of list view with their ids.
        programmingLanguagesLV = view.findViewById(R.id.idLVProgrammingLanguages)
        searchView = view.findViewById<SearchView>(R.id.idSV)

        // initializing list and adding data to list
        programmingLanguagesList = ArrayList()
        programmingLanguagesList.add("Learner1")
        programmingLanguagesList.add("Learner2")
        programmingLanguagesList.add("Learner3")
        programmingLanguagesList.add("Learner4")
        programmingLanguagesList.add("5")
        programmingLanguagesList.add("6")
        programmingLanguagesList.add("7")



        // initializing list adapter and setting layout
        // for each list view item and adding array list to it.
        listAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            programmingLanguagesList
        )

        // on below line setting list
        // adapter to our list view.
        programmingLanguagesLV.adapter = listAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                if (programmingLanguagesList.contains(query)) {
                    // if query exist within list we
                    // are filtering our list adapter.
                    listAdapter.filter.filter(query)
                } else {
                    // if query is not present we are displaying
                    // a toast message as no  data found..
//                    Toast.makeText(this@MainActivity, "No Language found..", Toast.LENGTH_LONG)
//                        .show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                listAdapter.filter.filter(newText)
                return false
            }
        })
        return view
    }
}