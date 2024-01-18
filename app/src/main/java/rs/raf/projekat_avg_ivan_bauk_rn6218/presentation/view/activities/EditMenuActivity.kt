package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
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
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivityEditMenuBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.MenuContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.AddMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MenuViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EDIT_MENU_KEY = "editMenuKey"
    }

    private val menuViewModel: MenuContract.ViewModel by viewModel<MenuViewModel>()

    private lateinit var binding: ActivityEditMenuBinding
    private var menu: Menu? = null
    lateinit var menuImagePath: String
    lateinit var datePickerDialog: DatePickerDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMenuBinding.inflate(layoutInflater)
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
        if (intent.extras != null) {
            menu = intent.getSerializableExtra(EXTRA_EDIT_MENU_KEY) as? Menu
            menuImagePath = menu!!.strMenuThumb
        }
    }

    private fun initUi() {
        Picasso.get()
            .load(menu!!.strMenuThumb)
            .fit()
            .into(binding.mealIv)
        binding.nameEdit.setText(menu!!.strMenu)
        binding.datePickerBtn.text = menu!!.strDate

        val spinner = binding.categorySpinner
        val categories = resources.getStringArray(R.array.menuCategory) // Replace with your array of categories
        val selectedItemPosition = categories.indexOf(menu!!.strMenuCategory) // Assuming menu.category represents the selected category

        if (selectedItemPosition != -1) {
            spinner.setSelection(selectedItemPosition)
        }
        initDatePicker()

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
                    menu!!.idMenu,
                    binding.nameEdit.text.toString(),
                    menuImagePath,
                    menu!!.strCategory,
                    binding.datePickerBtn.text.toString(),
                    binding.categorySpinner.selectedItem.toString(),
                    menu!!.strInstructions,
                    menu!!.strYoutube,
                    menu!!.strIngredient1,
                    menu!!.strIngredient2,
                    menu!!.strIngredient3,
                    menu!!.strIngredient4,
                    menu!!.strIngredient5,
                    menu!!.strIngredient6,
                    menu!!.strIngredient7,
                    menu!!.strIngredient8,
                    menu!!.strIngredient9,
                    menu!!.strIngredient10,
                    menu!!.strIngredient11,
                    menu!!.strIngredient12,
                    menu!!.strIngredient13,
                    menu!!.strIngredient14,
                    menu!!.strIngredient15,
                    menu!!.strIngredient16,
                    menu!!.strIngredient17,
                    menu!!.strIngredient18,
                    menu!!.strIngredient19,
                    menu!!.strIngredient20,
                    menu!!.strMeasure1,
                    menu!!.strMeasure2,
                    menu!!.strMeasure3,
                    menu!!.strMeasure4,
                    menu!!.strMeasure5,
                    menu!!.strMeasure6,
                    menu!!.strMeasure7,
                    menu!!.strMeasure8,
                    menu!!.strMeasure9,
                    menu!!.strMeasure10,
                    menu!!.strMeasure11,
                    menu!!.strMeasure12,
                    menu!!.strMeasure13,
                    menu!!.strMeasure14,
                    menu!!.strMeasure15,
                    menu!!.strMeasure16,
                    menu!!.strMeasure17,
                    menu!!.strMeasure18,
                    menu!!.strMeasure19,
                    menu!!.strMeasure20,
                    menu!!.strCalories1,
                    menu!!.strCalories2,
                    menu!!.strCalories3,
                    menu!!.strCalories4,
                    menu!!.strCalories5,
                    menu!!.strCalories6,
                    menu!!.strCalories7,
                    menu!!.strCalories8,
                    menu!!.strCalories9,
                    menu!!.strCalories10,
                    menu!!.strCalories11,
                    menu!!.strCalories12,
                    menu!!.strCalories13,
                    menu!!.strCalories14,
                    menu!!.strCalories15,
                    menu!!.strCalories16,
                    menu!!.strCalories17,
                    menu!!.strCalories18,
                    menu!!.strCalories19,
                    menu!!.strCalories20,
                    menu!!.strCalories
                )
            )
            menuViewModel.getAllMenus()
        }
    }

    private fun initDatePicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
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
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    SaveMealActivity.REQUEST_IMAGE_CAPTURE
                )
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
        startActivityForResult(takePictureIntent, SaveMealActivity.REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SaveMealActivity.REQUEST_IMAGE_CAPTURE) {
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
        if (requestCode == SaveMealActivity.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
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

    private fun initObservers() {
        menuViewModel.addDone.observe(this, Observer {
            displaySuccessMessage(it)
        })
    }

    private fun displaySuccessMessage(state: AddMenuState) {
        when (state) {
            is AddMenuState.Success ->
                Toast.makeText(this, "Menu has been updated", Toast.LENGTH_SHORT).show()
            is AddMenuState.Error ->
                Toast.makeText(this, "Menu has not been updated", Toast.LENGTH_SHORT).show()
        }
    }


}