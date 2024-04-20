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
    testApi(libs.junit)
    testApi(libs.test.coroutines)
}