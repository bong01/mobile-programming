package dev.bong.mobileprogramming.week11

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView

class MyDBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "mydb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "products"
        val PID = "pid"
        val PNAME = "pname"
        val PQUANTITY = "pquantity"
    }

    fun getAllRecords() {
        val strSql = "select * from $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(strSql, null)

        showRecords(cursor)

        cursor.close()
        db.close()
    }

    private fun showRecords(cursor: Cursor) {
        cursor.moveToFirst()

        val attrCount = cursor.columnCount
        val activity = context as DbActivity

        activity.binding.tableLayout.removeAllViewsInLayout()

        //타이틀 만들기
        val tableRow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)

        tableRow.layoutParams = rowParam
        val viewParam = TableRow.LayoutParams(0, 100, 1f)

        for (i in 0 until attrCount) {
            val textView = TextView(activity)
            textView.layoutParams = viewParam
            textView.text = cursor.getColumnName(i)
            textView.setBackgroundColor(Color.LTGRAY)
            textView.textSize = 15.0f
            textView.gravity = Gravity.CENTER
            tableRow.addView(textView)
        }

        activity.binding.tableLayout.addView(tableRow)

        if (cursor.count == 0) {
            return
        }

        //레코드 추가하기
        do {
            val row = TableRow(activity)
            row.layoutParams = rowParam

            row.setOnClickListener {
                for (i in 0 until attrCount) {
                    val textView = row.getChildAt(i) as TextView
                    when (textView.tag) {
                        0 -> activity.binding.pIdEdit.setText(textView.text)
                        1 -> activity.binding.pNameEdit.setText(textView.text)
                        2 -> activity.binding.pQuantityEdit.setText(textView.text)
                    }
                }
            }

            for (i in 0 until attrCount) {
                val textView = TextView(activity)
                textView.tag = i
                textView.layoutParams = viewParam
                textView.text = cursor.getString(i)
                textView.textSize = 13.0f
                textView.gravity = Gravity.CENTER
                row.addView(textView)
            }

            activity.binding.tableLayout.addView(row)
        } while (cursor.moveToNext())
    }

    fun insertProduct(product: Product): Boolean {
        val values = ContentValues()
        values.put(PNAME, product.pName)
        values.put(PQUANTITY, product.pQuantity)
        val db = writableDatabase
        val result = db.insert(TABLE_NAME, null, values) > 0

        db.close()

        return result
    }

    fun findProduct(name: String): Boolean {
        val strSql = "select * from $TABLE_NAME where $PNAME = '$name';"
        val db = readableDatabase
        val cursor = db.rawQuery(strSql, null)
        val result = cursor.count != 0

        showRecords(cursor)

        cursor.close()
        db.close()

        return result
    }

    fun deleteProduct(pid: String): Boolean {
        val strSql = "select * from $TABLE_NAME where $PID = '$pid';"
        val db = writableDatabase
        val cursor = db.rawQuery(strSql, null)
        val result = cursor.count != 0
        if (result) {
            cursor.moveToFirst()
            db.delete(TABLE_NAME, "$PID = ?", arrayOf(pid))
        }

        cursor.close()
        db.close()

        return result
    }

    fun updateProduct(product: Product): Boolean {
        val pid = product.pId
        val strSql = "select * from $TABLE_NAME where $PID = '$pid';"
        val db = writableDatabase
        val cursor = db.rawQuery(strSql, null)
        val result = cursor.count != 0
        if (result) {
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(PNAME, product.pName)
            values.put(PQUANTITY, product.pQuantity)
            db.update(TABLE_NAME, values, "$PID = ?", arrayOf(pid.toString()))
        }

        cursor.close()
        db.close()

        return result
    }

    fun findProduct2(name: String): Boolean {
        val strSql = "select * from $TABLE_NAME where $PNAME like'$name%';"
        val db = readableDatabase
        val cursor = db.rawQuery(strSql, null)
        val result = cursor.count != 0

        showRecords(cursor)

        cursor.close()
        db.close()

        return result
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME(" +
                "$PID integer primary key autoincrement, " +
                "$PNAME text, " +
                "$PQUANTITY integer);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}