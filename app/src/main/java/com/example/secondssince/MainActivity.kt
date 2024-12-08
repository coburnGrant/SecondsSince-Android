package com.example.secondssince

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.secondssince.data.AppDatabase
import com.example.secondssince.data.UserMediaRepository
import com.example.secondssince.data.UserPreferencesRepository
import com.example.secondssince.ui.SecondsSinceApp
import com.example.secondssince.ui.theme.SecondsSinceTheme
import com.example.secondssince.ui.viewModel.CreateNewLoveViewModel
import com.example.secondssince.ui.viewModel.LoveListViewModel
import com.example.secondssince.ui.viewModel.PreferencesViewModel

private const val SECOND_SINCE_PREFERENCES_NAME = "seconds_since_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SECOND_SINCE_PREFERENCES_NAME
)

class MainActivity : ComponentActivity() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferencesRepository = UserPreferencesRepository(dataStore)

        val preferencesViewModel = PreferencesViewModel(
            userPreferencesRepository = userPreferencesRepository
        )

        enableEdgeToEdge()

        val loveDao = AppDatabase.getDatabase(applicationContext).loveDao()
        val mediaRepository = UserMediaRepository(applicationContext)

        val loveListViewModel = LoveListViewModel(
            loveDao = loveDao,
            userMediaRepository = mediaRepository,
            selectedLove = null
        )

        val createNewLoveViewModel = CreateNewLoveViewModel()

        // Register the content picker
        val getContent = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            Log.i("imageSelection", "selected uri $uri")
            createNewLoveViewModel.updateSelectedImageUri(uri)
        }

        createNewLoveViewModel.setGetContent(getContent)

        // Pass the function as part of the state for composable usage
        setContent {
            SecondsSinceTheme {
                SecondsSinceApp(
                    loveListViewModel = loveListViewModel,
                    createNewLoveViewModel = createNewLoveViewModel,
                    preferencesViewModel = preferencesViewModel
                )
            }
        }
    }
}