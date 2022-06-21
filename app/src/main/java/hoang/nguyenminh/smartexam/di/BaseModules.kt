package hoang.nguyenminh.smartexam.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import hoang.nguyenminh.smartexam.module.credential.CredentialManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseModules {

    @Provides
    @Singleton
    fun provideCredentialManager(context: Context): CredentialManager =
        CredentialManagerImpl(context)
}