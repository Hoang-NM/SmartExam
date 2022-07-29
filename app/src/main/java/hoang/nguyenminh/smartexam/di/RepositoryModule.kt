package hoang.nguyenminh.smartexam.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.smartexam.module.network.SmartExamCloudService
import hoang.nguyenminh.smartexam.repository.SmartExamCloudRepository
import hoang.nguyenminh.smartexam.repository.SmartExamCloudRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCloudRepository(
        serializer: Serializer,
        service: SmartExamCloudService
    ): SmartExamCloudRepository =
        SmartExamCloudRepositoryImpl(serializer, service)
}