package dev.bong.mobileprogramming.week05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.R
import java.util.*
import kotlin.collections.ArrayList

class VocabActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: VocabAdapter
    lateinit var tts: TextToSpeech
    var ttsReady = false
    val data: ArrayList<VocabData> = ArrayList<VocabData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocab)
        initData()
        initRecyclerView()
        initTTS()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ttsReady) {
            tts.stop()
            tts.shutdown()
        }
    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            ttsReady = true
            tts.language = Locale.US
        })
    }

    private fun initData() {
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(VocabData(word, meaning))
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = VocabAdapter(data)
        adapter.itemClickListener = object : VocabAdapter.OnItemClickListener {
            override fun OnItemClick(data: VocabData, position: Int) {
                if (ttsReady) {
                    tts.speak(data.word, TextToSpeech.QUEUE_FLUSH, null, null)
                }
                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
                data.isOpen = !data.isOpen
                adapter.notifyItemChanged(position)
            }
        }
        recyclerView.adapter = adapter

        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean {
                    adapter.moveItem(p1.adapterPosition, p2.adapterPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter.removeItem(viewHolder.adapterPosition)
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}