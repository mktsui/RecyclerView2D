package com.balljoint.mktsui.recyclerview2d.Model

data class Videos (
    var category: String,
    val items: List<Items>
) {
    constructor() : this("", listOf(Items()))
}

data class Items (
    val title: String,
    val year: Int,
    val description: String,
    val images: Images
) {
    constructor() : this("",0,"",Images())
}

data class Images (
    val portrait: String,
    val landscape: String
) {
    constructor() : this("","")
}