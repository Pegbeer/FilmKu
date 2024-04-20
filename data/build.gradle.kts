plugins{
    id("local.library")
}

android{
    namespace = "me.pegbeer.filmku.data"
}

dependencies{
    api(libs.kotlin)
    api(libs.bundles.retrofit)
}