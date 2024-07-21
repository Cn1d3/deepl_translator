package com.deepl.api.test.model;

import java.io.File;

public class Cleaner {
         /**
         * Deletes all files within a specified directory.
         * @param folderPath The path of the folder to clean.
         * @return true if all files are successfully deleted, false otherwise.
         */
        public static boolean cleanFolder(String folderPath) {
            File folder = new File(folderPath);
            if (!folder.isDirectory()) {
                System.out.println("The provided path is not a directory.");
                return false;
            }

            File[] files = folder.listFiles();
            if (files == null) {
                System.out.println("Failed to list files in the directory.");
                return false;
            }

            boolean allDeleted = true;
            for (File file : files) {
                if (file.isFile() && !file.delete()) {
                    System.out.println("Failed to delete: " + file.getName());
                    allDeleted = false;
                }
            }

            return allDeleted;
        }
    }

