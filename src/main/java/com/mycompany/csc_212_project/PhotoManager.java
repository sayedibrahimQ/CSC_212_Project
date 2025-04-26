package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class PhotoManager {
    private LinkedList<Photo> photos;

    // Initializes a new PhotoManager with an empty linked list
    public PhotoManager() {
        this.photos = new LinkedList<>();
    }

    // Returns the linked list of photos
    public LinkedList<Photo> getPhotos() {
        return photos;
    }

    // Adds a photo to the list if it doesn't already exist
    public void addPhoto(Photo p) {
        if (p != null && !photoExists(p.getPath())) {
            photos.insert(p);
            System.out.println("Photo added: " + p.getPath());
        } else {
            System.out.println("Photo already exists or invalid.");
        }
    }

    // Deletes a photo with the specified path
    public void deletePhoto(String path) {
        if (photos.empty()) return;

        photos.findFirst();
        while (!photos.last()) {
            if (photos.retrieve().getPath().equals(path)) {
                photos.remove();
                System.out.println("Photo deleted: " + path);
                return;
            }
            photos.findNext();
        }

        // Check last photo
        if (photos.retrieve().getPath().equals(path)) {
            photos.remove();
            System.out.println("Photo deleted: " + path);
        }
    }

    // Checks if a photo with the specified path exists
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
}