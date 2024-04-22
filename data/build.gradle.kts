plugins{
    id("local.library")
}

android{
    namespace = "me.pegbeer.filmku.data"
}

dependencies{
    api(libs.kotlin)
    api(libs.bundles.retrofit)
    api(libs.bundles.koin)
    api(libs.bundles.room)
    api(libs.paging.runtime)

    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    testApi(libs.junit)
    testApi(libs.test.coroutines)
    testApi(libs.paging.common)


    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
}