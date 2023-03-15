package com.example.skuta.fabsnackbarassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var myListView: ListView
    lateinit var mainFAB: FloatingActionButton
    lateinit var addPersonFAB: FloatingActionButton
    lateinit var removePersonFAB: FloatingActionButton
    lateinit var undoClickListener: View.OnClickListener
    lateinit var redoClickListener: View.OnClickListener
    lateinit var enterName: EditText
    var temp: Int = 1

    var fabsVisiblity = false
    var items = ArrayList<String>()
    var myAdapter: ArrayAdapter<String>? = null
    var tempHold = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFAB = findViewById(R.id.main_fab)
        addPersonFAB = findViewById(R.id.add_person_fab)
        removePersonFAB = findViewById(R.id.remove_person_fab)
        myListView = findViewById(R.id.my_list_view)
        enterName = findViewById(R.id.enter_name)

        myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,items)
        myListView.adapter = myAdapter

        addPersonFAB.setOnClickListener {
            items.add(enterName.text.toString())
            myAdapter?.notifyDataSetChanged()
            Snackbar.make(it, "You added a Person", Snackbar.LENGTH_LONG)
                .setAction("Undo", undoClickListener).show()

        }

        undoClickListener = View.OnClickListener {
            items.removeAt(items.size-1)
            myAdapter?.notifyDataSetChanged()
            Snackbar.make(it, "You removed a Person", Snackbar.LENGTH_LONG).show()
        }

        mainFAB.setOnClickListener{
            toggleFABsVisibility()
        }

        removePersonFAB.setOnClickListener {
            if (items.size < 1){
                enterName.error = "You must add a Person first."
                enterName.requestFocus()
            }else{
                for (n in items.indices){
                    temp = enterName.text.toString().compareTo(items[n], false)
                    if (temp == 0){
                        items.removeAt(n)
                        myAdapter?.notifyDataSetChanged()
                        Snackbar.make(it, "You Removed a Person!", Snackbar.LENGTH_SHORT).show()
                    }


                }
                if (temp != 0){
                    enterName.error = "There is no Person in this list by that name."
                    enterName.requestFocus()
                }
            }
        }


    }



    private fun toggleFABsVisibility() {
        if (fabsVisiblity){
            addPersonFAB.hide()
            removePersonFAB.hide()
            fabsVisiblity = false
        }
        else {
            addPersonFAB.show()
            removePersonFAB.show()
            fabsVisiblity = true
        }
    }
}