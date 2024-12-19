package com.historialplus.historialplus.typedocument.repository;

import com.historialplus.historialplus.typedocument.entities.TypeDocumentEntity;
import com.historialplus.historialplus.typedocument.projection.TypeDocumentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocumentEntity, Integer>  {
    List<TypeDocumentProjection> findAllByOrderByUpdatedAtDesc();
}