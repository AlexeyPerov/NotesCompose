package com.casualapps.mynotes.data.mapper

import com.casualapps.mynotes.data.entities.UserEntity
import com.casualapps.mynotes.data.entities.User
import javax.inject.Inject

class UserMapper @Inject constructor() : EntityMapper<UserEntity, User> {

    override fun toDomain(entity: UserEntity): User {
        return User(entity.name, entity.password)
    }

    override fun toEntity(domain: User): UserEntity {
        return UserEntity(domain.name, domain.password)
    }
}
