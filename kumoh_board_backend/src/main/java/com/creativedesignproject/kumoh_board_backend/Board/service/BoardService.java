package com.creativedesignproject.kumoh_board_backend.Board.service;

import org.springframework.http.ResponseEntity;
import com.creativedesignproject.kumoh_board_backend.Board.dto.response.GetBoardResponseDto;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
}
