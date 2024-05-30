#pragma once
#ifndef SERVICE_H
#define SERVICE_H
#include "repo.h"
#include "domain.h"
#include <vector>
#include <string>
#include <time.h>

using namespace std;

class Service {
friend class Repo;
private:
	Repo& repo;

public:
	explicit Service(Repo& repo) : repo{ repo } {};

	//validates the genre, generates a random new id, adds the song
	bool add_song(const string& title, const string& artist, const string& genre) {
		if (genre == "pop" or genre == "folk" or genre == "rock" or genre == "disco") {
			srand(time(NULL));
			int id = rand() % 100;
			while (repo.existing_id(id)) {
				id = rand() % 100;
			}
			this->repo.add_song(Song{id, title, artist, genre});
			return true;
		}
		return false;
	}

	//checks if the song exists, deletes the song
	bool delete_song(int id) {
		if (id == -1) return false;
		if (this->repo.existing_id(id) == true) {
			this->repo.delete_song(this->repo.get_song(id));
			return true;
		}
		return false;
	}

	//sorts the song list after the artist 
	void sort_artist() {
		auto& song_list = repo.song_list;
		auto compare = [](const Song& song1, const Song& song2) {
			return song1.get_artist() < song2.get_artist();
			};
		sort(song_list.begin(), song_list.end(), compare);
	}

	//gets all the songs 
	vector<Song> get_all() {
		return repo.get_all();
	}

	//gets the number of the songs written by the same artist
	int get_number_artist(const Song& song) {
		int number = -1;
		for (const auto& song_itt : repo.song_list) {
			if (song.get_artist() == song_itt.get_artist()) {
				++number;
			}
		}
		return number;
	}

	//gets the number of the songs in that genre
	int get_number_genre(const Song& song) {
		int number = -1;
		for (const auto& song_itt : repo.song_list) {
			if (song.get_genre() == song_itt.get_genre()) {
				++number;
			}
		}
		return number;
	}

	//get size
	int get_size() {
		return this->repo.song_count();
	}
};


#endif //SERVICE_H