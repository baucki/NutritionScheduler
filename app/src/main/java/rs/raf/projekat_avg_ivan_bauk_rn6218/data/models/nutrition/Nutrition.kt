package rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.nutrition


data class Nutrition(
    val name: String,
    val calories: Double,
    val servingSizeG: Double,
    val fatTotalG: Double,
    val fatSaturatedG: Double,
    val proteinG: Double,
    val sodiumMg: Double,
    val potassiumMg: Double,
    val cholesterolMg: Double,
    val carbohydratesTotalG: Double,
    val fiberG: Double,
    val sugarG: Double
)