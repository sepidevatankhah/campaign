package de.westwing.campaignbrowser.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.westwing.campaignbrowser.databinding.ItemCampaignBinding
import de.westwing.campaignbrowser.domain.Campaign

class CampaignListAdapter: ListAdapter<Campaign, CampaignViewHolder>(campaignDiff) {

    companion object {
        val campaignDiff = object: DiffUtil.ItemCallback<Campaign>() {
            override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign) = oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        return CampaignViewHolder(
            ItemCampaignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        // TODO
    }
}

class CampaignViewHolder(internal val binding: ItemCampaignBinding): RecyclerView.ViewHolder(binding.root)