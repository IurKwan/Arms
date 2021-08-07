package com.krbb.arms_push

import android.os.Parcelable
import com.krbb.arms_push.source.local.PushsPersistenceContract
import kotlinx.parcelize.Parcelize

/**
 * @author IurKwan
 * @date 12/26/20
 */
@Parcelize
data class Push constructor(

        /**
         * id：自增，单纯用于数据库
         */
        var _id: Int,

        /**
         * 用户
         */
        var msgOwner: String? = null,

        /**
         * 推送类型
         */
        var msgType: Int,

        /**
         * 标题
         */
        var title: String,

        /**
         * 内容
         */
        var content: String,

        /**
         * 时间
         */
        var time: String,

        /**
         * 未读数量
         */
        var count: Int,

        /**
         * 图片
         */
        var photoUrl: String?,

        /**
         * 推送ID：服务器推送的唯一标识
         */
        var tsid: String

) : Parcelable