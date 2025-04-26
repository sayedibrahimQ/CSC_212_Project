package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class Test {

    // Converts a comma-separated tags string to a LinkedList of tags
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

    // Prints the contents of a LinkedList in a formatted way
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

    // Prints the paths of photos in a LinkedList
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

    // Main method to test PhotoManager and InvIndexPhotoManager
    public static void main(String[] args) {

        //  Using PhotoManager (Before Optimization) 
        PhotoManager manager = new PhotoManager();
        System.out.println("===== Adding Photos (PhotoManager) =====");

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

        System.out.println("\n===== All Photos (PhotoManager) =====");
        printPhotoPaths(manager.getPhotos());

        // Create an Album using PhotoManager (slow search)
        System.out.println("\n===== Creating Album with PhotoManager =====");
        Album album1 = new Album("Animals on Grass", "animal AND grass", manager);
        LinkedList<Photo> albumPhotos1 = album1.getPhotos();
        System.out.println("Photos matching 'animal AND grass' (PhotoManager):");
        printPhotoPaths(albumPhotos1);
        System.out.println("Tag comparisons used (PhotoManager): " + album1.getNbComps());

        // ## Using InvIndexPhotoManager (After Optimization) 
        InvIndexPhotoManager invManager = new InvIndexPhotoManager();
        System.out.println("\n===== Adding Photos (InvIndexPhotoManager) =====");

        invManager.addPhoto(photo1);
        invManager.addPhoto(photo2);
        invManager.addPhoto(photo3);
        invManager.addPhoto(photo4);
        invManager.addPhoto(photo5);

        System.out.println("\n===== All Photos (InvIndexPhotoManager) =====");
        printPhotoPaths(invManager.getAllPhotos());

        // ## Search photos by Tag directly (optimized)
        System.out.println("\n===== Searching Photos with InvIndexPhotoManager =====");
        LinkedList<Photo> searchResults = invManager.findPhotosByTag("grass");
        System.out.println("Photos with tag 'grass' (Using BST Inverted Index):");
        printPhotoPaths(searchResults);
    }
}