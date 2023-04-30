package ru.nsu.fit.pak.budle.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.exceptions.ImageSavingException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Iterator;


/**
 * Class, that responsible for image saving into file system
 * and image loading from file system.
 * Also, have methods for compress images and getting images from resources.
 */
@Component
public class ImageWorker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Method that saves image of establishment.
     *
     * @param imageContent string content representation (Base64).
     * @return filepath of image that will be saved in database.
     */
    public String saveImage(String imageContent) {
        String databaseFilepath = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".jpg";
        String saveFilepath = "./images/" + databaseFilepath;
        try {
            File file = new File(saveFilepath);
            byte[] imageBytes = Base64.getDecoder().decode(imageContent);
            FileUtils.writeByteArrayToFile(file, imageBytes);
            compressImage(saveFilepath);
            return databaseFilepath;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new ImageSavingException();

        }
    }

    /**
     * Compress existed image.
     *
     * @param filepath of image that we need to compress.
     */
    public void compressImage(String filepath) {
        try {
            File input = new File(filepath);
            BufferedImage image = ImageIO.read(input);

            File compressedImageFile = new File(filepath);
            OutputStream outputStream = new FileOutputStream(compressedImageFile);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
            ImageWriter writer = writers.next();

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.7f);

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);

            writer.write(null, new IIOImage(image, null, null), param);

            outputStream.close();
            imageOutputStream.close();
            writer.dispose();
        } catch (IOException exception) {
            logger.warn(exception.getMessage());
            throw new ImageSavingException();
        }

    }

    /**
     * Load existing image and return content of this image.
     *
     * @param imageName name of file in images folder.
     * @return Base64 encoded content of the image.
     */

    public String loadImage(String imageName) {
        try {
            File inputFile = new File("./images/" + imageName);
            byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
            //throw new ImageLoadingException();
        }
    }

    /**
     * Load existing image from resources and return content of this image.
     *
     * @param imageName name of image in the resource folder.
     * @return Base64 encoded content of the image.
     */
    public String getImageFromResource(String imageName) {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("images" + imageName)) {
            if (stream != null) {
                return Base64.getEncoder().encodeToString(stream.readAllBytes());
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
