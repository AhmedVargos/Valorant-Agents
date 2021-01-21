package com.ahmedvargos.agents_list.data.data_sources.remote

import com.ahmedvargos.base.data.AgentsResponse
import retrofit2.http.GET

interface AgentsListApi {
    @GET("agents")
    suspend fun getPopularAgents(): AgentsResponse
}