package com.ahmedvargos.favorites.utils

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.local.entities.AgentEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

fun createTempEmissionsFlow(isSuccess: Boolean = true): Flow<Resource<List<AgentInfo>>> {
    return flowOf(
        if (isSuccess)
            Resource.Success(createTempAgentList(), DataSource.CACHE)
        else
            Resource.Failure(createTempFailureData())
    )
}

fun createListOfAgentEntities(): List<AgentEntity> {
    return createTempAgentList().map { agent ->
        AgentEntity(id = agent.uuid, isFav = agent.isFav, data = agent)
    }
}

fun createTempAgentList(): List<AgentInfo> {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val typeCustom = Types.newParameterizedType(List::class.java, AgentInfo::class.java)
    val agentsList = moshi.adapter<List<AgentInfo>>(typeCustom).fromJson(agentsTempJson)
    return agentsList ?: mutableListOf()
}

fun createTempBoolEmissionsFlow(isSuccess: Boolean): Flow<Resource<Boolean>> {
    return flow {
        emit(
            if (isSuccess)
                Resource.Success(true, DataSource.CACHE)
            else
                Resource.Failure(createTempFailureData())
        )
    }
}

fun createTempFailureData() = FailureData(999, "Generic error")

/* ktlint-disable max-line-length */
val agentsTempJson = " [\n" +
        "    {\n" +
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
        "    },\n" +
        "    {\n" +
        "      \"uuid\": \"f94c3b30-42be-e959-889c-5aa313dba261\",\n" +
        "      \"displayName\": \"Raze\",\n" +
        "      \"description\": \"Raze explodes out of Brazil with her big personality and big guns. With her blunt-force-trauma playstyle, she excels at flushing entrenched enemies and clearing tight spaces with a generous dose of \\\"boom\\\".\",\n" +
        "      \"developerName\": \"Clay\",\n" +
        "      \"characterTags\": [\n" +
        "        \"Area Damage Specialist\"\n" +
        "      ],\n" +
        "      \"displayIcon\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/displayicon.png\",\n" +
        "      \"displayIconSmall\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/displayiconsmall.png\",\n" +
        "      \"bustPortrait\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/bustportrait.png\",\n" +
        "      \"fullPortrait\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/fullportrait.png\",\n" +
        "      \"assetPath\": \"ShooterGame/Content/Characters/Clay/Clay_PrimaryAsset\",\n" +
        "      \"isFullPortraitRightFacing\": true,\n" +
        "      \"isPlayableCharacter\": true,\n" +
        "      \"isAvailableForTest\": true,\n" +
        "      \"role\": {\n" +
        "        \"uuid\": \"dbe8757e-9e92-4ed4-b39f-9dfc589691d4\",\n" +
        "        \"displayName\": \"Duelist\",\n" +
        "        \"description\": \"Duelists are self-sufficient fraggers who their team expects, through abilities and skills, to get high frags and seek out engagements first.\",\n" +
        "        \"displayIcon\": \"https://media.valorant-api.com/agents/roles/dbe8757e-9e92-4ed4-b39f-9dfc589691d4/displayicon.png\",\n" +
        "        \"assetPath\": \"ShooterGame/Content/Characters/_Core/Roles/Assault_PrimaryDataAsset\"\n" +
        "      },\n" +
        "      \"abilities\": [\n" +
        "        {\n" +
        "          \"slot\": \"Ability1\",\n" +
        "          \"displayName\": \"Blast Pack\",\n" +
        "          \"description\": \"INSTANTLY throw a Blast Pack that will stick to surfaces. RE-USE the ability after deployment to detonate, damaging and moving anything hit.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/abilities/ability1/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ability2\",\n" +
        "          \"displayName\": \"Paint Shells\",\n" +
        "          \"description\": \"EQUIP a cluster grenade. FIRE to throw the grenade, which does damage and creates sub-munitions, each doing damage to anyone in their range.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/abilities/ability2/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Grenade\",\n" +
        "          \"displayName\": \"Boom Bot\",\n" +
        "          \"description\": \"EQUIP a Boom Bot. FIRE will deploy the bot, causing it to travel in a straight line on the ground, bouncing off walls. The Boom Bot will lock on to any enemies in its frontal cone and chase them, exploding for heavy damage if it reaches them.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/abilities/grenade/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ultimate\",\n" +
        "          \"displayName\": \"Showstopper\",\n" +
        "          \"description\": \"EQUIP a rocket launcher. FIRE shoots a rocket that does massive area damage on contact with anything.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/f94c3b30-42be-e959-889c-5aa313dba261/abilities/ultimate/displayicon.png\"\n" +
        "        }\n" +
        "      ]\n" +
        "    },\n" +
        "    {\n" +
        "      \"uuid\": \"6f2a04ca-43e0-be17-7f36-b3908627744d\",\n" +
        "      \"displayName\": \"Skye\",\n" +
        "      \"description\": \"Hailing from Australia, Skye and her band of beasts trailblaze the way through hostile territory. With her creations hampering the enemy, and her power to heal others, the team is strongest and safest by Skye's side.\",\n" +
        "      \"developerName\": \"Guide\",\n" +
        "      \"characterTags\": null,\n" +
        "      \"displayIcon\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/displayicon.png\",\n" +
        "      \"displayIconSmall\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/displayiconsmall.png\",\n" +
        "      \"bustPortrait\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/bustportrait.png\",\n" +
        "      \"fullPortrait\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/fullportrait.png\",\n" +
        "      \"assetPath\": \"ShooterGame/Content/Characters/Guide/Guide_PrimaryAsset\",\n" +
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
        "          \"displayName\": \"Trailblazer\",\n" +
        "          \"description\": \"EQUIP a Tasmanian tiger trinket. FIRE to send out and take control of the predator.  While in control, FIRE to leap forward, exploding in a concussive blast and damaging directly hit enemies.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/abilities/ability1/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ability2\",\n" +
        "          \"displayName\": \"Guiding Light\",\n" +
        "          \"description\": \"EQUIP a hawk trinket.  FIRE to send it forward.  HOLD FIRE to guide the hawk in the direction of your crosshair. RE-USE while the hawk is in flight to transform it into a flash.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/abilities/ability2/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Grenade\",\n" +
        "          \"displayName\": \"Regrowth\",\n" +
        "          \"description\": \"EQUIP a healing trinket.  HOLD FIRE to channel, healing allies in range and line of sight.  Can be reused until her healing pool is depleted.  Skye cannot heal herself.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/abilities/grenade/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ultimate\",\n" +
        "          \"displayName\": \"Seekers\",\n" +
        "          \"description\": \"EQUIP a Seeker trinket.  FIRE to send out three Seekers to track down the three closest enemies.  If a Seeker reaches its target, it nearsights them.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/6f2a04ca-43e0-be17-7f36-b3908627744d/abilities/ultimate/displayicon.png\"\n" +
        "        }\n" +
        "      ]\n" +
        "    },\n" +
        "    {\n" +
        "      \"uuid\": \"117ed9e3-49f3-6512-3ccf-0cada7e3823b\",\n" +
        "      \"displayName\": \"Cypher\",\n" +
        "      \"description\": \"The Moroccan information broker, Cypher is a one-man surveillance network who keeps tabs on the enemy's every move. No secret is safe. No maneuver goes unseen. Cypher is always watching.\",\n" +
        "      \"developerName\": \"Gumshoe\",\n" +
        "      \"characterTags\": [\n" +
        "        \"Detection\",\n" +
        "        \"Defensive Lockdown\"\n" +
        "      ],\n" +
        "      \"displayIcon\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/displayicon.png\",\n" +
        "      \"displayIconSmall\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/displayiconsmall.png\",\n" +
        "      \"bustPortrait\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/bustportrait.png\",\n" +
        "      \"fullPortrait\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/fullportrait.png\",\n" +
        "      \"assetPath\": \"ShooterGame/Content/Characters/Gumshoe/Gumshoe_PrimaryAsset\",\n" +
        "      \"isFullPortraitRightFacing\": true,\n" +
        "      \"isPlayableCharacter\": true,\n" +
        "      \"isAvailableForTest\": true,\n" +
        "      \"role\": {\n" +
        "        \"uuid\": \"5fc02f99-4091-4486-a531-98459a3e95e9\",\n" +
        "        \"displayName\": \"Sentinel\",\n" +
        "        \"description\": \"Sentinels are defensive experts who can lock down areas and watch flanks, both on attacker and defender rounds.\",\n" +
        "        \"displayIcon\": \"https://media.valorant-api.com/agents/roles/5fc02f99-4091-4486-a531-98459a3e95e9/displayicon.png\",\n" +
        "        \"assetPath\": \"ShooterGame/Content/Characters/_Core/Roles/Sentinel_PrimaryDataAsset\"\n" +
        "      },\n" +
        "      \"abilities\": [\n" +
        "        {\n" +
        "          \"slot\": \"Ability1\",\n" +
        "          \"displayName\": \"Cyber Cage\",\n" +
        "          \"description\": \"INSTANTLY toss the cyber cage in front of Cypher. ACTIVATE to create a zone that blocks vision and plays an audio cue when enemies pass through it\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/abilities/ability1/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ability2\",\n" +
        "          \"displayName\": \"Spycam\",\n" +
        "          \"description\": \"EQUIP a spycam. FIRE to place the spycam at the targeted location. RE-USE this ability to take control of the camera's view. While in control of the camera, FIRE to shoot a marking dart. This dart will reveal the location of any player struck by the dart.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/abilities/ability2/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Grenade\",\n" +
        "          \"displayName\": \"Trapwire\",\n" +
        "          \"description\": \"EQUIP a trapwire. FIRE to place a destructible and covert tripwire at the targeted location, creating a line that spans between the placed location and the wall opposite. Enemy players who cross a tripwire will be tethered, revealed, and dazed after a short period if they do not destroy the device in time. This ability can be picked up to be REDEPLOYED.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/abilities/grenade/displayicon.png\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"slot\": \"Ultimate\",\n" +
        "          \"displayName\": \"Neural Theft\",\n" +
        "          \"description\": \"INSTANTLY use on a dead enemy player in your crosshairs to reveal the location of all living enemy players.\",\n" +
        "          \"displayIcon\": \"https://media.valorant-api.com/agents/117ed9e3-49f3-6512-3ccf-0cada7e3823b/abilities/ultimate/displayicon.png\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  ]"
