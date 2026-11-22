package com.example.appmedianoche

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedianoche.domain.model.Dish
import com.example.appmedianoche.domain.model.DishType
import com.example.appmedianoche.util.EXTRA_MESSAGE_ID
import com.example.appmedianoche.util.Util
import com.example.appmedianoche.viewmodel.DishViewModel
import android.text.Editable
import android.text.TextWatcher

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var etDishSearch: EditText
    private lateinit var tvDishCount: TextView
    private lateinit var rvDishList: RecyclerView
    private lateinit var btnFilterRarity: Button
    private lateinit var btnFilterPrice: Button
    private lateinit var btnFilterType: Button
    private lateinit var btnFilterRating: Button

    // Active filters (default values mean "no filter")
    private var minRarity = 1
    private var maxPrice: Int? = null
    private var selectedType: DishType? = null
    private var minRating = 0

    private val priceOptions = listOf(1000, 2000, 3000, 5000, 10000)


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

        // Filters the list while the user types or deletes text
        etDishSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDishes()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        btnFilterRarity = findViewById<Button>(R.id.btnFilterRarity)
        btnFilterPrice = findViewById<Button>(R.id.btnFilterPrice)
        btnFilterType = findViewById<Button>(R.id.btnFilterType)
        btnFilterRating = findViewById<Button>(R.id.btnFilterRating)

        val ibSearch = findViewById<ImageButton>(R.id.ibSearch)
        val ibAddDish = findViewById<ImageButton>(R.id.ibAddDish)

        ibSearch.setOnClickListener(View.OnClickListener{
                View -> searchDishes()

        })

        ibAddDish.setOnClickListener(View.OnClickListener{
                View -> Util.openActivity(this, DishFormActivity::class.java)

        })

        btnFilterRarity.setOnClickListener(View.OnClickListener { view ->
            showRarityFilter()
        })

        btnFilterPrice.setOnClickListener(View.OnClickListener { view ->
            showPriceFilter()
        })

        btnFilterType.setOnClickListener(View.OnClickListener { view ->
            showTypeFilter()
        })

        btnFilterRating.setOnClickListener(View.OnClickListener { view ->
            showRatingFilter()
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
        showDishes(
            DishViewModel.filterDishes(
                query,
                minRarity,
                maxPrice,
                selectedType,
                minRating.toDouble()
            )
        )
        updateFilterButtons()
    }

    private fun showDishes(dishes: List<Dish>){
        rvDishList.adapter = DishListAdapter(dishes,this)
        tvDishCount.text = dishes.size.toString() + " " + getString(R.string.dish_count)
    }

    // Each button shows its active value, for example "Rareza 4+"
    private fun updateFilterButtons() {
        if (minRarity > 1) {
            btnFilterRarity.text = getString(R.string.filter_rarity) + " " + minRarity + "+"
        } else {
            btnFilterRarity.text = getString(R.string.filter_rarity)
        }

        val currentMaxPrice = maxPrice
        if (currentMaxPrice != null) {
            btnFilterPrice.text = getString(R.string.filter_price) + " " +
                    getString(R.string.dish_price_hint) + currentMaxPrice
        } else {
            btnFilterPrice.text = getString(R.string.filter_price)
        }

        val currentType = selectedType
        if (currentType != null) {
            btnFilterType.text = resources.getStringArray(R.array.dish_type_options)[currentType.ordinal]
        } else {
            btnFilterType.text = getString(R.string.filter_type)
        }

        if (minRating > 0) {
            btnFilterRating.text = getString(R.string.filter_rating) + " " + minRating + "+"
        } else {
            btnFilterRating.text = getString(R.string.filter_rating)
        }
    }

    private fun showRarityFilter() {
        val options = arrayOf(getString(R.string.filter_clear), "1+", "2+", "3+", "4+", "5+")
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.filter_rarity))
            .setItems(options) { dialog, which ->
                // which = 0 is "clear filter"; 1..5 is the minimum rarity
                minRarity = if (which == 0) 1 else which
                searchDishes()
            }
            .show()
    }

    private fun showPriceFilter() {
        val prices = priceOptions.map { getString(R.string.dish_price_hint) + it }.toTypedArray()
        val options = arrayOf(getString(R.string.filter_clear)) + prices
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.filter_price))
            .setItems(options) { dialog, which ->
                maxPrice = if (which == 0) null else priceOptions[which - 1]
                searchDishes()
            }
            .show()
    }

    private fun showTypeFilter() {
        val types = resources.getStringArray(R.array.dish_type_options)
        val options = arrayOf(getString(R.string.filter_clear)) + types
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.filter_type))
            .setItems(options) { dialog, which ->
                selectedType = if (which == 0) null else DishType.values()[which - 1]
                searchDishes()
            }
            .show()
    }

    private fun showRatingFilter() {
        val options = arrayOf(getString(R.string.filter_clear), "1+", "2+", "3+", "4+", "5+")
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.filter_rating))
            .setItems(options) { dialog, which ->
                // which = 0 is "clear filter"; 1..5 is the minimum rating
                minRating = which
                searchDishes()
            }
            .show()
    }

}