package wniemiec.api.nshopping.services;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wniemiec.api.nshopping.services.exceptions.FileException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Responsible for providing image services.
 */
@Service
public class ImageService {

    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
        assertFileCompatibility(uploadedFile);

        try {
            return parseImage(uploadedFile);
        } 
        catch (IOException e) {
            throw new FileException("Error while reading file");
        }
    }

    private void assertFileCompatibility(MultipartFile uploadedFile) {
        String extension = extractExtensionFromUploadedFile(uploadedFile);

        if (!hasCompatibility(extension)) {
            throw new FileException("Only PNG and JPG are allowed");
        }
    }

    private String extractExtensionFromUploadedFile(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }

    private boolean hasCompatibility(String extension) {
        return  "png".equals(extension)
                || "jpg".equals(extension);
    }

    private BufferedImage parseImage(MultipartFile uploadedFile) throws IOException {
        BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
        
        if (isPngImage(uploadedFile)) {
            image = convertPngToJpg(image);
        }

        return image;
    }

    private boolean isPngImage(MultipartFile uploadedFile) {
        String extension = extractExtensionFromUploadedFile(uploadedFile);
        
        return "png".equals(extension);
    }

    private BufferedImage convertPngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(
            img.getWidth(), 
            img.getHeight(), 
            BufferedImage.TYPE_INT_RGB
        );

        jpgImage
            .createGraphics()
            .drawImage(img, 0, 0, Color.WHITE, null);
        
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            
            return new ByteArrayInputStream(os.toByteArray());
        } 
        catch (IOException e) {
            throw new FileException("Error while reading file");
        }
    }

    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = getMinDimension(sourceImg);
        
        return Scalr.crop(
            sourceImg,
            (sourceImg.getWidth()/2) - (min/2),
            (sourceImg.getHeight()/2) - (min/2),
            min,
            min
        );
    }

    private int getMinDimension(BufferedImage sourceImg) {
        if (sourceImg.getHeight() <= sourceImg.getWidth()) {
            return sourceImg.getHeight();
        }

        return sourceImg.getWidth();
    }

    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(
            sourceImg, 
            Scalr.Method.ULTRA_QUALITY, 
            size
        );
    }
}
