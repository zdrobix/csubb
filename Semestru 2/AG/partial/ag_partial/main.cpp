#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

void afisare_matrice(int ma[][100], int n) {
    {
    for ( int i = 0; i <= n ; i ++ )

        for ( int j = 0; j <= n; j ++ ) {
            cout << ma[i][j];
        }
        cout << endl;
    }
}

void citire_matrice_adiacenta(int &n, int &m, int ma[100][100]) {

    ifstream fin("../input.txt");

    fin >> n >> m;

    int x, y;

    while ( fin >> x >> y ) {

        ma[x][y] = 1;

        ma[y][x] = 1;
    }
}

int graf_conex(int n, int m, int ma[][100]) {

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

    int ma[100][100], n, m;

    citire_matrice_adiacenta(n, m, ma);

    int oras1, oras2;

    cout << "Specificati muchia eliminata: ";

    cin >> oras1 >> oras2;

    ma[oras1][oras2] = 0;

    ma[oras2][oras1] = 0;

    if (graf_conex(n, m, ma))

        cout << "DA";

    else cout << "NU";

    return 0;
}