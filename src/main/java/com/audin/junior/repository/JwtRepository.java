package com.audin.junior.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audin.junior.entity.Jwt;

@Repository
public interface JwtRepository extends CrudRepository<Jwt, Integer>{
    Optional<Jwt> findByTokenAndDesactivateAndExpire(String token, boolean desactivate, boolean expire);

    @Query("FROM Jwt j WHERE j.expire = :expire AND j.desactivate = :desactivate AND j.user.email = :email")
    Optional<Jwt> findByUserValidToken(String email, boolean desactivate, boolean expire);

    @Query("FROM Jwt j WHERE j.user.email = :email AND j.expire = false AND j.desactivate = false")
    Stream<Jwt> findByUser(String email);

    @Query("FROM Jwt j where j.expire = false AND j.refreshToken.token = :refreshTokenMap")
    Optional<Jwt> findByRefrechToken(String refreshTokenMap);

    void deleteByExpireAndDesactivate(boolean expire, boolean desactivate);
}
