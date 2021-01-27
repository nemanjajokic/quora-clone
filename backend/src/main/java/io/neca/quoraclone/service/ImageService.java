package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.model.User;
import io.neca.quoraclone.utils.ImageUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ImageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ImageUtil imageUtil;

    private final String storagePath = "C:\\Users\\Neca\\Desktop\\quoraCloneImages";

    public void uploadToFileSystem(MultipartFile image) throws Exception {
        // Image name
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        // Image directory
        Path storageDirectory = Paths.get(storagePath);
        if(!Files.exists(storageDirectory)) Files.createDirectories(storageDirectory);
        // Image destination
        Path imageDestination = Paths.get(storageDirectory.toString() + "\\" + imageName);
        // Resize image
        BufferedImage bufferedImage = imageUtil.simpleResizeImage(convertToImage(image), 400);
        // Save image
        Files.copy(convertToInputStream(bufferedImage), imageDestination, StandardCopyOption.REPLACE_EXISTING);
        // Get image URI
        String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/images/getImage/")
                .path(imageName)
                .toUriString();
        // Update user's image URI
        User user = authService.getCurrentUser();
        user.setImageUri(imageUri);
        userRepository.save(user);
    }

    public byte[] getImage(String imageName) throws IOException {
        Path imageDestination = Paths.get(storagePath + "\\" + imageName);

        return IOUtils.toByteArray(imageDestination.toUri());
    }

    private static BufferedImage convertToImage(MultipartFile file) throws IOException {
        InputStream in = new ByteArrayInputStream(file.getBytes());

        return ImageIO.read(in);
    }

    private static InputStream convertToInputStream(BufferedImage file) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(file, "jpeg", os);

        return new ByteArrayInputStream(os.toByteArray());
    }

}
