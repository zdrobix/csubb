#pragma once
#ifndef REPO_H
#define REPO_H
#include "domain.h"
#include <vector>
#include <time.h>
#include <fstream>

using namespace std;

class Repo {
friend class Service;
private:
	string filename;
	vector<Song> song_list;

public:
	explicit Repo(const string& filename) : filename{ filename } {
		//deschide fisierul si adauga fiecare melodie in memorie
		this->load_from_file();
	};
	
	void add_song(const Song& song) {
		//adds a song
		song_list.push_back(song);
		this->save_to_file();
	}

	void delete_song(const Song& song) {
		//deletes a song
		for (auto it = song_list.begin(); it != song_list.end(); ++it) {
			if (*it == song) {
				song_list.erase(it);
				break;
			}
		}
		this->save_to_file();
	}
	
	bool existing_id(int id) {
		//return true if the id exists, false if it doesn't exist
		for (const auto& song : this->song_list) {
			if (song.get_id() == id)
				return true;
		}
		return false;
	}

	Song get_song(int id) {
		//gets the song by the id
		for (const auto& song : this->song_list) {
			if (song.get_id() == id)
				return song;
		}
		return Song{};
	}

	Song get_song2(const string& title, const string& artist, const string& genre) {
		//gets the song by the title, artist and genre
		for (const auto& song : this->song_list) {
			if (song.get_title() == title and song.get_artist() == artist and song.get_genre() == genre)
				return song;
		}
		return Song{};
	}

	void load_from_file() {
		ifstream in(filename);
		string id, title, artist, genre;
		while (getline(in, id)) {
			getline(in, title);
			getline(in, artist);
			getline(in, genre);
			this->add_song(Song{ stoi(id), title, artist, genre });
		}
		in.close();
	}

	void save_to_file() {
		ofstream out(filename);
		for (const auto& song : this->song_list) {
			out << song.get_id() << endl
				<< song.get_title() << endl
				<< song.get_artist() << endl
				<< song.get_genre() << endl;
		}
		out.close();
	}

	int song_count() {
		return song_list.size();
	}

	vector<Song> get_all() {
		return song_list;
	}
};

#endif //REPO_H