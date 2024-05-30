#ifndef DOMAIN_H
#define DOMAIN_H
#pragma once
#include <string>

using namespace std;

class Song {
private:
	int id;
	string title, artist, genre;
	
public:
	//generates a song
	explicit Song(int id, string title, string artist, string genre)
		: id{ id }, title{ title }, artist{ artist }, genre{ genre } {};

	//generates a null song
	Song() : id{ -1 }, title{ "" }, artist{ "" }, genre{ "" } {};

	int get_id() const {
		return this->id;
	}

	const string& get_title() const {
		return this->title;
	}

	const string& get_artist() const {
		return this->artist;
	}

	const string& get_genre() const {
		return this->genre;
	}

	bool operator==(const Song& other) const{

		return other.get_id() == this->get_id();
	};
};

#endif //DOMAIN_H