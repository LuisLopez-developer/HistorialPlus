package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.service.hospitalservice.IHospitalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final IHospitalService service;

    public HospitalController(IHospitalService service) {
        this.service = service;
    }

    @GetMapping
    public Page<HospitalResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String ruc,
            @RequestParam(required = false) Integer id,
            Pageable pageable) {
        return service.findAll(name, ruc, id, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDto> getHospitalById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createHospital(@RequestBody HospitalCreateDto hospitalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(hospitalDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
