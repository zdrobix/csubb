#include "service.h"
#include "domain.h"
#include <vector>
#include <string>
#include <algorithm>
#include <stdexcept>
#include "exception.h"
#include "undo.h"
#include <memory>

using namespace std;

vector<Product> Controller::getAllProducts() {

    return repository.getAll();
}

void Controller::deleteAllProducts() {

    repository.deleteAll();
}

int Controller::numberProducts() {

    return repository.numberProducts();
}

bool Controller::add(const string& name, const string& producer, const string& type, float price) {

    try {
        Validator::validate_product(name, producer, type, price);

    } catch (MyException& e) {

        return false;
    }catch(const runtime_error& e) {return false;}


    Product prt{name, producer, type, price};

    repository.addProduct(prt);

    undoActionList.push_back(make_unique<UndoAdd>(repository, prt));

    return true;
}

bool Controller::update(int position, const string& nName, const string& pProducer, const string& tType, float pPrice) {

    if (repository.numberProducts() == 0 ) return false;

    if (position > repository.numberProducts() or position < 0) return false;

    Validator::validate_product(nName, pProducer, tType, pPrice);

    Product p=Product(const_cast<Product &>(repository.get(position)));

    Product newP=Product(nName, pProducer, tType, pPrice);

    undoActionList.push_back(make_unique<UndoUpdate>(repository, newP, p));

    repository.updateProduct(const_cast<Product &>(repository.get(position)), nName, pProducer, tType, pPrice);

    return true;
}

bool Controller::_delete(int position) {

    if (repository.numberProducts() == 0 ) return false;

    if (position > repository.numberProducts() or position < 0 ) return false;

    Product prod = searchByPosition(position);

    repository.deleteProduct(prod);

    undoActionList.push_back(make_unique<UndoDelete>(repository, prod));

    return true;
}

int Controller::filterPrice(size_t counter, int pos[100], float price, bool direction) {

    vector<Product> lista = repository.getAll();

    for ( auto i = lista.begin(); i != lista.begin() + numberProducts(); ++ i ) {

        const Product& prt = *i;

        if ( (direction and prt.getPrice() <= price) or (!direction and prt.getPrice() >= price) ) {

            pos[counter++] = static_cast<int>(distance(lista.begin(), i));
        }
    }
    return static_cast<int>(counter);
}

int Controller::filterName(size_t counter, int pos[100], char letter, bool nameProd) {

    vector<Product> lista = repository.getAll();

    for ( auto i = lista.begin(); i != lista.begin() + numberProducts(); ++ i ) {

        const Product& prt = *i;

        if ( (nameProd and prt.getName().at(0) == letter) or (!nameProd and prt.getProducer().at(0) == letter) ) {

            pos[counter++] = static_cast<int>(distance(lista.begin(), i));
        }
    }
    return static_cast<int>(counter);
}

void Controller::sortPrice(bool direction) {

    auto& list = const_cast<vector<Product> &>(repository.getAll());

    auto comparePriceAsc = [](const Product& a, const Product& b) {
        return a.getPrice() < b.getPrice();
    };

    auto comparePriceDesc = [](const Product& a, const Product& b) {
        return a.getPrice() > b.getPrice();
    };

    auto compareFunction = direction ? comparePriceAsc : comparePriceDesc;

    sort( list.begin(), list.end() , compareFunction);
}

void Controller::sortName(bool direction, bool nameType) {

    auto& list = const_cast<vector<Product> &>(repository.getAll());

    auto compareNameAsc = [](const Product& a, const Product& b) {
        return a.getName() < b.getName();
    };

    auto compareTypeAsc = [](const Product& a, const Product& b) {
        return a.getType() < b.getType();
    };

    auto compareNameDesc = [](const Product& a, const Product& b) {
        return a.getName() > b.getName();
    };

    auto compareTypeDesc = [](const Product& a, const Product& b) {
        return a.getType() > b.getType();
    };

    auto compareFunctionAsc = nameType ? compareNameAsc : compareTypeAsc;

    auto compareFunctionDesc = nameType ? compareNameDesc : compareTypeDesc;

    if (direction)

        sort(list.begin(), list.end(), compareFunctionAsc);

    else sort(list.begin(), list.end(), compareFunctionDesc);
}

