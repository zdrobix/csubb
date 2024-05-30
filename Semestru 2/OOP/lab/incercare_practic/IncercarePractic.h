#pragma once

#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QTableView>
#include <QtWidgets/QLabel>
#include <QStandardItemModel>
#include <QTableWidget>
#include <QHeaderView>

#include "ui_IncercarePractic.h"
#include "service.h"

class IncercarePractic : public QMainWindow
{
    Q_OBJECT

public:
    IncercarePractic(Service& service) : service{ service } {
        
        init_elements();

        compose_elements();
    };


private:

    Service& service;

    QLineEdit* title_txt;
    QLineEdit* artist_txt;
    QLineEdit* genre_txt;

    QLabel* title_lbl;
    QLabel* artist_lbl;
    QLabel* genre_lbl;

    QPushButton* add_song_btn;
    QPushButton* delete_song_btn;
    
    QHBoxLayout* hbox_layout1;
    QVBoxLayout* vbox_layout1;
    QFormLayout* form_layout1;

    QTableWidget* song_widget;

    void init_elements() {
        title_txt = new QLineEdit;
        artist_txt = new QLineEdit;
        genre_txt = new QLineEdit;

        title_txt->setFixedWidth(70);
        artist_txt->setFixedWidth(70);
        genre_txt->setFixedWidth(70);

        title_lbl = new QLabel{ "Song title" };
        artist_lbl = new QLabel{ "Song artist" };
        genre_lbl = new QLabel{ "Song genre" };

        add_song_btn = new QPushButton{ "Add song" };
        connect(add_song_btn, &QPushButton::clicked, this, [=]() {

            string artist, title, genre;
            title = title_txt->text().toStdString();
            artist = artist_txt->text().toStdString();
            genre = genre_txt->text().toStdString();

            this->service.add_song(title, artist, genre);
            title_txt->clear();
            artist_txt->clear();
            genre_txt->clear();
            display_songs();
            });
        add_song_btn->setFixedWidth(140);

        delete_song_btn = new QPushButton{ "Delete song" };
        connect(delete_song_btn, &QPushButton::clicked, this, [&]() {
            if (!song_widget->selectedItems().isEmpty()) {
                int selected_row = song_widget->selectedItems().first()->row();
                int song_id = song_widget->item(selected_row, 0)->text().toInt();
                this->service.delete_song(song_id);
                display_songs();
            }
            });
        delete_song_btn->setFixedWidth(140);

        form_layout1 = new QFormLayout;
        form_layout1->setFieldGrowthPolicy(QFormLayout::ExpandingFieldsGrow);

        form_layout1->addRow(title_lbl, title_txt);
        form_layout1->addRow(artist_lbl, artist_txt);
        form_layout1->addRow(genre_lbl, genre_txt);
        form_layout1->addRow(add_song_btn);
        form_layout1->addRow(delete_song_btn);

    };

    void compose_elements() {
        QWidget* main_widget = new QWidget();

        hbox_layout1 = new QHBoxLayout(this);
        hbox_layout1->addLayout(form_layout1, 1);
        
        song_widget = new QTableWidget();
        display_songs(); //pentru a crea song_widget
        hbox_layout1->addWidget(song_widget, 4);

        main_widget->setLayout(hbox_layout1);
        setCentralWidget(main_widget);
        setFixedSize(900, 600);
    };

    void display_songs() {
        song_widget->clearContents();
        song_widget->setColumnCount(6);
        song_widget->setHorizontalHeaderLabels({ "ID", "Title", "Artist", "Genre", "#same Artist", "#same Genre" });
        song_widget->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
        song_widget->verticalHeader()->setVisible(false);
        //song_widget->verticalHeader()->setFixedWidth(50);
        song_widget->setRowCount(static_cast<int>(this->service.get_size()));

        int row = 0;
        this->service.sort_artist();
        for (const auto& song : this->service.get_all()) {
            song_widget->setItem(row, 0, new QTableWidgetItem(QString::number(song.get_id())));
            song_widget->setItem(row, 1, new QTableWidgetItem(QString::fromStdString(song.get_title())));
            song_widget->setItem(row, 2, new QTableWidgetItem(QString::fromStdString(song.get_artist())));
            song_widget->setItem(row, 3, new QTableWidgetItem(QString::fromStdString(song.get_genre())));
            song_widget->setItem(row, 4, new QTableWidgetItem(QString::number(this->service.get_number_artist(song))));
            song_widget->setItem(row, 5, new QTableWidgetItem(QString::number(this->service.get_number_genre(song))));
            ++row;
        }
    };
};
