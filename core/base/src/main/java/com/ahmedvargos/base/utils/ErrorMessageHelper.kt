package com.ahmedvargos.base.utils


object ErrorMessageHelper {

    fun getMessage(code:Int): String {
        return when(code){
            NetworkCodes.CONNECTION_ERROR->{
                "Your connection lost check it"
            }
            else->{
                "unknown error"
            }
        }
    }
}