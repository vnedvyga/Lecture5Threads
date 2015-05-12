package ua.org.oa.nedvygav;

import java.io.*;


public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Copying all files from " + new File("sourceFolder").getAbsolutePath());
        long time1 = CopyFiles.copy(new File("sourceFolder"), new File("destinationFolder"), 1);
        long time2 = CopyFiles.copy(new File("sourceFolder"), new File("destinationFolder1"), 2);
        long time3 = CopyFiles.copy(new File("sourceFolder"), new File("destinationFolder2"), 4);
        System.out.println("Time with 1 thread = "+time1);
        System.out.println("Time with 2 threads = "+time2);
        System.out.println("Time with 4 threads = "+time3);

    }

}
