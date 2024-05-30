#include "productGUI.h"

void productGUI::initGUIinterface() {

	nameLbl = new QLabel{ "Product name: " };
	producerLbl = new QLabel{ "Product producer: " };
	typeLbl = new QLabel{ "Product type: " };
	priceLbl = new QLabel{ "Product price: " };
	
	nameEdit = new QLineEdit;
	producerEdit = new QLineEdit;
	typeEdit = new QLineEdit;
	priceEdit = new QLineEdit;

	addProductButton = new QPushButton("Add Product");

	connect(addProductButton, &QPushButton::clicked, this, &productGUI::addProductButtonClicked);

	QVBoxLayout* mainLayout = new QVBoxLayout(this);

	mainLayout->addWidget(nameLbl);
	mainLayout->addWidget(nameEdit);
	mainLayout->addWidget(producerLbl);
	mainLayout->addWidget(producerEdit);
	mainLayout->addWidget(typeLbl);
	mainLayout->addWidget(typeEdit);
	mainLayout->addWidget(priceLbl);
	mainLayout->addWidget(priceEdit);
	mainLayout->addWidget(addProductButton);

	setLayout(mainLayout);

	titleLbl = new QLabel{ "Zdrobix Liqour Store" };

	setWindowTitle("Zdrobix Liqour Store");
}

void productGUI::addProductButtonClicked() {

	QString name = nameEdit->text();
	QString producer = producerEdit->text();
	QString type = typeEdit->text();
	float price = priceEdit->text().toFloat();

	string nameString = name.toStdString();
	string producerString = producer.toStdString();
	string typeString = type.toStdString();

	service.add(nameString, producerString, typeString, price);

	nameEdit->clear();
	producerEdit->clear();
	typeEdit->clear();
	priceEdit->clear();
}