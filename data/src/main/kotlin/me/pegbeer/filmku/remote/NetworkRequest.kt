package me.pegbeer.filmku.remote

import me.pegbeer.filmku.util.Result
import retrofit2.Response

abstract class NetworkRequest {
    protected fun <T> Response<T>. getResult(): Result<T> {
        if(!this.isSuccessful) return Result.error(this.code())
        if(this.body() == null) return Result.error(204)
        return Result.success(this.body()!!)
    }
}