#include <iostream>
#include <fstream>

using namespace std;

void matrice_adiacenta_citire(int ma[50][50], int &n, int &m) {

    ifstream fin("fisier.txt");

    fin >> n; //se citeste numarul de noduri

    fin >> m; //se citeste numarul de muchii

    int i; //cursor

    for ( i = 1; i <= m ; i ++ ) {

        int x, y;

        fin >> x >> y;

        ma[x][y] = 1;
        ma[y][x] = 1;
    }
}

void afisare_matrice(int ma[50][50], int n) {

    int i, j;

    for ( i = 1 ; i <= n ; i ++ )
    {
        for ( j = 1 ; j <= n ; j ++ )

            cout<< ma[i][j] << ' ';

        cout << endl;
    }
    cout << endl;
}

void lista_adiacenta_citire(int la[50][50], int n, int m ) {

    ifstream fin("fisier.txt");

    fin >> n; //se citeste numarul de noduri

    fin >> m; //se citeste numarul de muchii

    int i;

    for ( i = 0 ; i < n ; i ++ )
    {
        int x, y;

        fin >> x >> y;

        la[x][0] ++;
        la[x][la[x][0]] = y;

        la[y][0] ++;
        la[y][la[y][0]] = x;
    }
}

void afisare_lista(int ma[50][50], int n) {

    int i, j;

    for ( i = 0 ; i <= n ; i ++ )
    {
        for ( j = 0 ; j <= n ; j ++ )

            cout<< ma[i][j] << ' ';

        cout << endl;
    }
    cout << endl;
}

void matrice_incidenta_citire(int mi[50][50], int n, int m) {

    ifstream fin("fisier.txt");

    fin >> n; //se citeste numarul de noduri

    fin >> m; //se citeste numarul de muchii

    int i;

    for ( i = 1; i <= n ; i ++) {

        int x, y;

        fin >> x >> y;

        mi[x][i] = 1;

        mi[y][i] = 1;
    }
}

void afisare_matrice_incidenta(int mi[50][50], int n, int m) {

    int i, j;

    for ( i = 1 ; i <= n ; i ++)
    {
        for ( j = 1 ; j <= m ; j ++ )

            cout << mi[i][j] << ' ';

        cout << endl;
    }
}

int main()
{
    int n, m; //noduri&muchii

    int m_adiac[50][50] = {0}, l_adiac[50][50] = {0}, m_incid[50][50] = {0};

    matrice_adiacenta_citire(m_adiac,n , m);

    cout << "matrice de adiacenta: "<< endl;

    afisare_matrice(m_adiac, n);

    lista_adiacenta_citire(l_adiac, n, m);

    cout << "lista de adiacenta: "<< endl;

    afisare_lista(l_adiac, n);

    matrice_incidenta_citire(m_incid, n, m);

    cout << "matrice de incidenta: "<< endl;

    afisare_matrice_incidenta(m_incid, n, m);

    return 0;
}
