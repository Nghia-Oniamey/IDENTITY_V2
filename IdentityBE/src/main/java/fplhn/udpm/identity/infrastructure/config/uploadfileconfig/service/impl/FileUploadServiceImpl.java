package fplhn.udpm.identity.infrastructure.config.uploadfileconfig.service.impl;

import fplhn.udpm.identity.infrastructure.config.uploadfileconfig.service.FileUploadService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service("excelService")
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-excel}")
    private String path;

    private Path root;

    @Override
    @PostConstruct
    public void init() {
        root = Paths.get(path);
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
            String newFileName = generateUUIDFromTimestamp(timestamp) + fileExtension;
            Files.copy(file.getInputStream(), this.root.resolve(newFileName));
            return newFileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String save(MultipartFile file, Long id) {
        return null;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    private String generateUUIDFromTimestamp(Timestamp timestamp) {
        UUID uuid = UUID.nameUUIDFromBytes(ByteBuffer.allocate(16).putLong(timestamp.getTime()).array());
        return uuid.toString();
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void delete(String filename) {
        try {
            Path file = root.resolve(filename);
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete the file!");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try (Stream<Path> paths = Files.walk(this.root, 1)) {
            return paths.filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public String getFileLocation(String filename) {
        return root.resolve(filename).toString();
    }

    @Override
    public Boolean isFileExists(String filename) {
        return null;
    }
}

