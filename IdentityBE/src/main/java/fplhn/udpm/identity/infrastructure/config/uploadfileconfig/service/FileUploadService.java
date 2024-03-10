package fplhn.udpm.identity.infrastructure.config.uploadfileconfig.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileUploadService {

    void init();

    String save(MultipartFile file);

    String save(MultipartFile file, Long id);

    Resource load(String filename);

    void deleteAll();

    void delete(String filename);

    Stream<Path> loadAll();

    String getFileLocation(String filename);

    Boolean isFileExists(String filename);

}
