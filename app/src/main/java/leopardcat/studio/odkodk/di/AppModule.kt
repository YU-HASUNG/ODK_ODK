package leopardcat.studio.odkodk.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leopardcat.studio.odkodk.data.local.BookDatabase
import leopardcat.studio.odkodk.data.local.BookEntity
import leopardcat.studio.odkodk.data.remote.BookApi
import leopardcat.studio.odkodk.data.remote.BookRemoteMediator
import leopardcat.studio.odkodk.util.Preference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@OptIn(ExperimentalPagingApi::class)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "books.db"
        ).build()
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideBookApi(): BookApi {
        return Retrofit.Builder()
            .baseUrl(BookApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): Preference {
        return Preference(context)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DateQualifier

    @Provides
    @Singleton
    @DateQualifier
    fun provideDate(preference: Preference): String {
        return preference.getDate("date")
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SexQualifier
    @Provides
    @Singleton
    @SexQualifier
    fun provideSex(preference: Preference): String? {
        val sex = if((preference.getSex("sex") == 8888) || (preference.getSex("sex") == 9999)) {
            null
        } else {
            preference.getSex("sex").toString()
        }
        return sex
    }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AgeQualifier
    @Provides
    @Singleton
    @AgeQualifier
    fun provideAge(preference: Preference): String? {
        val age = if((preference.getAge("age") == 8888) || (preference.getAge("age") == 9999)) {
            null
        } else {
            preference.getAge("age").toString()
        }
        return age
    }

    @Provides
    @Singleton
    fun provideBookPager(bookDb: BookDatabase, bookApi: BookApi, @DateQualifier date: String, @SexQualifier sex: String?, @AgeQualifier age: String?): Pager<Int, BookEntity> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = BookRemoteMediator(
                bookDb = bookDb,
                bookApi = bookApi,
                date = date,
                gender = sex,
                age = age
            ),
            pagingSourceFactory = {
                bookDb.dao.pagingSource()
            }
        )
    }
}