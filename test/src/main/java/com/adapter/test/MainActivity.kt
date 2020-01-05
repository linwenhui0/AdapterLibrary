package com.adapter.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.test.databinding.ActivityMainBinding
import com.hlibrary.adapter.model.HeadFootAdapterVo
import com.hlibrary.adapter.recycler.ExpandRecyclerAdapter
import com.hlibrary.adapter.recycler.OnItemClickListener

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = object : ExpandRecyclerAdapter<String, HeadFootAdapterVo<String>, HeadFootAdapterVo<String>>(this, R.layout.adapter_test01) {
            override val variableId: Int
                get() = BR.text
        }
        for (i in 0 until 2) {
            val headerVo = HeadFootAdapterVo<String>(R.layout.adapter_header_test02, BR.text)
            headerVo.data = "header $i"
            adapter.addHeaderObject(headerVo)
        }
        adapter.addObject("test01")
        adapter.addObject("test02")
        adapter.setOnHeadItemClickListener(object : OnItemClickListener<HeadFootAdapterVo<String>> {
            override fun onItemClick(v: View?, position: Int, bean: HeadFootAdapterVo<String>?) {
                println("onHeaderItemClick $position ${bean?.data}")
            }

        })
        adapter.setOnItemClickListener(object : OnItemClickListener<String> {
            override fun onItemClick(v: View?, position: Int, bean: String?) {
                println("onItemClick $position $bean")
            }

        })
        dataBinding.recycler.adapter = adapter

    }
}
