package com.enesbayram.sm_core.base.utils;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class ImageUtils {

    public byte[] resizeImage(byte[] original, int width, int height) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(original);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) return original;

            Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = buffered.createGraphics();
            g2d.drawImage(scaled, 0, 0, null);
            g2d.dispose();

            ImageIO.write(buffered, "jpg", bos);
            return bos.toByteArray();
        } catch (Exception e) {
            return original;
        }
    }

}
