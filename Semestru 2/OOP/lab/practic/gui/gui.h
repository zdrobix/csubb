#pragma once

#include <QtWidgets/QMainWindow>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMessageBox>
#include <QtWidgets/QTableWidget>

#include "ui_gui.h"
#include "service.h"
#include "domain.h"

#include <unordered_map>
#include <time.h>

class gui : public QMainWindow
{
    Q_OBJECT

public:
    //constructor clasa
    explicit gui(Service& service) : service{ service } {
        populate_map();
        initialize();
        compose_elements();
    };

    //initializeaza butoane, line edit-uri
    void initialize() {
        id_lbl = new QLabel{ "Student ID" };
        name_lbl = new QLabel{ "Student name" };
        school_lbl = new QLabel{ "Student school" };
        hobby_lbl = new QLabel{ "Student Hobby" };

        id_txt = new QLineEdit;
        name_txt = new QLineEdit;
        school_txt = new QLineEdit;
        hobby_txt = new QLineEdit;

        add_btn = new QPushButton("Add student");
        connect(add_btn, &QPushButton::clicked, this, [=]() {
            QString id = id_txt->text();
            QString name = name_txt->text();
            QString school = school_txt->text();
            this->service.add_student(id.toInt(), name.toStdString(), school.toStdString());
            display_students();
            id_txt->clear();
            name_txt->clear();
            school_txt->clear();
            hobby_txt->clear();
            });

        sort_btn = new QPushButton("Sort students");
        connect(sort_btn, &QPushButton::clicked, this, [=]() {
            this->service.sort_students();
            display_students();
            QMessageBox::information(this, "Message", "The students have been sorted in alphabetical order!");
            });

        form_txt = new QFormLayout;
        form_txt->addRow(id_lbl, id_txt);
        form_txt->addRow(name_lbl, name_txt);
        form_txt->addRow(school_lbl, school_txt);
        form_txt->addRow(hobby_lbl, hobby_txt);
        form_txt->addRow(add_btn);
        form_txt->addRow(sort_btn);
    };

    //pune la un loc elementele
    void compose_elements() {
        QWidget* widg = new QWidget();
        main_layout = new QHBoxLayout(this);

        main_layout->addLayout(form_txt, 1);
        student_list = new QListWidget;
        display_students();
        connect_list();
        main_layout->addWidget(student_list, 3);

        widg->setLayout(main_layout);
        setCentralWidget(widg);
        setFixedSize(800, 600);

    };

    void display_students() {
        student_list->clear();
        vector<Student> list = this->service.get_all();
        for (const auto& stud : list) {
            string item = stud.get_name() + "      " + stud.get_school();
            student_list->addItem(new QListWidgetItem(QString::fromStdString(item)));
        }
    };

    void connect_list() {
        connect(student_list, &QListWidget::selectedItems, this, [=]() {
            
            //hobby_txt->setText();

            
        });
    }

    

private:
    Service& service;

    QLabel* id_lbl;
    QLabel* name_lbl;
    QLabel* school_lbl;
    QLabel* hobby_lbl;

    QLineEdit* id_txt;
    QLineEdit* name_txt;
    QLineEdit* school_txt;
    QLineEdit* hobby_txt;

    QPushButton* add_btn;
    QPushButton* sort_btn;

    QHBoxLayout* main_layout;
    QFormLayout* form_txt;
    QListWidget* student_list;
    QTableWidget* student_table;

    vector<int> desen;
    vector<int> foto;
    vector<int> muzica;
    vector<int> info;
    vector<int> jurnalism;

    vector<vector<int>> all;

    void populate_map() {
        
        for (const auto& stud : this->service.get_all()) {
            if (stud.get_id() % 5 == 1)
                desen.push_back(stud.get_id());
            if (stud.get_id() % 5 == 2)
                foto.push_back(stud.get_id());
            if (stud.get_id() % 5 == 3)
                muzica.push_back(stud.get_id());
            if (stud.get_id() % 5 == 4)
                info.push_back(stud.get_id());
            if (stud.get_id() % 5 == 5)
                jurnalism.push_back(stud.get_id());
        }
        all.push_back(desen);
        all.push_back(foto);
        all.push_back(info);
        all.push_back(muzica);
        all.push_back(jurnalism);
    }

    string cauta_hobby(int id) {
        for (const auto& idd : desen)
            if (idd == id)
                return "desen";
        for (const auto& idd : foto)
            if (idd == id)
                return "foto";
        for (const auto& idd : muzica)
            if (idd == id)
                return "muzica";
        for (const auto& idd : info)
            if (idd == id)
                return "info";
        for (const auto& idd : jurnalism)
            if (idd == id)
                return "jurnalism";
    }
};
