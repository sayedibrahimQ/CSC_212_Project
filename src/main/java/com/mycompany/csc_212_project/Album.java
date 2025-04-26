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
            requiredTags = condition.split("\\s*(AND|and)\\s*"); 
        }

        if (allPhotos == null || allPhotos.isEmpty()) {
            return resultPhotos; 
        }

        allPhotos.goToFirst();
        while (!allPhotos.isLast()) {
            Photo currentPhoto = allPhotos.getData();
            if (matchesCondition(currentPhoto, requiredTags)) {
                resultPhotos.add(currentPhoto);
            }
            allPhotos.goToNext();
        }
        
        Photo lastPhoto = allPhotos.getData();
        if (matchesCondition(lastPhoto, requiredTags)) {
            resultPhotos.add(lastPhoto);
        }

        return resultPhotos;
    }

    private boolean matchesCondition(Photo photo, String[] requiredTags) {
        if (requiredTags.length == 0) {
            return true; 
        }

        LinkedList<String> photoTags = photo.getTags();
        if (photoTags == null || photoTags.isEmpty()) {
            return false; 
        }

        for (String reqTag : requiredTags) {
            boolean foundTag = false;
            photoTags.goToFirst();
            while (!photoTags.isLast()) {
                nbComps++; 
                if (photoTags.getData().equalsIgnoreCase(reqTag.trim())) {
                    foundTag = true;
                    break; 
                }
                photoTags.goToNext();
            }
            
            if (!foundTag && !photoTags.isEmpty()) {
                nbComps++;
                if (photoTags.getData().equalsIgnoreCase(reqTag.trim())) {
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