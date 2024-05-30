#include "prodGUI.h"

void prodGUI::showMessage(const QString& message) {

	QMessageBox::information(this, "Message", message);
}

void prodGUI::initGUIinterface() {

	nameLbl = new QLabel{ "Product name " };
	producerLbl = new QLabel{ "Product producer " };
	typeLbl = new QLabel{ "Product type " };
	priceLbl = new QLabel{ "Product price " };

	nameEdit = new QLineEdit;
	producerEdit = new QLineEdit;
	typeEdit = new QLineEdit;
	priceEdit = new QLineEdit;
	deleteProductEdit = new QLineEdit;
	filterPriceEdit = new QLineEdit;
	filterTypeEdit = new QLineEdit;

	ascendingDescending = new QCheckBox("Check for ascending order");

	nameEdit->setFixedWidth(120);
	producerEdit->setFixedWidth(120);
	typeEdit->setFixedWidth(120);
	priceEdit->setFixedWidth(120);
	filterTypeEdit->setFixedWidth(120);
	deleteProductEdit->setFixedWidth(120);
	filterPriceEdit->setFixedWidth(120);

	addProductButton = new QPushButton("Add product");
	connect(addProductButton, &QPushButton::clicked, this, &prodGUI::addProductButtonClicked);
	addProductButton->setFixedWidth(223);

	deleteProductButton = new QPushButton("Delete product");
	connect(deleteProductButton, &QPushButton::clicked, this, &prodGUI::deleteProductButtonClicked);

	filterPriceButtonUnder = new QPushButton("Filter under");
	filterPriceButtonOver = new QPushButton("Filter over");
	connect(filterPriceButtonUnder, &QPushButton::clicked, this, &prodGUI::filterProductPriceClickedUnder);
	connect(filterPriceButtonOver, &QPushButton::clicked, this, &prodGUI::filterProductPriceClickedOver);

	filterTypeButton = new QPushButton("Filter by type");
	connect(filterTypeButton, &QPushButton::clicked, this, &prodGUI::filterProductTypeClicked);

	sortByName = new QPushButton("Sort by Name");
	connect(sortByName, &QPushButton::clicked, this, &prodGUI::sortNameButtonClicked);
	sortByName->setFixedWidth(223);

	sortByPrice = new QPushButton("Sort by Price");
	connect(sortByPrice, &QPushButton::clicked, this, &prodGUI::sortPriceButtonClicked);
	sortByPrice->setFixedWidth(223);

	sortByType = new QPushButton("Sort by Type");
	connect(sortByType, &QPushButton::clicked, this, &prodGUI::sortTypeButtonClicked);
	sortByType->setFixedWidth(223);

	placeOrderButton = new QPushButton("Place Order");
	connect(placeOrderButton, &QPushButton::clicked, this, &prodGUI::placeOrderButtonClicked);
	placeOrderButton->setFixedWidth(223);
	placeOrderButton->setFixedHeight(38);

	bagCRUDbutton = new QPushButton("Open CRUD and ReadOnly");
	connect(bagCRUDbutton, &QPushButton::clicked, this, [=]() {
		ReadOnly* drawWidget = new ReadOnly(this->service_cos);
		drawWidget->show();

		CosCRUDGUI* cosCrud = new CosCRUDGUI(this->service_cos, this->service);
		cosCrud->show();
		});

	undoButton = new QPushButton("Undo");
	connect(undoButton, &QPushButton::clicked, this, &prodGUI::undoButtonClicked);

	QLabel* productTypeCountLbl = new QLabel{ "Product count per type:" };

	undoButton->setFixedWidth(223);

	this->productListToDisplay = this->service.getAllProducts();

	formLayout = new QFormLayout;
	formLayout->setFieldGrowthPolicy(QFormLayout::ExpandingFieldsGrow);

	formLayout->addRow(nameLbl, nameEdit);
	formLayout->addRow(producerLbl, producerEdit);
	formLayout->addRow(typeLbl, typeEdit);
	formLayout->addRow(priceLbl, priceEdit);
	formLayout->addRow(addProductButton);
	formLayout->addRow(deleteProductButton, deleteProductEdit);
	formLayout->addRow(filterTypeButton, filterTypeEdit);
	formLayout->addRow(filterPriceButtonUnder, filterPriceEdit);
	formLayout->addRow(filterPriceButtonOver, filterPriceEdit);
	formLayout->addRow(ascendingDescending);
	formLayout->addRow(sortByName);
	formLayout->addRow(sortByPrice);
	formLayout->addRow(sortByType);
	formLayout->addRow(placeOrderButton);
	formLayout->addRow(bagCRUDbutton);
	formLayout->addRow(undoButton);
	formLayout->addRow(productTypeCountLbl);
}

