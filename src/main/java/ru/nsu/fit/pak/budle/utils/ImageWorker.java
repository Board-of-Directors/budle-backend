package ru.nsu.fit.pak.budle.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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


@Component
public class ImageWorker {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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
            return null;

        }
    }

    public void compressImage(String filepath) throws IOException {
        BufferedImage image = null;
        OutputStream os = null;
        try {
            File input = new File(filepath);
            image = ImageIO.read(input);

            File compressedImageFile = new File(filepath);
            os = new FileOutputStream(compressedImageFile);

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.7f);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

    }

    public String loadImage(String imageName) {
        try {
            File inputFile = new File("./images/" + imageName);
            byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public String getImageFromResource(String imageName) {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("images" + imageName)) {
            if (stream != null) {
                return Base64.getEncoder().encodeToString(stream.readAllBytes());
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return null;
        }
        return null;
    }
}
