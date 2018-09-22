package com.peeru.labs.task.ui.util;

import android.content.Context;

import com.peeru.labs.task.data.model.Song;
import com.peeru.labs.task.ui.viewmodal.MainViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Priyank Jain on 21-09-2018.
 */
public class ReadCsvFile {
    // region Data Read from CSV and store in DB
    Context context;
    MainViewModel mainViewModel;

    public ReadCsvFile(Context context, MainViewModel mainViewModel) {
        this.context = context;
        this.mainViewModel = mainViewModel;
    }

    public void dataSetup() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = context.getAssets().open("sample_music_data.csv");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                mainViewModel.addSong(new Song(row[0], row[1], row[2]));
            }

        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
    }
    //endregion

}
