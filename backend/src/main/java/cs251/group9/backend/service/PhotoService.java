package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoService {

    @Value("${file.upload-dir:./uploads/user/}")
    private String uploadDir;

    public String storePhoto(Long userId, MultipartFile file) throws IOException {
        // Create directory if it doesn't exist
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // Get file extension
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // Create the file path
        String filename = userId + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return targetPath.toString();
    }
    
    public String getPhotoPath(Long userId) {
        Path dirPath = Paths.get(uploadDir);
        try {
            // Search for any file with the userId as prefix
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith(userId.toString()))
                .findFirst()
                .map(Path::toString)
                .orElse(null);
        } catch (IOException e) {
            return null;
        }
    }
}
