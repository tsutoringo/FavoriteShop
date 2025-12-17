package local.hal.st31.android.favoriteshop30678.data.repository

import android.database.Cursor
import local.hal.st31.android.favoriteshop30678.data.local.DatabaseHelper
import local.hal.st31.android.favoriteshop30678.MODE_INSERT
import local.hal.st31.android.favoriteshop30678.data.local.Shop
import local.hal.st31.android.favoriteshop30678.data.local.ShopDAO

class ShopRepository(
    private val databaseHelper: DatabaseHelper
) {
    fun findAll(): Cursor {
        val db = databaseHelper.writableDatabase
        val dao = ShopDAO(db)
        return dao.findAll()
    }

    fun getById(id: Long): Shop? {
        val db = databaseHelper.writableDatabase
        val dao = ShopDAO(db)
        return dao.findById(id)
    }

    fun saveShop(mode: Int, id: Long, name: String, tel: String, url: String, note: String): Boolean {
        val db = databaseHelper.writableDatabase
        val memoDAO = ShopDAO(db)
        var result = false
        if(mode == MODE_INSERT) {
            val insertedId = memoDAO.insert(name, tel, url, note)
            if(insertedId >= 1) {
                result = true
            }
        } else {
            val updateResult = memoDAO.update(id, name, tel, url, note)
            if(updateResult == 1) {
                result = true
            }
        }
        return result
    }

    fun deleteShop(id: Long) {
        val db = databaseHelper.writableDatabase
        val memoDAO = ShopDAO(db)

        memoDAO.delete(id)
    }
}