package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        File dirGames = new File("./Games");
        StringBuilder log = new StringBuilder();

        File src = new File(dirGames, "src");
        log.append("Папка" + src + " создана: " + src.mkdir() + "\n");

        File res = new File(dirGames, "res");
        log.append("Папка" + res + " создана: " + res.mkdir() + "\n");

        File savegames = new File(dirGames, "savegames");
        log.append("Папка" + savegames + " создана: " + savegames.mkdir() + "\n");

        File temp = new File(dirGames, "temp");
        log.append("Папка" + temp + " создана: " + temp.mkdir() + "\n");

        File main = new File(src, "main");
        log.append("Папка" + main + " создана: " + main.mkdir() + "\n");
        File test = new File(src, "test");
        log.append("Папка" + test + " создана: " + test.mkdir() + "\n");

        File mainJava = new File(main, "Main.java");
        File utilsJava = new File(main, "Utils.java");
        File tempTxt = new File(temp, "temp.txt");


        try {
            log.append("Файл" + mainJava + " создан: " + mainJava.createNewFile() + "\n");
            log.append("Файл" + utilsJava + " создан: " + utilsJava.createNewFile() + "\n");
            log.append("Файл" + tempTxt + " создан: " + tempTxt.createNewFile() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File drawables = new File(res, "drawables");
        log.append("Папка" + drawables + " создана: " + drawables.mkdir() + "\n");

        File vectors = new File(res, "vectors");
        log.append("Папка" + vectors + " создана: " + vectors.mkdir() + "\n");

        File icons = new File(res, "icons");
        log.append("Папка" + icons + " создана: " + icons.mkdir() + "\n");

        try {
            FileWriter writer = new FileWriter(tempTxt);
            writer.write(log.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameProgress gameProgress1 = new GameProgress(89, 13, 3, 135.3);
        GameProgress gameProgress2 = new GameProgress(50, 13, 3, 135.3);
        GameProgress gameProgress3 = new GameProgress(89, 13, 3, 135.3);

        File save1 = new File(savegames, "save1.dat");
        saveGame(save1.getPath(), gameProgress1);
        File save2 = new File(savegames, "save2.dat");
        saveGame(save2.getPath(), gameProgress2);
        File save3 = new File(savegames, "save3.dat");
        saveGame(save3.getPath(), gameProgress3);

        List<File> objects = new ArrayList<>();
        objects.add(save1);
        objects.add(save2);
        objects.add(save3);
        zipFiles(new File(savegames, "zip.zip").getPath(), objects);
        for (File file : objects) {
            if (!file.delete()) {
                System.out.println("Ошибка удаления файла " + file);
            }
        }
    }


    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oss = new ObjectOutputStream(fos)) {
            oss.writeObject(gameProgress);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<File> objects) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (File file : objects) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zout.putNextEntry(zipEntry);
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] bytes = new byte[1024];
                    int length;
                    while((length = fis.read(bytes)) >= 0) {
                        zout.write(bytes, 0, length);
                    }
                }
                zout.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}