package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class Album {
    private String name;
    private String condition;
    private PhotoManager manager;
    private int nbComps; 

    public Album(String name, String condition, PhotoManager manager) {
        this.name = name;
        this.condition = condition;
        this.manager = manager;
        this.nbComps = 0; 
    }

   
    public String getName() {
        return name;
    }

    
    public String getCondition() {
        return condition;
    }

    public PhotoManager getManager() {
        return manager;
    }

    public LinkedList<Photo> getPhotos() {
        nbComps = 0; 
        LinkedList<Photo> resultPhotos = new LinkedList<>();
        LinkedList<Photo> allPhotos = manager.getPhotos();

        String[] requiredTags = {};
        if (condition != null && !condition.trim().isEmpty()) {
            requiredTags = condition.split("\\s*AND\\s*");
        }

        if (allPhotos == null || allPhotos.empty()) {
            return resultPhotos; 
        }

        allPhotos.findFirst();
        while (!allPhotos.last()) {
            Photo currentPhoto = allPhotos.retrieve();
            if (matchesCondition(currentPhoto, requiredTags)) {
                resultPhotos.insert(currentPhoto);
            }
            allPhotos.findNext();
        }
        
        Photo lastPhoto = allPhotos.retrieve();
        if (matchesCondition(lastPhoto, requiredTags)) {
            resultPhotos.insert(lastPhoto);
        }

        return resultPhotos;
    }

    private boolean matchesCondition(Photo photo, String[] requiredTags) {
        if (requiredTags.length == 0) {
            return true; 
        }

        LinkedList<String> photoTags = photo.getTags();
        if (photoTags == null || photoTags.empty()) {
            return false; 
        }

        for (String reqTag : requiredTags) {
            boolean foundTag = false;
            photoTags.findFirst();
            while (!photoTags.last()) {
                nbComps++; 
                if (photoTags.retrieve().equalsIgnoreCase(reqTag.trim())) {
                    foundTag = true;
                    break;
                }
                photoTags.findNext();
            }
            
            if (!foundTag && !photoTags.empty()) {
                nbComps++;
                if (photoTags.retrieve().equalsIgnoreCase(reqTag.trim())) {
                    foundTag = true;
                }
            }

            if (!foundTag) {
                return false; 
            }
        }

        return true; 
    }

    public int getNbComps() {
        return nbComps;
    }
}