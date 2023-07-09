package com.example.movietvshowapp.di

import android.content.Context
import androidx.room.Room
import com.example.movietvshowapp.data.local.AccountDatabase
import com.example.movietvshowapp.data.local.DetailsDatabase
import com.example.movietvshowapp.data.local.MovieDatabase
import com.example.movietvshowapp.data.local.SearchDatabase
import com.example.movietvshowapp.data.local.TVDatabase
import com.example.movietvshowapp.util.Constants.ACCOUNT_DATABASE
import com.example.movietvshowapp.util.Constants.DETAILS_DATABASE
import com.example.movietvshowapp.util.Constants.MOVIE_DATABASE
import com.example.movietvshowapp.util.Constants.SEARCH_DATABASE
import com.example.movietvshowapp.util.Constants.TV_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase{
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MOVIE_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideTVDatabase(
        @ApplicationContext context: Context
    ): TVDatabase{
        return Room.databaseBuilder(
            context,
            TVDatabase::class.java,
            TV_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchDatabase(
        @ApplicationContext context: Context
    ): SearchDatabase{
        return Room.databaseBuilder(
            context,
            SearchDatabase::class.java,
            SEARCH_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideDetailsDatabase(
        @ApplicationContext context: Context
    ): DetailsDatabase{
        return Room.databaseBuilder(
            context,
            DetailsDatabase::class.java,
            DETAILS_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideAccountDatabase(
        @ApplicationContext context: Context
    ): AccountDatabase{
        return Room.databaseBuilder(
            context,
            AccountDatabase::class.java,
            ACCOUNT_DATABASE
        ).build()
    }
}