package com.techand.sampletest.data.repository

import com.techand.sampletest.data.models.Album
import com.techand.sampletest.data.models.User
import com.techand.sampletest.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserInfoRepository @Inject constructor(
    private val remoteDataSource: ApiService
) {

    suspend fun getUser(): Flow<Result<List<User>?>> {
        return remoteDataSource.fetchUsers().map {
            if (it.isSuccess)
                Result.success(it.getOrNull())
            else
                Result.failure(it.exceptionOrNull()!!)

        }
    }

    suspend fun getPhotos(albumId: String): Flow<Result<List<Album>?>> {
        return remoteDataSource.fetchPhotos(albumId).map {
            if (it.isSuccess)
                Result.success(it.getOrNull())
            else
                Result.failure(it.exceptionOrNull()!!)

        }
    }

}