#include <iostream>
#include "ui.h"
#include <string>
#include "service.h"
#include <iomanip>

using namespace std;

void Ui::printMenu() {

    cout << "\n1. Add\n2. Update\n3. Delete\n4. Filter\n5. Sort\n";

    cout << "\n8. Standard\n9. Print\n0. Exit\n";
}

string Ui::readOption() {

    string opt;

    cout << endl << "Enter option: ";

    getline(cin >> ws, opt);

    return opt;
}

void Ui::uiProducts() {

    const Product* lista = service.getAllProducts();

    if (service.numberProducts() == 0 ) {

        cout << endl << "No products added!";

        return;
    }

    cout << endl;

    for (int i = 0; i < service.numberProducts();) {
        Product prt = lista[i];
        cout << ((i <= 9) ? " " + to_string(i++) : to_string(i++)) << ") "
             << setw(50) << left << prt.getName() << left
             << setw(40) << prt.getProducer() << left
             << setw(15) << prt.getType() << left
             << setw(8) << prt.getPrice() << endl;
    }
}

void Ui::uiAdd () {

    string name; string producer; string type; float price;

    cout << endl <<"Adding a product. Please enter: \n";

    cout << "Name: "; getline(cin >> ws, name);

    cout << "Producer: "; getline(cin >> ws, producer);

    cout << "Type: "; getline(cin >> ws, type);

    cout << "Price: "; cin >> price;

    if ( !service.add(name, producer, type, price)  )

        cout << "The procedure has not been completed succesfully!\n";

    else cout << "The product has been succesfully added!\n";
}

void Ui::uiUpdate () {

    if (service.numberProducts() == 0) {

        cout << "No products added!\n";

        return;
    }
    string newName; string newProducer; string newType; float newPrice;

    cout << endl << "Updating a product. Please select:";

    uiProducts();

    cout << endl << "Please select a product: ";

    int position;

    cin >> position;

    cout << endl <<"Updating a product. Please enter: \n";

    cout << "Name: "; getline(cin >> ws, newName);

    cout << "Producer: "; getline(cin >> ws, newProducer);

    cout << "Type: "; getline(cin >> ws, newType);

    cout << "Price: "; cin >> newPrice;

    if ( !service.update(position - 1,newName, newProducer, newType, newPrice)  )

        cout << "The procedure has not been completed succesfully!\n";

    else cout << "The product has been succesfully updated!\n";
}

void Ui::uiDelete () {

    if (service.numberProducts() == 0) {

        cout << "No products added!\n";

        return;
    }

    cout << endl << "Deleting a product. Please select:";

    uiProducts();

    cout << endl << "Please select a product: ";

    int position;

    cin >> position;

    if ( !service._delete(position -1 ))

        cout << "The procedure has not been completed succesfully!\n";

    else cout << "The product has been succesfully deleted!\n";
}

void Ui::uiFilter () {

    if (service.numberProducts() == 0) {

        cout << "No products added!\n";

        return;
    }
    cout << endl << "Filtering the products, please select the criteria: ";

    cout << "\n\n1. Price\n2. Name\n3. Producer\n4. Return to menu\n";

    string option = readOption();

    int counter, pos[100];

    if (option == "1") {

        cout << "\n1. Under\n2. Over\n";

        string option1 = readOption();

        float price;

        cout << "\nEnter a price: ";

        cin >> price;

        counter = service.filterPrice(0, pos, price, option1 == "1");

    } else if (option == "2") {

        char letter;

        cout << "\nEnter a letter: ";

        cin >> letter;

        counter = service.filterName(0, pos, letter, true);

    } else if (option == "3") {

        char letter;

        cout << "\nEnter a letter: ";

        cin >> letter;

        counter = service.filterName(0, pos, letter, false);

    }else if (option == "4")

        return;

    else { cout << "Invalid option!\n";

        return;
    }

    if (counter == 0 ) {

        cout << "There are no products matching the criteria\n";

        return;
    }

    const Product* lista = service.getAllProducts();

    cout << endl;

    for ( int i = 0; i < counter; i ++ ) {

        Product prt = lista[pos[i]];

        cout << ((i + 1<= 9) ? " " + to_string(i + 1) : to_string(i + 1)) << ") "
             << setw(50) << left << prt.getName() << left
             << setw(40) << prt.getProducer() << left
             << setw(15) << prt.getType() << left
             << setw(8) << prt.getPrice() << endl;
    }
}

void Ui::uiSort () {

    if (service.numberProducts() <= 1) {

        cout << "Not enough products!\n";

        return;
    }

    cout << "Select the criteria for the sort: \n";

    cout << "1. Price\n2. Name\n3. Type\n4. Name & Type\n5. Return to menu\n";

    string option = readOption();

    if (option == "1") {

        cout << "1. Ascending\n2. Descending\n";

        string option1 = readOption();

        service.sortPrice(option1 == "1");

        cout << "\nThe sort has been completed succesfully\n";

    }else if (option == "2") {

        cout << "1. Ascending\n2. Descending\n";

        string option1 = readOption();

        service.sortName(option1 == "1", true);

        cout << "\nThe sort has been completed succesfully\n";

    } else if (option == "3") {

        cout << "1. Ascending\n2. Descending\n";

        string option1 = readOption();

        service.sortName(option1 == "1", false);

        cout << "\nThe sort has been completed succesfully\n";

    } else if (option == "4") {

        cout << "\nType:\n1. Ascending\n2. Descending\n";

        string option1 = readOption();

        cout << "\nName:\n1. Ascending\n2. Descending\n";

        string option2 = readOption();

        service.sortName(option1 == "1", false);

        service.sortNamePart2(option2 == "1");

        cout << "\nThe sort has been completed succesfully\n";

    }else if (option == "5")

        return;

    else {

        cout << "\nInvalid option\n";

        return;
    }
}

void Ui::runApp () {

    bool run = true;

    while(run) {

        printMenu();

        string option = readOption();

        if (option == "0") run = false;

        else if (option == "1") uiAdd();

        else if (option == "2") uiUpdate();

        else if (option == "3") uiDelete();

        else if (option == "4") uiFilter();

        else if (option == "5") uiSort();

        else if (option == "8") service.addStandard();

        else if (option == "9") uiProducts();

        else cout << "Invalid option\n";
    }
}
