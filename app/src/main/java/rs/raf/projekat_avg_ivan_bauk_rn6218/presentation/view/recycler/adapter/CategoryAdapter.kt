package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.LayoutItemCategoryBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    interface OnCategoryItemClickListener {
        fun onCategoryItemClick(category: Category)
    }

    private var onCategoryItemClickListener: OnCategoryItemClickListener? = null

    fun setOnCategoryItemClickListener(listener: OnCategoryItemClickListener) {
        this.onCategoryItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))

        // Set click listener on the item view
        holder.itemView.setOnClickListener {
            onCategoryItemClickListener?.onCategoryItemClick(getItem(position))
        }
    }

}