void Controller::sortNamePart2(bool direction) {

    auto& list = const_cast<vector<Product> &>(repository.getAll());

    auto compareNameAsc = [](const Product& a, const Product& b) {
        return a.getName() < b.getName();
    };

    auto compareNameDesc = [](const Product& a, const Product& b) {
        return a.getName() > b.getName();
    };

    auto compareFunction = direction ? compareNameAsc : compareNameDesc;

    string curent_type = list.at(0).getType();

    int start_index = 0;

    for ( int i = 0; i < numberProducts(); i ++ ) {

        if (list.at(i).getType() != curent_type) {

            curent_type = list.at(i).getType();

            if ( i - start_index > 1)

                sort(list.begin() + start_index, list.begin() + i , compareFunction);

            start_index = i;
        }
    }
    sort( list.begin() + start_index , list.begin() + numberProducts(), compareFunction);
}

const Product& Controller::searchByName (const string& search) {

    const vector<Product>& products = repository.getAll();

    for ( const auto& prt: products ){

        if (prt.getName().find(search) == 0 )

            return prt;
    }
    throw MyException((string&)"Product not found by name.");
}

const Product& Controller::searchByPosition( const int position) {

    if (position > repository.numberProducts() or position < 0)

        throw MyException((string &) "Product not found by position");

    return repository.get(position);
}

float Controller::totalProductCost() {

    const auto& list = const_cast<vector<Product> &>(repository.getAll());

    float sum = 0;

    for ( const auto& prt: list )

        sum = sum + prt.getPrice();

    return sum;
}

unordered_map<string, int> Controller::createProductMap() {

    vector<Product> list = repository.getAll();

    unordered_map<string, int> prodMap;

    for ( const auto& prt: list) {

        if (prodMap.find(prt.getType()) != prodMap.end())

            prodMap[prt.getType()] ++;

        else prodMap[prt.getType()] = 1;
    }
    return prodMap;
}

void Controller::undo() {

    if (undoActionList.empty())

        throw MyException((string&)"Nu se mai poate efectua undo!");

    undoActionList.back()->doUndo();

    undoActionList.pop_back();
}

