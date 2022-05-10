package dev.bong.mobileprogramming.week10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bong.mobileprogramming.databinding.ActivityMovieDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MovieDataActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDataBinding
    lateinit var adapter: MovieAdapter
    val url = "http://www.cgv.co.kr/movies"
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun getMovies() {
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(url).get()
            val titleElements = doc.select("div.box-contents strong.title")
            val rateElements = doc.select("div.box-contents strong.percent")

            for (i in 0 until titleElements.size) {
                val title = titleElements[i].text()
                val rate = rateElements[i].text().replace("예매율", "")
                adapter.items.add(MovieData(i + 1, title, rate))
            }

            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }

    private fun init() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getMovies()
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = MovieAdapter(ArrayList())
        binding.recyclerView.adapter = adapter
        getMovies()
    }
}