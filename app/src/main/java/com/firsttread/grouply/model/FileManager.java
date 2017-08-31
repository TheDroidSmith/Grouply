package com.firsttread.grouply.model;


import android.util.Log;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileManager implements IFileManager {

    @Override
    public ArrayList<CharSequence> importFile(String[] path) {

        ArrayList<CharSequence> names;
        names = new ArrayList<>();

        try {
            File csvFile = new File(path[0]);
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                names.add(nextLine[0]);
                Log.i("FileManager: ", nextLine[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("FileManager: ", "ERROR LOADING FILE");
        }

        //will be either full of names or empty
        //if empty, user will be notified
        return names;

    }


}
