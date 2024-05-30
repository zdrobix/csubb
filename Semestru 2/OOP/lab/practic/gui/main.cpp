#include "gui.h"
#include "service.h"
#include "repo.h"
#include "test.h"

#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    test_all();
    Repo repo{ "./input.txt" };
    Service service{ repo };
    gui w{ service };
    w.show();
    return a.exec();
}
