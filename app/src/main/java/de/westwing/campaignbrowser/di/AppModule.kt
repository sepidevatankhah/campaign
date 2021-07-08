package de.westwing.campaignbrowser.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.westwing.campaignbrowser.di.scope.ForApplication
import de.westwing.campaignbrowser.domain.executer.PostExecutionThread
import de.westwing.campaignbrowser.common.NetworkManagerImp
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.data.ApiInterface
import de.westwing.campaignbrowser.data.CampaignRepositoryImpl
import de.westwing.campaignbrowser.data.extractor.NetworkJobExecutor
import de.westwing.campaignbrowser.domain.CampaignRepository
import de.westwing.campaignbrowser.domain.NetworkManager
import de.westwing.campaignbrowser.domain.executer.UseCaseExecutor
import de.westwing.campaignbrowser.presentation.CampaignApp
import de.westwing.campaignbrowser.presentation.executer.UiThreadExecutor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
        fun okHttpClient(
            connectivityManager: NetworkManager,
            @ForApplication context: Context
        ): OkHttpClient {
            val cacheSize = (5 * 1024 * 1024).toLong()

            val myCache = Cache(context.cacheDir, cacheSize)

            val builder = OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                // Specify the cache we created earlier.
                .cache(myCache)
                // Add an Interceptor to the OkHttpClient.
                .addInterceptor { chain ->

                    // Get the request from the chain.
                    var request = chain.request()
                    val url = request.url.newBuilder().build()
                    request = request.newBuilder()
                        .url(url)
                        .build()
                    /*
                    *  Leveraging the advantage of using Kotlin,
                    *  we initialize the request and change its header depending on whether
                    *  the device is connected to Internet or not.
                    */
                    request = if (connectivityManager.hasNetwork())
                    /*
                    *  If there is Internet, get the cache that was stored 5 seconds ago.
                    *  If the cache is older than 5 seconds, then discard it,
                    *  and indicate an error in fetching the response.
                    *  The 'max-age' attribute is responsible for this behavior.
                    */
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    else
                    /*
                    *  If there is no Internet, get the cache that was stored 7 days ago.
                    *  If the cache is older than 7 days, then discard it,
                    *  and indicate an error in fetching the response.
                    *  The 'max-stale' attribute is responsible for this behavior.
                    *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                    */
                        request.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        ).build()


                    // End of if-else statement

                    // Add the modified request to the chain.
                    chain.proceed(request)
                }
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(logging)  // <-- this is the important line!

            return builder.build()
        }

        @Provides
        @Singleton
        fun moshi(): Moshi {
            return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()

        @Singleton
        @Provides
        fun provideForSquareApi(retrofit: Retrofit): ApiInterface =
            retrofit.create(ApiInterface::class.java)

        @Provides
        @Singleton
        fun provideUseCaseExecutor(): UseCaseExecutor {
            return NetworkJobExecutor()
        }

        @Provides
        @Singleton
        fun postExecutionThread(): PostExecutionThread = UiThreadExecutor()


        @Provides
        @ForApplication
        fun provideContext(app: CampaignApp): Context = app.applicationContext

        @Provides
        @Singleton
        fun provideConnectivityManager(connectivityManagerImp: NetworkManagerImp)
                : NetworkManager = connectivityManagerImp
    }
}