package hoang.nguyenminh.smartexam.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.serializer.impl.MoshiSerializer
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import hoang.nguyenminh.smartexam.module.credential.CredentialManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseModules {

    @Provides
    @Singleton
    fun provideSerializer(serializer: MoshiSerializer): Serializer = MoshiSerializer()

    @Provides
    @Singleton
    fun provideCredentialManager(context: Context, serializer: Serializer): CredentialManager =
        CredentialManagerImpl(context, serializer)
}