package com.historialplus.historialplus.record.service;

import com.historialplus.historialplus.record.dto.request.RecordCreateDto;
import com.historialplus.historialplus.record.dto.response.RecordResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecordService {
    List<RecordResponseDto> findAll();

    Optional<RecordResponseDto> findById(UUID id);

    RecordCreateDto save(RecordCreateDto recordCreateDto);

    UUID findPersonIdByDocumentNumber(String documentNumber);
}
