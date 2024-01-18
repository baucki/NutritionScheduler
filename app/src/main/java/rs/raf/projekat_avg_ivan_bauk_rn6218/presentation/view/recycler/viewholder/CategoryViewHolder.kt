package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.viewholder

import android.app.AlertDialog
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.LayoutItemCategoryBinding

class CategoryViewHolder(private val itemBinding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    private val normalColor: Int = ContextCompat.getColor(itemView.context, R.color.unselectedColor)
    private val highlightColor: Int = ContextCompat.getColor(itemView.context, R.color.selectedColor)

    private var isHighlighted: Boolean = false

    fun bind(category: Category) {
        itemBinding.categoryTv.text = category.strCategory
        Picasso.get()
            .load(category.strCategoryThumb)
            .into(itemBinding.categoryIv)
        itemBinding.categoryDescriptionIv.setImageResource(R.drawable.baseline_more_vert_24)

        itemBinding.categoryDescriptionIv.setOnClickListener {
            openPopup(category)
        }

        itemBinding.categoryDescriptionIv.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // User started touching the view
                    updateCategoryDescriptionStyle(true)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // User released the touch or gesture canceled
                    updateCategoryDescriptionStyle(false)
                }
            }
            false
        }

        itemBinding.categoryBoxFl.setOnTouchListener { _, event ->
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

    private fun updateCategoryDescriptionStyle(isHighlighted: Boolean) {
        this.isHighlighted = isHighlighted
        if (isHighlighted) {
            itemBinding.categoryDescriptionIv.setBackgroundColor(highlightColor)
        } else {
            itemBinding.categoryDescriptionIv.setBackgroundColor(normalColor)
        }
    }

    private fun updateCategoryBoxStyle(isHighlighted: Boolean) {
        this.isHighlighted = isHighlighted
        if (isHighlighted) {
            itemBinding.categoryBoxFl.setBackgroundColor(highlightColor)
        } else {
            itemBinding.categoryBoxFl.setBackgroundResource(R.drawable.category_box_background)
        }
    }

    private fun openPopup(category: Category) {
        if (!isHighlighted) {
            // Handle categoryDescriptionIv click event
            // Show a popup or dialog
            val dialog = AlertDialog.Builder(itemView.context)
                .setTitle(category.strCategory)
                .setMessage(category.strCategoryDescription)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }
    }




}