#include "tests.h"
#include "domain.h"
#include "repo.h"
#include "service.h"
#include <string>
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

    Validator val{};

    try {
        Validator::validate_product("noProducer", "", "dairy", 12.99);

    } catch (const runtime_error& e) {

        assert(true);
    }

    try {
        Validator::validate_product("noProducer", "", "dairy", 12.99);

    } catch (const runtime_error& e) {

        assert(true);
    }

    try {
        Validator::validate_product("", "noName", "dairy", 12.99);

    } catch (const runtime_error& e) {

        assert(true);
    }

    try {
        Validator::validate_product("invalidPrice", "invalidPrice", "invalidPrice", -1);

    } catch (const runtime_error& e) {

        assert(true);
    }

    try {
        Validator::validate_product("invalidPrice", "invalidPrice", "invalidPrice", 0);

    } catch (const runtime_error& e) {

        assert(true);
    }
}

void testUpdateProduct() {

    Repo repo{};

    Validator val{};

    Controller service{repo, val};

    assert(service.numberProducts() == 0);

    assert(service.add("milk", "napolact", "dairy", 12.99) == true);

    assert(service.update(0, "milk", "napolact", "dairy", 12.99) == true);

    //assert(service.update(2, "invalidPos", "invalidPos", "invalidPos", 12.99) == false);

    //assert(service.update(0, "", "invalidName", "invalidName", 12.99) == false);

    //assert(service.update(0, "invalidProd", "", "invalidProd", 12.99) == false);

    //assert(service.update(0, "invalidType", "invalidType", "", 12.99) == false);

    //assert(service.update(0, "invalidPrice", "napolact", "napolact", 0) == false);

    //assert(service.update(12, "invalidPos", "invalidPos", "invalidPos", 1000) == false);

    assert(service.update(0,"MILK", "NAPOLACT", "DAIRY", 9999.99) == true);

    Product* list = repo.getAll();

    Product p1 = list[0];

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
#include "ui.h"
void testSortProduct(){

    Repo repo{};

    Validator val{};

    Controller service{repo, val};  Ui console{service};

    assert(service.numberProducts() == 0);

    assert(service.add("a1", "prod1", "tip1", 5) == true);

    assert(service.add("b2", "prod1", "tip2", 2) == true);

    assert(service.add("c3", "xpro", "tip1", 3) == true);

    assert(service.add("d4", "prod1", "tip2", 1) == true);

    assert(service.add("e5", "xpro", "tip1", 4) == true);

    service.sortPrice(true);

    Product* lista = repo.getAll();

    assert(lista[0].getPrice() == 5);
    assert(lista[1].getPrice() == 4);
    assert(lista[2].getPrice() == 3);
    assert(lista[3].getPrice() == 2);
    assert(lista[4].getPrice() == 1);

    service.sortPrice(false);

    assert(lista[0].getPrice() == 1);
    assert(lista[1].getPrice() == 2);
    assert(lista[2].getPrice() == 3);
    assert(lista[3].getPrice() == 4);
    assert(lista[4].getPrice() == 5);

    service.sortName(true, true);

    assert(lista[4].getName() == "a1");
    assert(lista[3].getName() == "b2");
    assert(lista[2].getName() == "c3");
    assert(lista[1].getName() == "d4");
    assert(lista[0].getName() == "e5");

    service.sortName(true, false);

    assert(lista[3].getType() == "tip1");
    assert(lista[4].getType() == "tip1");
    assert(lista[2].getType() == "tip1");
    assert(lista[1].getType() == "tip2");
    assert(lista[0].getType() == "tip2");

    service.sortName(false, true);

    assert(lista[4].getName() == "e5");
    assert(lista[3].getName() == "d4");
    assert(lista[2].getName() == "c3");
    assert(lista[1].getName() == "b2");
    assert(lista[0].getName() == "a1");

    service.sortName(false, false);

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

    assert(lista[7].getType() == "tip1"); assert(lista[7].getName() == "a1");
    assert(lista[6].getType() == "tip1"); assert(lista[6].getName() == "c3");
    assert(lista[5].getType() == "tip1"); assert(lista[5].getName() == "e5");
    assert(lista[4].getType() == "tip2"); assert(lista[4].getName() == "a1");
    assert(lista[3].getType() == "tip2"); assert(lista[3].getName() == "b2");
    assert(lista[2].getType() == "tip2"); assert(lista[2].getName() == "d2");
    assert(lista[1].getType() == "tip2"); assert(lista[1].getName() == "d4");
    assert(lista[0].getType() == "tip2"); assert(lista[0].getName() == "e6");

    service.sortNamePart2(false);

}

void testAll() {

    testCreateProduct();

    testValidator();

    testAddProduct();

    testUpdateProduct();

    testDeleteProduct();

    testFilterProduct();

    testSortProduct();

}