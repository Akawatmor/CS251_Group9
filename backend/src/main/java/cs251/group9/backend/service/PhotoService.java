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

    @Value("${file.upload-dir:./uploads/}")
    private String baseUploadDir;
    
    private final String userPicturesDir = "user/";
    private final String gamePicturesDir = "games/pictures/";
    
    // Store a user profile photo
    public String storePhoto(Long userId, MultipartFile file) throws IOException {
        String uploadDir = baseUploadDir + userPicturesDir;
        
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
        String filename = userId + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    
    // Get a user profile photo path
    public String getPhotoPath(Long userId) {
        Path dirPath = Paths.get(baseUploadDir + userPicturesDir);
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
    
    // Store a game picture
    public String storeGamePicture(Long gameId, int pictureNumber, MultipartFile file) throws IOException {
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
    
    // Delete a file
    public boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    public byte[] getPhotoData(Long userId) throws Exception {
        // Get the path to the photo using the existing method
        String photoPath = getPhotoPath(userId);
        
        // Read the photo file into a byte array
        java.nio.file.Path path = java.nio.file.Paths.get(photoPath);
        if (!java.nio.file.Files.exists(path)) {
            throw new Exception("Photo does not exist");
        }
        
        try {
            return java.nio.file.Files.readAllBytes(path);
        } catch (java.io.IOException e) {
            throw new Exception("Failed to read photo: " + e.getMessage());
        }
    }

    
}
