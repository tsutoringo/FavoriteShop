package local.hal.st31.android.favoriteshop30678.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import local.hal.st31.android.favoriteshop30678.DATABASE_NAME
import local.hal.st31.android.favoriteshop30678.DATABASE_VERSION

/**
 * ST31 Androidサンプル10　メモ帳アプリ
 *
 * データベースヘルパークラス。
 *
 * @author Shinzo SAITO
 *
 * @param context コンテキスト。
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql = StringBuilder()
            .append("CREATE TABLE shops (")
            .append("  _id INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append("  name TEXT NOT NULL,")
            .append("  tel TEXT,")
            .append("  url TEXT,")
            .append("  note TEXT")
            .append(");")
            .toString()
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}