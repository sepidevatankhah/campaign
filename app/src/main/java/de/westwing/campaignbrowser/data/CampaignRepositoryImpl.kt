package de.westwing.campaignbrowser.data

import de.westwing.campaignbrowser.data.entity.CampaignDto
import de.westwing.campaignbrowser.data.entity.ImageDto
import de.westwing.campaignbrowser.domain.CampaignRepository
import de.westwing.campaignbrowser.domain.model.Campaign
import de.westwing.campaignbrowser.domain.model.ImageModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class CampaignRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    CampaignRepository {

    override fun getCampaigns(): Single<List<Campaign>> {
        return Single.create { emitter ->
            apiInterface.getCampaigns()
                .map { response ->
                    response.metadata.data.filterNot { model -> model.name.isNullOrBlank() || model.description.isBlank() }
                        .map { it.map() }
                }
                .subscribe(
                    { response ->
                        emitter.onSuccess(response)
                    },
                    { error ->
                        emitter.onError(error)
                    }
                )
        }
    }

    private fun CampaignDto.map(): Campaign =
        Campaign(
            name = name,
            description = description,
            image = image.mapImage()
        )
}

fun ImageDto.mapImage(): ImageModel = ImageModel(url = this.url)
