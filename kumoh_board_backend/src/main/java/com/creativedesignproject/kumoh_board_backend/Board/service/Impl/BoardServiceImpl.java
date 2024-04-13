package com.creativedesignproject.kumoh_board_backend.Board.service.Impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.creativedesignproject.kumoh_board_backend.Auth.dto.response.ResponseDto;
import com.creativedesignproject.kumoh_board_backend.Board.dto.response.GetBoardResponseDto;
import com.creativedesignproject.kumoh_board_backend.Board.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        // GetBoardResultSet resultSet = null;
        // List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            // resultSet = boardRepository.getBoard(boardNumber);
            // if (resultSet == null) return GetBoardResponseDto.notExistBoard();

            // imageEntities = imageRepository.findByBoardNumber(boardNumber);

            // BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            // boardEntity.increaseViewCount();
            // boardRepository.save(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return null;
    }
    
}
