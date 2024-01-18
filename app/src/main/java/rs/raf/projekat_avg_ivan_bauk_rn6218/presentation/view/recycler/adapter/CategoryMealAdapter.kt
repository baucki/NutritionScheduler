package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.LayoutItemCategoryMealBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.diff.CategoryMealDiffCallback
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.viewholder.CategoryMealViewHolder


class CategoryMealAdapter : ListAdapter<CategoryMeal, CategoryMealViewHolder>(CategoryMealDiffCallback()) {

    interface OnCategoryMealItemClickListener {
        fun onCategoryMealItemClick(categoryMeal: CategoryMeal)
    }

    private var onCategoryMealItemClickListener: OnCategoryMealItemClickListener? = null

    fun setOnCategoryMealItemClickListener(listener: OnCategoryMealItemClickListener) {
        this.onCategoryMealItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        val itemBinding = LayoutItemCategoryMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMealViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        holder.bind(getItem(position))


        holder.itemView.setOnClickListener {
            onCategoryMealItemClickListener?.onCategoryMealItemClick(getItem(position))
        }
    }
}