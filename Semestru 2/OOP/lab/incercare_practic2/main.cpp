#include "gui.h"
#include "repo.h"
#include "service.h"
#include "validator.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repo repo{ "./input.txt" };
    Validator val;
    Service service{ repo, val };
    gui gui{ service };
    gui.show();
    return a.exec();
}
