package com.historialplus.historialplus.dto.peopleDTOs.mapper;

import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;
import com.historialplus.historialplus.entities.PeopleEntity;
import com.historialplus.historialplus.typedocument.entities.TypeDocumentEntity;
import com.historialplus.historialplus.typesex.entities.SexTypeEntity;

public class PeopleDtoMapper {

    private PeopleDtoMapper() {
    }

    // Método para mapear de PeopleEntity a PeopleResponseDto
    public static PeopleResponseDto toPeopleResponseDto(PeopleEntity entity) {
        return new PeopleResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getPaternalSurname(),
                entity.getMaternalSurname(),
                entity.getBirthdate(),
                entity.getDocumentNumber(),
                entity.getBloodType(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getNationality(),
                entity.getSexType().getName(),
                entity.getTypeDocument().getName()
        );
    }

    // Método para mapear de PeopleCreateDto a PeopleEntity
    public static PeopleEntity toPeopleEntity(PeopleCreateDto dto) {
        PeopleEntity entity = new PeopleEntity();
        entity.setName(dto.getName());
        entity.setPaternalSurname(dto.getPaternalSurname());
        entity.setMaternalSurname(dto.getMaternalSurname());
        entity.setBirthdate(dto.getBirthdate());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setBloodType(dto.getBloodType());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setNationality(dto.getNationality());

        // agregar el tipo de sexo
        var sexType = new SexTypeEntity();
        sexType.setId(dto.getSexTypeId());
        entity.setSexType(sexType);

        // Agregar el tipo de documento
        var typeDocument = new TypeDocumentEntity();
        typeDocument.setId(dto.getTypeDocumentId());
        entity.setTypeDocument(typeDocument);

        return entity;
    }

    // convertir de tipo PeopleEntity a MinimalPeopleResponseDto
    public static MinimalPeopleResponseDto toMinimalPeopleDto(PeopleEntity peopleEntity) {
        MinimalPeopleResponseDto reniecNameDto = new MinimalPeopleResponseDto();
        reniecNameDto.setName(peopleEntity.getName());
        reniecNameDto.setFatherLastName(peopleEntity.getPaternalSurname());
        reniecNameDto.setMotherLastName(peopleEntity.getMaternalSurname());
        reniecNameDto.setPhone(peopleEntity.getPhone());
        reniecNameDto.setFromReniec(false);
        return reniecNameDto;
    }
}
