// In BurracoApplication.kt
package com.burraco.app // Assicurati che il package sia corretto

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BurracoApplication : Application()