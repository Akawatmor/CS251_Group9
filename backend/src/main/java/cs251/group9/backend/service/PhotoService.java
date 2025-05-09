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
    private final String developerPicturesDir = "developers/";
    private final String bannerPicturesDir = "banners/";
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
        String baseFilename = userId.toString();
        String filename = baseFilename + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing picture files with same userId but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith(baseFilename + ".") && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Log error but continue
                        System.err.println("Failed to delete old photo: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            // Log error but continue with save
            System.err.println("Error checking for old photos: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
      // Store a developer team photo
    public String storeDeveloperTeamPhoto(Long devId, MultipartFile file) throws IOException {
        String uploadDir = baseUploadDir + developerPicturesDir;
        
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
        String baseFilename = "team_" + devId;
        String filename = baseFilename + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing team photo files with same devId but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith(baseFilename + ".") && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Log error but continue
                        System.err.println("Failed to delete old team photo: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            // Log error but continue with save
            System.err.println("Error checking for old team photos: " + e.getMessage());
        }
        
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
    
    // Get a developer team photo path
    public String getDeveloperTeamPhotoPath(Long devId) {
        Path dirPath = Paths.get(baseUploadDir + developerPicturesDir);
        try {
            // Search for any file with the team_devId as prefix
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith("team_" + devId))
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
        String baseFilename = gameId + "_" + pictureNumber;
        String filename = baseFilename + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing game picture files with same gameId_position but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith(baseFilename + ".") && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Log error but continue
                        System.err.println("Failed to delete old game picture: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            // Log error but continue with save
            System.err.println("Error checking for old game pictures: " + e.getMessage());
        }
        
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
    
    // Get a game picture path
    public String getGamePicturePath(Long gameId, int pictureNumber) {
        Path dirPath = Paths.get(baseUploadDir + gamePicturesDir);
        try {
            // Search for any file with the gameId_pictureNumber as prefix
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith(gameId + "_" + pictureNumber))
                .findFirst()
                .map(Path::toString)
                .orElse(null);
        } catch (IOException e) {
            return null;
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
    
    public byte[] getDeveloperTeamPhotoData(Long devId) throws Exception {
        // Get the path to the photo using the existing method
        String photoPath = getDeveloperTeamPhotoPath(devId);
        
        if (photoPath == null) {
            throw new Exception("Team photo does not exist");
        }
        
        // Read the photo file into a byte array
        java.nio.file.Path path = java.nio.file.Paths.get(photoPath);
        if (!java.nio.file.Files.exists(path)) {
            throw new Exception("Team photo file not found");
        }
        
        try {
            return java.nio.file.Files.readAllBytes(path);
        } catch (java.io.IOException e) {
            throw new Exception("Failed to read team photo: " + e.getMessage());
        }
    }
    
    public byte[] getGamePictureData(Long gameId, int pictureNumber) throws Exception {
        // Get the path to the game picture
        String picturePath = getGamePicturePath(gameId, pictureNumber);
        
        if (picturePath == null) {
            throw new Exception("Game picture does not exist");
        }
        
        // Read the photo file into a byte array
        java.nio.file.Path path = java.nio.file.Paths.get(picturePath);
        if (!java.nio.file.Files.exists(path)) {
            throw new Exception("Game picture file not found");
        }
        
        try {
            return java.nio.file.Files.readAllBytes(path);
        } catch (java.io.IOException e) {
            throw new Exception("Failed to read game picture: " + e.getMessage());
        }
    }
    
    // Banner related methods
    
    // Store a banner picture (independent from any entity)
    public String storeBannerPicture(int bannerPosition, MultipartFile file) throws IOException {
        if (bannerPosition < 1 || bannerPosition > 25) {
            throw new IOException("Banner position must be between 1 and 25");
        }
        
        String uploadDir = baseUploadDir + bannerPicturesDir;
        
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
        String baseFilename = "banner_" + bannerPosition;
        String filename = baseFilename + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing banner files with same position but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith(baseFilename) && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Log error but continue
                        System.err.println("Failed to delete old banner: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            // Log error but continue with save
            System.err.println("Error checking for old banners: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    
    // Get a banner picture path
    public String getBannerPicturePath(int bannerPosition) {
        if (bannerPosition < 1 || bannerPosition > 25) {
            return null;
        }
        
        Path dirPath = Paths.get(baseUploadDir + bannerPicturesDir);
        if (!Files.exists(dirPath)) {
            return null;
        }
        
        String baseFilename = "banner_" + bannerPosition;
        try {
            // Search for any file with the banner_{position} prefix
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith(baseFilename))
                .findFirst()
                .map(Path::toString)
                .orElse(null);
        } catch (IOException e) {
            return null;
        }
    }
    
    // Get banner picture data as bytes
    public byte[] getBannerPictureData(int bannerPosition) throws Exception {
        // Get the path to the banner picture
        String picturePath = getBannerPicturePath(bannerPosition);
        
        if (picturePath == null) {
            throw new Exception("Banner picture does not exist");
        }
        
        // Read the photo file into a byte array
        Path path = Paths.get(picturePath);
        if (!Files.exists(path)) {
            throw new Exception("Banner picture file not found");
        }
        
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new Exception("Failed to read banner picture: " + e.getMessage());
        }
    }
    
    // Delete a banner picture
    public boolean deleteBannerPicture(int bannerPosition) {
        if (bannerPosition < 1 || bannerPosition > 25) {
            return false;
        }
        
        String picturePath = getBannerPicturePath(bannerPosition);
        if (picturePath == null) {
            return false;
        }
        
        return deleteFile(picturePath);
    }
}
