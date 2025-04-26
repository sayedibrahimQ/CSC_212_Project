package com.mycompany.csc_212_project;

import com.mycompany.csc_212_project.datastructures.LinkedList;

public class Test {

    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        if (tags != null && !tags.trim().isEmpty()) {
            String[] tagsArray = tags.split("\\s*,\\s*");
            for (String tag : tagsArray) {
                result.add(tag.trim());
            }
        }
        return result;
    }

    public static <T> void printList(LinkedList<T> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        list.goToFirst();
        while (!list.isLast()) {
            System.out.print(list.getData() + ", ");
            list.goToNext();
        }
        System.out.print(list.getData());
        System.out.println("]");
    }

    private static void printPhotoPaths(LinkedList<Photo> photos) {
        if (photos.isEmpty()) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        photos.goToFirst();
        while (!photos.isLast()) {
            System.out.print(photos.getData().getPath() + ", ");
            photos.goToNext();
        }
        System.out.print(photos.getData().getPath());
        System.out.println("]");
    }

    public static void main(String[] args) {
        PhotoManager manager = new PhotoManager();

        System.out.println("===== Adding Photos =====");
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

        System.out.println("\n===== All Photos =====");
        printPhotoPaths(manager.getPhotos());
    }
}
