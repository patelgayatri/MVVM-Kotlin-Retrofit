package com.techand.sampletest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.techand.sampletest.data.models.User
import com.techand.sampletest.data.repository.UserInfoRepository
import com.techand.sampletest.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import java.lang.Exception
import java.net.UnknownHostException

class UserViewModel @ViewModelInject constructor(private val mainRepository: UserInfoRepository) :
    ViewModel() {


    fun getUser() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUser()))
        } catch (exception: UnknownHostException) {
            emit(Resource.error(data = null, message = "No Internet Connection"))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getPhotos(albumId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPhotos(albumId.toString())))
        } catch (exception: UnknownHostException) {
            emit(Resource.error(data = null, message = "No Internet Connection"))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}