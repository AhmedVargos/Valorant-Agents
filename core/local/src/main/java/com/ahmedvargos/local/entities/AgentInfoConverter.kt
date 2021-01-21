package com.ahmedvargos.local.entities

import androidx.room.TypeConverter
import com.ahmedvargos.base.data.AgentInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AgentInfoConverter {
    @TypeConverter
    fun fromString(value: String): AgentInfo? {
        return if (!value.contentEquals("null")) {
            val type: Type =
                object : TypeToken<AgentInfo?>() {}.type

            Gson().fromJson(value, type)
        } else null
    }

    @TypeConverter
    fun fromHomeResponse(agentInfo: AgentInfo?): String? {
        return Gson().toJson(agentInfo)
    }
}