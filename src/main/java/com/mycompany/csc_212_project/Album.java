package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class Album {
    private String name;
    private String condition;
    private PhotoManager manager;
    private int nbComps; // To track comparisons

    // Constructor
    public Album(String name, String condition, PhotoManager manager) {
        this.name = name;
        this.condition = condition; // "tag1 AND tag2 ..." or ""
        this.manager = manager;
        this.nbComps = 0; // Initialize comparison counter
    }

    // Return the name of the album
    public String getName() {
        return name;
    }

    // Return the condition associated with the album
    public String getCondition() {
        return condition;
    }

    // Return the manager
    public PhotoManager getManager() {
        return manager;
    }

    // Return all photos that satisfy the album condition
    public LinkedList<Photo> getPhotos() {
        nbComps = 0; // Reset counter each time method is called
        LinkedList<Photo> resultPhotos = new LinkedList<>();
        LinkedList<Photo> allPhotos = manager.getPhotos();

        String[] requiredTags = {};
        if (condition != null && !condition.trim().isEmpty()) {
            requiredTags = condition.split("\\s*(AND|and)\\s*"); // Split condition into individual tags
        }

        if (allPhotos == null || allPhotos.empty()) {
            return resultPhotos; // No photos to manage
        }

        // Iterate through all photos in the manager
        allPhotos.findFirst();
        while (!allPhotos.last()) {
            Photo currentPhoto = allPhotos.retrieve();
            if (matchesCondition(currentPhoto, requiredTags)) {
                resultPhotos.insert(currentPhoto);
            }
            allPhotos.findNext();
        }
        
        // Check the isLast photo
        Photo lastPhoto = allPhotos.retrieve();
        if (matchesCondition(lastPhoto, requiredTags)) {
            resultPhotos.insert(lastPhoto);
        }

        return resultPhotos;
    }

    // Helper method to check if a photo matches the condition
    private boolean matchesCondition(Photo photo, String[] requiredTags) {
        if (requiredTags.length == 0) {
            return true; // Empty condition means match all photos
        }

        LinkedList<String> photoTags = photo.getTags();
        if (photoTags == null || photoTags.empty()) {
            return false; // Photo has no tags
        }

        for (String reqTag : requiredTags) {
            boolean foundTag = false;
            // Look for the required tag in the photo's tag list
            photoTags.findFirst();
            while (!photoTags.last()) {
                nbComps++; // Increment comparison counter
                if (photoTags.retrieve().equalsIgnoreCase(reqTag.trim())) {
                    foundTag = true;
                    break; // Found the tag, move on to the next required tag
                }
                photoTags.findNext();
            }
            
            // Check the isLast tag
            if (!foundTag && !photoTags.empty()) {
                nbComps++;
                if (photoTags.retrieve().equalsIgnoreCase(reqTag.trim())) {
                    foundTag = true;
                }
            }

            if (!foundTag) {
                return false; // One of the required tags is not in the photo
            }
        }

        return true; // All required tags are present in the photo
    }

    // Return the number of tag comparisons used to find all photos of the album
    public int getNbComps() {
        return nbComps;
    }
}