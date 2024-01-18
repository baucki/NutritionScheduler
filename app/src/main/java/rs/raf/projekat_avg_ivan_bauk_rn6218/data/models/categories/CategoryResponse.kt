package rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val idCategory: Long,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)