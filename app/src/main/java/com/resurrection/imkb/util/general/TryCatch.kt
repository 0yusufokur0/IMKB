package com.resurrection.imkb.util.general

import android.util.Log

fun tryCatch(func:()->Unit){
    try {
        func()
    }catch (e:Exception){
        ThrowableError(e.toString())
    }
}

class ThrowableError(msg:String):Throwable(msg){
    init { Log.e("ThrowableError",msg) }
}