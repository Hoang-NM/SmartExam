package hoang.nguyenminh.smartexam.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.serializer.impl.GsonSerializer
import hoang.nguyenminh.smartexam.SmartExamApplication
import hoang.nguyenminh.smartexam.appInstance
import hoang.nguyenminh.smartexam.module.configuration.ConfigurationManager
import hoang.nguyenminh.smartexam.module.configuration.ConfigurationManagerImpl
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import hoang.nguyenminh.smartexam.module.credential.CredentialManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseModules {

    @Provides
    @Singleton
    fun provideSerializer(): Serializer = GsonSerializer()

    @Provides
    @Singleton
    fun provideContext(): Context = appInstance().applicationContext

    @Provides
    @Singleton
    fun provideCredentialManager(context: Context, serializer: Serializer): CredentialManager =
        CredentialManagerImpl(context, serializer)

    @Provides
    @Singleton
    fun provideConfigurationManager(
        context: Context, serializer: Serializer
    ): ConfigurationManager =
        ConfigurationManagerImpl(context, serializer)
}