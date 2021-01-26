package com.ahmedvargos.local.entities

import androidx.room.TypeConverter
import com.ahmedvargos.base.data.AgentInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AgentInfoConverter {
    @TypeConverter
    fun fromString(value: String): AgentInfo? {
        return if (!value.contentEquals("null")) {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val typeCustom =
                Types.newParameterizedType(AgentInfo::class.java, AgentInfo::class.java)
            moshi.adapter<AgentInfo>(typeCustom).fromJson(value)
        } else null
    }

    @TypeConverter
    fun fromHomeResponse(agentInfo: AgentInfo?): String? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val typeCustom = Types.newParameterizedType(AgentInfo::class.java, AgentInfo::class.java)
        return moshi.adapter<AgentInfo>(typeCustom).toJson(agentInfo)
    }
}
