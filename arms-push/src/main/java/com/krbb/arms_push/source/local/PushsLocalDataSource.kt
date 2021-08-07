package com.krbb.arms_push.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import android.util.Log
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.krbb.arms_push.BaseSQL
import com.krbb.arms_push.Push
import com.krbb.arms_push.source.PushsDataSource
import com.krbb.arms_push.util.TimeConstants
import com.krbb.arms_push.util.TimeUtil
import com.squareup.sqlbrite3.BriteDatabase
import com.squareup.sqlbrite3.SqlBrite
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author IurKwan
 * @date 12/29/20
 */
class PushsLocalDataSource(
    context: Context,
    baseSQL: BaseSQL
) : PushsDataSource {

    private var mDatabaseHelper: BriteDatabase
    private val mPushMapperFunction: Function<Cursor, Push>

    init {
        val dbHelper = PushsDbHelper(context, baseSQL)
        val sqlBrite = SqlBrite.Builder().build()

        val configuration = SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(baseSQL.name())
            .callback(PushsDbSupportHelper(baseSQL))
            .build()

        val factory = FrameworkSQLiteOpenHelperFactory()
        val helper = factory.create(configuration)

        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io())
        // 开启日志
        mDatabaseHelper.setLoggingEnabled(true)

        mPushMapperFunction = Function { c: Cursor ->
            this.getPush(c)
        }
    }

    companion object {

        const val TAG = "PushsLocalDataSource"

        private var INSTANCE: PushsLocalDataSource? = null

        @JvmStatic
        fun getInstance(
            context: Context,
            baseSQL: BaseSQL
        ): PushsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(PushsLocalDataSource::class.java) {
                    INSTANCE = PushsLocalDataSource(context, baseSQL)
                }
            }
            return INSTANCE!!
        }
    }

    override fun getPushs(userId: Int): Flowable<MutableList<Push>> {
        val projection = arrayOf(
            PushsPersistenceContract.PushEntry.COLUMN_NAME_ID,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGTYPE,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TITLE,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_CONTENT,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TIME,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_PHOTOURL,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID
        )
        val sql = String.format(
            "SELECT %s FROM %s WHERE %s LIKE ?",
            TextUtils.join(",", projection),
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER
        )
        return mDatabaseHelper.createQuery(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            sql,
            userId.toString()
        )
            .mapToList(mPushMapperFunction)
            .toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun getPush(c: Cursor): Push {
        val id =
            c.getInt(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_ID))
        val msgType =
            c.getInt(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGTYPE))
        val title =
            c.getString(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_TITLE))
        val content =
            c.getString(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_CONTENT))
        val time =
            c.getString(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_TIME))
        val count =
            c.getInt(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT))
        val photoUrl: String? =
            c.getString(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_PHOTOURL))
        val tsid =
            c.getString(c.getColumnIndexOrThrow(PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID))
        return Push(
            _id = id,
            msgType = msgType,
            count = count,
            title = title,
            photoUrl = photoUrl,
            time = time,
            content = content,
            tsid = tsid
        )
    }

    override fun refreshPushs() {
        // 无需处理
    }

    override fun savePush(userId: Int, push: Push, coverWithType: Boolean) {
        // 兼容旧版本，需要删除同样类型数据，不能直接覆盖数据
        // 判断是否重复添加，tsId是唯一的
        val projection = arrayOf(
            PushsPersistenceContract.PushEntry.COLUMN_NAME_ID,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGTYPE,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TITLE,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_CONTENT,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TIME,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_PHOTOURL,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID
        )
        val sql = String.format(
            "SELECT %s FROM %s WHERE %s LIKE ?",
            TextUtils.join(",", projection),
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID
        )
        val cursor = mDatabaseHelper.query(sql, push.tsid)
        cursor?.let {
            if (!it.moveToFirst()) {
                Log.d(TAG, "推送-数据不存在，可以添加")
                if (coverWithType) {
                    // 覆盖同类型的推送
                    // 先删除再添加
                    Log.d(TAG, "推送-删除同类型")
                    val selection =
                        PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER + " LIKE ? AND " +
                                PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGTYPE + " LIKE ?"
                    val selectionArgs = arrayOf(userId.toString(), push.msgType.toString())
                    val row = mDatabaseHelper.delete(
                        PushsPersistenceContract.PushEntry.TABLE_NAME,
                        selection,
                        *selectionArgs
                    )
                    if (row > 0) {
                        Log.d(TAG, "推送-删除成功")
                    } else {
                        Log.d(TAG, "推送-删除失败")
                    }
                }

                savePush(userId, push)

            } else {
                Log.d(TAG, "推送-TsId已经存在，更新")
                // 覆盖同类型的推送
                // 先删除再添加
                Log.d(TAG, "推送-删除同类型")
                val selection =
                    PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER + " LIKE ? AND " +
                            PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID + " LIKE ?"
                val selectionArgs = arrayOf(userId.toString(), push.tsid)
                val row = mDatabaseHelper.delete(
                    PushsPersistenceContract.PushEntry.TABLE_NAME,
                    selection,
                    *selectionArgs
                )
                if (row > 0) {
                    Log.d(TAG, "推送-删除成功")
                } else {
                    Log.d(TAG, "推送-删除失败")
                }

                savePush(userId, push)
            }
            // 关闭
            it.close()
        }
    }

    override fun savePush(userId: Int, push: Push) {
        val values = ContentValues()
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER, userId)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGTYPE, push.msgType)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_TITLE, push.title)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_CONTENT, push.content)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_TIME, push.time)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT, push.count)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_PHOTOURL, push.photoUrl)
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID, push.tsid)
        val id = mDatabaseHelper.insert(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            SQLiteDatabase.CONFLICT_REPLACE,
            values
        )
        if (id != (-1).toLong()) {
            Log.d(TAG, "推送-添加推送成功")
        } else {
            Log.d(TAG, "推送-添加推送失败")
        }
    }

    override fun readPush(push: Push) {
        val values = ContentValues()
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT, 0)

        val selection = PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID + " LIKE ?"
        val selectionArgs = arrayOf(push.tsid)
        mDatabaseHelper.update(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            SQLiteDatabase.CONFLICT_REPLACE,
            values,
            selection,
            *selectionArgs
        )
    }

    override fun readAllPush(userId: Int) {
        val values = ContentValues()
        values.put(PushsPersistenceContract.PushEntry.COLUMN_NAME_COUNT, 0)

        val selection = PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER + " LIKE ?"
        val selectionArgs = arrayOf(userId.toString())
        mDatabaseHelper.update(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            SQLiteDatabase.CONFLICT_REPLACE,
            values,
            selection,
            *selectionArgs
        )
    }

    override fun deletePush(push: Push) {
        val selection = PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER + " LIKE ?"
        val selectionArgs = arrayOf(push._id.toString())
        mDatabaseHelper.delete(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            selection,
            *selectionArgs
        )
    }

    override fun deletePushByTsId(tsId: String) {
        val selection = "${PushsPersistenceContract.PushEntry.COLUMN_NAME_TSID} LIKE ?"
        val selectionArgs = arrayOf(tsId)
        mDatabaseHelper.delete(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            selection,
            *selectionArgs
        )
    }

    override fun deleteDaysPush(userId: Int, days: Int) {
        val daysFormat = TimeUtil.getStringByNow(-days.toLong(), TimeConstants.DAY)

        val selection =
            "${PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER} LIKE ? AND ${PushsPersistenceContract.PushEntry.COLUMN_NAME_TIME} < ?"
        val selectionArgs = arrayOf(userId.toString(), daysFormat)
        mDatabaseHelper.delete(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            selection,
            *selectionArgs
        )
    }

    override fun deleteAllPushs(userId: Int) {
        val selection = PushsPersistenceContract.PushEntry.COLUMN_NAME_MSGOWNER + " LIKE ?"
        val selectionArgs = arrayOf(userId.toString())
        mDatabaseHelper.delete(
            PushsPersistenceContract.PushEntry.TABLE_NAME,
            selection,
            *selectionArgs
        )
    }

}