package me.pegbeer.filmku.util

data class Result<out T>(val status:Status, val data:T?, val code:Int?){

    enum class Status{
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object{

        fun <T> success(data:T,code: Int = 200):Result<T>{
            return Result(Status.SUCCESS,data,code)
        }

        fun <T> error(code:Int):Result<T>{
            return Result(Status.ERROR,null,code)
        }

        fun <T> loading():Result<T>{
            return Result(Status.LOADING,null,null)
        }
    }
}
