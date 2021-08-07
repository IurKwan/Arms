package com.krbb.arms_push.source.local

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.krbb.arms_push.BaseSQL

/**
 * @author IurKwan
 * @date 2021/7/20
 */
class PushsDbSupportHelper(private val baseSQL: BaseSQL) : SupportSQLiteOpenHelper.Callback(baseSQL.version()){

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

    override fun onCreate(db: SupportSQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)

        baseSQL.onCreate(db)
    }

    override fun onUpgrade(db: SupportSQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        baseSQL.onUpgrade(db, oldVersion, newVersion)
    }
}