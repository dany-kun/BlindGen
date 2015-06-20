package com.smabo.dany_kun.blindgen

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast


import kotlinx.android.synthetic.activity_main.*
import kotlinx.android.synthetic.activity_main.view.*
import java.util.*


private val OUT_NUMBER_LIST = "out_number_list"

public class MainActivity : AppCompatActivity() {

    private var adapter: BlindAdapter? = null

    private var maxInt = 2
    private val generator = Random()

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putStringArrayList(OUT_NUMBER_LIST, (recycler.getAdapter() as BlindAdapter).dataList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.setLayoutManager(LinearLayoutManager(this))

        fab.setOnClickListener {
            adapter?.addToList(generate())
            recycler.scrollToPosition((adapter?.getItemCount() ?: 1) - 1)
        }
        textview_clear.setOnClickListener { clear() }
        textview_start.setOnClickListener {
            maxInt = spinner_new.getSelectedItem() as Int
            clear()
        }

        spinner_new.setAdapter(ArrayAdapter(this, R.layout.spinner_item, arrayListOf(2, 3, 4, 5, 6, 7, 8, 9, 10)))
        adapter = BlindAdapter(this, savedInstanceState?.getStringArrayList(OUT_NUMBER_LIST) ?: ArrayList<String>())
        recycler.setAdapter(adapter)

    }

    private fun generate() = generator.nextInt(maxInt)

    private fun clear() = adapter?.clear()

}

class BlindViewHolder(v: TextView) : RecyclerView.ViewHolder(v) {}
class BlindAdapter(c: Context, val dataList: ArrayList<String>) : RecyclerView.Adapter<BlindViewHolder>() {

    private val inflater: LayoutInflater

    init {
        inflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = BlindViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false) as TextView)

    override fun onBindViewHolder(holder: BlindViewHolder?, position: Int) {
        (holder?.itemView as TextView ).setText(dataList.get(position))
    }

    override fun getItemCount() = dataList.size()

    fun addToList(newItem: Int) {
        dataList.add(newItem.toString())
        notifyItemInserted(dataList.size() - 1)
    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }
}
