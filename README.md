# ComposeFlowLayout
Compose版FlowLayout

# 效果
![](https://raw.githubusercontents.com/shenzhen2017/resource/main/2021/augest/p6.png)
![](https://raw.githubusercontents.com/shenzhen2017/resource/main/2021/augest/p7.png)

# 特性
1. 自定义`Layout`,从左向右排列，超出一行则换行显示
2. 支持设置子`View`间距及行间距
3. 当子`View`高度不一致时，支持一行内居上，居中，居下对齐

# 使用
```kotlin
    ComposeFlowLayout(
        itemSpacing = 16.dp,
        lineSpacing = 16.dp,
        gravity = Gravity.CENTER
    ) {
        for (index in topics.indices) {
            if (index % 3 == 1) {
                Chip(modifier = Modifier.height(80.dp),text = topics[index])
            } else {
                Chip(text = topics[index])
            }
        }
    }
```

# 原理
![Compose版FlowLayout了解一下~](https://juejin.cn/post/6996661434083967013)

