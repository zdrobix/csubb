#pragma once
#include <vector>
#include <string>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QPushButton>
#include <QHBoxLayout>
#include <QFormLayout>
#include <QLineEdit>
#include <QTableWidget>
#include <QMessageBox>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include <qlistwidget.h>
#include <QStringList>
#include "service.h"

using namespace std;

class productGUI : public QWidget {

private:
	Controller& service;
	Controller& service_cos;

	QLabel* titleLbl;

	QLabel* nameLbl;
	QLabel* producerLbl;
	QLabel* typeLbl;
	QLabel* priceLbl;

	QLineEdit* nameEdit;
	QLineEdit* producerEdit;
	QLineEdit* typeEdit;
	QLineEdit* priceEdit;

	QPushButton* addProductButton;
	void addProductButtonClicked();

public:
	productGUI( Controller& serv, Controller& serv2) : service(serv), service_cos(serv2) {
		initGUIinterface();
	};
	
	void initGUIinterface();
};