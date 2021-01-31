package com.ahmedvargos.local.db

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.ahmedvargos.local.AppDatabase
import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.utils.createListOfAgentEntities
import com.ahmedvargos.local.utils.deepEqualTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class AgentsDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var agentsDao: AgentsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()

        agentsDao = database.agentsDao()
    }

    @Test
    fun givenListOfAgentsEntities_WhenInsertAll_ThenListIsSavedCorrectly() =
        runBlocking {
            // Arrange
            val expectedListOfAgents = createListOfAgentEntities()
            // Act
            agentsDao.insertAll(createListOfAgentEntities())
            val result = agentsDao.getAll()
            // Assert
            Assert.assertEquals(result.size, expectedListOfAgents.size)
            Assert.assertTrue(result.deepEqualTo(expectedListOfAgents))
        }

    @Test
    fun givenListOfAgentsEntities_WhenInsertAllAndGetSingleAgent_ThenReturnExpectedAgent() =
        runBlocking {
            // Arrange
            val expectedAgent = createListOfAgentEntities()[3]
            // Act
            agentsDao.insertAll(createListOfAgentEntities())
            val result = agentsDao.getAgent(expectedAgent.id)
            // Assert
            Assert.assertEquals(result, expectedAgent)
        }
}
