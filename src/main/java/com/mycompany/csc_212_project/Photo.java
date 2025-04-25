package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Photo {
    private String path;
    private LinkedList<String> tags;
    private BufferedImage image;

    public Photo(String path, LinkedList<String> tags) {
        this.path = path;
        this.tags = tags;
        
        try {
            File imageFile = new File(path);
            if (imageFile.exists() && imageFile.isFile()) {
                this.image = ImageIO.read(imageFile);
            } else {
                System.out.println("Warning: Image file not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load image file: " + path);
        }
    }

    public String getPath() {
        return path;
    }

    public LinkedList<String> getTags() {
        return tags;
    }
    
    public boolean imageLoaded() {
        return image != null;
    }
    
    public int getWidth() {
        return image != null ? image.getWidth() : 0;
    }
    
    public int getHeight() {
        return image != null ? image.getHeight() : 0;
    }
    
    public String getFileName() {
        return new File(path).getName();
    }
}