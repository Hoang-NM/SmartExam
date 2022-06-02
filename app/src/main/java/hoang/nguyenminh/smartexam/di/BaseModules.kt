package hoang.nguyenminh.smartexam.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.smartexam.service.preference.CredentialManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseModules {

    @Singleton
    @Provides
    fun provideCredentialManager(): CredentialManager = CredentialManager()
}