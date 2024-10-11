package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateFileDeleter {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java DuplicateFileDeleter <directory_path>");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return;
        }

        System.out.println("Deleting duplicate files in: " + directoryPath);
        deleteDuplicateFiles(directory);
        System.out.println("Duplicate file deletion completed.");
    }

    private static void deleteDuplicateFiles(File directory) {
        Map<String, List<File>> fileChecksumMap = new HashMap<>();

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                try {
                    String checksum = calculateChecksum(file);
                    fileChecksumMap.computeIfAbsent(checksum, k -> new ArrayList<>()).add(file);
                } catch (IOException e) {
                    System.err.println("Error calculating checksum for file: " + file.getAbsolutePath());
                }
            } else if (file.isDirectory()) {
                deleteDuplicateFiles(file);
            }
        }

        for (List<File> duplicates : fileChecksumMap.values()) {
            if (duplicates.size() > 1) {
                for (int i = 1; i < duplicates.size(); i++) {
                    File duplicateFile = duplicates.get(i);
                    if (duplicateFile.delete()) {
                        System.out.println("Deleted duplicate file: " + duplicateFile.getAbsolutePath());
                    } else {
                        System.err.println("Error deleting duplicate file: " + duplicateFile.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static String calculateChecksum(File file) throws IOException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: MD5 algorithm not found. " + e.getMessage());
            return ""; // Return an empty string to indicate an error
        }

        FileInputStream fis = new FileInputStream(file);
        byte[] dataBytes = new byte[1024];
        int bytesRead;

        while ((bytesRead = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, bytesRead);
        }

        byte[] mdBytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte mdByte : mdBytes) {
            sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
        }

        fis.close();
        return sb.toString();
    }
}
