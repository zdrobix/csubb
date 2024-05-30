#pragma once
#include <vector>
#include <string>
#include <random>
#include <fstream>
#include <iomanip>

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
#include <QCheckBox>
#include <QScrollArea>
#include <QGridLayout>
#include <QMainWindow>

#include "service.h"
#include "observer.h"
#include "exception.h"
#include "service_bag.h"
#include "cosReadOnly.h"
#include "productTableModel.h"

using namespace std;

class prodGUI : public QWidget, public Observer {

private:
	Controller& service;
	Controller_bag& service_cos;
	
	vector<Product> productListToDisplay;

	unordered_map<string, int> prodMap;

	//Add a product labels
	QLabel* nameLbl;
	QLabel* producerLbl;
	QLabel* typeLbl;
	QLabel* priceLbl;

	//Add a product text boxes
	QLineEdit* nameEdit;
	QLineEdit* producerEdit;
	QLineEdit* typeEdit;
	QLineEdit* priceEdit;

	//Add a product button
	QPushButton* addProductButton;
	void addProductButtonClicked();
	
	QTableView* displayProducts();

	void updateProductWidget();
	
	//Delete a product
	QLineEdit* deleteProductEdit;
	QPushButton* deleteProductButton;
	void deleteProductButtonClicked();

	//Filter by price
	QPushButton* filterPriceButtonUnder;
	QPushButton* filterPriceButtonOver;
	QLineEdit* filterPriceEdit;
	void filterProductPriceClickedUnder();
	void filterProductPriceClickedOver();

	//Filter by type
	QPushButton* filterTypeButton;
	QLineEdit* filterTypeEdit;
	void filterProductTypeClicked();

	//Checkbox and sort functions
	QCheckBox* ascendingDescending;
	QPushButton* sortByName;
	QPushButton* sortByPrice;
	QPushButton* sortByType;
	void sortNameButtonClicked();
	void sortPriceButtonClicked();
	void sortTypeButtonClicked();

	//Add a product labels + textboxes
	QFormLayout* formLayout;
	//Main layout
	QHBoxLayout* mainLayout;
	//Product list scroll area widget
	QTableView* productWidget;

	//Combines the upper elements
	void composeWidgets();

	void showMessage(const QString& message);

	//Shopping bag elements:
	QPushButton* placeOrderButton;
	QPushButton* bagCRUDbutton;
	void placeOrderButtonClicked();
	//Main layout shopping bag
	QHBoxLayout* mainBagLayout;
	QFormLayout* formLayoutBag;
	QTableView* productBagWidget;
	//Add random number of products
	QPushButton* addRandomButton;
	QLineEdit* addRandomEdit;
	void addRandomButtonClicked();
	//Empty shopping bag
	QPushButton* emptyBagButton;
	void emptyBagButtonClicked();
	//Place an order button (purchase)
	QPushButton* exportPurchase;
	QLineEdit* fileName;
	QLineEdit* orderName;
	QLineEdit* orderAddress;
	void exportPurchaseClicked();

	void updateProductShoppingBag();

	QTableView* displayProductsBag();

	void parcurgereMapButoane();

	//undo button and function
	QPushButton* undoButton;
	void undoButtonClicked();

public:
	prodGUI(Controller& service, Controller_bag& service_cos) :
		service{ service }, service_cos{ service_cos } {

		service_cos.addObserver(this);

		initGUIinterface();

		composeWidgets();

		prodMap = this->service.createProductMap();

		parcurgereMapButoane();
	};

	void initGUIinterface();

	void update_o() override {

		this->updateProductShoppingBag();
	}
};