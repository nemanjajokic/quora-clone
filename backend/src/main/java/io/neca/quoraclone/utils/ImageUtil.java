package io.neca.quoraclone.utils;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class ImageUtil {

    public BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) throws Exception {
        return Scalr.resize(originalImage, targetWidth);
    }

}
