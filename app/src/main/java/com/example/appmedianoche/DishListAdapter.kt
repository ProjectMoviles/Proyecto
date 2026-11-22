package com.example.appmedianoche
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedianoche.domain.model.Dish



class DishListAdapter(
    private var dishes: List<Dish>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
       val itemView = LayoutInflater.from(parent.context)
           .inflate(R.layout.activity_dish_item, parent, false  )
        return DishViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val item =dishes[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

}

class DishViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val tvDishItemName = view.findViewById<TextView>(R.id.tvDishItemName)
    val tvDishItemPlace = view.findViewById<TextView>(R.id.tvDishItemPlace)
    val tvDishItemRarity = view.findViewById<TextView>(R.id.tvDishItemRarity)
    val tvDishItemTaste = view.findViewById<TextView>(R.id.tvDishItemTaste)

    fun bind(dish: Dish, itemClickListener: OnItemClickListener){
        val context = itemView.context
        tvDishItemName.text = dish.name
        tvDishItemPlace.text = dish.placeId + " . " +
                context.getString(R.string.dish_price_hint) + dish.price
        tvDishItemRarity.text = context.getString(R.string.filter_rarity) +
                " " + dish.rarityLevel + "/5"
        tvDishItemTaste.text = context.getString(R.string.dish_taste) +
                " " + dish.deliciousScore
        itemView.setOnClickListener {
            itemClickListener.OnItemClick(dish)
        }
    }



}