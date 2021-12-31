package com.resurrection.imkb.data.model

data class TestModel(
    val list :List<String>,
    val intList :List<Int>
){
    override fun toString(): String {
        return list.toString()+intList.toString()
    }
}