#include "tests.h"
#include "repo.h"
#include "service.h"
#include "ui.h"

using namespace std;

int main() {

    testAll();

    cout << "Contor: " << Product::contor << "\n\n";

    string filename = "../products.txt";

    RepoFile repository{filename}, repository_cos{};

    Validator val{};

    Controller controller{repository, val}, controller_cos{repository_cos, val};

    Ui console{controller, controller_cos};

    console.runApp();

    return 0;
}