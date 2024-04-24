package me.pegbeer.filmku.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

fun <T,A> resultFlow(
    local: suspend () -> Flow<T>,
    network: suspend () -> Result<A>,
    saveCallback: suspend (A) -> Unit
):Flow<Result<T>> = flow{
    emit(Result.loading())
    val source = local.invoke().map { Result.success(it) }
    emitAll(source)

    val responseStatus = network.invoke()
    if(responseStatus.status == Result.Status.SUCCESS){
        saveCallback(responseStatus.data!!)
    }else if (responseStatus.status == Result.Status.ERROR){
        emit(Result.error(responseStatus.code!!))
        emitAll(source)
    }
}