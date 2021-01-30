package com.ahmedvargos.uicomponents.utils

import android.view.View
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
internal class ExtensionsKtTest {

    @Test
    fun viewGone_whenViewIsVisible_shouldMakeViewGone() {
        // Arrange
        val view = View(InstrumentationRegistry.getInstrumentation().targetContext)
        view.visibility = View.VISIBLE
        // Act
        view.gone()
        // Assert
        assertEquals(view.visibility, View.GONE)
    }

    @Test
    fun viewGone_whenViewIsGone_shouldRemainGone() {
        // Arrange
        val view = View(InstrumentationRegistry.getInstrumentation().targetContext)
        view.visibility = View.GONE
        // Act
        view.gone()
        // Assert
        assertEquals(view.visibility, View.GONE)
    }

    @Test
    fun viewVisible_whenViewIsGone_shouldMakeViewVisible() {
        // Arrange
        val view = View(InstrumentationRegistry.getInstrumentation().targetContext)
        view.visibility = View.GONE
        // Act
        view.visible()
        // Assert
        assertEquals(view.visibility, View.VISIBLE)
    }

    @Test
    fun viewVisible_whenViewIsVisible_shouldRemainVisible() {
        // Arrange
        val view = View(InstrumentationRegistry.getInstrumentation().targetContext)
        view.visibility = View.VISIBLE
        // Act
        view.visible()
        // Assert
        assertEquals(view.visibility, View.VISIBLE)
    }
}
