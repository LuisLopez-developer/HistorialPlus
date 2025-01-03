package com.historialplus.historialplus.internal.hospital.repository;

import com.historialplus.historialplus.internal.hospital.dto.response.HospitalResponseDto;
import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.hospital.projection.HospitalNameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Integer>, JpaSpecificationExecutor<HospitalEntity> {
    Page<HospitalNameProjection> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPhone(String phone);
    boolean existsByRuc(String ruc);

    @Query("SELECT new com.historialplus.historialplus.internal.hospital.dto.response.HospitalResponseDto(" +
            "h.id, h.name, h.phone, h.email, h.ruc, h.state.name) " +
            "FROM HospitalEntity h " +
            "JOIN h.state s")
    Page<HospitalResponseDto> findAllWithProjection(Specification<HospitalEntity> spec, Pageable pageable);
}
