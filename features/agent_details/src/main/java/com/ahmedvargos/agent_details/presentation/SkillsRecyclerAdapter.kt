package com.ahmedvargos.agent_details.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedvargos.agent_details.databinding.ItemAgentSkillBinding
import com.ahmedvargos.base.data.Ability
import com.ahmedvargos.uicomponents.utils.loadImage

class SkillsRecyclerAdapter(
    private var skillsList: MutableList<Ability> = mutableListOf(),
) : RecyclerView.Adapter<SkillsRecyclerAdapter.SkillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = ItemAgentSkillBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SkillViewHolder(binding)
    }

    fun addSkills(newSkillsList: List<Ability>) {
        skillsList.clear()
        skillsList.addAll(newSkillsList)

        notifyDataSetChanged()
    }

    override fun getItemCount() = skillsList.size

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) =
        holder.bind(skillsList[position])

    inner class SkillViewHolder(private val binding: ItemAgentSkillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(skill: Ability) {
            binding.tvSkillName.text = skill.displayName
            binding.tvSkillDesc.text = skill.description
            binding.ivSkill.loadImage(skill.displayIcon)
        }
    }
}