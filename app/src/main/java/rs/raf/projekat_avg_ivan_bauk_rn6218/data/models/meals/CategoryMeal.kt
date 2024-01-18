package rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals

data class CategoryMeal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var strTags: String?,
    var strCalories: String
)