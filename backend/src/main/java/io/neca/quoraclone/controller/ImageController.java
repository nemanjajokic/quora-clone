package io.neca.quoraclone.controller;

import io.neca.quoraclone.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/images")
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping(value = "upload")
    public void uploadImage(@RequestParam MultipartFile file) throws IOException {
        service.uploadToFileSystem(file);
    }

    @GetMapping(value = "getImage/{imageName}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE })
    public @ResponseBody byte[] getImage(@PathVariable(name = "imageName") String fileName) throws IOException {
        return service.getImage(fileName);
    }

}
