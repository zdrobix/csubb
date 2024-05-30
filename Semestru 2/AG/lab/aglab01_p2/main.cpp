//
// Created by EZDROBA7Y on 3/21/2024.
//
/*
2. Fie un graf reprezentat sub o anumita forma (graful este citit dintr-un fisier). Sa se rezolve:
a. sa se determine nodurile izolate dintr-un graf.
b. sa se determine daca graful este regular.
c. pentru un graf reprezentat cu matricea de adiacenta sa se determine matricea distantelor.
d. pentru un graf reprezentat cu o matrice de adiacenta sa se determine daca este conex.
 */
#include <iostream>
#include <fstream>
#include <queue>

using namespace std;

void citire_graf(int &n, int &m, int ma[100][100]) {

    ifstream fin("../input.txt");

    fin >> n >> m;

    int x, y;

    while ( fin >> x >> y ) {

        ma[x][y] = 1;

        ma[y][x] = 1;
    }
}

void print_matrice_adiac(int n, int ma[100][100]) {

    for ( int i = 1 ; i <= n ; i ++) {

        for (int j = 1; j <= n; j++)

            cout << ma[i][j] << ' ';

        cout << endl;
    }
}

int noduri_izolate (int n, int ma[100][100]) {

    for ( int i = 1 ; i <= n ; i ++ ) {

        int ok = 1;

        for (int j = 1; j <= n; j++)

            if (ma[i][j] == 1 && i != j) {

                ok = 0; j = n + 1;
            }

        if (ok) return 1;
    }
    return 0;
}

int graf_regular ( int &n, int &m, int la[100][100]) {

    ifstream fin("../input.txt");

    fin >> n >> m;

    int i;

    for ( i = 1 ; i <= m ; i ++ )
    {
        int x, y;

        fin >> x >> y;

        la[x][1] ++;
        la[x][la[x][1]+1] = y;

        la[y][1] ++;
        la[y][la[y][1]+1] = x;
    }
    for ( int j = 1; j < n; j ++ )

        if (la[j][1] != la[j+1][1])

            return 0;

    return 1;
}

void matricea_distantelor (int n, int m, int md[100][100])
{
    citire_graf(n, m, md);
    //se citeste in matricea distantelor matricea de adiacenta

    for ( int i = 1 ; i <= n ; i ++ )

        for ( int j = 1 ; j <= n ; j ++ )

            for ( int q = 1 ; q <= n ; q ++ )

                if ( md[i][j] != 0 && md [j][q] != 0 && md [q][i] )

                    md[i][j] = min( md[i][j], md[i][q] + md[j][q]);

                else if (md[i][q] != 0 && md[j][q] != 0 && i != j )

                    md[i][j] = md[i][q] + md[j][q];
}

int graf_conex(int n, int m, int ma[100][100]) {

    citire_graf(n, m, ma);

    int parcurs[100] = {0}; //pentru a marca nodurile vizitate

    queue<int> q;

    q.push(1); //se pune primul nod in coada

    parcurs[1] = 1; //primul nod = vizitat

    while (q.empty() == 0) {

        int nod = q.front();
        q.pop();

        for (int i = 1; i <= n; i++) {

            if (ma[nod][i] == 1 && parcurs[i] == 0) {

                q.push(i);

                parcurs[i] = 1;
            }
        }
    }
    for (int i = 1; i <= n; i++)

        if (parcurs[i] == 0)

            return 0;

    return 1;
}

int main() {

    int n = 0, m = 0, ma[100][100] = {0}, la[100][100] = {0}, md[100][100] = {0};

    citire_graf(n, m, ma);


    if (noduri_izolate(n, ma) == 1)

        cout << "\nExista noduri izolate" << endl;

    else cout << "\nNu exista noduri izolate" << endl;

    if (graf_regular(n, m, la) == 1)

        cout << "\nEste un graf regular" << endl;

    else cout << "\nNu este un graf regular" << endl;

    cout << "\nMatricea de adiacenta este" << endl << endl;

    print_matrice_adiac(n, ma);

    cout << "\nMatricea distantelor este" << endl << endl;

    matricea_distantelor(n, m, md);

    print_matrice_adiac(n, md);

    if (graf_conex(n, m, la) == 1)

        cout << "\nEste un graf conex" << endl;

    else cout << "\nNu este un graf conex" << endl;

    return 0;

}


