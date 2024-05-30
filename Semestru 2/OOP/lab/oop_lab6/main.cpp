#include "tests.h"
#include "repo.h"
#include "service.h"
#include "ui.h"

using namespace std;

int main() {

    testAll();

    Repo repository{};

    Validator val{};

    Controller controller{repository, val};

    Ui console{controller};

    console.runApp();

    return 0;
}