package com.noministic.secupaycodingtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.secupaycodingtest.data.DefaultRepository
import com.noministic.secupaycodingtest.others.Constants
import com.noministic.secupaycodingtest.others.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FragmentStatusCodeViewModel @Inject constructor(private val defaultRepository: DefaultRepository) :
    ViewModel() {
    private val _statusCodeResponse = MutableLiveData<String>()
    val statusCodeResponse: LiveData<String> = _statusCodeResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getRandomStatusCode(statusCode: String = Constants.DEFAULT_STATUS_CODES) {
        viewModelScope.launch {
            _loading.value = true
            val response = defaultRepository.getHttpStatus(statusCode)
            when (response.status) {
                Status.ERROR -> {
                    _loading.value = false
                    response.message.let { _statusCodeResponse.value = it }
                }
                Status.SUCCESS -> {
                    _loading.value = false
                    response.data.let { _statusCodeResponse.value = it.toString() }
                    /**
                     * if response is successfull which is code 200 then i am just printing the
                     * response which can be converted to a model class
                     * otherwise it is showing error code only because there is no other data.
                     */
                }
                Status.LOADING -> {
                    _loading.value = true
                }
            }
        }
    }
}