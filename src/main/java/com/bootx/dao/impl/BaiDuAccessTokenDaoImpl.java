package com.bootx.dao.impl;

import com.bootx.dao.BaiDuAccessTokenDao;
import com.bootx.entity.BaiDuAccessToken;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class BaiDuAccessTokenDaoImpl extends BaseDaoImpl<BaiDuAccessToken,Long> implements BaiDuAccessTokenDao {
    @Override
    public String getToken() {
        try {
            String jpql = "select baiDuAccessToken from BaiDuAccessToken baiDuAccessToken order by baiDuAccessToken.id desc limit 1";
            return entityManager.createQuery(jpql, BaiDuAccessToken.class).getSingleResult().getAccessToken();
        } catch (NoResultException e) {
            return null;
        }
    }
}
