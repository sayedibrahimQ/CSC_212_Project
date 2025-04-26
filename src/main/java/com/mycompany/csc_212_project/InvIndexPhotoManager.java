package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;
import com.mycompany.csc_212_project.datastructures.BST;
import java.io.File;

public class InvIndexPhotoManager {
    private BST<String, LinkedList<Photo>> invertedIndex;
    private LinkedList<Photo> allPhotosList;

    public InvIndexPhotoManager() {
        this.invertedIndex = new BST<>();
        this.allPhotosList = new LinkedList<>();
    }

    public void addPhoto(Photo p) {
        if (p == null || photoExists(p.getPath())) {
            return;
        }

        allPhotosList.add(p);
        System.out.println("Photo added to inverted index: " + new File(p.getPath()).getName());

        LinkedList<String> tags = p.getTags();
        if (tags != null && !tags.isEmpty()) {
            tags.goToFirst();
            while (!tags.isLast()) {
                String tag = tags.getData();
                updateIndexForAddition(tag, p);
                tags.goToNext();
            }
            updateIndexForAddition(tags.getData(), p);
        }
    }

    private void updateIndexForAddition(String tag, Photo photo) {
        boolean found = invertedIndex.find(tag);

        if (found) {
            LinkedList<Photo> photoList = invertedIndex.getValue();
            if (!listContainsPhoto(photoList, photo.getPath())) {
                photoList.add(photo);
            }
        } else {
            LinkedList<Photo> newPhotoList = new LinkedList<>();
            newPhotoList.add(photo);
            invertedIndex.add(tag, newPhotoList);
        }
    }

    public void deletePhoto(String path) {
        Photo photoToRemove = findPhotoByPath(path);

        if (photoToRemove != null) {
            removePhotoFromList(allPhotosList, path);

            LinkedList<String> tags = photoToRemove.getTags();
            if (tags != null && !tags.isEmpty()) {
                tags.goToFirst();
                while (!tags.isLast()) {
                    String tag = tags.getData();
                    updateIndexForDeletion(tag, path);
                    tags.goToNext();
                }
                updateIndexForDeletion(tags.getData(), path);
            }
            
            System.out.println("Photo deleted from inverted index: " + new File(path).getName());
        }
    }

    private void updateIndexForDeletion(String tag, String photoPath) {
        boolean found = invertedIndex.find(tag);
        if (found) {
            LinkedList<Photo> photoList = invertedIndex.getValue();
            removePhotoFromList(photoList, photoPath);

            if (photoList.isEmpty()) {
                invertedIndex.remove(tag);
            }
        }
    }

    private Photo findPhotoByPath(String path) {
        if (allPhotosList.isEmpty()) return null;
        
        allPhotosList.goToFirst();
        while (!allPhotosList.isLast()) {
            Photo p = allPhotosList.getData();
            if (p.getPath().equals(path)) {
                return p;
            }
            allPhotosList.goToNext();
        }
        
        Photo lastPhoto = allPhotosList.getData();
        if (lastPhoto.getPath().equals(path)) {
            return lastPhoto;
        }
        
        return null;
    }

    private void removePhotoFromList(LinkedList<Photo> list, String path) {
        if (list.isEmpty()) return;
        
        list.goToFirst();
        while (!list.isLast()) {
            if (list.getData().getPath().equals(path)) {
                list.remove();
                return;
            }
            list.goToNext();
        }
        
        if (list.getData().getPath().equals(path)) {
            list.remove();
        }
    }

    private boolean listContainsPhoto(LinkedList<Photo> list, String path) {
        if (list.isEmpty()) return false;
        
        list.goToFirst();
        while (!list.isLast()) {
            if (list.getData().getPath().equals(path)) {
                return true;
            }
            list.goToNext();
        }
        
        return list.getData().getPath().equals(path);
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
        boolean found = invertedIndex.find(tag);
        if (found) {
            return invertedIndex.getValue();
        } else {
            return new LinkedList<Photo>(); 
        }
    }
}