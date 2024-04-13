package com.creativedesignproject.kumoh_board_backend.Auth.service.serviceimpl;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.creativedesignproject.kumoh_board_backend.Auth.common.CertificationNumber;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.request.CheckCertificationRequestDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.request.EmailCertificationRequestDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.request.NicknameCheckRequestDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.request.SignInRequestDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.request.SignUpRequestDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.CheckCertificationResponseDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.EmailCertificationResponseDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.NicknameCheckResponseDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.ResponseDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.SignInResponseDto;
import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.SignUpResponseDto;
import com.creativedesignproject.kumoh_board_backend.Auth.entity.CertificationEntity;
import com.creativedesignproject.kumoh_board_backend.Auth.entity.UserEntity;
import com.creativedesignproject.kumoh_board_backend.Auth.provider.EmailProvider;
import com.creativedesignproject.kumoh_board_backend.Auth.provider.JwtProvider;
import com.creativedesignproject.kumoh_board_backend.Auth.service.AuthService;
import com.creativedesignproject.kumoh_board_backend.mapper.AuthMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthMapper authMapper;
    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super NicknameCheckResponseDto> nickNameCheck(NicknameCheckRequestDto dto) {
        try {
            String userNickname = dto.getUserNickname();
            boolean isExistNickname = authMapper.existsByUserNickname(userNickname); // repository에 유저가 존재하는지 확인하고
            if (isExistNickname) return NicknameCheckResponseDto.duplicateNickname(); // 만약 있다면 중복된 닉네임라고 반환

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return NicknameCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getUserId();
            String email = dto.getEmail();

            boolean isExistUserId = authMapper.existsByUserId(userId);
            if (isExistUserId) return EmailCertificationResponseDto.duplicateId();

            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccessed) return EmailCertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = CertificationEntity.builder()
                        .certification_email(email)
                        .certification_number(certificationNumber)
                        .build();
            
            authMapper.certificationSave(certificationEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = authMapper.findByUserEmail(email);
            if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();

            boolean isMatched = certificationEntity.getCertification_email().equals(email)
                    && certificationEntity.getCertification_number().equals(certificationNumber);
            if (!isMatched)
                return CheckCertificationResponseDto.certificationFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String userId = dto.getUserId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            boolean isExistId = authMapper.existsByUserId(userId);
            if (isExistId) return SignUpResponseDto.duplicateId();

            CertificationEntity certificationEntity = authMapper.findByUserEmail(email);
            boolean isMatched = certificationEntity.getCertification_email().equals(email)
                    && certificationEntity.getCertification_number().equals(certificationNumber);
            if (!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);

            UserEntity userEntity = UserEntity.builder()
                    .user_id(userId)
                    .password(encodedPassword)
                    .nickname(dto.getNickName())
                    .email(email)
                    .isAdmin(false)
                    .role("ROLE_USER")
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .profile_image(dto.getProfileImage())
                    .build();
            
            authMapper.UserSave(userEntity);

            authMapper.deleteByCertificationUserEmail(email); // 회원가입 하고 certification 테이블에 있는 이메일 삭제

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String token = null;
        try {
            String userId = dto.getUserId();
            UserEntity userEntity = authMapper.findByUserId(userId);
            if (userEntity == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFail();

            token = jwtProvider.create(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(token);
    }
    
}
