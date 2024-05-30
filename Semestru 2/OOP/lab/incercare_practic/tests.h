#pragma once
#ifndef TESTS_H
#define TESTS_H

#include "domain.h"
#include "service.h"
#include "repo.h"
#include <vector>
#include <string>
#include <assert.h>

using namespace std;

string filename = "./test_input_EMPTY.txt";

void test_domain() {
	Song song{ 1, "title", "artist", "genre" };
	assert(song.get_artist() == "artist");
	assert(song.get_id() == 1);
	assert(song.get_title() == "title");
	assert(song.get_genre() == "genre");

	Song other_song{ 1, "not", "the", "same, but same ID" };
	assert(song == other_song);
	assert(song.get_artist() != other_song.get_artist());
}

void test_add() {
	Repo repo{ filename };
	Service service{ repo };
	assert(repo.song_count() == 0);
	assert(service.add_song("a", "b", "pop")); 
	assert(repo.song_count() == 1);

	assert(!service.add_song("a", "b", "NOT A VALID GENRE"));
	assert(!service.add_song("a", "b", "NOT DISCO"));
	assert(!service.add_song("a", "b", "NOT FOLK folk"));
	assert(!service.add_song("a", "b", "NOTHER"));
	assert(repo.song_count() == 1);

	repo.delete_song(repo.get_song2("a", "b", "pop"));
	assert(repo.song_count() == 0);
}


void test_delete() {
	Repo repo{ filename };
	Service service{ repo };
	assert(repo.song_count() == 0);
	assert(service.add_song("a", "b", "folk"));
	assert(repo.song_count() == 1);

	Song song = repo.get_song2("a", "b", "folk");
	assert(false == service.delete_song(song.get_id() + 1));
	assert(true == service.delete_song(song.get_id()));
	assert(repo.song_count() == 0);

}

void test_sort() {
	Repo repo{ filename };
	Service service{ repo };
	assert(service.add_song("a", "Inna", "folk"));
	assert(service.add_song("a", "Petrica Matu Stoian", "folk"));
	assert(service.add_song("a", "Alex Velea", "folk"));
	service.sort_artist();
	vector<Song> list = service.get_all();
	assert(list.at(0).get_artist() == "Alex Velea");
	assert(list.at(1).get_artist() == "Inna");
	assert(list.at(2).get_artist() == "Petrica Matu Stoian");

	repo.delete_song(repo.get_song2("a", "Inna", "folk"));
	repo.delete_song(repo.get_song2("a", "Petrica Matu Stoian", "folk"));
	repo.delete_song(repo.get_song2("a", "Alex Velea", "folk"));
}



void testAll() {
	test_domain();
	test_add();
	test_delete();
	test_sort();
}

#endif