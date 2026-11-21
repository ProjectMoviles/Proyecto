package com.example.appmedianoche

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmedianoche.domain.model.Dish
import com.example.appmedianoche.domain.model.DishType
import com.example.appmedianoche.util.EXTRA_MESSAGE_ID
import com.example.appmedianoche.viewmodel.DishViewModel

class DishFormActivity : AppCompatActivity() {

    private lateinit var tvDishNewTitle: TextView
    private lateinit var etDishName: EditText
    private lateinit var etDishPlace: EditText
    private lateinit var etDishPrice: EditText
    private lateinit var spDishTypeOptions: Spinner
    private lateinit var rarityButtons: List<Button>
    private lateinit var currentDish: Dish
    private var isEditMode = false
    private var rarityLevel = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dish_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDishNewTitle = findViewById<TextView>(R.id.tvDishNewTitle)
        etDishName = findViewById<EditText>(R.id.etDishName)
        etDishPlace = findViewById<EditText>(R.id.etDishPlace)
        etDishPrice = findViewById<EditText>(R.id.etDishPrice)
        spDishTypeOptions = findViewById<Spinner>(R.id.spDishTypeOptions)

        val btnRarity1 = findViewById<Button>(R.id.btnRarity1)
        val btnRarity2 = findViewById<Button>(R.id.btnRarity2)
        val btnRarity3 = findViewById<Button>(R.id.btnRarity3)
        val btnRarity4 = findViewById<Button>(R.id.btnRarity4)
        val btnRarity5 = findViewById<Button>(R.id.btnRarity5)
        rarityButtons = listOf(btnRarity1, btnRarity2, btnRarity3, btnRarity4, btnRarity5)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        btnRarity1.setOnClickListener(View.OnClickListener { view -> selectRarity(1) })
        btnRarity2.setOnClickListener(View.OnClickListener { view -> selectRarity(2) })
        btnRarity3.setOnClickListener(View.OnClickListener { view -> selectRarity(3) })
        btnRarity4.setOnClickListener(View.OnClickListener { view -> selectRarity(4) })
        btnRarity5.setOnClickListener(View.OnClickListener { view -> selectRarity(5) })

        btnSave.setOnClickListener(View.OnClickListener { view ->
            saveDish()
        })

        btnCancel.setOnClickListener(View.OnClickListener { view ->
            finish()
        })

        // If the screen was opened with a dish id, it works in edit mode
        val dishId = intent.getStringExtra(EXTRA_MESSAGE_ID)
        if (!dishId.isNullOrBlank()) {
            loadDish(dishId)
        }
    }

    private fun selectRarity(level: Int) {
        rarityLevel = level
        for (i in 0..4) {
            rarityButtons[i].isEnabled = (i + 1 != level)
        }
    }

    private fun loadDish(id: String) {
        val dish = DishViewModel.getDishesById(id)
        if (dish == null) {
            Toast.makeText(this, getString(R.string.msg_not_found), Toast.LENGTH_LONG).show()
            return
        }
        currentDish = dish
        isEditMode = true
        tvDishNewTitle.text = getString(R.string.dish_edit_title)
        etDishName.setText(dish.name)
        etDishPlace.setText(dish.placeId)
        etDishPrice.setText(dish.price.toString())
        spDishTypeOptions.setSelection(dish.type.ordinal)
        selectRarity(dish.rarityLevel)
    }

    private fun isValidationData(): Boolean {
        return etDishName.text.toString().trim().isNotBlank()
                && etDishPlace.text.toString().trim().isNotBlank()
                && etDishPrice.text.toString().trim().toIntOrNull() != null
                && rarityLevel > 0
    }

    private fun saveDish() {
        if (!isValidationData()) {
            Toast.makeText(this, getString(R.string.msg_error_required), Toast.LENGTH_LONG).show()
            return
        }
        if (isEditMode) {
            showUpdateDialog()
        } else {
            createDish()
        }
    }

    private fun buildDish(
        id: String,
        deliciousScore: Double,
        description: String,
        photoUri: String?
    ): Dish {
        return Dish(
            id,
            etDishName.text.toString().trim(),
            etDishPlace.text.toString().trim(),
            etDishPrice.text.toString().trim().toInt(),
            DishType.values()[spDishTypeOptions.selectedItemPosition],
            rarityLevel,
            deliciousScore,
            description,
            photoUri
        )
    }

    private fun createDish() {
        try {
            DishViewModel.createDish(buildDish(DishViewModel.getNextId(), 0.0, "", null))
            Toast.makeText(this, getString(R.string.msg_save_success), Toast.LENGTH_LONG).show()
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showUpdateDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_update_title)
            .setMessage(R.string.dialog_update_message)
            .setNegativeButton(R.string.btn_cancel, null)
            .setPositiveButton(R.string.btn_update) { _, _ ->
                updateDish()
            }
            .show()
    }

    private fun updateDish() {
        try {
            DishViewModel.updateDish(
                buildDish(
                    currentDish.id,
                    currentDish.deliciousScore,
                    currentDish.description,
                    currentDish.photoUri
                )
            )
            Toast.makeText(this, getString(R.string.msg_save_success), Toast.LENGTH_LONG).show()
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }


}