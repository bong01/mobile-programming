package dev.bong.mobileprogramming.week10

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bong.mobileprogramming.databinding.ActivityParsingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class ParsingActivity : AppCompatActivity() {

    lateinit var binding: ActivityParsingBinding
    lateinit var adapter: MyAdapter
    val url = "https://news.daum.net"
    val rssUrl = "http://fs.jtbc.joins.com//RSS/culture.xml"
    val jsonUrl = "http://api.icndb.com/jokes/random"
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParsingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getJson()
    }

    private fun getJson() {
        scope.launch {
            val doc = Jsoup.connect(jsonUrl).ignoreContentType(true).get()
            Log.i("Joke", doc.text())
            val json = JSONObject(doc.text())
            val joke = json.getJSONObject("value")
            val jokeStr = joke.getString("joke")
            Log.i("JokeStr", jokeStr)
        }
    }

    private fun getNews() {
        scope.launch {
            adapter.items.clear()
            /**
             * 다음 뉴스
             */
//            val doc = Jsoup.connect(url).get()
//            val headlines = doc.select("ul.list_newsissue>li>div.item_issue>div>strong.tit_g>a")
//
//            for (news in headlines) {
//                adapter.items.add(MyData(news.text(), news.absUrl("href")))
//            }

            /**
             * JTBC 뉴스
             */
            val doc = Jsoup.connect(rssUrl).parser(Parser.xmlParser()).get()
            val headlines = doc.select("item")

            for (news in headlines) {
                adapter.items.add(MyData(news.select("title").text(),
                news.select("link").text()))
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
            getNews()
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = MyAdapter(ArrayList<MyData>())
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun OnItemCLick(position: Int) {
                val intent = Intent(ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter = adapter
        getNews()
    }
}