package hoang.nguyenminh.smartexam.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.base.network.RetrofitXs
import hoang.nguyenminh.smartexam.model.AppConst
import hoang.nguyenminh.smartexam.network.SmartExamCloudService
import hoang.nguyenminh.smartexam.network.SmartExamLocalService
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCloudService(): SmartExamCloudService =
        RetrofitXs.newInstance(AppConst.BASE_URL).create()

    @Provides
    @Singleton
    fun provideLocalService(): SmartExamLocalService =
        RetrofitXs.newInstance(AppConst.BASE_URL).create()
}