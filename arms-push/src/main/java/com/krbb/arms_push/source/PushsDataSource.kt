package com.krbb.arms_push.source

import com.krbb.arms_push.Push
import io.reactivex.rxjava3.core.Flowable

/**
 * @author IurKwan
 * @date 12/26/20
 */
interface PushsDataSource {

    /**
     * 获取推送列表
     */
    fun getPushs(userId: Int): Flowable<MutableList<Push>>

    /**
     * 缓存数据不准确，需要重置
     */
    fun refreshPushs()

    /**
     * 保存推送信息
     */
    fun savePush(
        userId: Int,
        push: Push,
        coverWithType: Boolean = false
    )

    /**
     * 保存推送信息
     */
    fun savePush(userId: Int,push: Push)

    /**
     * 设置推送已读
     */
    fun readPush(push: Push)

    /**
     * 设置该用户的推送全部已读
     */
    fun readAllPush(userId: Int)

    /**
     * 删除推送
     */
    fun deletePush(push: Push)

    /**
     * 根据tsId删除推送
     */
    fun deletePushByTsId(tsId: String)

    /**
     * 删除N天前的推送
     */
    fun deleteDaysPush(userId: Int, days: Int)

    /**
     * 删除用户的所有推送信息
     */
    fun deleteAllPushs(userId: Int)
}