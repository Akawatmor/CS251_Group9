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
public class GameFileService {

    @Value("${file.upload-dir}")
    private String baseUploadDir;
    
    // Updated standardized directory paths
    private final String gameFilesDir = "gamefile/";
    private final String gameIconDir = "gameicon/";
    private final String gamePicturesDir = "gamepic/";
    
    // Store game executable file with standardized naming
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
        
        // Create the file path with standardized naming
        String filename = "gamefile" + gameId + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing game files with same id but different extension
        try {
            String prefix = "gamefile" + gameId;
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith(prefix) && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete old game file: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.err.println("Error checking for old game files: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    
    // Store game picture with standardized naming and path
    public String storeGamePicture(Long gameId, MultipartFile file, int pictureNumber) throws IOException {
        String uploadDir;
        String filename;
        
        // Use appropriate directory and naming based on picture number
        if (pictureNumber == 1) {
            // Game icon (picture 1)
            uploadDir = baseUploadDir + gameIconDir;
            String extension = getExtension(file);
            filename = "gameicon" + gameId + extension;
        } else {
            // Game additional pictures (pictures 2-5)
            uploadDir = baseUploadDir + gamePicturesDir;
            String extension = getExtension(file);
            filename = "game" + gameId + "pic" + pictureNumber + extension;
        }
        
        // Create directory if it doesn't exist
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing files with same naming pattern but different extension
        try {
            String baseFilename = filename.substring(0, filename.lastIndexOf("."));
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith(baseFilename) && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete old game picture: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.err.println("Error checking for old game pictures: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    
    // Helper method to get file extension
    private String getExtension(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("File name is null");
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
    
    // Delete a file - remain unchanged
    public boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }
}
