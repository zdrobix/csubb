#pragma once
#ifndef GUI_H
#define GUI_H

#include <QtWidgets/QMainWindow>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QLabel>
#include <QtWidgets/QWidget>
#include <QtWidgets/QTableWidget>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QMessageBox>
#include <QtWidgets/QTableView>
#include <QtWidgets/QHeaderView>
#include <QtGui/QStandardItemModel>
#include <QtWidgets/QComboBox>
#include <QColor>
#include <QPainter>

#include "ui_gui.h"
#include "service.h"

#include <stdexcept>

class gui : public QMainWindow {
    Q_OBJECT

public:
    explicit gui(Service& service) : service{ service } {
        initialize_elements();
        compose_elements();
    };
    
    void initialize_elements() {
        denumire_txt = new QLineEdit;
        tip_txt = new QLineEdit;
        roti_txt = new QLineEdit;

        denumire_txt->setFixedWidth(100);
        tip_txt->setFixedWidth(100);
        roti_txt->setFixedWidth(100);

        denumire_lbl = new QLabel{ "Denumire" };
        tip_lbl = new QLabel{ "Tip" };
        roti_lbl = new QLabel{ "Numar Roti" };
        add_btn = new QPushButton("Add truck");
        //add_btn->setFixedWidth(100 + roti_lbl->widthMM());
        connect(add_btn, &QPushButton::clicked, this, [=]() {
            string denumire, tip;
            int roti;
            denumire = denumire_txt->text().toStdString();
            tip = tip_txt->text().toStdString();
            roti = stoi(roti_txt->text().toStdString());
            try {
                this->service.add_tractor(denumire, tip, roti);
                denumire_txt->clear();
                tip_txt->clear();
                roti_txt->clear();
                add_types_combo_box();
                display_tractoare();
            }
            catch (const runtime_error& e) {
                this->alert_message(QString::fromStdString(e.what()));
            }
            });
        
        combo_box = new QComboBox;
        add_types_combo_box();
        connect(combo_box, &QComboBox::currentIndexChanged, this, [=]() {
            selected_type = combo_box->currentText().toStdString();
            display_tractoare();
            });

        drawing_area = new QWidget;
        connect(tractor_table, &QTableWidget::itemClicked, this, [&](QTableWidgetItem* itemm) {
            int row = itemm->row();
            Tractor tractor = this->service.get(tractor_table->item(row, 0)->text().toInt());
            this->alert_message(QString::fromStdString(tractor.get_denumire()));

            QPainter* painter = new QPainter(drawing_area);
            painter->setPen(QColor(250, 9, 9));
            painter->setBrush(Qt::SolidPattern);
            for (int i = 1; i <= tractor.get_roti(); i++) {
                painter->drawEllipse(QPointF(20 * i, 30), 10, 10);

            }
            drawing_area->update();
            });

        form_layout = new QFormLayout;
        form_layout->addRow(denumire_lbl, denumire_txt);
        form_layout->addRow(tip_lbl, tip_txt);
        form_layout->addRow(roti_lbl, roti_txt);
        form_layout->addRow(add_btn);
        form_layout->addRow(combo_box);
    };

    void compose_elements() {
        QWidget* widg = new QWidget();
        main_layout = new QHBoxLayout(this);

        main_layout->addLayout(form_layout, 1);
        tractor_table = new QTableWidget;
        display_tractoare();
        main_layout->addWidget(tractor_table, 4);

        widg->setLayout(main_layout);
        setCentralWidget(widg);
        setFixedSize(800, 600);
        
    };

    void display_tractoare() {
        int count = static_cast<int>(this->service.get_all().size()), row = 0;
        this->service.sort_denumire();
        tractor_table->setColumnCount(5);
        tractor_table->setHorizontalHeaderLabels({ "ID", "Denumire", "Tip", "Numar Roti", "Cu acelasi tip" });
        tractor_table->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
        tractor_table->setRowCount(count);
        tractor_table->verticalHeader()->setVisible(false);
        for (const auto& tractor : this->service.get_all()) {
            tractor_table->setItem(row, 0, new QTableWidgetItem(QString::number(tractor.get_id())));
            tractor_table->setItem(row, 1, new QTableWidgetItem(QString::fromStdString(tractor.get_denumire())));
            tractor_table->setItem(row, 2, new QTableWidgetItem(QString::fromStdString(tractor.get_tip())));
            tractor_table->setItem(row, 3, new QTableWidgetItem(QString::number(tractor.get_roti())));
            tractor_table->setItem(row, 4, new QTableWidgetItem(QString::number(this->service.number_same_type(tractor))));
            if (tractor.get_tip() == selected_type) {
                for (int i = 0; i < 5; i++) {
                    QTableWidgetItem* row_item = tractor_table->item(row, i);
                    row_item->setBackground(QBrush(QColor(250, 9, 9)));
                }
            }
            ++row;
        }
    }

    void add_types() {
        for (const auto& tractor : this->service.get_all()) {
            bool found = false;
            for (const auto& type : types)
                if (type == tractor.get_tip())
                    found = true;
            if (!found)
                types.push_back(tractor.get_tip());
        }
    }

    void add_types_combo_box() {
        add_types();
        combo_box->clear();
        for (const auto& type : types) {
            combo_box->addItem(QString::fromStdString(type));
        }
    }
private:
    Service& service;

    QLineEdit* denumire_txt;
    QLineEdit* tip_txt;
    QLineEdit* roti_txt;

    QLabel* denumire_lbl;
    QLabel* tip_lbl;
    QLabel* roti_lbl;

    QPushButton* add_btn;

    QFormLayout* form_layout;
    QTableWidget* tractor_table;
    QHBoxLayout* main_layout;

    QComboBox* combo_box;
    vector<string> types;
    string selected_type;

    QWidget* drawing_area;

    void alert_message(const QString message) {
        QMessageBox::information(this, "Message", message);
    }
};

#endif //GUI_H