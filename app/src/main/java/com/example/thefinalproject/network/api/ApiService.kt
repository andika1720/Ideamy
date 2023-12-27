package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.chapters.ChaptersById1Response
import com.example.thefinalproject.network.model.chapters.ChaptersResponseList
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.network.model.modules.ResponseModuleById1
import com.example.thefinalproject.network.model.modules.all.ModulesResponseAll
import com.example.thefinalproject.network.model.mycourse.MyCourseResponse
import com.example.thefinalproject.network.model.order.DeleteResponseOrder
import com.example.thefinalproject.network.model.order.GetResponse
import com.example.thefinalproject.network.model.order.PostResponse
import com.example.thefinalproject.network.model.order.PutResponseOrder
import com.example.thefinalproject.network.model.order.RequestPutOrder
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassResponse
import com.example.thefinalproject.network.model.user.forgotpassword.putdata.PutForgotPassRequest
import com.example.thefinalproject.network.model.user.forgotpassword.putdata.PutForgotPassResponse
import com.example.thefinalproject.network.model.user.getuser.GetCurrentUser
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.login.LoginResponse
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.OtpResponse
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpResponse
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.network.model.user.register.RegisterResponse
import com.example.thefinalproject.network.model.user.resetpassword.ChangePasswordRequest
import com.example.thefinalproject.network.model.user.resetpassword.ChangePasswordResponse
import com.example.thefinalproject.network.model.user.testNotif.NotipResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("courses")
    suspend fun getDataByCategory(
        @Query("category") category: String?): CategoryResponse
    @GET("courses/{id}")
    suspend fun getDataById(
        @Header("authorization") token : String?,
        @Path("id") id: String
    ): DetailResponse

    @GET("courses/{id}")
    suspend fun getDataById1(
        @Path("id") id: String
    ): DetailResponse

    @GET("chapters")
    suspend fun getChapters(
        @Query("courseId") courseId: String?
    ): ChaptersResponseList
    @GET("chapters/{id}")
    suspend fun getChaptersById(
        @Path("id") id: String
    ): ChaptersById1Response

    @GET("modules")
    suspend fun getModules(
        @Query("chapterId") chapterId: String?
    ): ModulesResponseAll
    @GET("modules/{id}")
    suspend fun getModulesById(
        @Path("id") id: String
    ): ResponseModuleById1
    @GET("courses")
    suspend fun getCourseByTitle(
        @Query("title") title: String?
    ) : CategoryResponse
    @GET("courses")
    suspend fun getFilterCourse(
        @Query("id") id: String?,
        @Query("category") category: String?,
        @Query("level") level: String?,
        @Query("type") type: String?,
        @Query("search") search: String?,
    ) : ListResponse


    //AUTH
    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST ("account-verify")
    suspend fun checkOtp(
        @Body otpRequest: OtpRequest
    ): OtpResponse

    @GET ("current-user")
    suspend fun currentUser(
        @Header("authorization") token : String?
    ): GetCurrentUser

    @POST ("resend-otp")
    suspend fun resendOtp(
        @Body resendOtpRequest: ResendOtpRequest
    ): ResendOtpResponse

    @PUT ("reset-password")
    suspend fun resetPasswordUser(
        @Header("authorization") token : String?,
        @Body changePasswordRequest: ChangePasswordRequest
    ): ChangePasswordResponse


    //MYCOURSE
    @GET("my-courses")
    suspend fun myCourse(
        @Header("authorization") token : String?
    ): MyCourseResponse

    //ORDER
    @GET("orders")
    suspend fun getOrders(
        @Header("authorization") token : String?
    ) : GetResponse

    @POST("orders/{courseId}")
    suspend fun ordersId(
        @Header("authorization") token : String?,
        @Path("courseId") courseId: String?
    ) : PostResponse

    @PUT("orders/{id}")
    suspend fun updatePayment(
        @Header("authorization") token : String?,
        @Path("id") id: String?,
        @Body requestOrder: RequestPutOrder
    ) : PutResponseOrder

    @DELETE("orders/{id}")
    suspend fun deletePayment(
        @Header("authorization") token : String?,
        @Path("id") id: String?
    ) : DeleteResponseOrder

    @POST ("forgot-password")
    suspend fun inputEmailForgot(
        @Body forgotPassRequest: PostForgotPassRequest
    ): PostForgotPassResponse
    @PUT ("forgot-password")
    suspend fun inputOtpForgot(
        @Body forgotPassRequest: PutForgotPassRequest
    ): PutForgotPassResponse

    @GET("notifications")
    suspend fun getNotification(
        @Header("authorization") token: String?,
    ):NotipResponse





}