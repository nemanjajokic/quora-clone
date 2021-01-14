package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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

    private final String storagePath = "C:\\Users\\Neca\\Desktop\\quoraCloneImages";

    public void uploadToFileSystem(MultipartFile image) throws IOException {
        // Image name
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        // Image directory
        Path storageDirectory = Paths.get(storagePath);
        if(!Files.exists(storageDirectory)) Files.createDirectories(storageDirectory);
        // Image URI
        Path imageDestination = Paths.get(storageDirectory.toString() + "\\" + imageName);
        Files.copy(image.getInputStream(), imageDestination, StandardCopyOption.REPLACE_EXISTING);
        String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/images/getImage/")
                .path(imageName)
                .toUriString();

        User user = authService.getCurrentUser();
        user.setImageUri(imageUri);
        userRepository.save(user);
    }

    public byte[] getImage(String imageName) throws IOException {
        Path imageDestination = Paths.get(storagePath + "\\" + imageName);

        return IOUtils.toByteArray(imageDestination.toUri());
    }

}
