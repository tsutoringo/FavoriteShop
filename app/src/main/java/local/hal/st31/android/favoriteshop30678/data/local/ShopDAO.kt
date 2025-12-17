package local.hal.st31.android.favoriteshop30678.data.local

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class ShopDAO(
    private val db: SQLiteDatabase
) {
    companion object {
        fun shopFrom(cursor: Cursor): Shop {
            val idIndex = cursor.getColumnIndex("_id")
            val nameIndex = cursor.getColumnIndex("name")
            val telIndex = cursor.getColumnIndex("tel")
            val urlIndex = cursor.getColumnIndex("url")
            val noteIndex = cursor.getColumnIndex("note")

            val id = cursor.getLong(idIndex)
            val name = cursor.getString(nameIndex)
            val tel = cursor.getString(telIndex)
            val url = cursor.getString(urlIndex)
            val note = cursor.getString(noteIndex)

            return Shop(id, name, tel, url, note)
        }
    }

    fun findAll(): Cursor {
        val sql = "SELECT * FROM shops"
        val cursor = db.rawQuery(sql, null)

        return cursor
    }

    fun findById(id: Long): Shop? {
        val sql = "SELECT * FROM shops WHERE _id = ? LIMIT 1"
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))

        val shop: Shop? = if (cursor.moveToFirst()) {
            ShopDAO.shopFrom(cursor)
        } else {
            null
        }

        cursor.close()
        return shop
    }

    /**
     * ショップ情報を新規登録するメソッド。
     *
     * @param name 店名。
     * @param tel 電話番号。
     * @param url URL。
     * @param note メモ。
     * @return 登録したレコードの主キー値。
     */
    fun insert(name: String, tel: String, url: String, note: String): Long {
        Log.i("TAG", "insert: ")
        val sql = "INSERT INTO shops (name, tel, url, note) VALUES (?, ?, ?, ?)"
        val stmt = db.compileStatement(sql)
        stmt.bindString(1, name)
        stmt.bindString(2, tel)
        stmt.bindString(3, url)
        stmt.bindString(4, note)
        val insertedId = stmt.executeInsert()
        return insertedId
    }

    /**
     * ショップ情報を更新するメソッド。
     *
     * @param id 主キー値。
     * @param name 店名。
     * @param tel 電話番号。
     * @param url URL。
     * @param note メモ。
     * @return 更新件数。
     */
    fun update(id: Long, name: String, tel: String, url: String, note: String): Int {
        val sql = "UPDATE shops SET name = ?, tel = ?, url = ?, note = ? WHERE _id = ?"
        val stmt = db.compileStatement(sql)
        stmt.bindString(1, name)
        stmt.bindString(2, tel)
        stmt.bindString(3, url)
        stmt.bindString(4, note)
        stmt.bindLong(5, id)
        val result = stmt.executeUpdateDelete()
        return result
    }

    fun delete(id: Long) {
        val sql = "DELETE FROM shops WHERE _id = ?"
        val stmt = db.compileStatement(sql)
        stmt.bindLong(1, id)
        stmt.executeUpdateDelete()
    }
}