package com.appsnipp.eadvokate.retrofitintrigation

import com.appsnipp.eadvokate.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiHolder {

    @POST("customers/auth/sign-in")
    fun Login(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<LoginResponseModel>

    @POST("customers/auth/verify-otp")
    fun VerifyOtp(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<VerifyOtpResponseModel>

    @POST("customers/auth/sign-up")
    fun SignUp(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<SignupResponseModel>

    @POST("customers/instant-consultancy/request-new-consultancy")
    fun RequestConsultancy(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<RequestConsultancyDataModel>

    @GET("common/states")
    fun GetState(@HeaderMap fields1: Map<String, String>):Call<StateDataModel>

    @GET("customers/profile/user-profile")
    fun GetProfile(@HeaderMap fields1: Map<String, String>):Call<ProfiledataModel>

    @GET("common/states/{id}")
    fun GetCity(@HeaderMap fields1: Map<String, String>,@Path("id") id: Int):Call<CityDataModel>

    @GET("website/services/1")
    fun GetServices(@HeaderMap fields1: Map<String, String>):Call<ServicesDataModel>

    @GET("customers/review-document/get-packages")
    fun GetServicePackages(@HeaderMap fields1: Map<String, String>):Call<ServicePackegesModel>


  /*  @Multipart
    @POST("customers/review-document/pre-cases-order")
    fun uploadDocumentForReview(@HeaderMap headers: Map<String, String>, @Part("package_type") packageId: RequestBody *//*, @Part file: MultipartBody.Part*//*): Call<GetReviewDocumentResponseModel>
*/

    @PUT("customers/meeting/join-meeting")
    fun JoinMeeting(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<JoinMeetingModel>

    @POST("customers/feedback/add-feedback")
    fun RequestFeedbackAfterVideoCall(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<FeedbackResponseModel>


    @GET("customers/payments/get-payment-requests")
    fun RequestPayment(@HeaderMap fields1: Map<String, String>):Call<RequestPaymentModel>


    @GET("customers/review-document/get-price-for-payment/{id}")
    fun GetPaymentBreakUpAfterUploadDocument(@HeaderMap fields1: Map<String, String>,@Path("id") id: Int):Call<PaymentBreakUpModel>


    @PUT("customers/payments/cancel-payment-requests")
    fun CancelPayment(@HeaderMap fields1: Map<String, String>,  @Body params: RequestBody):Call<PaymentCancelModel>


    @GET("customers/instant-consultancy/all-consultations")
    fun GetAllConsulationList(@HeaderMap fields1: Map<String, String>):Call<ConsultationModel>


    @GET("customers/cases/review-doc-cases")
    fun GetReviewDocList(@HeaderMap fields1: Map<String, String>):Call<ReviewDocumentModel>

    @GET("customers/cases/instant-cases")
    fun GetInstantServiceList(@HeaderMap fields1: Map<String, String>):Call<InstantServiceCasesModel>

    @PUT("customers/payments/initiate-payment")
    fun GetAcceptPendingPayment(@HeaderMap fields1: Map<String, String>,@Body params: RequestBody):Call<PendingPaymentAcceptModel>

    @GET("customers/payments/all-payment")
    fun GetAllPaymentList(@HeaderMap fields1: Map<String, String>):Call<AllPaymentListModel>

    @GET("website/banners/get-all-banners")
    fun GetBannerList(@HeaderMap fields1: Map<String, String>):Call<DashboardBannerModel>

}