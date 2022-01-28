package com.techand.sampletest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.techand.sampletest.data.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import java.net.UnknownHostException

class UserViewModel @ViewModelInject constructor(val mainRepository: UserInfoRepository) :
    ViewModel() {


    val loader = MutableLiveData<Boolean>()

    fun getUser() = liveData {
        loader.postValue(true)
        emitSource(mainRepository.getUser().onEach {
            loader.postValue(false)
        }.asLiveData())
    }


    fun getPhotos(albumId: Int) = liveData(Dispatchers.IO) {
        loader.postValue(true)
        emitSource(mainRepository.getPhotos(albumId.toString().onEach {
            loader.postValue(false)
        }).asLiveData())
    }
}