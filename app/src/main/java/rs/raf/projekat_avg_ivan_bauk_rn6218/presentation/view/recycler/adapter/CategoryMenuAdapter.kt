package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.LayoutItemCategoryMenuBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.diff.CategoryMenuDiffCallback
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.viewholder.CategoryMenuViewHolder

class CategoryMenuAdapter : ListAdapter<Menu, CategoryMenuViewHolder>(CategoryMenuDiffCallback()) {

    interface OnCategoryMenuItemClickListener {
        fun onCategoryMenuItemClick(menu: Menu)
    }

    interface OnCategoryMenuItemElementClickListener {
        fun onCategoryMenuDeleteClick(menu: Menu)
        fun onCategoryMenuEditClick(menu: Menu)
    }

    private var onCategoryMenuItemClickListener: OnCategoryMenuItemClickListener? = null
    private var onCategoryMenuItemElementClickListener: OnCategoryMenuItemElementClickListener? = null


    fun setOnCategoryMenuItemClickListener(listener: OnCategoryMenuItemClickListener) {
        this.onCategoryMenuItemClickListener = listener
    }

    fun setOnCategoryMenuItemElementClickListener(listener: OnCategoryMenuItemElementClickListener) {
        this.onCategoryMenuItemElementClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMenuViewHolder {
        val itemBinding = LayoutItemCategoryMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMenuViewHolder(itemBinding, onCategoryMenuItemElementClickListener)
    }

    override fun onBindViewHolder(holder: CategoryMenuViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            onCategoryMenuItemClickListener?.onCategoryMenuItemClick(getItem(position))
        }
    }


}