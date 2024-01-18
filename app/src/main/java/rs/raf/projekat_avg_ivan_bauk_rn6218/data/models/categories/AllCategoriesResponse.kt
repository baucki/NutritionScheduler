package rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllCategoriesResponse(
    val categories: List<CategoryResponse>
)