void prodGUI::composeWidgets() {

	mainLayout = new QHBoxLayout(this);
	productWidget = displayProducts();
	mainLayout->addLayout(formLayout);
	mainLayout->addWidget(productWidget);


	QWidget* mainWidget = new QWidget();
	mainWidget->setLayout(mainLayout);
	mainWidget->setFixedSize(1000, 700);

	QVBoxLayout* mainWindowLayout = new QVBoxLayout();
	mainWindowLayout->addWidget(mainWidget);


	setLayout(mainWindowLayout);

	setWindowTitle("Zdrobix Liquor Store");
}

void prodGUI::addProductButtonClicked() {

	QString name = nameEdit->text();
	QString producer = producerEdit->text();
	QString type = typeEdit->text();
	float price = priceEdit->text().toFloat();

	string nameString = name.toStdString();
	string producerString = producer.toStdString();
	string typeString = type.toStdString();

	this->service.add(nameString, producerString, typeString, price);

	prodMap = this->service.createProductMap();

	nameEdit->clear();
	producerEdit->clear();
	typeEdit->clear();
	priceEdit->clear();

	this->productListToDisplay = this->service.getAllProducts();

	updateProductWidget();
}

QTableView* prodGUI::displayProducts() {

	QTableView* tableView = new QTableView;
	ProductTableModel* model = new ProductTableModel(this, this->service.getAllProducts());

	tableView->setModel(model);
	tableView->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
	tableView->verticalHeader()->setVisible(false);
	
	return tableView;
}

void prodGUI::updateProductWidget() {

	QWidget* newProductWidget = displayProducts();

	if (mainLayout) {

		QLayoutItem* item = mainLayout->itemAt(1);
		if (item) {
			QWidget* oldWidget = item->widget();
			mainLayout->removeWidget(oldWidget);
			oldWidget->deleteLater();
		}

		mainLayout->addWidget(newProductWidget, 1);
	}
}

void prodGUI::filterProductPriceClickedUnder() {

	QString filterPrice = filterPriceEdit->text();
	float price = filterPrice.toFloat();

	size_t counter = 0;
	int pos[100];

	counter = this->service.filterPrice(counter, pos, price, true);

	vector<Product> filteredProductList;
	vector<Product> list = this->service.getAllProducts();

	for (int i = 0; i < counter; i++) {

		filteredProductList.push_back(list[pos[i]]);
	}

	this->productListToDisplay = filteredProductList;
	updateProductWidget();
}

void prodGUI::filterProductPriceClickedOver() {

	QString filterPrice = filterPriceEdit->text();
	float price = filterPrice.toFloat();

	size_t counter = 0;
	int pos[100];

	counter = this->service.filterPrice(counter, pos, price, false);

	vector<Product> filteredProductList;
	vector<Product> list = this->service.getAllProducts();

	for (int i = 0; i < counter; i++) {

		filteredProductList.push_back(list[pos[i]]);
	}

	this->productListToDisplay = filteredProductList;
	updateProductWidget();
}

void prodGUI::filterProductTypeClicked() {

	QString filterName = filterTypeEdit->text();
	string filterString = filterName.toStdString();

	size_t counter = 0;
	int pos[100];

	counter = this->service.filterType(counter, pos, filterString);

	vector<Product> filteredProductList;
	vector<Product> list = this->service.getAllProducts();

	for (int i = 0; i < counter; i++) {

		filteredProductList.push_back(list[pos[i]]);
	}

	this->productListToDisplay = filteredProductList;
	updateProductWidget();
}

void prodGUI::deleteProductButtonClicked() {

	QString deleteString = deleteProductEdit->text();
	int position = deleteString.toInt();

	if (position - 1 < 0 or position - 1 > this->service.numberProducts()) {

		showMessage("Invalid position");
		return;
	}

	this->service._delete(position - 1);

	this->productListToDisplay = this->service.getAllProducts();

	updateProductWidget();
}

void prodGUI::sortNameButtonClicked() {

	this->service.sortName(ascendingDescending->isChecked(), true);

	updateProductWidget();
}

void prodGUI::sortPriceButtonClicked() {

	this->service.sortPrice(ascendingDescending->isChecked());

	updateProductWidget();
}

void prodGUI::sortTypeButtonClicked() {

	this->service.sortName(ascendingDescending->isChecked(), false);

	updateProductWidget();
}

