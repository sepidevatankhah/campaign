package de.westwing.campaignbrowser.presentation.list.adapter

import androidx.recyclerview.widget.RecyclerView
import de.westwing.campaignbrowser.databinding.ItemCampaignBinding
import de.westwing.campaignbrowser.domain.model.Campaign
import de.westwing.campaignbrowser.presentation.list.ClickListener
import de.westwing.campaignbrowser.presentation.utils.loadUrl

class CampaignViewHolder(
    val binding: ItemCampaignBinding,
    private val onItemClicked: ClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Campaign) {
        with(binding) {
            model.apply {
                campaignName.text = name
                campaignImage.loadUrl(image.url)
                root.setOnClickListener { onItemClicked.invoke(this) }
            }
        }
    }
}