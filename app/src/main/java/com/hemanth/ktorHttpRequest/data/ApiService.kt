package com.hemanth.ktorHttpRequest.data

import android.util.Log
import com.hemanth.ktorHttpRequest.data.model.Post
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class ApiService @Inject constructor() {

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    private val client = HttpClient(Android){
        install(DefaultRequest){
            headers.append("Content-Type", "application/json")
        }
        install(JsonFeature){
            serializer = GsonSerializer()
        }

   /*     // Json
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }*/

        // Apply to All Requests
        defaultRequest {
//            parameter("api_key", "some_api_key")
            // Content Type
            if (this.method != HttpMethod.Get) contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        // Logging
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.e("Ktor", message)
                }
            }
            level = LogLevel.ALL
        }

        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000

        }
    }

    suspend fun getPost() : List<Post> {
        return client.get {
            url("https://jsonplaceholder.typicode.com/posts")
        }
    }
}