package ua.org.oa.nedvygav;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CopyFiles implements Runnable{
    private File destinationFolder;
    private List<File> listOfFiles;
    private static final Logger logger = Logger.getLogger("CopyFiles logger");
    private CopyFiles(File destinationFolder, List<File> list){
        this.destinationFolder=destinationFolder;
        listOfFiles=list;
    }
    public static long copy(File sourceFolder, File destinationFolder, int threadsNumber){
        long timeBefore = System.currentTimeMillis();
        if (!destinationFolder.exists()) destinationFolder.mkdirs();
        if (!sourceFolder.exists()) return -1;
        CopyFiles temp = new CopyFiles(destinationFolder, new ArrayList<File>(Arrays.asList(sourceFolder.listFiles())));
        List<Thread> listOfThreads = new ArrayList<>();
        for (int i=0;i<threadsNumber;i++){
            listOfThreads.add(new Thread(temp, "Thread " + i));
            listOfThreads.get(i).start();
        }
        for (Thread t : listOfThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return System.currentTimeMillis()-timeBefore;
    }
    @Override
    public void run() {
        File fileInstance;
        while (listOfFiles.size()>0){
            synchronized (listOfFiles){
                fileInstance = listOfFiles.remove(0);
            }
            try {
                Files.copy(fileInstance.toPath(), new File(destinationFolder, fileInstance.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                logger.log(Level.INFO, destinationFolder + " " + fileInstance.getAbsolutePath()+" "+Thread.currentThread().getName());
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
