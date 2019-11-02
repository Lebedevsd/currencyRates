package com.lebedevsd.currencyrates.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Scope of resources which should be scoped by Activity
 */
@Retention(RetentionPolicy.RUNTIME)
@Scope
annotation class PerActivity

/**
 * Scope of resources which should be scoped by Fragment
 */
@Retention(RetentionPolicy.RUNTIME)
@Scope
annotation class PerFragment