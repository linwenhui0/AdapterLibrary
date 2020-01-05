package com.hlibrary.adapter.model

/**
 *
 * @author linwenhui
 * @date 2017/2/28
 */
open class HeadFootAdapterVo<T>(override val layoutResId: Int, var variableId: Int = 0) : AdapterVo(layoutResId) {
    var data: T? = null
}