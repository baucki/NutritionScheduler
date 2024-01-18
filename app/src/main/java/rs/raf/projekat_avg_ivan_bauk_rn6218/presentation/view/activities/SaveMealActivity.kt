package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.MealDetail
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivitySaveMealBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.AddMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MenuViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SaveMealActivity : AppCompatActivity() {

    lateinit var datePickerDialog: DatePickerDialog
    lateinit var menuImagePath: String

    companion object {
        const val EXTRA_SAVE_MEAL_KEY = "saveMealKey"
        const val REQUEST_IMAGE_CAPTURE = 100
    }

    private val menuViewModel: MenuViewModel by viewModel<MenuViewModel>()

    private lateinit var binding: ActivitySaveMealBinding
    private var mealDetail: MealDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        loadIntent()
        initUi()
        initListeners()
        initObservers()
    }

    private fun loadIntent() {
        mealDetail = intent.getSerializableExtra(EXTRA_SAVE_MEAL_KEY) as? MealDetail
        menuImagePath = mealDetail!!.strMealThumb
    }

    private fun initUi() {
        Picasso.get()
            .load(mealDetail!!.strMealThumb)
            .fit()
            .into(binding.mealIv)
        binding.nameTv.text = mealDetail!!.strMeal
        initDatePicker()
        binding.datePickerBtn.text = getTodayDate()
    }

    private fun initListeners() {
        binding.mealIv.setOnClickListener {
            showCameraDialog()
        }

        binding.cancelBtn.setOnClickListener {
            finish()
        }

        binding.saveBtn.setOnClickListener {
            menuViewModel.insertMenu(
                Menu(
                    mealDetail!!.idMeal,
                    mealDetail!!.strMeal,
                    menuImagePath,
                    mealDetail!!.strCategory,
                    binding.datePickerBtn.text.toString(),
                    binding.categorySpinner.selectedItem.toString(),
                    mealDetail!!.strInstructions,
                    mealDetail!!.strYoutube,
                    mealDetail!!.strIngredient1,
                    mealDetail!!.strIngredient2,
                    mealDetail!!.strIngredient3,
                    mealDetail!!.strIngredient4,
                    mealDetail!!.strIngredient5,
                    mealDetail!!.strIngredient6,
                    mealDetail!!.strIngredient7,
                    mealDetail!!.strIngredient8,
                    mealDetail!!.strIngredient9,
                    mealDetail!!.strIngredient10,
                    mealDetail!!.strIngredient11,
                    mealDetail!!.strIngredient12,
                    mealDetail!!.strIngredient13,
                    mealDetail!!.strIngredient14,
                    mealDetail!!.strIngredient15,
                    mealDetail!!.strIngredient16,
                    mealDetail!!.strIngredient17,
                    mealDetail!!.strIngredient18,
                    mealDetail!!.strIngredient19,
                    mealDetail!!.strIngredient20,
                    mealDetail!!.strMeasure1,
                    mealDetail!!.strMeasure2,
                    mealDetail!!.strMeasure3,
                    mealDetail!!.strMeasure4,
                    mealDetail!!.strMeasure5,
                    mealDetail!!.strMeasure6,
                    mealDetail!!.strMeasure7,
                    mealDetail!!.strMeasure8,
                    mealDetail!!.strMeasure9,
                    mealDetail!!.strMeasure10,
                    mealDetail!!.strMeasure11,
                    mealDetail!!.strMeasure12,
                    mealDetail!!.strMeasure13,
                    mealDetail!!.strMeasure14,
                    mealDetail!!.strMeasure15,
                    mealDetail!!.strMeasure16,
                    mealDetail!!.strMeasure17,
                    mealDetail!!.strMeasure18,
                    mealDetail!!.strMeasure19,
                    mealDetail!!.strMeasure20,
                    mealDetail!!.strCalories1,
                    mealDetail!!.strCalories2,
                    mealDetail!!.strCalories3,
                    mealDetail!!.strCalories4,
                    mealDetail!!.strCalories5,
                    mealDetail!!.strCalories6,
                    mealDetail!!.strCalories7,
                    mealDetail!!.strCalories8,
                    mealDetail!!.strCalories9,
                    mealDetail!!.strCalories10,
                    mealDetail!!.strCalories11,
                    mealDetail!!.strCalories12,
                    mealDetail!!.strCalories13,
                    mealDetail!!.strCalories14,
                    mealDetail!!.strCalories15,
                    mealDetail!!.strCalories16,
                    mealDetail!!.strCalories17,
                    mealDetail!!.strCalories18,
                    mealDetail!!.strCalories19,
                    mealDetail!!.strCalories20,
                    mealDetail!!.strCalories
                )
            )
            menuViewModel.getAllMenus()
        }
    }

    private fun initObservers() {
        menuViewModel.addDone.observe(this, Observer {
            displaySuccessMessage(it)
        })
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date: String = makeDateString(day, month, year)
                binding.datePickerBtn.text = date
            }
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
    }

    private fun getTodayDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month = month + 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return if (day in 1 .. 9)
            getMonthFormat(month) + " 0" + day + " " + year
        else
            getMonthFormat(month) + " " + day + " " + year

    }

    private fun getMonthFormat(month: Int): String {
        return when (month) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "JAN"
        }
    }

    fun openDatePicker(view: View?) {
        datePickerDialog.show()
    }

    private fun showCameraDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Capture a new photo from the camera?")
        builder.setPositiveButton("Yes") { _, _ ->
            // Open the camera to capture a new image
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
            } else {
                // Camera permission is already granted; you can proceed to capture a photo
                dispatchTakePictureIntent()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted; you can proceed to capture a photo
                dispatchTakePictureIntent()
            } else {
                // Camera permission denied; handle this case (e.g., show a message to the user)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Get the captured image as a Bitmap
            val imageBitmap = data?.extras?.get("data") as Bitmap

            // Save the image to a file
            val savedImagePath = saveImageToInternalStorage(imageBitmap)
            menuImagePath = "file://$savedImagePath"

            // Load the saved image into mealIv using Picasso
            Picasso.get()
                .load(menuImagePath)
                .fit()
                .into(binding.mealIv)
        }
    }

    private fun saveImageToInternalStorage(image: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)

        // Create a directory to store images (you can specify a directory name)
        val file = wrapper.getDir("images", Context.MODE_PRIVATE)

        // Generate a unique file name for the image (e.g., timestamp)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_${timeStamp}.jpg"

        // Create a file object for the image
        val imageFile = File(file, imageFileName)

        // Save the image to the file
        try {
            val stream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the path to the saved image file
        return imageFile.absolutePath
    }

    private fun displaySuccessMessage(state: AddMenuState) {
        when (state) {
            is AddMenuState.Success ->
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            is AddMenuState.Error ->
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
        }
    }


}