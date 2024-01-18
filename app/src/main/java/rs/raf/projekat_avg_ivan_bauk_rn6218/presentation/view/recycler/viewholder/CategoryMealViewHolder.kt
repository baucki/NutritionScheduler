package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.viewholder

import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.LayoutItemCategoryMealBinding

class CategoryMealViewHolder(private val itemBiding: LayoutItemCategoryMealBinding) : RecyclerView.ViewHolder(itemBiding.root) {

    private val highlightColor: Int = ContextCompat.getColor(itemView.context, R.color.selectedColor)

    private var isHighlighted: Boolean = false

    fun bind(categoryMeal: CategoryMeal) {
        itemBiding.mealTv.text = categoryMeal.strMeal
        itemBiding.caloriesTv.text = categoryMeal.strCalories
        Picasso.get()
            .load(categoryMeal.strMealThumb)
            .into(itemBiding.mealIv)

        itemBiding.categoryMealBoxFl.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // User started touching the view
                    updateCategoryBoxStyle(true)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // User released the touch or gesture canceled
                    updateCategoryBoxStyle(false)
                }
            }
            false
        }

    }

    private fun updateCategoryBoxStyle(isHighlighted: Boolean) {
        this.isHighlighted = isHighlighted
        if (isHighlighted) {
            itemBiding.categoryMealBoxFl.setBackgroundColor(highlightColor)
        } else {
            itemBiding.categoryMealBoxFl.setBackgroundResource(R.drawable.category_box_background)
        }
    }



}