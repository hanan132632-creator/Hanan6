package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_stats")
data class ArticleStatsEntity(
    @PrimaryKey
    val articleId: String,
    val viewsCount: Int,
    val likesCount: Int,
    val isLikedByUser: Boolean = false
)
