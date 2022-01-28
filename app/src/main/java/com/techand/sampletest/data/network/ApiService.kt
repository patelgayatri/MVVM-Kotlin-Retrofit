package com.techand.sampletest.data.network

import com.techand.sampletest.data.models.Album
import com.techand.sampletest.data.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiService @Inject constructor(val api: Api) {

    suspend fun fetchUsers(): Flow<Result<List<User>>> {
        return flow {
            emit(Result.success(api.getUserData()))
        }.catch {
            emit(Result.failure(RuntimeException(it.message ?: "Error")))
        }
    }

    suspend fun fetchPhotos(albumId: String): Flow<Result<List<Album>>> {
        return flow {
            emit(Result.success(api.getPhotos(albumId)))
        }.catch {
            emit(Result.failure(RuntimeException(it.message ?: "Error")))
        }
    }

}