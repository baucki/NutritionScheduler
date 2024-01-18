package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return (
            oldItem.strCategory == newItem.strCategory &&
                    oldItem.strCategoryThumb == newItem.strCategoryThumb &&
                    oldItem.strCategoryDescription == newItem.strCategoryDescription
        )
    }
}