#include <iostream>
#include "ui.h"
#include <string>
#include "service.h"
#include <iomanip>
#include <random>
#include <algorithm>
#include <fstream>

using namespace std;

void Ui::printMenu() {

    cout <<   "Magazin              Cos de cumparaturi"
          "\n\n1. Add               A. Adauga in cos"
            "\n2. Update            B. Goleste cos"
            "\n3. Delete            C. Adaugare random"
            "\n4. Filter            D. Export"
            "\n5. Sort              "
            "\n6. Undo              "
            "\n                     E. Afiseaza cos"
            "\n8. Standard          "
            "\n9. Print             "
            "\n0. Exit              "
            "\n";
}

string Ui::readOption() {

    string opt;

    cout << endl << "Enter option: ";

    getline(cin >> ws, opt);

    return opt;
}

void Ui::uiProducts(Controller& serv) {

    vector<Product> lista = serv.getAllProducts();

    if (serv.numberProducts() == 0 ) {

        cout << endl << "No products added!\n\n";

        return;
    }

    cout << endl;

    int i = 1;

    for (const auto& prt: lista)
    {
        cout << ((i <= 9) ? " " + to_string(i++) : to_string(i++)) << ") "
             << setw(50) << left << prt.getName() << left
             << setw(40) << prt.getProducer() << left
             << setw(15) << prt.getType() << left
             << setw(8) << prt.getPrice() << endl;
    }
    cout << endl;
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

    uiProducts(Ui::service);

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

    uiProducts(Ui::service);

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

    size_t counter; int pos[100];

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

    vector<Product> lista = service.getAllProducts();

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

void Ui::uiUndo() {

    try {
        service.undo();

        cout << "The undo operation performed succesfully\n";

    } catch (const MyException& e) {

        e.getMessage();
    }
    catch(const bad_alloc& e) {cout << "Not succesful\n";}

    catch(const length_error& e) {cout << "Not succesful\n";}
}

void Ui::uiAddBag() {

    if ( service.numberProducts() <= 0) {

        cout << "No products have been added in the shop.\n";

        return;
    }

    string searchName;

    cout << "Enter the name: ";

    cin >> searchName;

    try {
        Product p = service.searchByName(searchName);

        service_cos.add(p.getName(), p.getProducer(), p.getType(), p.getPrice());
    }
    catch(const MyException& e) {

        cout << e.getMessage();

        return;
    }
    catch(const runtime_error& e) {cout << "Not succesful"; return;}

    catch(const length_error& e) {cout << "Not succesful"; return;}

    cout << "The product has been succesfully added to your shopping bag!\n";

    cout << "Shopping bag items : " << service_cos.numberProducts() << endl;
}

void Ui::uiEmptyBag() {

    if (service_cos.numberProducts() <= 0 ) {

        cout << "Your shopping bag is already empty!\n";

        return;
    }
    service_cos.deleteAllProducts();

    cout << "Shopping bag items : " << service_cos.numberProducts() << endl;
}

void Ui::uiRandomAdd() {

    if ( service.numberProducts() <= 0) {

        cout << "No products have been added in the shop.\n";

        return;
    }
    cout << "How many products do you want to add? ";

    int number;

    cin >> number;

    if ( number <= 0 or number > service.numberProducts()) {

        cout << "Invalid number!\n";

        return;
    }
    random_device r;

    mt19937 gen(r());

    uniform_int_distribution<int> dist(1, service.numberProducts());

    for ( int i = 1; i <= number; i ++ ) {

        int randPos = dist(gen) % service.numberProducts();

        Product p = service.searchByPosition(randPos);

        service_cos.add(p.getName(), p.getProducer(), p.getType(), p.getPrice());
    }
    cout << "Shopping bag items : " << service_cos.numberProducts() << endl;
}

void Ui::uiExportBag() {

    if (service_cos.numberProducts() <= 0 ) {

        cout << "Your shopping bag is empty!\n";

        return;
    }
    string filename;

    cout << "Enter the filename: ";

    cin >> filename;

    if ( filename.substr(filename.length() - 4) != ".csv")

        throw runtime_error("Not a csv file");

    ofstream xout(filename);

    if ( xout.is_open()) {

        for ( const auto& prt : service_cos.getAllProducts())

            xout << prt.getName() << "," << prt.getProducer() << "," << prt.getType() << "," << prt.getPrice() << endl;

        xout.close();

        cout << "The products have been succesfully exported in " << filename << endl;

        return;
    }
    cout << "Error: the file couldn't be opened.\n";
}

void Ui::uiViewBag(Controller& serv) {

    uiProducts(serv);

    float total_cost = serv.totalProductCost();

    cout << "\n\n" << "Total cost: " << total_cost << "\n\n";
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

        else if (option == "6") uiUndo();

        else if (option == "8") service.addStandard();

        else if (option == "9") uiProducts(Ui::service);

        else if (option == "A" or option == "a") uiAddBag();

        else if (option == "B" or option == "b" ) uiEmptyBag();

        else if (option == "C" or option == "c" ) uiRandomAdd();

        else if (option == "D" or option == "d" ) uiExportBag();

        else if (option == "E" or option == "e" ) uiViewBag(Ui::service_cos);

        else cout << "Invalid option\n";
    }
}
