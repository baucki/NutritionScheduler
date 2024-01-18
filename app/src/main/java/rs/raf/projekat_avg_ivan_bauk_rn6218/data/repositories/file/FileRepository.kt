package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.file

import android.content.Context
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.user.User
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileRepository {

    private val users: MutableList<User> = ArrayList()

    fun load(context: Context, path: String) {
        // Uncomment the code below to load data from a raw resource

        val listToReturn = ArrayList<User>()
        val inputStream = context.resources.openRawResource(R.raw.users)
        try {
            val br = BufferedReader(InputStreamReader(inputStream))
            var line: String? = ""
            line = br.readLine()
            while (line != null) {
                val parts = line.split("\\|".toRegex()).toTypedArray()
                if (parts.size >= 3) {
                    val username = parts[0]
                    val password = parts[1]
                    val weight = parts[2]
                    val height = parts[3]
                    val user = User(username, password, weight, height)
                    listToReturn.add(user)
                }
                line = br.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        users.addAll(listToReturn)
    }

    fun findUser(username: String, password: String) : User? {
        for (user in users) {
            if (user.username == username && user.password == password) {
                return user
            }
        }
        return null
    }

}
