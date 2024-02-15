# This To Do application build using kotlin, Room Database and followed by MVVM architecture.

<img src="https://github.com/ArjunGupta08/To-Do-MVVM-Kotlin/assets/85922120/e2837788-d2d8-4a39-a9af-6aa1279cd29a" width="550">

## Project Setup

    id 'kotlin-kapt'

    //Room
    implementation("androidx.room:room-runtime:2.5.0")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //Coroutines   
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

