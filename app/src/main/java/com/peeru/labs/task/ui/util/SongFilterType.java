package com.peeru.labs.task.ui.util;

import com.peeru.labs.task.data.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Priyank Jain on 11-09-2018.
 */
public class SongFilterType {
    private List<Song> songList;
    HashMap<String, ArrayList<String>> albumList = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> artistList = new HashMap<String, ArrayList<String>>();

    public SongFilterType(List<Song> songList) {
        this.songList = songList;
        for (Song song : songList) {
            if (!albumList.containsKey(song.getAlbum())) {
                ArrayList<String> songAdded = new ArrayList<String>();
                songAdded.add(song.getName());
                albumList.put(song.getAlbum(), songAdded);
            } else {
                albumList.get(song.getAlbum()).add(song.getName());
            }

            if (!artistList.containsKey(song.getArtist())) {
                ArrayList<String> artistSong = new ArrayList<String>();
                artistSong.add(song.getName());
                artistList.put(song.getArtist(), artistSong);
            } else {
                artistList.get(song.getArtist()).add(song.getName());
            }
        }
    }

    public HashMap<String, ArrayList<String>> getAlbumList() {
        return albumList;
    }

    public HashMap<String, ArrayList<String>> getArtistList() {
        return artistList;
    }

}
