package com.example.outdoor

import android.view.KeyEvent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.outdoor.models.DialogInfo
import com.example.outdoor.models.RentalListData
import com.example.outdoor.view_models.RentalViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.outdoor", appContext.packageName)
    }

    @Test
    fun rentalListTest() {
        val viewModel = RentalViewModel()
        val event = KeyEvent(KeyEvent.ACTION_DOWN, 0)
        viewModel.processEnteredKey(KeyEvent.KEYCODE_ENTER, event, "trailer")
        TimeUnit.MILLISECONDS.sleep(15000)
        val data = viewModel.rentalListData.value as ArrayList<RentalListData>

        assertEquals(
            data[1].imageUrl,
            "https://res.cloudinary.com/outdoorsy/image/upload/v1595306989/p/rentals/175858/images/suootiifr7uqopxbronv.jpg"
        )
        assertEquals(data[1].name, "Clipper - Kids Approved Trailer")
    }

    @Test
    fun rentalNoListTest() {
        val viewModel = RentalViewModel()
        val event = KeyEvent(KeyEvent.ACTION_DOWN, 0)
        viewModel.processEnteredKey(KeyEvent.KEYCODE_ENTER, event, "xyzzyzzz")
        TimeUnit.MILLISECONDS.sleep(15000)
        val dialogInfo = viewModel.displayDialog.value as DialogInfo
        assertEquals(dialogInfo.title, R.string.no_results_title)
        assertEquals(dialogInfo.notification, R.string.no_results_description)
        assertEquals(dialogInfo.positiveText, R.string.no_results_positive)
        assertEquals(dialogInfo.displayNegativeButton, false)
        assertEquals(dialogInfo.negativeText, 0)
    }

    @Test
    fun testSearchIconSelection() {
        val viewModel = RentalViewModel()
        viewModel.processSearchIconSelection()
        TimeUnit.MILLISECONDS.sleep(500)
        val keyboardShown = viewModel.showKeyboard.value as Boolean
        assertEquals(keyboardShown, true)
    }

    @Test
    fun testSearchFieldSelection() {
        val viewModel = RentalViewModel()
        viewModel.processSearchFieldSelection()
        TimeUnit.MILLISECONDS.sleep(500)
        val keyboardShown = viewModel.showKeyboard.value as Boolean
        assertEquals(keyboardShown, true)
    }
}
