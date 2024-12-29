package com.historialplus.historialplus.external.facade.CompressAndUploadService;

import com.historialplus.historialplus.external.compress.dto.CompressFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ICompressAndUploadService {
    CompletableFuture<CompressFileDto> compressAndUpload(MultipartFile file);
}