package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PhotoManager photoManager = new PhotoManager();
    private static InvIndexPhotoManager invIndexManager = new InvIndexPhotoManager();
    private static LinkedList<Album> albums = new LinkedList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1: viewAllPhotos(); break;
                case 2: addNewPhoto(); break;
                case 3: deletePhoto(); break;
                case 4: createAlbum(); break;
                case 5: viewAlbums(); break;
                case 6: searchPhotosByTag(); break;
                case 0: running = false; System.out.println("Exiting program. Goodbye!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n===== PHOTO MANAGEMENT SYSTEM =====");
        System.out.println("1. View all photos");
        System.out.println("2. Add new photo");
        System.out.println("3. Delete photo");
        System.out.println("4. Create album");
        System.out.println("5. View albums");
        System.out.println("6. Search photos by tag");
        System.out.println("0. Exit");
        System.out.println("===================================");
    }

    private static void addNewPhoto() {
        System.out.print("Enter image name (e.g., hedgehog.jpg): ");
        String imageName = scanner.nextLine();

        System.out.print("Enter tags (comma separated): ");
        String tagsInput = scanner.nextLine();

        Photo photo = new Photo(imageName, toTagsLinkedList(tagsInput));
        photoManager.addPhoto(photo);
        invIndexManager.addPhoto(photo);

        System.out.println("Photo added successfully!");
        waitForEnter();
    }

    private static void viewAllPhotos() {
        System.out.println("\n===== ALL PHOTOS =====");
        LinkedList<Photo> photos = photoManager.getPhotos();

        if (photos.isEmpty()) {
            System.out.println("No photos available.");
            waitForEnter();
            return;
        }

        int index = 1;
        photos.goToFirst();
        while (!photos.isLast()) {
            Photo photo = photos.getData();
            printPhotoDetails(index++, photo);
            photos.goToNext();
        }
        Photo lastPhoto = photos.getData();
        printPhotoDetails(index, lastPhoto);

        waitForEnter();
    }

    private static void deletePhoto() {
        System.out.println("\n===== DELETE PHOTO =====");
        LinkedList<Photo> photos = photoManager.getPhotos();

        if (photos.isEmpty()) {
            System.out.println("No photos to delete.");
            waitForEnter();
            return;
        }

        int index = 1;
        photos.goToFirst();
        while (!photos.isLast()) {
            Photo photo = photos.getData();
            System.out.println(index++ + ". " + photo.getPath());
            photos.goToNext();
        }
        Photo lastPhoto = photos.getData();
        System.out.println(index + ". " + lastPhoto.getPath());

        int choice = getIntInput("Enter the number of the photo to delete (0 to cancel): ");
        if (choice == 0 || choice > index) {
            System.out.println("Operation canceled.");
            waitForEnter();
            return;
        }

        photos.goToFirst();
        for (int i = 1; i < choice; i++) {
            photos.goToNext();
        }
        Photo selectedPhoto = photos.getData();
        String path = selectedPhoto.getPath();

        photoManager.deletePhoto(path);
        invIndexManager.deletePhoto(path);

        System.out.println("Photo deleted successfully!");
        waitForEnter();
    }

    private static void createAlbum() {
        System.out.println("\n===== CREATE ALBUM =====");
        System.out.print("Enter album name: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Album name cannot be empty.");
            waitForEnter();
            return;
        }

        if (albumExists(name)) {
            System.out.println("Album with this name already exists.");
            waitForEnter();
            return;
        }

        System.out.print("Enter album condition (e.g., 'tag1 AND tag2') or leave empty for all photos: ");
        String condition = scanner.nextLine();

        Album album = condition.trim().isEmpty() ?
            new Album(name, "", photoManager) :
            new Album(name, condition, photoManager);

        albums.add(album);
        System.out.println("Album created successfully!");
        waitForEnter();
    }

    private static void viewAlbums() {
        System.out.println("\n===== ALBUMS =====");

        if (albums.isEmpty()) {
            System.out.println("No albums available.");
            waitForEnter();
            return;
        }

        int index = 1;
        albums.goToFirst();
        while (!albums.isLast()) {
            Album album = albums.getData();
            System.out.println(index++ + ". " + album.getName() + " (Condition: " +
                (album.getCondition().isEmpty() ? "All Photos" : album.getCondition()) + ")");
            albums.goToNext();
        }
        Album lastAlbum = albums.getData();
        System.out.println(index + ". " + lastAlbum.getName() + " (Condition: " +
            (lastAlbum.getCondition().isEmpty() ? "All Photos" : lastAlbum.getCondition()) + ")");

        int choice = getIntInput("Enter the number of the album to view (0 to cancel): ");
        if (choice == 0 || choice > index) {
            System.out.println("Operation canceled.");
            waitForEnter();
            return;
        }

        albums.goToFirst();
        for (int i = 1; i < choice; i++) {
            albums.goToNext();
        }
        Album selectedAlbum = albums.getData();

        System.out.println("\n===== ALBUM: " + selectedAlbum.getName() + " =====");
        System.out.println("Condition: " +
            (selectedAlbum.getCondition().isEmpty() ? "All Photos" : selectedAlbum.getCondition()));

        LinkedList<Photo> albumPhotos = selectedAlbum.getPhotos();
        if (albumPhotos.isEmpty()) {
            System.out.println("No matching photos.");
            waitForEnter();
            return;
        }

        int photoIndex = 1;
        albumPhotos.goToFirst();
        while (!albumPhotos.isLast()) {
            Photo photo = albumPhotos.getData();
            printPhotoDetails(photoIndex++, photo);
            albumPhotos.goToNext();
        }
        Photo lastPhoto = albumPhotos.getData();
        printPhotoDetails(photoIndex, lastPhoto);

        System.out.println("Tag comparisons used: " + selectedAlbum.getNbComps());
        waitForEnter();
    }

    private static void searchPhotosByTag() {
        System.out.println("\n===== SEARCH PHOTOS BY TAG =====");
        System.out.print("Enter tag to search for: ");
        String tag = scanner.nextLine();

        if (tag.trim().isEmpty()) {
            System.out.println("Tag cannot be empty.");
            waitForEnter();
            return;
        }

        Album searchAlbum = new Album("Search Result", tag, photoManager);
        LinkedList<Photo> results = searchAlbum.getPhotos();

        if (results.isEmpty()) {
            System.out.println("No photos found with tag: " + tag);
            waitForEnter();
            return;
        }

        int index = 1;
        results.goToFirst();
        while (!results.isLast()) {
            Photo photo = results.getData();
            printPhotoDetails(index++, photo);
            results.goToNext();
        }
        Photo lastPhoto = results.getData();
        printPhotoDetails(index, lastPhoto);

        System.out.println("Tag comparisons used: " + searchAlbum.getNbComps());
        waitForEnter();
    }

    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        if (tags != null && !tags.trim().isEmpty()) {
            String[] tagsArray = tags.split("\\s*,\\s*");
            for (String tag : tagsArray) {
                if (!tag.trim().isEmpty()) {
                    result.add(tag.trim());
                }
            }
        }
        return result;
    }

    private static void printPhotoDetails(int index, Photo photo) {
        System.out.println(index + ". " + photo.getPath());
        System.out.print("   Tags: ");
        printList(photo.getTags());
    }

    private static void printList(LinkedList<String> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("[]");
            return;
        }

        StringBuilder sb = new StringBuilder("[");
        list.goToFirst();
        while (!list.isLast()) {
            sb.append(list.getData()).append(", ");
            list.goToNext();
        }
        sb.append(list.getData()).append("]");
        System.out.println(sb.toString());
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return getIntInput();
    }

    private static void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static boolean albumExists(String name) {
        if (albums.isEmpty()) return false;
        albums.goToFirst();
        while (!albums.isLast()) {
            if (albums.getData().getName().equalsIgnoreCase(name)) {
                return true;
            }
            albums.goToNext();
        }
        return albums.getData().getName().equalsIgnoreCase(name);
    }
}
