package com.krbb.arms_push.source

import com.krbb.arms_push.Push
import com.krbb.arms_push.util.TimeConstants
import com.krbb.arms_push.util.TimeUtil
import io.reactivex.rxjava3.core.Flowable

/**
 * @author IurKwan
 * @date 12/29/20
 */
class PushsRepository(
    private val pushsLocalDataSource: PushsDataSource
) : PushsDataSource {

    /**
     * 缓存数据库数据
     */
    private var cachedPushs: LinkedHashMap<String, Push>? = null

    /**
     * 缓存数据是否不准确
     */
    private var cacheIsDirty = false

    companion object {
        @Volatile
        private var INSTANCE: PushsRepository? = null

        @JvmStatic
        fun getInstance(
            pushsLocalDataSource: PushsDataSource
        ): PushsRepository {
            return INSTANCE ?: PushsRepository(
                pushsLocalDataSource
            ).apply {
                INSTANCE = this
            }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getPushs(userId: Int): Flowable<MutableList<Push>> {
        if (cachedPushs != null && !cacheIsDirty) {
            return Flowable.fromIterable(cachedPushs!!.values)
                .toList()
                .toFlowable()
        } else if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        // 清空缓存
        if (cacheIsDirty){
            cachedPushs?.clear()
        }

        // 直接返回数据库数据
        return pushsLocalDataSource.getPushs(userId)
            .flatMap {
                return@flatMap Flowable.fromIterable(it)
                    .doOnNext { push ->
                        // 缓存数据
                        cachedPushs?.put(push.tsid, push)
                    }
                    .toList()
                    .toFlowable()
            }
            .doOnComplete {
                cacheIsDirty = false
            }
    }

    override fun refreshPushs() {
        cacheIsDirty = true
    }

    override fun savePush(userId: Int, push: Push, coverWithType: Boolean) {
        pushsLocalDataSource.savePush(userId, push, coverWithType)

        if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        cachedPushs?.let {
            if (coverWithType) {
                // 删除重复类型
                it.forEach { (s, p) ->
                    if (p.msgType == push.msgType) {
                        it.remove(s)
                    }
                }
            }
            cachedPushs?.put(push.tsid, push)
        }
    }

    override fun savePush(userId: Int, push: Push) {
        savePush(userId, push, false)
    }

    override fun readPush(push: Push) {
        pushsLocalDataSource.readPush(push)

        val readPush = Push(
            _id = push._id,
            msgType = push.msgType,
            count = 0,
            title = push.title,
            photoUrl = push.photoUrl,
            time = push.time,
            content = push.content,
            tsid = push.tsid
        )

        if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        cachedPushs?.put(push.tsid, readPush)
    }

    override fun readAllPush(userId: Int) {
        pushsLocalDataSource.readAllPush(userId)

        cachedPushs?.forEach {
            it.value.count = 0
        }
    }

    override fun deletePush(push: Push) {
        pushsLocalDataSource.deletePush(push)

        if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        cachedPushs?.remove(push.tsid)
    }

    override fun deletePushByTsId(tsId: String) {
        pushsLocalDataSource.deletePushByTsId(tsId)

        if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        cachedPushs?.remove(tsId)
    }

    override fun deleteDaysPush(userId: Int, days: Int) {
        pushsLocalDataSource.deleteDaysPush(userId, days)

        if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        val daysFormat = TimeUtil.getStringByNow(-days.toLong(), TimeConstants.DAY)
        val it = cachedPushs!!.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.value.time < daysFormat) {
                it.remove()
            }
        }
    }

    override fun deleteAllPushs(userId: Int) {
        pushsLocalDataSource.deleteAllPushs(userId)

        if (cachedPushs == null) {
            cachedPushs = LinkedHashMap()
        }

        cachedPushs?.clear()
    }

}