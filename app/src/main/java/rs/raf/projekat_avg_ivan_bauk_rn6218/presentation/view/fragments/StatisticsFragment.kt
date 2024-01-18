package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.FragmentStatisticsBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.MenuContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities.MainActivity
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.MenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MenuViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val menuViewModel: MenuContract.ViewModel by sharedViewModel<MenuViewModel>()

    private var _binding: FragmentStatisticsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initObservers()
        initUi()
    }

    private fun initUi() {
        val entries: MutableList<BarEntry> = mutableListOf()
        entries.add(BarEntry(1f, 0f))
        entries.add(BarEntry(2f, 0f))
        entries.add(BarEntry(3f, 0f))
        entries.add(BarEntry(4f, 0f))
        entries.add(BarEntry(5f, 0f))
        entries.add(BarEntry(6f, 0f))
        entries.add(BarEntry(7f, 0f))

        val dataSet = BarDataSet(entries, "Calories per day")
        dataSet.color = Color.BLUE // Customize bar colors, if needed

        val barData = BarData(dataSet)

        binding.calorieBarChart.axisLeft.setDrawGridLines(false) // Y-axis
        binding.calorieBarChart.xAxis.setDrawGridLines(false)     // X-axis
        binding.calorieBarChart.description.text = ""


        binding.calorieBarChart.data = barData
        binding.calorieBarChart.invalidate()

        val secondEntries: MutableList<BarEntry> = mutableListOf()
        secondEntries.add(BarEntry(1f, 0f))
        secondEntries.add(BarEntry(2f, 0f))
        secondEntries.add(BarEntry(3f, 0f))
        secondEntries.add(BarEntry(4f, 0f))
        secondEntries.add(BarEntry(5f, 0f))
        secondEntries.add(BarEntry(6f, 0f))
        secondEntries.add(BarEntry(7f, 0f))

        val secondDataSet = BarDataSet(secondEntries, "Number of meals")
        secondDataSet.color = Color.RED

        val secondBarData = BarData(secondDataSet)

        binding.mealCountBarChart.axisLeft.setDrawGridLines(false) // Y-axis
        binding.mealCountBarChart.xAxis.setDrawGridLines(false)     // X-axis
        binding.mealCountBarChart.description.text = ""

        binding.mealCountBarChart.data = secondBarData
        binding.mealCountBarChart.invalidate()

    }

    private fun initObservers() {
        menuViewModel.menuState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
        menuViewModel.getAllMenus()
    }

    private fun renderState(state: MenuState) {
        when (state) {
            is MenuState.Success -> {
                updateCharts(state.menus)
            }
            is MenuState.Error -> {

            }
        }
    }

    private fun updateCharts(menus: List<Menu>) {
        // Create new lists of BarEntry objects with updated data
        val calorieEntries = mutableListOf<BarEntry>()
        val mealCountEntries = mutableListOf<BarEntry>()

        val dates = pastSevenDays()
        val numberOfMeals = MutableList(7) { 0 }
        val caloriesOfMeals = MutableList(7) { 0 }

        for (menu in menus) {
            for (date in dates) {
                Timber.e(date)
                if (menu.strDate == date) {
                    val index = dates.indexOf(date)
                    numberOfMeals[index]++
                    caloriesOfMeals[index] += menu.strCalories!!.toInt()
                }
            }
            // Add menu data to the appropriate lists
        }

        for (i in 1 .. 7) {
            mealCountEntries.add(BarEntry(i.toFloat(), numberOfMeals[i-1].toFloat()))
            if (caloriesOfMeals[i-1].toFloat() > MainActivity.UserProperties.calories.toFloat()) {
                val surplus = caloriesOfMeals[i-1].toFloat() - MainActivity.UserProperties.calories.toFloat()
                val floatArray = floatArrayOf(MainActivity.UserProperties.calories.toFloat(), surplus)
                calorieEntries.add(BarEntry(i.toFloat(), floatArray))
            } else {
                val floatArray = floatArrayOf(caloriesOfMeals[i-1].toFloat(), 0.0f)
                calorieEntries.add(BarEntry(i.toFloat(), floatArray))
            }
        }

        val colors = listOf(Color.GREEN, Color.RED)

        // Create BarDataSet objects with the updated data
        val calorieDataSet = BarDataSet(calorieEntries, "Calories per day")
        calorieDataSet.colors = colors

        val mealCountDataSet = BarDataSet(mealCountEntries, "Number of meals")
        mealCountDataSet.color = Color.BLUE

        // Create BarData objects with the updated BarDataSet objects
        val calorieBarData = BarData(calorieDataSet)
        val mealCountBarData = BarData(mealCountDataSet)

        // Update the chart data
        binding.calorieBarChart.data = calorieBarData
        binding.mealCountBarChart.data = mealCountBarData

        // Notify the chart that the data has changed and refresh the chart appearance
        binding.calorieBarChart.data.notifyDataChanged()
        binding.calorieBarChart.notifyDataSetChanged()
        binding.calorieBarChart.invalidate()

        binding.mealCountBarChart.data.notifyDataChanged()
        binding.mealCountBarChart.notifyDataSetChanged()
        binding.mealCountBarChart.invalidate()
    }

    fun pastSevenDays(): List<String> {
        val dates = mutableListOf<String>()
        val calendar = Calendar.getInstance()

        // Iterate through the past 7 days
        for (i in 0 until 7) {
            val date = calendar.time
            val formattedDate = formatDate(date)
            dates.add(formattedDate)
            calendar.add(Calendar.DAY_OF_MONTH, -1) // Move one day back
        }

        return dates.reversed() // Reverse the list to get dates in ascending order
    }

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
        return dateFormat.format(date).toUpperCase(Locale.US)
    }

}
