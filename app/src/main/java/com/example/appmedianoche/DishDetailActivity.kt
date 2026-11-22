package com.example.appmedianoche

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmedianoche.util.EXTRA_MESSAGE_ID
import com.example.appmedianoche.util.Util
import com.example.appmedianoche.viewmodel.DishViewModel

class DishDetailActivity : AppCompatActivity() {

    private lateinit var tvDishName: TextView
    private lateinit var tvDishPlace: TextView
    private lateinit var tvDishType: TextView
    private lateinit var tvDishRarity: TextView
    private lateinit var tvDishTaste: TextView
    private var dishId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dish_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // The dish id arrives from the previous screen
        val id = intent.getStringExtra(EXTRA_MESSAGE_ID)
        if (id.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.msg_not_found), Toast.LENGTH_LONG).show()
            finish()
            return
        }
        dishId = id

        tvDishName = findViewById<TextView>(R.id.tvDishName)
        tvDishPlace = findViewById<TextView>(R.id.tvDishPlace)
        tvDishType = findViewById<TextView>(R.id.tvDishType)
        tvDishRarity = findViewById<TextView>(R.id.tvDishRarity)
        tvDishTaste = findViewById<TextView>(R.id.tvDishTaste)

        val btnVoteYes = findViewById<Button>(R.id.btnVoteYes)
        val btnVoteNo = findViewById<Button>(R.id.btnVoteNo)
        val btnMiniMap = findViewById<Button>(R.id.btnMiniMap)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // Votes are not stored yet: only a thank-you message
        btnVoteYes.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(this, getString(R.string.vote_thanks), Toast.LENGTH_SHORT).show()
        })

        btnVoteNo.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(this, getString(R.string.vote_thanks), Toast.LENGTH_SHORT).show()
        })

        btnMiniMap.setOnClickListener(View.OnClickListener { view ->
            Util.openActivity(this, MapActivity::class.java, EXTRA_MESSAGE_ID, dishId)
        })

        // Opens the teammate's form in edit mode
        btnEdit.setOnClickListener(View.OnClickListener { view ->
            Util.openActivity(this, DishFormActivity::class.java, EXTRA_MESSAGE_ID, dishId)
        })

        btnDelete.setOnClickListener(View.OnClickListener { view ->
            confirmDelete()
        })
    }

    // onResume runs every time the screen is visible again,
    // so after editing the dish the data is refreshed
    override fun onResume() {
        super.onResume()
        if (dishId.isEmpty()) return
        showDish()
    }

    private fun showDish() {
        // NOTE: if the teammate renames it, change to DishViewModel.getDishById
        val dish = DishViewModel.getDishesById(dishId)
        if (dish == null) {
            finish()
            return
        }
        tvDishName.text = dish.name
        tvDishPlace.text = dish.placeId + " - " +
                getString(R.string.dish_price_hint) + dish.price
        tvDishType.text = resources.getStringArray(R.array.dish_type_options)[dish.type.ordinal]
        tvDishRarity.text = getString(R.string.filter_rarity) + " " + dish.rarityLevel + "/5"
        tvDishTaste.text = getString(R.string.dish_taste) + " " + dish.deliciousScore
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_delete_title))
            .setMessage(getString(R.string.dialog_delete_message))
            .setNegativeButton(getString(R.string.btn_cancel), null)
            .setPositiveButton(getString(R.string.btn_delete)) { dialog, which ->
                try {
                    DishViewModel.deleteDish(dishId)
                    Toast.makeText(this, getString(R.string.msg_delete_success),
                        Toast.LENGTH_LONG).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
            .show()
    }
}