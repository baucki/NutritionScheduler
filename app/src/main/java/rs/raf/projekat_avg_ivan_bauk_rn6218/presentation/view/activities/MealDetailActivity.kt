package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.MealDetail
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivityMealDetailBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MealViewModel


class MealDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY_MEAL_KEY = "categoryMealKey"
    }

    private val mealViewModel: MealViewModel by viewModel<MealViewModel>()

    private lateinit var binding: ActivityMealDetailBinding
    private var mealDetail: MealDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        loadIntent()
        initUi()
        initListeners()
    }

    private fun loadIntent() {
        mealDetail = intent.getSerializableExtra(EXTRA_CATEGORY_MEAL_KEY) as? MealDetail
    }

    private fun initUi() {
        if (mealDetail != null) {
            Picasso.get()
                .load(mealDetail!!.strMealThumb)
                .fit()
                .into(binding.mealThumbIv)

            binding.mealTv.text = mealDetail!!.strMeal
            binding.caloriesTv.text = mealDetail!!.strCalories
            binding.tagsTv.text = mealDetail?.strTags?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strTags } ?: "Not Found"
            binding.areaTv.text = mealDetail?.strArea?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strArea } ?: "Not Found"
            binding.categoryTv.text = mealDetail?.strCategory?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCategory } ?: "Not Found"
            binding.instructionsTv.text = mealDetail?.strInstructions?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strInstructions } ?: "Not Found"

            val spannableString = SpannableString(mealDetail!!.strYoutube)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val youtubeUri = Uri.parse(mealDetail!!.strYoutube)
                    val intent = Intent(Intent.ACTION_VIEW, youtubeUri)
                    startActivity(intent)
                }
            }
            spannableString.setSpan(clickableSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.youtubeTv.text = mealDetail?.strYoutube?.takeIf { it.isNotEmpty() }?.let { spannableString } ?: "Not Found"
            binding.youtubeTv.movementMethod = LinkMovementMethod.getInstance()

            binding.ingredients1Tv.text = mealDetail!!.strIngredient1
            binding.measures1Tv.text = mealDetail!!.strMeasure1
            binding.Calories1Tv.text = mealDetail?.strIngredient1?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories1 } ?: ""

            binding.ingredients2Tv.text = mealDetail!!.strIngredient2
            binding.measures2Tv.text = mealDetail!!.strMeasure2
            binding.Calories2Tv.text = mealDetail?.strIngredient2?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories2 } ?: ""

            binding.ingredients3Tv.text = mealDetail!!.strIngredient3
            binding.measures3Tv.text = mealDetail!!.strMeasure3
            binding.Calories3Tv.text = mealDetail?.strIngredient3?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories3 } ?: ""

            binding.ingredients4Tv.text = mealDetail!!.strIngredient4
            binding.measures4Tv.text = mealDetail!!.strMeasure4
            binding.Calories4Tv.text = mealDetail?.strIngredient4?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories4 } ?: ""

            binding.ingredients5Tv.text = mealDetail!!.strIngredient5
            binding.measures5Tv.text = mealDetail!!.strMeasure5
            binding.Calories5Tv.text = mealDetail?.strIngredient5?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories5 } ?: ""

            binding.ingredients6Tv.text = mealDetail!!.strIngredient6
            binding.measures6Tv.text = mealDetail!!.strMeasure6
            binding.Calories6Tv.text = mealDetail?.strIngredient6?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories6 } ?: ""

            binding.ingredients7Tv.text = mealDetail!!.strIngredient7
            binding.measures7Tv.text = mealDetail!!.strMeasure7
            binding.Calories7Tv.text = mealDetail?.strIngredient7?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories7 } ?: ""

            binding.ingredients8Tv.text = mealDetail!!.strIngredient8
            binding.measures8Tv.text = mealDetail!!.strMeasure8
            binding.Calories8Tv.text = mealDetail?.strIngredient8?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories8 } ?: ""

            binding.ingredients9Tv.text = mealDetail!!.strIngredient9
            binding.measures9Tv.text = mealDetail!!.strMeasure9
            binding.Calories9Tv.text = mealDetail?.strIngredient9?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories9 } ?: ""

            binding.ingredients10Tv.text = mealDetail!!.strIngredient10
            binding.measures10Tv.text = mealDetail!!.strMeasure10
            binding.Calories10Tv.text = mealDetail?.strIngredient10?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories10 } ?: ""

            binding.ingredients11Tv.text = mealDetail!!.strIngredient11
            binding.measures11Tv.text = mealDetail!!.strMeasure11
            binding.Calories11Tv.text = mealDetail?.strIngredient11?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories11 } ?: ""

            binding.ingredients12Tv.text = mealDetail!!.strIngredient12
            binding.measures12Tv.text = mealDetail!!.strMeasure12
            binding.Calories12Tv.text = mealDetail?.strIngredient12?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories12 } ?: ""

            binding.ingredients13Tv.text = mealDetail!!.strIngredient13
            binding.measures13Tv.text = mealDetail!!.strMeasure13
            binding.Calories13Tv.text = mealDetail?.strIngredient13?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories13 } ?: ""

            binding.ingredients14Tv.text = mealDetail!!.strIngredient14
            binding.measures14Tv.text = mealDetail!!.strMeasure14
            binding.Calories14Tv.text = mealDetail?.strIngredient14?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories14 } ?: ""

            binding.ingredients15Tv.text = mealDetail!!.strIngredient15
            binding.measures15Tv.text = mealDetail!!.strMeasure15
            binding.Calories15Tv.text = mealDetail?.strIngredient15?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories15 } ?: ""

            binding.ingredients16Tv.text = mealDetail!!.strIngredient16
            binding.measures16Tv.text = mealDetail!!.strMeasure16
            binding.Calories16Tv.text = mealDetail?.strIngredient16?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories16 } ?: ""

            binding.ingredients17Tv.text = mealDetail!!.strIngredient17
            binding.measures17Tv.text = mealDetail!!.strMeasure17
            binding.Calories17Tv.text = mealDetail?.strIngredient17?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories17 } ?: ""

            binding.ingredients18Tv.text = mealDetail!!.strIngredient18
            binding.measures18Tv.text = mealDetail!!.strMeasure18
            binding.Calories18Tv.text = mealDetail?.strIngredient18?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories18 } ?: ""

            binding.ingredients19Tv.text = mealDetail!!.strIngredient19
            binding.measures19Tv.text = mealDetail!!.strMeasure19
            binding.Calories19Tv.text = mealDetail?.strIngredient19?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories19 } ?: ""

            binding.ingredients20Tv.text = mealDetail!!.strIngredient20
            binding.measures20Tv.text = mealDetail!!.strMeasure20
            binding.Calories20Tv.text = mealDetail?.strIngredient20?.takeIf { it.isNotEmpty() }?.let { mealDetail!!.strCalories20 } ?: ""

        }
    }

    private fun initListeners() {
        binding.cancelBtn.setOnClickListener {
            finish()
        }

        binding.addToMenuBtn.setOnClickListener {
            val intent = Intent(this, SaveMealActivity::class.java)

            intent.putExtra(SaveMealActivity.EXTRA_SAVE_MEAL_KEY, mealDetail)
            startActivity(intent)
        }
    }

}