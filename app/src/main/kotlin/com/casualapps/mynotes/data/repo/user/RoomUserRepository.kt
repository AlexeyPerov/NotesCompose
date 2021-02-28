package com.casualapps.mynotes.data.repo.user

import com.casualapps.mynotes.data.database.dao.UserDao
import com.casualapps.mynotes.data.mapper.UserMapper
import com.casualapps.mynotes.data.entities.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class RoomUserRepository @Inject constructor(private val userDao: UserDao, private val userMapper: UserMapper) :
    UserRepository {
    override suspend fun insertUser(user: User) =
        withContext(Dispatchers.IO) {
            userDao.insertUser(userMapper.toEntity(user))
        }

    override suspend fun isValidUser(user: User) = withContext(Dispatchers.IO) {
        val loggedInUser = userDao.login(user.name, user.password)
        loggedInUser?.id ?: -1
    }

    override suspend fun userExists(user: User) = withContext(Dispatchers.IO) {
        userDao.isUserExists(user.name) != null
    }

    override suspend fun fetchUser(id: Long) = withContext(Dispatchers.IO) {
        userDao.fetchUser(id)?.let {
            userMapper.toDomain(it)
        }
    }
}
