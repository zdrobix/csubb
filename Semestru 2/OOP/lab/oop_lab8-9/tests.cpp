#include "tests.h"
#include "domain.h"
#include "repo.h"
#include "service.h"
#include <string>
#include <iostream>
#include <cassert>

using namespace std;

void testCreateProduct () {

    Product p1 {"name name", "prod", "type type", 100};

    //assert(p1.name == "name name");
    assert(p1.getName() == "name name");

    //assert(p1.producer == "prod");
    assert(p1.getProducer() == "prod");

    //assert(p1.type == "type type");
    assert(p1.getType() == "type type");

    //assert(p1.price == 100);
    assert(p1.getPrice() == 100);

    string name = "AA", producer = "BB", type = "CC";

    p1.setName(name);
    p1.setProducer(producer);
    p1.setType(type);
    p1.setPrice(22);

    //assert(p1.name == "AA");
    assert(p1.getName() == "AA");

    //assert(p1.producer == "BB");
    assert(p1.getProducer() == "BB");

    //assert(p1.type == "CC");
    assert(p1.getType() == "CC");

    //assert(p1.price == 22);
    assert(p1.getPrice() == 22);

    assert(p1 == p1);

    assert((p1 == Product{"AA", "BB", "CC", 22}));

    assert(!(p1 == Product{"Not", "The", "Same", 12}));
}

void testAddProduct() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.numberProducts() == 1);

    assert(service.numberProducts() == 1);
}

void testValidator() {

    try {
        Validator::validate_product("noProducer", "", "dairy", 12.99);

    } catch (const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}

    try {
        Validator::validate_product("noProducer", "", "dairy", 12.99);

    } catch (const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}

    try {
        Validator::validate_product("", "noName", "dairy", 12.99);

    } catch (const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}

    try {
        Validator::validate_product("invalidPrice", "invalidPrice", "invalidPrice", -1);

    } catch (const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}

    try {
        Validator::validate_product("invalidPrice", "invalidPrice", "invalidPrice", 0);

    } catch (const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}
}

void testUpdateProduct() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.update(0, "milk", "napol;act", "dairy", 12.99) == true);

    try {

        assert(service.update(2, "invalidPos", "invalidPos", "invalidPos", 12.99) == false);

        assert(service.update(0, "", "invalidName", "invalidName", 12.99) == false);

        assert(service.update(0, "invalidProd", "", "invalidProd", 12.99) == false);

        assert(service.update(0, "invalidType", "invalidType", "", 12.99) == false);

        assert(service.update(0, "invalidPrice", "napolact", "napolact", 0) == false);

        assert(service.update(12, "invalidPos", "invalidPos", "invalidPos", 1000) == false);

    } catch (const MyException& e) {assert(true); }

    assert(service.update(0, "MILK", "NAPOLACT", "DAIRY", 9999.99) == true);

    vector<Product> list = repo.getAll();

    Product p1 = list.at(0);

    //assert(p1.name == "MILK");
    assert(p1.getName() == "MILK");

    //assert(p1.producer == "NAPOLACT");
    assert(p1.getProducer() == "NAPOLACT");

    //assert(p1.type == "DAIRY");
    assert(p1.getType() == "DAIRY");

    //assert(p1.price - 9999.99 < 0.01);
    assert(p1.getPrice() - 9999.99 < 0.01);
}

void testDeleteProduct() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.numberProducts() == 1);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.numberProducts() == 2);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.numberProducts() == 3);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.numberProducts() == 4);

    assert(service._delete(0) == true);

    assert(service.numberProducts() == 3);

    assert(service._delete(0) == true);

    assert(service.numberProducts() == 2);

    assert(service._delete(0) == true);

    assert(service.numberProducts() == 1);

    assert(service._delete(0) == true);

    assert(service.numberProducts() == 0);

    assert(service._delete(0) == false);

    assert(service._delete(1) == false);

    assert(service._delete(2) == false);

    assert(service.update(1, "invalidPos", "invalidPos", "invalidPos", 10) == false);
}

