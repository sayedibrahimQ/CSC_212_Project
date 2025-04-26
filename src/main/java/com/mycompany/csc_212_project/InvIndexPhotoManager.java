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

    // Add a photo
    public void addPhoto(Photo p) {
        if (p == null || photoExists(p.getPath())) {
            return;
        }

        allPhotosList.insert(p);
        System.out.println("Photo added to inverted index: " + new File(p.getPath()).getName());

        LinkedList<String> tags = p.getTags();
        if (tags != null && !tags.empty()) {
            tags.findFirst();
            while (!tags.last()) {
                String tag = tags.retrieve();
                updateIndexForAddition(tag, p);
                tags.findNext();
            }
            // Process the last tag
            updateIndexForAddition(tags.retrieve(), p);
        }
    }

    // Helper method to update the inverted index when adding a photo
    private void updateIndexForAddition(String tag, Photo photo) {
        boolean found = invertedIndex.findkey(tag);

        if (found) {
            // Tag exists, add the photo to its list
            LinkedList<Photo> photoList = invertedIndex.retrieve();
            if (!listContainsPhoto(photoList, photo.getPath())) {
                photoList.insert(photo);
            }
        } else {
            // Tag doesn not exist, create a new list and add to BST
            LinkedList<Photo> newPhotoList = new LinkedList<>();
            newPhotoList.insert(photo);
            invertedIndex.insert(tag, newPhotoList);
        }
    }
    
    // Searches for a photo by its path in the linked list
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

    // Delete a photo
    public void deletePhoto(String path) {
        Photo photoToRemove = findPhotoByPath(path);

        if (photoToRemove != null) {
            //  Remove from main list
            removePhotoFromList(allPhotosList, path);

            // Remove from inverted index
            LinkedList<String> tags = photoToRemove.getTags();
            if (tags != null && !tags.empty()) {
                tags.findFirst();
                while (!tags.last()) {
                    String tag = tags.retrieve();
                    updateIndexForDeletion(tag, path);
                    tags.findNext();
                }
                // Process the last tag
                updateIndexForDeletion(tags.retrieve(), path);
            }
            
            System.out.println("Photo deleted from inverted index: " + new File(path).getName());
        }
    }

    // Helper method to update the index when deleting a photo
    private void updateIndexForDeletion(String tag, String photoPath) {
        boolean found = invertedIndex.findkey(tag);
        if (found) {
            LinkedList<Photo> photoList = invertedIndex.retrieve();
            removePhotoFromList(photoList, photoPath);

            // If the photo list for this tag is now empty, remove the tag from the index
            if (photoList.empty()) {
                invertedIndex.removeKey(tag);
            }
        }
    }

    // Helper method to remove a photo from a list
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
        
        // Check the last element
        if (list.retrieve().getPath().equals(path)) {
            list.remove();
        }
    }

    // Helper method to check if a list contains a photo
    private boolean listContainsPhoto(LinkedList<Photo> list, String path) {
        if (list.empty()) return false;
        
        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getPath().equals(path)) {
                return true;
            }
            list.findNext();
        }
        
        return list.retrieve().getPath().equals(path);
    }

    // Helper method to check if a photo exists
    private boolean photoExists(String path) {
        return findPhotoByPath(path) != null;
    }

    // Return the inverted index
    public BST<String, LinkedList<Photo>> getPhotos() {
        return invertedIndex;
    }
    
    // Get all photos (for compatibility with PhotoManager)
    public LinkedList<Photo> getAllPhotos() {
        return allPhotosList;
    }
    
    // Find photos by tag - more efficient than using Album
    public LinkedList<Photo> findPhotosByTag(String tag) {
        boolean found = invertedIndex.findkey(tag);
        if (found) {
            return invertedIndex.retrieve();
        } else {
            return new LinkedList<Photo>(); // Empty list
        }
    }
}