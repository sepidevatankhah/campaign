package de.westwing.campaignbrowser.data

import de.westwing.campaignbrowser.data.entity.CampaignsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiInterface {
    companion object {
        const val BASE_URL = "https://static.westwing.de/"
    }

    @GET("cms/test/campaigns.json")
    fun getCampaigns(): Single<CampaignsResponse>

}