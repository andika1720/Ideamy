package com.example.thefinalproject.mvvm.repository


import com.example.thefinalproject.network.api.ApiService
import com.example.thefinalproject.network.model.order.RequestPutOrder
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.network.model.user.forgotpassword.putdata.PutForgotPassRequest
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.network.model.user.resetpassword.ChangePasswordRequest
import com.example.thefinalproject.network.model.user.updateprofile.ReqNewUser


class Repository(private val apiService: ApiService) {

    suspend fun login(loginRequest: LoginRequest) = apiService.loginUser(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) = apiService.registerUser(registerRequest)

    suspend fun getCurrentUser(token: String?) = apiService.currentUser(token)
    suspend fun getCategory(category: String?) = apiService.getDataByCategory(category)


    suspend fun getChapters(courseId: String?) = apiService.getChapters(courseId)
    suspend fun getModules(chapterId: String?) = apiService.getModules(chapterId)
    suspend fun getDataByid(token: String?,id: String)= apiService.getDataById(token,id)

    suspend fun getDataByid1(id: String)= apiService.getDataById1(id)

    suspend fun getChapterById(token: String?,id: String) = apiService.getChaptersById(token,id)

    suspend fun getModulesById(token: String?,id: String) = apiService.getModulesById(token,id)

    suspend fun getCoursesByTitle(title:String) = apiService.getCourseByTitle(title)

   suspend fun getFilter2(id: String?,category: String?,level: String?, type: String?, search: String?) = apiService.getFilterCourse(id,level,category,type,search)

    suspend fun checkOtp(otpRequest: OtpRequest) = apiService.checkOtp(otpRequest)
    suspend fun changePassword(token: String?,changePasswordRequest: ChangePasswordRequest)=apiService.resetPasswordUser(token,changePasswordRequest)

    suspend fun resendOtp(resendOtpRequest: ResendOtpRequest) = apiService.resendOtp(resendOtpRequest)


    //ORDER
    suspend fun getOrders(token: String?) = apiService.getOrders(token)

    suspend fun ordersId(token: String?,courseId: String?) = apiService.ordersId(token, courseId)

    suspend fun updatePayment(token: String?,id: String?,requestPutOrder: RequestPutOrder) = apiService.updatePayment(token, id,requestPutOrder)

    suspend fun deletePayment(token: String?,id: String?) = apiService.deletePayment(token,id)

    //MyCourse
    suspend fun myCourse(token: String?) = apiService.myCourse(token)

    //UpdateProfile
    suspend fun updateProfile(dataUser: ReqNewUser) = apiService.updateProfile(dataUser)

    // forgot password
    suspend fun inputForgotPassword(postForgotPassRequest: PostForgotPassRequest) = apiService.inputEmailForgot(postForgotPassRequest)

    suspend fun putForgotPassword(putForgotPasswordRequest: PutForgotPassRequest) = apiService.inputOtpForgot(putForgotPasswordRequest)

    suspend fun getNotification(token: String?) = apiService.getNotification(token)
}