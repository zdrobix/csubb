#ifndef OOP_LAB8_9_UNDO_H
#define OOP_LAB8_9_UNDO_H

#include "repo.h"
#include "domain.h"
#include "exception.h"
#include <string>

class UndoAction {
public:

    virtual void doUndo() = 0;

    virtual ~UndoAction() = default;
};


class UndoAdd: public UndoAction{

    Product addedProduct;

    RepoAbs &repo;

public:
    UndoAdd(RepoAbs &repo, const Product& product): repo{repo}, addedProduct{product} {};

    void doUndo() override {

        if (!repo.exist(addedProduct))

            throw MyException((string &) "Error: data leak.");

        repo.deleteProduct(addedProduct);
    }
};


class UndoDelete: public UndoAction {

    Product deletedProduct;

    RepoAbs &repo;

public:
    UndoDelete(RepoAbs &repo, const Product& product): repo{repo}, deletedProduct{product} {};

    void doUndo() override {

        repo.addProduct(deletedProduct);
    }
};


class UndoUpdate: public UndoAction {

    RepoAbs &repo;

    Product newProduct;

    Product oldProduct;

public:
    UndoUpdate(RepoAbs &repo, const Product& productNew, const Product& productOld): repo{repo}, newProduct{productNew}, oldProduct{productOld} {};

    void doUndo() override {

        repo.deleteProduct(newProduct);

        repo.addProduct(oldProduct);
    }
};


#endif //OOP_LAB8_9_UNDO_H
