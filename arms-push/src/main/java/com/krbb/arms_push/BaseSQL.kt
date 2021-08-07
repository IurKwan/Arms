package com.krbb.arms_push

import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @author IurKwan
 * @date 12/27/20
 */
interface BaseSQL {

    fun name(): String

    fun version() : Int

    fun onCreate(db: SupportSQLiteDatabase?)

    fun onUpgrade(db: SupportSQLiteDatabase?, oldVersion: Int, newVersion: Int)

}