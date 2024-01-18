package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu

class CategoryMenuDiffCallback : DiffUtil.ItemCallback<Menu>() {
    override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return oldItem.idMenu == newItem.idMenu
    }

    override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return (
                oldItem.idMenu == newItem.idMenu &&
                        oldItem.strMenuThumb == newItem.strMenuThumb
                )
    }
}