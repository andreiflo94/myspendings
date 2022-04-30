package com.heixss.myspendings.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category(@PrimaryKey(autoGenerate = true) val id: Long = 0, val name: String)