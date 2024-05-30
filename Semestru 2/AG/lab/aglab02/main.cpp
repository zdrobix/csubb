#include <iostream>
#include <fstream>
#include <queue>

using namespace std;

/*
1. Implementați algoritmul lui Moore pentru un graf orientat neponderat
 (algoritm bazat pe Breath-first search, vezi cursul 2).
 Datele sunt citete din fisierul graf.txt. Primul rând din graf.txt
 conține numărul vârfurilor, iar următoarele rânduri conțin muchiile grafului.
 Programul trebuie să afiseze lanțul cel mai scurt dintr-un vârf (vârful sursa poate
 fi citit de la tastatura).
 */

void citire_graf(int &n, int &m, int ma[100][100]) {

    ifstream fin("../input.txt");

    fin >> n;

    int x, y;

    while ( fin >> x >> y ) {

        ma[x][y] = 1;

        ma[y][x] = 1;

        m ++;
    }
}

void moore_alg(int nod_sursa, int n, int ma[100][100], int lungimi[100], int parinti[100]) {

    lungimi[nod_sursa] = 0;

    queue<int> q;

    q.push(nod_sursa);

    while ( q.empty() == 0 ) {

        int nod_curent = q.front(); q.pop();

        for ( int i = 1 ; i <= n ; i ++ )

            if (lungimi[i] == -1 && ma[nod_curent][i] == 1) {

                parinti[i] = nod_curent;

                lungimi[i] = lungimi[nod_curent] + 1;

                q.push(i);
            }
    }
}

int main() {

    int n, m, ma[100][100], lungimi[100], parinti[100];

    citire_graf(n, m, ma);

    cout << "Din ce nod doriti sa afisati cel mai scurt lant? ";

    int nod_sursa;

    cin >> nod_sursa; cout << endl;

    if (nod_sursa > n ) return 9999;

    for ( int i = 1 ; i <= n ; i ++ )

        lungimi[i] = -1;

    moore_alg(nod_sursa, n, ma, lungimi, parinti);

    for ( int i = 1 ; i <= n ; i ++ )

        cout << lungimi[i] << ' ';

    cout << endl;

    for ( int i = 1 ; i <= n ; i ++ )

        cout << parinti[i] << ' ';

    return 0;
}