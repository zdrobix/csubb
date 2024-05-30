#include <QtWidgets/QApplication>
#include <string>

#include "prodGUI.h"
#include "repo.h"
#include "validator.h"
#include "service.h"
#include "service_bag.h"
#include "tests.h"

int main(int argc, char *argv[])
{   
    QApplication a(argc, argv);
    string filename = "./products - Copy.txt";
    string filenameBag = "./productsBag.txt";
    RepoFile repo{ filename }, repo_cos{filenameBag};
    Validator val;
    Controller service{ repo, val };
    Controller_bag service_cos{repo_cos};
    prodGUI gui{ service, service_cos };
    gui.show();
    return a.exec();
}
