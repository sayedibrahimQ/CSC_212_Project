package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class Test {

    // Converts comma-separated tags into LinkedList
    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        if (tags != null && !tags.trim().isEmpty()) {
            String[] tagsArray = tags.split("\\s*,\\s*");
            for (String tag : tagsArray) {
                result.insert(tag.trim());
            }
        }
        return result;
    }

    // Prints a LinkedList nicely
    public static <T> void printList(LinkedList<T> list) {
        if (list == null || list.empty()) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        list.findFirst();
        while (!list.last()) {
            System.out.print(list.retrieve() + ", ");
            list.findNext();
        }
        System.out.print(list.retrieve());
        System.out.println("]");
    }

    // Prints photo paths
    private static void printPhotoPaths(LinkedList<Photo> photos) {
        if (photos.empty()) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        photos.findFirst();
        while (!photos.last()) {
            System.out.print(photos.retrieve().getPath() + ", ");
            photos.findNext();
        }
        System.out.print(photos.retrieve().getPath());
        System.out.println("]");
    }

    public static void main(String[] args) {

        // === Using PhotoManager (Before Optimization) ===
        System.out.println("\n===== Using PhotoManager =====");
        PhotoManager manager = new PhotoManager();

        Photo photo1 = new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, grass"));
        Photo photo2 = new Photo("bear.jpg", toTagsLinkedList("animal, bear, grass"));
        Photo photo3 = new Photo("butterfly.jpg", toTagsLinkedList("insect, butterfly, flower"));
        Photo photo4 = new Photo("panda.jpg", toTagsLinkedList("animal, panda, grass"));
        Photo photo5 = new Photo("fox.jpg", toTagsLinkedList("animal, fox, forest"));

        manager.addPhoto(photo1);
        manager.addPhoto(photo2);
        manager.addPhoto(photo3);
        manager.addPhoto(photo4);
        manager.addPhoto(photo5);

        System.out.println("\nAll Photos (PhotoManager):");
        printPhotoPaths(manager.getPhotos());

        // Create an Album (slow search)
        Album album1 = new Album("Animals on Grass", "animal AND grass", manager);
        LinkedList<Photo> albumPhotos1 = album1.getPhotos();
        System.out.println("\nPhotos matching 'animal AND grass' (PhotoManager):");
        printPhotoPaths(albumPhotos1);
        System.out.println("Tag comparisons used (PhotoManager): " + album1.getNbComps());

        // Delete a photo
        System.out.println("\nDeleting 'bear.jpg' (PhotoManager)...");
        manager.deletePhoto("bear.jpg");
        System.out.println("All Photos after deletion (PhotoManager):");
        printPhotoPaths(manager.getPhotos());

        // === Using InvIndexPhotoManager (After Optimization) ===
        System.out.println("\n===== Using InvIndexPhotoManager =====");
        InvIndexPhotoManager invManager = new InvIndexPhotoManager();

        invManager.addPhoto(photo1);
        invManager.addPhoto(photo2);
        invManager.addPhoto(photo3);
        invManager.addPhoto(photo4);
        invManager.addPhoto(photo5);

        System.out.println("\nAll Photos (InvIndexPhotoManager):");
        printPhotoPaths(invManager.getAllPhotos());

        // Search for photos with "grass" tag (fast search)
        LinkedList<Photo> searchResults = invManager.findPhotosByTag("grass");
        System.out.println("\nPhotos with tag 'grass' (Using Inverted Index):");
        printPhotoPaths(searchResults);

        // Delete a photo
        System.out.println("\nDeleting 'bear.jpg' (InvIndexPhotoManager)...");
        invManager.deletePhoto("bear.jpg");
        System.out.println("All Photos after deletion (InvIndexPhotoManager):");
        printPhotoPaths(invManager.getAllPhotos());

        // Search again after deletion
        LinkedList<Photo> searchResultsAfterDelete = invManager.findPhotosByTag("grass");
        System.out.println("\nPhotos with tag 'grass' after deletion:");
        printPhotoPaths(searchResultsAfterDelete);
    }
}
