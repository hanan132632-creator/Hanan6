package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val propertyId: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_mortgages")
data class SavedMortgageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val propertyTitle: String,
    val propertyPrice: Double,
    val downPaymentPercentage: Double,
    val loanTermYears: Int,
    val annualProfitRate: Double,
    val monthlyInstallment: Double,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "property_inquiries")
data class PropertyInquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val propertyId: String,
    val propertyTitle: String,
    val agentName: String,
    val channel: String, // "WHATSAPP", "PHONE", "VIP_FORM"
    val timestamp: Long = System.currentTimeMillis()
)
