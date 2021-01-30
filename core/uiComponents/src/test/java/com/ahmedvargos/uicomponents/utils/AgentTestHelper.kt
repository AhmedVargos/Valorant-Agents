package com.ahmedvargos.uicomponents.utils

import com.ahmedvargos.base.data.AgentInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

internal fun createTempAgent(): AgentInfo {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val typeCustom = Types.newParameterizedType(AgentInfo::class.java, AgentInfo::class.java)
    val agentsList = moshi.adapter<AgentInfo>(typeCustom).fromJson(agentsTempJson)
    return agentsList!!
}

/* ktlint-disable max-line-length */
val agentsTempJson = "    {\n" +
        "      \"uuid\": \"5f8d3a7f-467b-97f3-062c-13acf203c006\",\n" +
        "      \"displayName\": \"Breach\",\n" +
        "      \"description\": \"The bionic Swede Breach fires powerful, targeted kinetic blasts to aggressively clear a path through enemy ground. The damage and disruption he inflicts ensures no fight is ever fair.\",\n" +
        "      \"developerName\": \"Breach\",\n" +
        "      \"characterTags\": null,\n" +
        "      \"displayIcon\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/displayicon.png\",\n" +
        "      \"displayIconSmall\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/displayiconsmall.png\",\n" +
        "      \"bustPortrait\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/bustportrait.png\",\n" +
        "      \"fullPortrait\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/fullportrait.png\",\n" +
        "      \"assetPath\": \"ShooterGame/Content/Characters/Breach/Breach_PrimaryAsset\",\n" +
        "      \"isFullPortraitRightFacing\": false,\n" +
        "      \"isPlayableCharacter\": true,\n" +
        "      \"isAvailableForTest\": true,\n" +
        "      \"role\": {\n" +
        "        \"uuid\": \"1b47567f-8f7b-444b-aae3-b0c634622d10\",\n" +
        "        \"displayName\": \"Initiator\",\n" +
        "        \"description\": \"Initiators challenge angles by setting up their team to enter contested ground and push defenders away.\",\n" +
        "        \"displayIcon\": \"https://media.valorant-api.com/agents/roles/1b47567f-8f7b-444b-aae3-b0c634622d10/displayicon.png\",\n" +
        "        \"assetPath\": \"ShooterGame/Content/Characters/_Core/Roles/Breaker_PrimaryDataAsset\"\n" +
        "      },\n" +
        "      \"abilities\": [\n" +
        "        {\n" +
        "          \"slot\": \"Ability1\",\n" +
        "          \"displayName\": \"Flashpoint\",\n" +
        "          \"description\": \"EQUIP a blinding charge. FIRE the charge to set a fast-acting burst through the wall. The charge detonates to blind all players looking at it.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/abilities/ability1/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ability2\",\n" +
        "          \"displayName\": \"Fault Line\",\n" +
        "          \"description\": \"EQUIP a seismic blast. HOLD FIRE to increase the distance. RELEASE to set off the quake, dazing all players in its zone and in a line up to the zone.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/abilities/ability2/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Grenade\",\n" +
        "          \"displayName\": \"Aftershock\",\n" +
        "          \"description\": \"EQUIP a fusion charge. FIRE the charge to set a slow-acting burst through the wall. The burst does heavy damage to anyone caught in its area.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/abilities/grenade/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ultimate\",\n" +
        "          \"displayName\": \"Rolling Thunder\",\n" +
        "          \"description\": \"EQUIP a Seismic Charge. FIRE to send a cascading quake through all terrain in a large cone. The quake dazes and knocks up anyone caught in it.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/5f8d3a7f-467b-97f3-062c-13acf203c006/abilities/ultimate/displayicon.png\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }"
