package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;
import com.mycompany.csc_212_project.datastructures.BST;

public class InvIndexPhotoManager {
    private BST<String, LinkedList<Photo>> invertedIndex;
    private LinkedList<Photo> allPhotosList;

    public InvIndexPhotoManager() {
        this.invertedIndex = new BST<>();
        this.allPhotosList = new LinkedList<>();
    }

    // Add a photo
    public void addPhoto(Photo p) {
        if (p == null || photoExists(p.getPath())) {
            return;
        }

        allPhotosList.insert(p);
        System.out.println("Photo added to inverted index: " + p.getPath());

        LinkedList<String> tags = p.getTags();
        if (tags != null && !tags.empty()) {
            tags.findFirst();
            while (!tags.last()) {
                String tag = tags.retrieve();
                updateIndexForAddition(tag, p);
                tags.findNext();
            }
            updateIndexForAddition(tags.retrieve(), p); // last tag
        }
    }

    // Update index for a new tag
    private void updateIndexForAddition(String tag, Photo photo) {
        boolean found = invertedIndex.findKey(tag);
        if (found) {
            LinkedList<Photo> photoList = invertedIndex.retrieve();
            if (!listContainsPhoto(photoList, photo.getPath())) {
                photoList.insert(photo);
            }
        } else {
            LinkedList<Photo> newPhotoList = new LinkedList<>();
            newPhotoList.insert(photo);
            invertedIndex.insert(tag, newPhotoList);
        }
    }

    // Delete a photo
    public void deletePhoto(String path) {
        Photo photoToRemove = findPhotoByPath(path);

        if (photoToRemove != null) {
            removePhotoFromList(allPhotosList, path);

            LinkedList<String> tags = photoToRemove.getTags();
            if (tags != null && !tags.empty()) {
                tags.findFirst();
                while (!tags.last()) {
                    String tag = tags.retrieve();
                    updateIndexForDeletion(tag, path);
                    tags.findNext();
                }
                updateIndexForDeletion(tags.retrieve(), path);
            }

            System.out.println("Photo deleted from inverted index: " + path);
        }
    }

    // Update index after photo deletion
    private void updateIndexForDeletion(String tag, String photoPath) {
        boolean found = invertedIndex.findKey(tag);
        if (found) {
            LinkedList<Photo> photoList = invertedIndex.retrieve();
            removePhotoFromList(photoList, photoPath);
            if (photoList.empty()) {
                invertedIndex.removeKey(tag);
            }
        }
    }

    // Helpers
    private Photo findPhotoByPath(String path) {
        if (allPhotosList.empty()) return null;

        allPhotosList.findFirst();
        while (!allPhotosList.last()) {
            Photo p = allPhotosList.retrieve();
            if (p.getPath().equals(path)) {
                return p;
            }
            allPhotosList.findNext();
        }

        Photo lastPhoto = allPhotosList.retrieve();
        return lastPhoto.getPath().equals(path) ? lastPhoto : null;
    }

    private void removePhotoFromList(LinkedList<Photo> list, String path) {
        if (list.empty()) return;

        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getPath().equals(path)) {
                list.remove();
                return;
            }
            list.findNext();
        }

        if (list.retrieve().getPath().equals(path)) {
            list.remove();
        }
    }

    private boolean listContainsPhoto(LinkedList<Photo> list, String path) {
        if (list.empty()) return false;

        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getPath().equals(path)) return true;
            list.findNext();
        }

        return list.retrieve().getPath().equals(path);
    }

    private boolean photoExists(String path) {
        return findPhotoByPath(path) != null;
    }

    public BST<String, LinkedList<Photo>> getPhotos() {
        return invertedIndex;
    }

    public LinkedList<Photo> getAllPhotos() {
        return allPhotosList;
    }

    public LinkedList<Photo> findPhotosByTag(String tag) {
        boolean found = invertedIndex.findKey(tag);
        return found ? invertedIndex.retrieve() : new LinkedList<>();
    }
}
