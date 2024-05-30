#ifndef COSREADONLY H
#define COSREADONLY H

#pragma once
#include "service_bag.h"
#include "observer.h"

#include <vector>
#include <time.h>
#include <qwidget.h>
#include <qpainter.h>

using namespace std;

class ReadOnly : public QWidget, public Observer {
private:
	Controller_bag& bag;
public:
	ReadOnly(Controller_bag& bag) : bag{ bag } {
		this->bag.addObserver(this);
	};

	~ReadOnly() {
		bag.removeObserver(this);
	}

	void update_o() override {
		this->repaint();
        this->update();
     	};

	void paintEvent(QPaintEvent* ev) override {
		QPainter p{ this };
		srand(time(NULL));
		for (const auto& u : bag.getAllProducts()) {
			int x = rand() % 700;
			int y = rand() % 300;
			p.drawRect(x, y, 20, u.getPrice() / 10);
		}

	}
};


//clasa cosCRUDGUI
class CosCRUDGUI : public QWidget, public Observer
{
private:
    Controller_bag& bag;
    Controller& serv;
    QListWidget* lst;
    QPushButton* btn;
    QPushButton* btnRandom;
    QLineEdit* lineEdit;
    void loadList(const vector<Product>& list)
    {
        lst->clear();
        for (const auto& p : list)
        {
            auto item = new QListWidgetItem(QString::fromStdString(p.getName() + " " + p.getType() + " " + to_string(p.getPrice())));
            lst->addItem(item);
        }
    }
    void initGUI()
    {
        QVBoxLayout* ly = new QVBoxLayout;
        lst = new QListWidget;
        btn = new QPushButton("Goleste cos");
        lineEdit = new QLineEdit;
        btnRandom = new QPushButton("Adauga produs");
        ly->addWidget(lst);
        ly->addWidget(btn);
        ly->addWidget(btnRandom);
        ly->addWidget(lineEdit);
        setLayout(ly);
    }
    void connectSignals()
    {
        QObject::connect(btn, &QPushButton::clicked, [&]() {
            bag.product_list.clear();
            loadList(bag.getAllProducts());
            });
        QObject::connect(btnRandom, &QPushButton::clicked, [&]() {
            for(int i = 0; i < lineEdit->text().toInt(); i ++ )
                this->bag.add_product_bag( this->serv.searchByPosition(rand()% 10) );
            lineEdit->clear();
            loadList(bag.getAllProducts());
            });
    }
public:
    CosCRUDGUI(Controller_bag& bag, Controller& serv) :bag{ bag }, serv{serv}
    {
        bag.addObserver(this);
        initGUI();
        connectSignals();
        loadList(bag.getAllProducts());
    }
    void update_o() override
    {
        loadList(bag.getAllProducts());
    }
    ~CosCRUDGUI()
    {
        bag.removeObserver(this);
    }
};

#endif //COSREADONLY H