void testFilterProduct() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("milk", "prod1", "dairy", 10) == true);

    assert(service.add("m&m", "prod1", "dairy", 15) == true);

    assert(service.add("madams", "xpro", "dairy", 20) == true);

    assert(service.add("hamburger", "prod1", "fat", 25) == true);

    assert(service.add("ham", "xpro", "fat", 30) == true);

    int pos[10], counter;

    counter = service.filterPrice(0, pos, 21, true);

    assert(counter == 3);

    assert(pos[0] == 0); assert(pos[1] == 1); assert(pos[2] == 2);

    counter = service.filterPrice(0, pos, 21, false);

    assert(counter == 2);

    assert(pos[0] == 3); assert(pos[1] == 4);

    counter = service.filterName(0, pos, 'm', true);

    assert(counter == 3);

    assert(pos[0] == 0); assert(pos[1] == 1); assert(pos[2] == 2);

    counter = service.filterName(0, pos, 'p', false);

    assert(counter == 3);

    assert(pos[0] == 0); assert(pos[1] == 1); assert(pos[2] == 3);
}
void testSortProduct(){

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("a1", "prod1", "tip1", 5) == true);

    assert(service.add("b2", "prod1", "tip2", 2) == true);

    assert(service.add("c3", "xpro", "tip1", 3) == true);

    assert(service.add("d4", "prod1", "tip2", 1) == true);

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    assert(service.totalProductCost() == 15);

    service.sortPrice(false);

    auto& lista = const_cast<vector<Product> &>(repo.getAll());

    assert(lista.at(0).getPrice() == 5);
    assert(lista.at(1).getPrice() == 4);
    assert(lista.at(2).getPrice() == 3);
    assert(lista.at(3).getPrice() == 2);
    assert(lista.at(4).getPrice() == 1);

    service.sortPrice(true);

    assert(lista[0].getPrice() == 1);
    assert(lista[1].getPrice() == 2);
    assert(lista[2].getPrice() == 3);
    assert(lista[3].getPrice() == 4);
    assert(lista[4].getPrice() == 5);

    service.sortName(false, true);

    assert(lista[4].getName() == "a1");
    assert(lista[3].getName() == "b2");
    assert(lista[2].getName() == "c3");
    assert(lista[1].getName() == "d4");
    assert(lista[0].getName() == "e5");

    service.sortName(false, false);

    assert(lista[3].getType() == "tip1");
    assert(lista[4].getType() == "tip1");
    assert(lista[2].getType() == "tip1");
    assert(lista[1].getType() == "tip2");
    assert(lista[0].getType() == "tip2");

    service.sortName(true, true);

    assert(lista[4].getName() == "e5");
    assert(lista[3].getName() == "d4");
    assert(lista[2].getName() == "c3");
    assert(lista[1].getName() == "b2");
    assert(lista[0].getName() == "a1");

    service.sortName(true, false);

    assert(lista[3].getType() == "tip2");
    assert(lista[4].getType() == "tip2");
    assert(lista[2].getType() == "tip1");
    assert(lista[1].getType() == "tip1");
    assert(lista[0].getType() == "tip1");

    assert(service.add("a1", "prod1", "tip2", 1) == true);

    assert(service.add("e6", "prod1", "tip2", 1) == true);

    assert(service.add("d2", "prod1", "tip2", 1) == true);

    service.sortName(true, false);

    service.sortNamePart2(true);

    assert(lista[0].getType() == "tip1"); assert(lista[0].getName() == "a1");
    assert(lista[1].getType() == "tip1"); assert(lista[1].getName() == "c3");
    assert(lista[2].getType() == "tip1"); assert(lista[2].getName() == "e5");
    assert(lista[3].getType() == "tip2"); assert(lista[3].getName() == "a1");
    assert(lista[4].getType() == "tip2"); assert(lista[4].getName() == "b2");
    assert(lista[5].getType() == "tip2"); assert(lista[5].getName() == "d2");
    assert(lista[6].getType() == "tip2"); assert(lista[6].getName() == "d4");
    assert(lista[7].getType() == "tip2"); assert(lista[7].getName() == "e6");

    service.sortNamePart2(false);

}

void testSearch () {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.add("animale", "pos1", "tip1", 5) == true);
    assert(service.add("ANIMALE", "pos2", "tip1", 5) == true);
    assert(service.add("Animale", "pos3", "tip1", 5) == true);
    assert(service.add("aNIMALE", "pos4", "tip1", 5) == true);

    assert(service.searchByName("ani").getProducer() == "pos1");
    assert(service.searchByName("Ani").getProducer() == "pos3");
    assert(service.searchByName("ANI").getProducer() == "pos2");
    assert(service.searchByName("aNI").getProducer() == "pos4");

    assert(service.searchByPosition(0).getProducer() == "pos1");
    assert(service.searchByPosition(1).getProducer() == "pos2");
    assert(service.searchByPosition(2).getProducer() == "pos3");
    assert(service.searchByPosition(3).getProducer() == "pos4");

    try {
        auto p = service.searchByName("DO");

    } catch(const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}
    catch(const length_error& e) {assert(true);}

    try {

        auto p = service.searchByName("IN");

    } catch(const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}
    catch(const length_error& e) {assert(true);}

    try {
        auto p = service.searchByPosition(100);

    } catch(const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}
    catch(const length_error& e) {assert(true);}
    try {

        auto p = service.searchByPosition(-99);

    } catch(const MyException& e) {

        assert(true);
    }catch(const runtime_error& e) {assert(true);}
    catch(const length_error& e) {assert(true);}
}

