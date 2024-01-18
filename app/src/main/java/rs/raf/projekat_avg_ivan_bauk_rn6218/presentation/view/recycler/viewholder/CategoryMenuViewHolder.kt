package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.viewholder

import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.LayoutItemCategoryMenuBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter.CategoryMenuAdapter

class CategoryMenuViewHolder(private val itemBiding: LayoutItemCategoryMenuBinding,
                             private val elementClickListener: CategoryMenuAdapter.OnCategoryMenuItemElementClickListener?
) : RecyclerView.ViewHolder(itemBiding.root) {

    private val normalColor: Int = ContextCompat.getColor(itemView.context, R.color.unselectedColor)
    private val highlightColor: Int = ContextCompat.getColor(itemView.context, R.color.selectedColor)

    private var isHighlighted: Boolean = false

    fun bind(menu: Menu) {
        itemBiding.mealTv.text = menu.strMenu
        itemBiding.dateTv.text = menu.strDate
        itemBiding.caloriesTv.text = menu.strCalories
        Picasso.get()
            .load(menu.strMenuThumb)
            .into(itemBiding.mealIv)
        itemBiding.categoryMenuEditIv.setImageResource(R.drawable.baseline_edit_24)
        itemBiding.categoryMenuDeleteIv.setImageResource(R.drawable.baseline_delete_24)

        itemBiding.categoryMenuEditIv.setOnClickListener {
            elementClickListener!!.onCategoryMenuEditClick(menu)
        }

        itemBiding.categoryMenuDeleteIv.setOnClickListener {
            elementClickListener!!.onCategoryMenuDeleteClick(menu)
        }

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

        itemBiding.categoryMenuEditIv.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // User started touching the view
                    updateCategoryMenuEditStyle(true)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // User released the touch or gesture canceled
                    updateCategoryMenuEditStyle(false)
                }
            }
            false
        }

        itemBiding.categoryMenuDeleteIv.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // User started touching the view
                    updateCategoryMenuDeleteStyle(true)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // User released the touch or gesture canceled
                    updateCategoryMenuDeleteStyle(false)
                }
            }
            false
        }


    }

    private fun updateCategoryMenuEditStyle(isHighlighted: Boolean) {
        this.isHighlighted = isHighlighted
        if (isHighlighted) {
            itemBiding.categoryMenuEditIv.setBackgroundColor(highlightColor)
        } else {
            itemBiding.categoryMenuEditIv.setBackgroundColor(normalColor)
        }
    }

    private fun updateCategoryMenuDeleteStyle(isHighlighted: Boolean) {
        this.isHighlighted = isHighlighted
        if (isHighlighted) {
            itemBiding.categoryMenuDeleteIv.setBackgroundColor(highlightColor)
        } else {
            itemBiding.categoryMenuDeleteIv.setBackgroundColor(normalColor)
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