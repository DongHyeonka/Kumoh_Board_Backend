package com.creativedesignproject.kumoh_board_backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.creativedesignproject.kumoh_board_backend.Auth.entity.CertificationEntity;
import com.creativedesignproject.kumoh_board_backend.Auth.entity.UserEntity;

@Mapper
public interface AuthMapper {
    public boolean existsByUserNickname(String nickname);
    public boolean existsByUserId(String userId);
    public int certificationSave(CertificationEntity certificationEntity);
    public CertificationEntity findByUserEmail(String email);
    public int UserSave(UserEntity entity);
    public int deleteByCertificationUserEmail(String email);
    public UserEntity findByUserId(String userId);
}