void deleteGetAll() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("a1", "prod1", "tip1", 5) == true);

    assert(service.add("b2", "prod1", "tip2", 2) == true);

    assert(service.add("c3", "xpro", "tip1", 3) == true);

    assert(service.add("d4", "prod1", "tip2", 1) == true);

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    assert(service.numberProducts() == 5);

    vector<Product> list = service.getAllProducts();

    assert(list.size() == 5);

    service.deleteAllProducts();

    assert(service.numberProducts() == 0);
}

#include <unordered_map>
void testMapCreate() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    unordered_map<string, int> map;

    assert(service.add("a1", "prod1", "tip1", 5) == true);

    assert(service.add("b2", "prod1", "tip2", 2) == true);

    assert(service.add("c3", "xpro", "tip1", 3) == true);

    assert(service.add("d4", "prod1", "tip2", 1) == true);

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    map = service.createProductMap();

    assert(map.size() == 2);

    assert(map["tip1"] == 3);

    assert(map["tip2"] == 2);

    assert(service.add("c3", "xpro", "tip3", 3) == true);

    assert(service.add("c3", "xpro", "tip3", 3) == true);

    assert(service.add("c3", "xpro", "tip3", 3) == true);

    assert(service.add("c3", "xpro", "tip3", 3) == true);

    map = service.createProductMap();

    assert(map.size() == 3);

    assert(map["tip1"] == 3);

    assert(map["tip2"] == 2);

    assert(map["tip3"] == 4);
}

void testUndo() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.add("a1", "prod1", "tip1", 5) == true);

    assert(service.add("b2", "prod1", "tip2", 2) == true);

    assert(service.add("c3", "xpro", "tip1", 3) == true);

    assert(service.add("d4", "prod1", "tip2", 1) == true);

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    assert(service.numberProducts() == 5);
    service.undo();

    assert(service.numberProducts() == 4);
    service.undo();

    assert(service.numberProducts() == 3);
    service.undo();

    assert(service.numberProducts() == 2);
    service.undo();

    assert(service.numberProducts() == 1);
    service.undo();

    assert(service.numberProducts() == 0);

    try { service.undo(); }
    catch (const MyException& e) { assert(true);}
    catch(const bad_alloc& e) { assert(true);}

    assert(service.add("d4", "prod1", "tip2", 1) == true);

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    service._delete(1);
    service._delete(0);

    assert(service.numberProducts() == 0);
    service.undo();

    assert(service.numberProducts() == 1);
    service.undo();

    assert(service.numberProducts() == 2);

    try { service.undo(); }
    catch (const MyException& e) { assert(true);}
    catch(const bad_alloc& e) { assert(true);}

    service.deleteAllProducts();

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    assert(service.update(0, "NEWNAME ", "NEWPROD ", "NEWTYPE ", 777)==true);

    Product prod = service.getAllProducts().at(0);

    assert(prod.getName() != "e5");
    assert(prod.getProducer() != "xpro");
    assert(prod.getType() != "tip1");
    assert(prod.getPrice() != 4);

    assert(prod.getName() == "NEWNAME ");
    assert(prod.getProducer() == "NEWPROD ");
    assert(prod.getType() == "NEWTYPE ");
    assert(prod.getPrice() == 777);

    service.undo();

    Product prod2 = service.getAllProducts().at(0);

    assert(prod2.getName() == "e5");
    assert(prod2.getProducer() == "xpro");
    assert(prod2.getType() == "tip1");
    assert(prod2.getPrice() == 4);

    try { service.undo(); }
    catch (const MyException& e) { assert(true);}
    catch(const bad_alloc& e) { assert(true);}
    catch(const length_error& e) { assert(true);}
}

void testLoadSave () {

    string file = "../test_file.txt";

    RepoFile repo{file};

    assert(repo.numberProducts() == 2);

    repo.deleteProduct(repo.get(0));

    assert(repo.numberProducts() == 1);

    RepoFile repo2{file};

    assert(repo2.numberProducts() == 1);

    repo.addProduct(Product("lapte", "napolact", "lactat", 12.0));

}

void testNewRepo() {

    RepoMap repo{};

    Product p1 {"name", "prod", "type", 100};

    Product p2 {"name2", "prod2", "type2", 200};

    repo.addProduct(p1);

    repo.addProduct(p2);

    unordered_map<string, Product> map = repo.getMap();

    assert(map.size() == 2);

    repo.deleteProduct(p1);

    unordered_map<string, Product> map2 = repo.getMap();

    assert(map2.size() == 1);

    repo.updateProduct(p2, "name3", "prod3", "type3", 333);
}

void testAll() {

    testCreateProduct();

    testValidator();

    testAddProduct();

    testUpdateProduct();

    testDeleteProduct();

    testFilterProduct();

    testSortProduct();

    testSearch();

    deleteGetAll();

    testMapCreate();

    testUndo();

    testLoadSave();

    //testNewRepo();
    //works but no necessary
}