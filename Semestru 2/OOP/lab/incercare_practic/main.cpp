#include "IncercarePractic.h"
#include "tests.h"
#include "service.h"
#include "repo.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    testAll();
    Repo repo{ "./input.txt" };
    Service service{ repo };
    IncercarePractic gui{ service };
    gui.show();
    return a.exec();
}
