#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>

#include "domain.h"
#include "repo.h"
#include "service.h"
#include "tests.h"
#include "productGUI.h"

#include <vector>
#include <string>

using namespace std;

int main(int argc, char *argv[])
{
    testAll();

    QApplication a(argc, argv);

    string filename = "./products.txt";

    
    RepoFile repository{ filename }, repository_cos{};
    Validator val{};
    Controller controller{ repository, val }, controller_cos{ repository_cos, val };
    productGUI gui { controller, controller_cos };

    gui.show();

    return a.exec();
}
