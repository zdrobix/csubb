#ifndef UI_H
#define UI_H
#include "service.h"

using namespace std;

class Ui {
public:
    /*
     * Constructor pentru clasa Ui
     * Ia ca parametru un Controller
     */
    explicit Ui(Controller& service,Controller& service_cos): service{(service)}, service_cos{(service_cos)}{};

    explicit Ui(Controller& service) :service{service}, service_cos{service} {};

    /*
     * Daca exista alta consola deschisa, se sterge.
     * Pentru a asigura buna functionare a programului(fara duplicate).
     */
    Ui(const Ui& ot) = delete;

    /*
     * Ruleaza aplicatia.
     */
    void runApp();

    /*
     * Afiseaza utilizatorului meniul cu optiuni.
     */
    static void printMenu();

    /*
     * Intreaba utilizatorul ce optiune doreste sa aleaga.
     */
    static string readOption();

    /*
     * Afiseaza toate produsele din memorie.
     */
    static void uiProducts(Controller& serv);

    /*
     * Interfata pentru meniul de adaugare.
     */
    void uiAdd();

    /*
     * Interfata pentru meniul de modificare.
     */
    void uiUpdate();

    /*
     * Interfata pentru meniul de stergere.
     */
    void uiDelete();

    /*
     * Interfata pentru meniul de filtrare.
     */
    void uiFilter();

    /*
     * Interfata pentru meniul de sortare.
     */
    void uiSort();

    /*
     * Interfata pentru undo.
     */
    void uiUndo();

    void uiAddBag();

    void uiEmptyBag();

    void uiRandomAdd();

    void uiExportBag();

    static void uiViewBag(Controller& serv);

private:
    Controller& service;

    Controller& service_cos;
};


#endif //UI_H