package com.ahmedvargos.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.ahmedvargos.local.AppDatabase
import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.dao.FavoritesDao
import com.ahmedvargos.local.utils.createListOfAgentEntities
import com.ahmedvargos.local.utils.deepEqualTo
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class FavoritesDaoTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var database: AppDatabase
    private lateinit var favoritesDao: FavoritesDao
    private lateinit var agentsDao: AgentsDao

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)

        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()

        favoritesDao = database.favoritesDao()
        agentsDao = database.agentsDao()
    }

    @Test
    fun givenListOfAgentsEntitiesWithFavStatus_WhenInsertAll_ThenGetFavsCorrectly() =
        runBlocking {
            // Arrange
            val insertedList = createListOfAgentEntities()
            for (i in 0..1)
                insertedList[i].apply {
                    isFav = true
                    data.isFav = true
                }
            val expectedFavsList = mutableListOf(insertedList[0], insertedList[1])
            // Act
            agentsDao.insertAll(insertedList)
            val result = favoritesDao.getAllFavorites().asLiveData().test()
            // Assert
            Assert.assertEquals(result.value().size, 2)
            Assert.assertTrue(result.value().deepEqualTo(expectedFavsList))
        }
}
