package com.abrahamlay.movielicious.kmm.movie.di

import Platform
import getPlatform
import org.koin.dsl.module

val platformModule = module {
    single { getPlatform() }
}