void Controller::addStandard() {

    repository.addProduct(Product{"Ridge Cal Zin Paso Robles - 750Ml", "A Vintners Selections", "Wine", 274.66});
    repository.addProduct(Product{"Bodegas Castano Hecula - 750Ml", "A Vintners Selections", "Wine", 577.60});
    repository.addProduct(Product{"Hendry Block 7 Napa Zin - 750Ml", "A Vintners Selections", "Wine", 445.86});
    repository.addProduct(Product{"Alsace William Gew - 750Ml", "Monsieur Touton Selection", "Wine", 341.78});
    repository.addProduct(Product{"Alsace William Cremant Brut - 750Ml", "Monsieur Touton Selection", "Wine", 632.12});
    repository.addProduct(Product{"Ch Darvigny Haut Medoc - 750Ml", "Monsieur Touton Selection", "Wine", 227.36});
    repository.addProduct(Product{"Montevina Wh Zin - 750Ml", "Republic National Distributing Co", "Wine", 466.84});
    repository.addProduct(Product{"Ravenswood Napa Vly Zin - 750Ml", "Republic National Distributing Co", "Wine", 733.27});
    repository.addProduct(Product{"Chalk Hill Eb Chard - 750Ml", "Republic National Distributing Co", "Wine", 512.63});
    repository.addProduct(Product{"Nederburg Shz - 750Ml", "Southern Glazers Wine And Spirits", "Wine", 837.70});
    repository.addProduct(Product{"Veramonte P/Noir - 750Ml", "Southern Glazers Wine And Spirits", "Wine", 447.90});
    repository.addProduct(Product{"Wine Gift Tote 4 Bottle", "Default", "Supplies", 843.67});
    repository.addProduct(Product{"Founding Spirits Amer Whiskey - 750Ml", "Prestige Beverage Group Of Md Llc", "Liquor", 336.51});
    repository.addProduct(Product{"Sutter Home Fre' Chard - 750Ml", "Republic National Distributing Co", "Non-Alcohol", 873.87});
    repository.addProduct(Product{"Pierre Ferrand 1840 Cognac - 750Ml", "Bacchus Importers Ltd", "Liquor", 638.74});
    repository.addProduct(Product{"Finest Call Grenadine - 1L", "Republic National Distributing Co", "Non-Alcohol", 799.60});
    repository.addProduct(Product{"Westland American Single Malt - 750Ml", "Remy Cointreau Usa", "Liquor", 763.71});
    repository.addProduct(Product{"Vincenzi Amaretto Di Torino - 1L", "Lanterna Distributors Inc", "Liquor", 354.70});
    repository.addProduct(Product{"Aufsesser Pils - 500Ml", "Kysela Pere Et Fils Ltd", "Beer", 494.43});
    repository.addProduct(Product{"Sweetwater Hash  15 Pk Can- 12.Oz Can", "Sweetwater Brewing Company Llc", "Beer", 212.18});
    repository.addProduct(Product{"St Tangerine Express - 22.Oz", "Buck Distributing Company Inc", "Beer", 99.94});
    repository.addProduct(Product{"Rogue Mocha Porter 4/6 Nr - 12Oz", "Katcef Brothers Inc", "Beer", 520.38});
    repository.addProduct(Product{"Echigo Red Ale Nr 12/Cs - 335Ml", "Young Won Trading Inc", "Beer", 871.31});
    repository.addProduct(Product{"Brewers Alley 1634 Ale 4/6 Nr - 12Oz", "Monocacy Brewing Co Llc", "Beer", 612.93});
    repository.addProduct(Product{"National Premium 4/6 Nr - 12Oz", "Frederick P Winner Ltd", "Beer", 82.37});
    repository.addProduct(Product{"Ipa Oze No Yukidoke Beer Nr 24/Cs - 11Oz", "Young Won Trading Inc", "Beer", 85.16});
    repository.addProduct(Product{"Wray & Nephew Rum White 126P - 200Ml", "Campari America Llc", "Liquor", 611.37});
    repository.addProduct(Product{"Sutter Home Fre Mer - 750Ml", "Republic National Distributing Co", "Non-Alcohol", 241.71});
    repository.addProduct(Product{"Sutter Home Fre' Spark - 750Ml", "Republic National Distributing Co", "Non-Alcohol", 237.47});
    repository.addProduct(Product{"Masion Rouge Cognac Vsop - 750Ml", "Prestige Beverage Group Of Md Llc", "Liquor", 986.69});
    repository.addProduct(Product{"Exotico Blanco Teq - 1L", "Luxco Spirited Brands", "Liquor", 622.67});
    repository.addProduct(Product{"Sobieski Vodka - 1L", "Reliable Churchill Lllp", "Liquor", 143.64});
    repository.addProduct(Product{"Leroux Brandy - Cherry - 750Ml", "Jim Beam Brands Co", "Liquor", 201.92});
    repository.addProduct(Product{"Austin Cider Variety Pack - 2/12 12.Oz Can", "Legends Ltd", "Beer", 903.95});
    repository.addProduct(Product{"Nauti Seltzer Grapefruit - 12.Oz 4/6 Can", "Legends Ltd", "Beer", 182.42});
    repository.addProduct(Product{"Nauti Seltzer Raspberry - 12.Oz 4/6 Can", "Legends Ltd", "Beer", 497.28});
    repository.addProduct(Product{"Chum-Churum Soonhari Peach Soju - 375Ml", "Young Won Trading Inc", "Liquor", 960.95});
    repository.addProduct(Product{"Old Mexico Blanco Tequila - 1L", "Southern Glazers Wine And Spirits", "Liquor", 374.76});
    repository.addProduct(Product{"Wray & Nephew Rum White 126P - 200Ml", "Campari America Llc", "Liquor", 340.79});
    repository.addProduct(Product{"Joseph Cartron Triple Sec - 750Ml", "Craft Wine & Spirits Of Maryland Llc", "Liquor", 51.51});
    repository.addProduct(Product{"Fuzzy Vodka - 1L", "Southern Glazers Wine And Spirits", "Liquor", 817.34});
    repository.addProduct(Product{"Prairie Organic Vodka - 1L", "Reliable Churchill Lllp", "Liquor", 689.55});
    repository.addProduct(Product{"Monks Cafe Sour Ale 6/4 Nr - 11.2Oz", "Washburn Wine Co", "Beer", 934.46});
    repository.addProduct(Product{"Cass Nr - 22Oz", "Young Won Trading Inc", "Beer", 135.87});
    repository.addProduct(Product{"Victory Vital Ipa 4/6 Cans", "Katcef Brothers Inc", "Beer", 416.10});
    repository.addProduct(Product{"Ginga Kogen - 300Ml", "Young Won Trading Inc", "Beer", 787.86});
    repository.addProduct(Product{"Stary Melnik Myagkoe - 500Ml", "Interbalt Products Corp", "Beer", 560.51});
    repository.addProduct(Product{"Nisko Luger - 500Ml", "Interbalt Products Corp", "Beer", 813.67});
    repository.addProduct(Product{"Stary Melnik Light - 500Ml", "Interbalt Products Corp", "Beer", 738.85});
    repository.addProduct(Product{"Empty 1/2 Keg (30.00)", "Anheuser Busch Inc", "Dunnage", 776.98});
    repository.addProduct(Product{"Empty 1/6 Keg (30.00)", "Anheuser Busch Inc", "Dunnage", 745.38});
    repository.addProduct(Product{"Empty 1/4 Keg (30.00)", "Anheuser Busch Inc", "Dunnage", 405.80});
    repository.addProduct(Product{"Can Xa Brut Cava - 750Ml", "Pwswn Inc", "Wine", 728.89});
    repository.addProduct(Product{"Bex Ries - 750Ml", "Bacchus Importers Ltd", "Wine", 848.34});
    repository.addProduct(Product{"Store Special Wine 355 Ml - 355Ml", "Default", "Ref", 234.80});
    repository.addProduct(Product{"Store Special Wine 355 Ml - 355Ml", "Default", "Ref", 246.21});
    repository.addProduct(Product{"Store Special Wine 10 Liter - 10L", "Default", "Ref", 650.50});
    repository.addProduct(Product{"Cutty Sark Scotch - 1L", "The Edrington Group Usa Llc", "Liquor", 311.58});
    repository.addProduct(Product{"Glenfiddich 18Yr Sm Scotch - 750Ml", "William Grant And Sons Inc", "Liquor", 710.57});
    repository.addProduct(Product{"Stoli Crushed Ruby Red Grapefruit Vodka - 750Ml", "Stoli Group (Usa) Llc", "Liquor", 465.70});
    repository.addProduct(Product{"Bacardi Rum '8' W/ Glass & Ice Mold - 750Ml", "Bacardi Usa Inc", "Liquor", 30.27});
    repository.addProduct(Product{"Speyburn Braden Orach - 750Ml", "Reliable Churchill Lllp", "Liquor", 926.39});
    repository.addProduct(Product{"Capel Pisco Res - 750Ml", "Lapham Sales & Marketing Dba Lapham", "Liquor", 488.49});
    repository.addProduct(Product{"Master Of Mixes Cosmo - 1L", "American Beverage Marketers", "Non-Alcohol", 910.16});
    repository.addProduct(Product{"Master Of Mixes Cosmo - 1L", "American Beverage Marketers", "Non-Alcohol", 371.39});
    repository.addProduct(Product{"Store Special Wine 355 Ml - 355Ml", "Default", "Ref", 620.40});
    repository.addProduct(Product{"Master Of Mixes Mojito - 1L", "American Beverage Marketers", "Non-Alcohol", 708.42});
    repository.addProduct(Product{"Master Of Mixes Cosmo - 1L", "American Beverage Marketers", "Non-Alcohol", 788.39});
    repository.addProduct(Product{"Kona Koko Brown 4/6Nr - 12Oz", "Anheuser Busch Inc", "Beer", 309.82});
    repository.addProduct(Product{"Redhook Blackhook 4/6 Nr", "Anheuser Busch Inc", "Beer", 206.77});
    repository.addProduct(Product{"Shock Top Winter Sampler 12Pk Nr", "Anheuser Busch Inc", "Beer", 684.42});
    repository.addProduct(Product{"Duclaw Devils Milk 4/6Nr - 12Oz", "Duclaw Brewery Llc", "Beer", 56.70});
    repository.addProduct(Product{"Tbc Jack O Traveler Nr", "Boston Beer Corporation", "Beer", 597.78});
    repository.addProduct(Product{"Finest Call Strawberry Puree - 1L", "American Beverage Marketers", "Non-Alcohol", 738.95});
    repository.addProduct(Product{"Master Of Mixes Lime Juice - 375Ml", "American Beverage Marketers", "Non-Alcohol", 301.83});
    repository.addProduct(Product{"Angostura Bitters - 200Ml", "Reliable Churchill Lllp", "Non-Alcohol", 355.41});
    repository.addProduct(Product{"Georges Bloody Mary Spicey - 32Oz", "Reliable Churchill Lllp", "Non-Alcohol", 217.76});
    repository.addProduct(Product{"Finest Call Pina Colada - 1L", "American Beverage Marketers", "Non-Alcohol", 59.19});
    repository.addProduct(Product{"Master Of Mixes Str/Daq/Mar - 1.75L", "American Beverage Marketers", "Non-Alcohol", 133.99});
    repository.addProduct(Product{"Angostura Bitters - 200Ml", "Reliable Churchill Lllp", "Non-Alcohol", 612.70});
    repository.addProduct(Product{"Binyamina Res Zin - 750Ml", "Royal Wine Corp", "Wine", 887.98});
    repository.addProduct(Product{"Olmeca Altos Teq - Reposado - 750Ml", "Pernod Ricard Usa Llc", "Liquor", 658.99});
    repository.addProduct(Product{"Jose Cuervo Golden Margarita - 1.75L", "Proximo Spirits Inc", "Liquor", 820.12});
    repository.addProduct(Product{"Flor De Cana Rum - Centenario - 750Ml", "William Grant And Sons Inc", "Liquor", 175.47});
    repository.addProduct(Product{"Love P/Noir - 750Ml", "The Wine Group", "Wine", 708.70});
    repository.addProduct(Product{"Chloe Prosecco - 750Ml", "The Wine Group", "Wine", 267.63});
    repository.addProduct(Product{"Ava Grace Chard - 750Ml", "The Wine Group", "Wine", 892.16});
    repository.addProduct(Product{"Brouilly Ch Des Tours - 375Ml", "Baron Francois Ltd", "Wine", 425.46});
    repository.addProduct(Product{"Vampire P/Noir - 750Ml", "Ti Beverage Group Ltd", "Wine", 144.80});
    repository.addProduct(Product{"Fess Parker Sb County P/Noir - 750Ml", "Dops Inc", "Wine", 579.42});
    repository.addProduct(Product{"Adega Da Cartuxa Red - 750Ml", "Dmv Distributing Llc", "Wine", 830.20});
    repository.addProduct(Product{"Montmayou Res Mal 750Ml", "Constantine Wines Inc", "Wine", 815.94});
    repository.addProduct(Product{"Rebuli Prosecco Rse - 750Ml", "Kysela Pere Et Fils Ltd", "Wine", 198.40});
    repository.addProduct(Product{"Rogue Fresh Roast - 22.0Z", "Katcef Brothers Inc", "Beer", 938.60});
    repository.addProduct(Product{"Ommegang World Beer Cup - 3/3 25.4Oz", "Legends Ltd", "Beer", 215.11});
    repository.addProduct(Product{"Rogue Brutal Bitter Nr - 12/22Oz", "Katcef Brothers Inc", "Beer", 981.37});
    repository.addProduct(Product{"Corsendonk Christmas Tin", "Legends Ltd", "Beer", 432.41});
    repository.addProduct(Product{"Bags - Plastic 20Lb", "Default", "Supplies", 854.47});
    repository.addProduct(Product{"Smirnoff Bloody Mary Mix - 32Oz", "Diageo North America Inc", "Non-Alcohol", 348.95});

}

//baza de date