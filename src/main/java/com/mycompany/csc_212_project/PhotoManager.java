package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class PhotoManager {
    private LinkedList<Photo> photos;

    public PhotoManager() {
        this.photos = new LinkedList<>();
    }

    public LinkedList<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo p) {
        if (p != null && !photoExists(p.getPath())) {
            photos.insert(p);
            System.out.println("Photo added to manager: " + new java.io.File(p.getPath()).getName());
        }
    }

    public void deletePhoto(String path) {
        if (photos.empty()) return;
        
        photos.findFirst();
        while (!photos.last()) {
            if (photos.retrieve().getPath().equals(path)) {
                System.out.println("Photo deleted from manager: " + new java.io.File(path).getName());
                photos.remove();
                return;
            }
            photos.findNext();
        }
        
        if (photos.retrieve().getPath().equals(path)) {
            System.out.println("Photo deleted from manager: " + new java.io.File(path).getName());
            photos.remove();
        }
    }

    private boolean photoExists(String path) {
        if (photos.empty()) return false;
        
        photos.findFirst();
        while (!photos.last()) {
            if (photos.retrieve().getPath().equals(path)) {
                return true;
            }
            photos.findNext();
        }
        
        return photos.retrieve().getPath().equals(path);
    }
    
    public int count() {
        int count = 0;
        if (photos.empty()) return count;
        
        photos.findFirst();
        while (!photos.last()) {
            count++;
            photos.findNext();
        }
        return count + 1; 
    }
}