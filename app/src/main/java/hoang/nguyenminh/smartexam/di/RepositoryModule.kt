package hoang.nguyenminh.smartexam.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.smartexam.network.SmartExamCloudService
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCloudRepository(service: SmartExamCloudService): SmartExamCloudRepository =
        SmartExamCloudRepositoryImpl(service)
}