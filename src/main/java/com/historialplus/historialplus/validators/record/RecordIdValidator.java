package com.historialplus.historialplus.validators.record;

import com.historialplus.historialplus.service.recordservice.IRecordService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class RecordIdValidator implements ConstraintValidator<ValidRecordId, UUID> {
    @Autowired
    private IRecordService recordService;

    @Override
    public boolean isValid(UUID recordId, ConstraintValidatorContext context) {
        return recordId != null && recordService.findById(recordId).isPresent();
    }
}
