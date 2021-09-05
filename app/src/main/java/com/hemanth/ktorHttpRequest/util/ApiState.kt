package com.hemanth.ktorHttpRequest.util

import com.hemanth.ktorHttpRequest.data.model.Post


sealed class ApiState{
    object Loading : ApiState()
    object Empty : ApiState()
    class Success(val response: List<Post>) : ApiState()
    class Failure(val error:Throwable) : ApiState()
}
