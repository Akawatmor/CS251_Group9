package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class GameFileService {

    @Value("${file.upload-dir:./uploads/}")
    private String baseUploadDir;
    
    private final String gameFilesDir = "games/";
    private final String gamePicturesDir = "game-pictures/";
    
    public String storeGameFile(Long gameId, MultipartFile file) throws IOException {
        String uploadDir = baseUploadDir + gameFilesDir;
        
        // Create directory if it doesn't exist
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // Get file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("File name is null");
        }
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // Create the file path with a unique name
        String filename = gameId + "_" + UUID.randomUUID().toString() + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    
    public String storeGamePicture(Long gameId, MultipartFile file, int pictureNumber) throws IOException {
        String uploadDir = baseUploadDir + gamePicturesDir;
        
        // Create directory if it doesn't exist
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // Get file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("File name is null");
        }
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // Create the file path
        String filename = gameId + "_" + pictureNumber + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    
    public boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }
}
