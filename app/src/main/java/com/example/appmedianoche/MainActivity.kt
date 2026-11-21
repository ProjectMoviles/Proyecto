package com.example.appmedianoche

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedianoche.domain.model.Dish
import com.example.appmedianoche.util.EXTRA_MESSAGE_ID
import com.example.appmedianoche.util.Util
import com.example.appmedianoche.viewmodel.DishViewModel

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var etDishSearch: EditText
    private lateinit var tvDishCount: TextView
    private lateinit var rvDishList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etDishSearch = findViewById<EditText>(R.id.etDishSearch)
        tvDishCount = findViewById<TextView>(R.id.tvDishCount)
        rvDishList = findViewById<RecyclerView>(R.id.rvDishList)
        rvDishList.layoutManager = LinearLayoutManager(applicationContext)

        val ibSearch = findViewById<ImageButton>(R.id.ibSearch)
        val ibAddDish = findViewById<ImageButton>(R.id.ibAddDish)

        ibSearch.setOnClickListener(View.OnClickListener{
            View -> searchDishes()

        })

        ibAddDish.setOnClickListener(View.OnClickListener{
                View -> Util.openActivity(this, DishFormActivity::class.java)

        })
    }

    override fun onResume(){
        super.onResume()
        searchDishes()
    }

    override fun OnItemClick(dish: Dish){
        Util.openActivity(this, DishFormActivity::class.java, EXTRA_MESSAGE_ID, dish.id)
    }

    private fun searchDishes(){
        val query = etDishSearch.text.toString().trim()
        showDishes(DishViewModel.filterDishes(query))
    }

    private fun showDishes(dishes: List<Dish>){
        rvDishList.adapter = DishListAdapter(dishes,this)
        tvDishCount.text = dishes.size.toString() + " " + getString(R.string.dish_count)
    }

}