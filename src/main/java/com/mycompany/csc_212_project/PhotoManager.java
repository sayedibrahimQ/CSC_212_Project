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
            photos.add(p);
            System.out.println("Photo added: " + p.getPath());
        } else {
            System.out.println("Photo already exists or invalid.");
        }
    }

    public void deletePhoto(String path) {
        if (photos.isEmpty()) return;

        photos.goToFirst();
        while (!photos.isLast()) {
            if (photos.getData().getPath().equals(path)) {
                photos.remove();
                System.out.println("Photo deleted: " + path);
                return;
            }
            photos.goToNext();
        }

        if (photos.getData().getPath().equals(path)) {
            photos.remove();
            System.out.println("Photo deleted: " + path);
        }
    }

    private boolean photoExists(String path) {
        if (photos.isEmpty()) return false;

        photos.goToFirst();
        while (!photos.isLast()) {
            if (photos.getData().getPath().equals(path)) {
                return true;
            }
            photos.goToNext();
        }
        return photos.getData().getPath().equals(path);
    }
}
