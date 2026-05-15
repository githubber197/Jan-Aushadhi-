package com.janaushadhi.finder.data.network

import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.model.Store

data class MedicinesResponse(
    val data: List<Medicine>,
    val total: Int,
    val limit: Int,
    val offset: Int
)

data class StoresResponse(
    val data: List<Store>
)

data class Category(
    val id: String,
    val label: String,
    val icon: String
)

data class HealthResponse(
    val status: String,
    val timestamp: String
)
