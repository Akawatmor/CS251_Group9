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

    //File Base Directory
    @Value("${file.upload-dir:./backend/uploads/}")
    private String baseUploadDir;
    
    // Updated standardized directory paths
    private final String userPicturesDir = "userprofile/";
    private final String gameIconDir = "gameicon/";
    private final String gamePicturesDir = "gamepic/";
    private final String developerPicturesDir = "developers/";
    private final String bannerPicturesDir = "banner/";
    

/////////////////////// User profile picture methods ////////////////////////

    // Store a user profile photo with standardized naming
    public String storePhoto(Long userId, MultipartFile file) throws IOException {
        
        String DIR = baseUploadDir + userPicturesDir;
        //System.out.println("Upload Profile Pic to "+ uploadDir);
        
        // Create directory if it doesn't exist
        Path dirPath = Paths.get(DIR);
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
        String filename = "userprofile" + userId + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing picture files with same userId but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith("userprofile" + userId) && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete old photo: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.err.println("Error checking for old photos: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return DIR + filename;
    }

    // Get a user profile photo path with updated directory structure
    public String getPhotoPath(Long userId) {
        String DIR = baseUploadDir + userPicturesDir;
        Path dirPath = Paths.get(DIR);
        try {
            // Search for any file with the standardized user profile naming
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith("userprofile" + userId))
                .findFirst()
                .map(Path::toString)
                .orElse(null);
        } catch (IOException e) {
            return null;
        }
    }

////////////////////////// Developer team picture methods ////////////////////////
    
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
        
        // Create the file path with standardized naming
        String filename = "team_" + devId + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing team photo files with same devId but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith("team_" + devId) && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete old team photo: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.err.println("Error checking for old team photos: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
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

////////////////////////////// Game picture methods ////////////////////////
    
    // Store a game picture with standardized naming
    public String storeGamePicture(Long gameId, int pictureNumber, MultipartFile file) throws IOException {
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
    
    // Delete a file
    public boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }
    
    // Get a game picture path with updated directory structure
    public String getGamePicturePath(Long gameId, int pictureNumber) {
        Path dirPath;
        String prefix;
        
        if (pictureNumber == 1) {
            // Game icon (picture 1)
            dirPath = Paths.get(baseUploadDir + gameIconDir);
            prefix = "gameicon" + gameId;
        } else {
            // Game additional pictures (pictures 2-5)
            dirPath = Paths.get(baseUploadDir + gamePicturesDir);
            prefix = "game" + gameId + "pic" + pictureNumber;
        }
        
        try {
            // Search for any file with the appropriate prefix
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith(prefix))
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
    
    // Banner related methods with standardized naming
    
    // Store a banner picture with standardized naming
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
        String extension = getExtension(file);
        
        // Create the file path with standardized naming
        String filename = "banner" + bannerPosition + extension;
        Path targetPath = dirPath.resolve(filename);
        
        // Delete any existing banner files with same position but different extension
        try {
            Files.list(dirPath)
                .filter(path -> {
                    String fname = path.getFileName().toString();
                    return fname.startsWith("banner" + bannerPosition) && !fname.equals(filename);
                })
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete old banner: " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.err.println("Error checking for old banners: " + e.getMessage());
        }
        
        // Save the file
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return uploadDir + filename;
    }
    

////////////////////// Banner picture methods ////////////////////////

    // Get a banner picture path with standardized naming
    public String getBannerPicturePath(int bannerPosition) {
        if (bannerPosition < 1 || bannerPosition > 25) {
            return null;
        }
        
        Path dirPath = Paths.get(baseUploadDir + bannerPicturesDir);
        if (!Files.exists(dirPath)) {
            return null;
        }
        
        String prefix = "banner" + bannerPosition;
        try {
            // Search for any file with the banner{position} prefix
            return Files.list(dirPath)
                .filter(path -> path.getFileName().toString().startsWith(prefix))
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