void prodGUI::placeOrderButtonClicked() {

	QLabel* nameLblO = new QLabel{ "Order name " };
	QLabel* addressLblO = new QLabel{ "Order address" };

	orderName = new QLineEdit;
	orderAddress = new QLineEdit;

	addRandomEdit = new QLineEdit;
	addRandomEdit->setFixedWidth(120);

	QLineEdit* addEdit = new QLineEdit;
	addEdit->setFixedWidth(120);

	fileName = new QLineEdit;
	fileName->setFixedWidth(120);


	addRandomButton = new QPushButton("Add random");
	connect(addRandomButton, &QPushButton::clicked, this, &prodGUI::addRandomButtonClicked);

	QPushButton* addProduct = new QPushButton("Add");
	connect(addProduct, &QPushButton::clicked, this, [=]() {
		if (addEdit->text().isEmpty())
			return;
		this->service_cos.add_product_bag(this->service.searchByPosition(addEdit->text().toInt() - 1));
		addEdit->clear();
		});
	
	emptyBagButton = new QPushButton("Empty shopping bag");
	connect(emptyBagButton, &QPushButton::clicked, this, &prodGUI::emptyBagButtonClicked);
	emptyBagButton->setFixedWidth(202);

	exportPurchase = new QPushButton("Place order");
	connect(exportPurchase, &QPushButton::clicked, this, &prodGUI::exportPurchaseClicked);

	formLayoutBag = new QFormLayout;
	formLayoutBag->setFieldGrowthPolicy(QFormLayout::ExpandingFieldsGrow);

	formLayoutBag->addRow(addRandomButton, addRandomEdit);
	formLayoutBag->addRow(addProduct, addEdit);
	formLayoutBag->addRow(emptyBagButton);
	formLayoutBag->addRow(nameLblO, orderName);
	formLayoutBag->addRow(addressLblO, orderAddress);
	formLayoutBag->addRow(exportPurchase, fileName);

	productBagWidget = displayProductsBag();

	mainBagLayout = new QHBoxLayout(this);
	mainBagLayout->addLayout(formLayoutBag);
	mainBagLayout->addWidget(productBagWidget);

	QWidget* popOutWindow = new QWidget();
	popOutWindow->setLayout(mainBagLayout);

	popOutWindow->setWindowTitle("Place an order");

	popOutWindow->setFixedSize(700, 300);

	popOutWindow->show();
}


void prodGUI::addRandomButtonClicked() {

	QString addRandomNumber = addRandomEdit->text();
	int addRandomInt = addRandomNumber.toInt();
	vector<Product> list = this->service.getAllProducts();
	try {
		this->service_cos.add_random_product(list, addRandomInt);
	}
	catch (const bad_alloc& e) { this->showMessage("Error"); };
	addRandomEdit->clear();
}

void prodGUI::emptyBagButtonClicked() {

	this->service_cos.delete_all();
}

void prodGUI::exportPurchaseClicked() {

	QString fileNameQString = fileName->text();
	string fileNameStr = fileNameQString.toStdString();

	QString orderNameQString = orderName->text();
	string orderNameStr = orderNameQString.toStdString();

	QString orderAddressQString = orderAddress->text();
	string orderAddressStr = orderAddressQString.toStdString();

	ofstream out(fileNameStr);

	out << "Zdrobix Liquor Store\n\n\n\nOrder for:     " << orderNameStr << "\nOrder address: " << orderAddressStr << "\n\n\n";

	if (out.is_open()) {

		for (const auto& prt : service_cos.getAllProducts())

			out << setw(50) << left << prt.getName() << left
			<< setw(40) << prt.getProducer() << left
			<< setw(15) << prt.getType() << left
			<< setw(8) << prt.getPrice() << endl;

		out << "\n\nTotal cost: " << this->service_cos.totalProductCost();

		out.close();
	}


}

QTableView* prodGUI::displayProductsBag() {

	QTableView* tableView = new QTableView;
	ProductTableModel* model = new ProductTableModel(this, this->service_cos.getAllProducts());

	tableView->setModel(model);
	tableView->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);  
	tableView->verticalHeader()->setVisible(false);

	return tableView;
}

void prodGUI::updateProductShoppingBag() {

	QWidget* newProductWidget = displayProductsBag();

	if (mainBagLayout) {

		QLayoutItem* item = mainBagLayout->itemAt(1);
		if (item) {
			QWidget* oldWidget = item->widget();
			mainBagLayout->removeWidget(oldWidget);
			oldWidget->deleteLater();
		}

		mainBagLayout->addWidget(newProductWidget, 1);
	}
}

void prodGUI::parcurgereMapButoane() {
	
	for (const auto& element : prodMap) {
		QPushButton* buttonType = new QPushButton(QString::fromStdString(element.first));
		connect(buttonType, &QPushButton::clicked, this, [=]() {
			int count = prodMap.at(element.first);
			QString message = "There are " + QString::number(count) + " products of type " + buttonType->text();
			showMessage(message);
			});
		buttonType->setFixedWidth(223);
		formLayout->addRow(buttonType);
	}
}


void prodGUI::undoButtonClicked() {

	try {
		this->service.undo();
		updateProductWidget();
		
	}
	catch (const MyException& e) {

		this->showMessage(QString::fromStdString(e.getMessage()));
	}
	catch (const bad_alloc& e) { this->showMessage(QString::fromStdString("Not succesful!")); }
	
}