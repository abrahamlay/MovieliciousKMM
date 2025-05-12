package com.abrahamlay.movielicious.kmm.movie.di

import Greeting
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf<Greeting>(::Greeting)
}