package rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.AllCategoriesResponse

interface CategoryService {

    @GET("categories.php")
    fun getAll(@Query("limit") limit: Int = 10): Observable<AllCategoriesResponse>

}