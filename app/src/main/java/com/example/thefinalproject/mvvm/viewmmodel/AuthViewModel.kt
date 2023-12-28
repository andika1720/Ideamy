package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.network.model.order.RequestPutOrder
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.network.model.user.forgotpassword.putdata.PutForgotPassRequest
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.network.model.user.updateprofile.DataUser
import com.example.thefinalproject.network.model.user.updateprofile.ReqNewUser
import com.example.thefinalproject.network.model.user.resetpassword.ChangePasswordRequest
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: Repository): ViewModel() {


    fun loginUser(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {

            emit(Resource.success(repo.login(loginRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    fun getCurrentUser(token: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {

            emit(Resource.success(repo.getCurrentUser(token)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

     fun regisUser(registerRequest: RegisterRequest) = liveData(
        Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.register(registerRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

     fun otpUser(otpRequest: OtpRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.checkOtp(otpRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }

    }
    fun resetPasswordUser(token: String?,changePasswordRequest: ChangePasswordRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.changePassword(token,changePasswordRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }

    }

    fun resendOtpUser(resendOtpRequest: ResendOtpRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.resendOtp(resendOtpRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    //ORDERS
    fun getOrders(token: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.getOrders(token)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun ordersId(token: String?,id:String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.ordersId(token, id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updatePayment(token: String?,id:String?,requestPutOrder: RequestPutOrder) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.updatePayment(token, id,requestPutOrder)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun deletePayment(token: String?,id:String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.deletePayment(token, id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun myCourse(token: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.myCourse(token)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun forgotPassword(postForgotPassRequest: PostForgotPassRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.inputForgotPassword(postForgotPassRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }

    }
    fun putForgotPassword(putForgotPassRequest: PutForgotPassRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.putForgotPassword(putForgotPassRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }

    }

    fun updateProfile (dataUser: ReqNewUser) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.updateProfile( dataUser)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }
}
