package com.krbb.arms_push.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.krbb.arms_push.BaseSQL

/**
 * @author IurKwan
 * @date 12/26/20
 */
class PushsDbHelper(
        context: Context,
        baseSQL: BaseSQL
) : SQLiteOpenHelper(
        context,
        baseSQL.name(),
        null,
        baseSQL.version()
) {

    private val mBaseSQL: BaseSQL = baseSQL

    companion object {

        private const val TEXT_TYPE = " TEXT"

        private const val BOOLEAN_TYPE = " INTEGER"

        private const val COMMA_SEP = ","

        private const val SQL_CREATE_ENTRIES =
                "CREATE TABLE [" + PushsPersistenceContract.PushEntry.TABLE_NAME + "] (" +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_ID}] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER}] ${TEXT_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGTYPE}] ${BOOLEAN_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_TITLE}] ${TEXT_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_CONTENT}] ${TEXT_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_TIME}] ${TEXT_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT}] ${BOOLEAN_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_PHOTOURL}] ${TEXT_TYPE}${COMMA_SEP} " +
                        "[${PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID}] ${TEXT_TYPE});"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(SQL_CREATE_ENTRIES)

//        mBaseSQL.onCreate(p0)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        mBaseSQL.onUpgrade(p0, p1, p2)
    }
}