package de.westwing.campaignbrowser.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.data.CampaignRepositoryImpl
import de.westwing.campaignbrowser.domain.CampaignRepository
import de.westwing.campaignbrowser.presentation.CampaignApp
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(app: CampaignApp): Context

    @Binds
    abstract fun bindCampaignRepository(campaignRepositoryImpl: CampaignRepositoryImpl): CampaignRepository

    companion object {

        @Provides
        @Named("baseUrl")
        fun baseUrl(context: Context): String {
            return context.getString(R.string.base_url)
        }

        @Provides
        @Singleton
        fun okHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder.retryOnConnectionFailure(true)
            return builder.build()
        }

        @Provides
        @Singleton
        fun moshi(): Moshi {
            return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }
    }
}