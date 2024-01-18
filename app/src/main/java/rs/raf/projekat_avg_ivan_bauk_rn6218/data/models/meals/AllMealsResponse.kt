package rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllMealsResponse(
    val meals: List<MealResponse>?
)