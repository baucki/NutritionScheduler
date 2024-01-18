package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal

class CategoryMealDiffCallback : DiffUtil.ItemCallback<CategoryMeal>(){
    override fun areItemsTheSame(oldItem: CategoryMeal, newItem: CategoryMeal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: CategoryMeal, newItem: CategoryMeal): Boolean {
        return (
                oldItem.strMeal == newItem.strMeal &&
                        oldItem.strMealThumb == newItem.strMealThumb
                )
    }
}