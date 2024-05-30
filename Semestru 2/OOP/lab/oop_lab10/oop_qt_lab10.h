#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_oop_qt_lab10.h"

class oop_qt_lab10 : public QMainWindow
{
    Q_OBJECT

public:
    oop_qt_lab10(QWidget *parent = nullptr);
    ~oop_qt_lab10();

private:
    Ui::oop_qt_lab10Class ui;
};
