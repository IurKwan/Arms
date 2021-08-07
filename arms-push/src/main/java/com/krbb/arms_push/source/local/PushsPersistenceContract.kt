package com.krbb.arms_push.source.local

/**
 * @author IurKwan
 * @date 12/29/20
 */
class PushsPersistenceContract
private constructor() {
    object PushEntry {
        const val TABLE_NAME = "MessageTable"
        const val COLUMN_NAME_ID = "_id"
        const val COLUMN_NAME_MSGOWNER = "msgOwner"
        const val COLUMN_NAME_MSGTYPE = "msgType"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_CONTENT = "content"
        const val COLUMN_NAME_TIME = "time"
        const val COLUMN_NAME_COUNT = "count"
        const val COLUMN_NAME_PHOTOURL = "photoUrl"
        const val COLUMN_NAME_TSID = "tsid"
    }
}