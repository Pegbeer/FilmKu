package me.pegbeer.filmku.util


enum class SortBy(val value:String,val displayName:String){
    NowPlaying("",""),
    Popular("popularity.desc","Popularity"),
    TopRated("vote_count.desc","Top Rated"),
    Upcoming("release_date.desc","Upcoming")
}

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
