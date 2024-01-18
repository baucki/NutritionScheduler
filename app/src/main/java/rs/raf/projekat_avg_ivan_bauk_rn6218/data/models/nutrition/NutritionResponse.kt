package rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.nutrition
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NutritionResponse (
    val name: String,
    val calories: Double,
    val serving_size_g: Double,
    val fat_total_g: Double,
    val fat_saturated_g: Double,
    val protein_g: Double,
    val sodium_mg: Double,
    val potassium_mg: Double,
    val cholesterol_mg: Double,
    val carbohydrates_total_g: Double,
    val fiber_g: Double,
    val sugar_g: Double
)
