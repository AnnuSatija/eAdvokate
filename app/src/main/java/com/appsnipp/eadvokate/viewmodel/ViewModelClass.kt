package com.appsnipp.eadvokate.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appsnipp.eadvokate.model.*
import com.appsnipp.eadvokate.repository.RepositoryClass

class ViewModelClass:ViewModel() {

    var consultationList=MutableLiveData<ConsultationModel>()
    var reviewDocList=MutableLiveData<ReviewDocumentModel>()
    var instantServiceList=MutableLiveData<InstantServiceCasesModel>()
    var pendingAcceptPaymentList=MutableLiveData<PendingPaymentAcceptModel>()
    var allPaymentList=MutableLiveData<AllPaymentListModel>()
    var serviceDataList=MutableLiveData<ServicesDataModel>()
    var bannerList=MutableLiveData<DashboardBannerModel>()

        open  fun getConsultationList(context: Context): LiveData<ConsultationModel>? {
            consultationList =RepositoryClass.getConsultationApiCall(context)
            return consultationList
        }

       open fun getReviewDocList(context: Context):LiveData<ReviewDocumentModel>{
           reviewDocList=RepositoryClass.getReviewDocumentApiCall(context)
           return reviewDocList
       }

      open fun getInstantServiceList(context: Context):LiveData<InstantServiceCasesModel>{
          instantServiceList=RepositoryClass.getInstantServiceApiCall(context)
          return instantServiceList
      }

    open fun getPendingPaymentAcceptList(context: Context,id: Int):LiveData<PendingPaymentAcceptModel>{
        pendingAcceptPaymentList=RepositoryClass.getPendingAcceptPaymentApiCall(context,id)
        return pendingAcceptPaymentList
    }

    open fun getAllPaymentList(context: Context):LiveData<AllPaymentListModel>{
         allPaymentList=RepositoryClass.getAllPaymentApiCall(context)
         return allPaymentList
    }

    open fun getServiceDataList(context: Context):LiveData<ServicesDataModel>{
        serviceDataList=RepositoryClass.getServicesApiCall(context)
        return serviceDataList
    }

    open fun getBannerDataList(context: Context):LiveData<DashboardBannerModel>{
        bannerList=RepositoryClass.getBannerApiCall(context)
        return bannerList
    }
}