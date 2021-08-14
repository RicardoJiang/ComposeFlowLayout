package com.zj.composeflowlayout

import android.util.Log
import android.view.Gravity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun ComposeFlowLayout(
    modifier: Modifier = Modifier,
    itemSpacing: Dp = 0.dp,
    lineSpacing: Dp = 0.dp,
    gravity: Int = Gravity.TOP,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val parentWidthSize = constraints.maxWidth
        var lineWidth = 0
        var totalHeight = 0
        var lineHeight = 0
        val mAllPlaceables = mutableListOf<MutableList<Placeable>>()  // 所有可放置的内容
        val mLineHeight = mutableListOf<Int>() //每行的最高高度
        var lineViews = mutableListOf<Placeable>() //每行放置的内容
        // 测量子View，获取FlowLayout的宽高
        measurables.mapIndexed { i, measurable ->
            // 测量子view
            val placeable = measurable.measure(constraints)
            val childWidth = placeable.width
            val childHeight = placeable.height
            if (lineWidth + childWidth > parentWidthSize) {
                mLineHeight.add(lineHeight)
                mAllPlaceables.add(lineViews)
                lineViews = mutableListOf()
                lineViews.add(placeable)
                //记录总高度
                totalHeight += lineHeight
                lineWidth = childWidth
                lineHeight = childHeight
                totalHeight += lineSpacing.toPx().toInt()
            } else {
                lineWidth += childWidth + if (i == 0) 0 else itemSpacing.toPx().toInt()
                lineHeight = maxOf(lineHeight, childHeight)
                lineViews.add(placeable)
            }
            if (i == measurables.size - 1) {
                totalHeight += lineHeight
                mLineHeight.add(lineHeight)
                mAllPlaceables.add(lineViews)
            }
        }


        layout(parentWidthSize, totalHeight) {
            var topOffset = 0
            var leftOffset = 0
            for (i in mAllPlaceables.indices) {
                lineViews = mAllPlaceables[i]
                lineHeight = mLineHeight[i]
                for (j in lineViews.indices) {
                    val child = lineViews[j]
                    val childWidth = child.width
                    val childHeight = child.height
                    val childTop = getItemTop(gravity, lineHeight, topOffset, childHeight)
                    child.placeRelative(leftOffset, childTop)
                    leftOffset += childWidth + itemSpacing.toPx().toInt()
                }
                leftOffset = 0
                topOffset += lineHeight + lineSpacing.toPx().toInt()
            }
        }
    }
}

private fun getItemTop(gravity: Int, lineHeight: Int, topOffset: Int, childHeight: Int): Int {
    return when (gravity) {
        Gravity.CENTER -> topOffset + (lineHeight - childHeight) / 2
        Gravity.BOTTOM -> topOffset + lineHeight - childHeight
        else -> topOffset
    }
}

