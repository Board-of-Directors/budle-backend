package ru.nsu.fit.pak.budle.utils;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Establishment;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class ImageWorker {
    public String saveImage(Establishment establishment) {
        String filepath = "./" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(establishment.getImage());
            return filepath;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Cannot save image");

        }
    }

    public String loadImage(Establishment establishment) {
        try (FileReader reader = new FileReader(establishment.getImage())) {
            Scanner scan = new Scanner(reader);
            return String.valueOf(scan.next());
        } catch (Exception e) {
            System.out.println("Cannot load image");
            return null;
        }
    }
}
