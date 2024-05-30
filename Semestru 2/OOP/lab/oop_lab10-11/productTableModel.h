#ifndef PRODUCTTABLEMODEL H
#define PRODUCTTABLEMODEL H
#pragma once

#include <QAbstractTableModel>
#include "service.h" 

class ProductTableModel : public QAbstractTableModel {
    Q_OBJECT
private:
    vector<Product> productList;
public:
    
    ProductTableModel(QObject* parent, vector<Product> productList) : QAbstractTableModel(parent), productList{ productList } {};

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return productList.size();
    }

    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 5;
    }

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
        if (!index.isValid() || index.row() >= productList.size() || index.column() >= 5)
            return QVariant();

        const Product& product = productList.at(index.row());
        if (role == Qt::DisplayRole) {
            switch (index.column()) {
            case 0: 
                return index.row() + 1;
            case 1: 
                return QString::fromStdString(product.getName()); 
            case 2: 
                return QString::fromStdString(product.getProducer()); 
            case 3: 
                return QString::fromStdString(product.getType());
            case 4: 
                return product.getPrice();
            default: return QVariant();
            }
        }
        return QVariant();
    }

    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override {
        if (role != Qt::DisplayRole)
            return QVariant();

        if (orientation == Qt::Horizontal) {
            switch (section) {
            case 0: 
                return tr("Index");
            case 1: 
                return tr("Name");
            case 2: 
                return tr("Producer");
            case 3: 
                return tr("Type");
            case 4: 
                return tr("Price");
            default: return QVariant();
            }
        }
        return QVariant();
    }
};


#endif //PRODUCTTABLEMODEL H