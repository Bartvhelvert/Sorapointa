plugins {
    id("sorapointa-conventions")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:_")

    implementation(KotlinX.serialization.json)
    implementation("org.jetbrains.kotlinx:atomicfu:_")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:_")
}
