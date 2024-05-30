#include <iostream>
#include <fstream>
#include <queue>

using namespace std;

void floyd_warshall() {

    int n, m = 0, ma[100][100], mi[100][100];

    ifstream fin("../input.txt");

    fin >> n;

    int x, y;

    while ( fin >> x >> y ) {

        ma[x][y] = 1;

        m ++;
    }

    for ( int a = 0; a < n ; a ++ )

        for ( int b = 0; b < n ; b ++ )

            mi[a][b] = ma[a][b];

    for ( int a = 0; a < n; a ++ )

        for ( int b = 0; b < n; b ++ )

            for ( int c = 0; c < n; c ++ )

                mi[b][c] = mi[b][c] || mi[b][a] && mi[a][c];

    cout << "Matricea inchiderii tranzitive: \n";

    for ( int a = 0; a < n; a++ ) {

        for ( int b = 0; b < n; b++ ) {

            cout << mi[a][b] << " ";
        }
        cout << "\n";
    }
}

void bfs_parcurgere() {

    int n, m = 0, ma[100][100] = {0}, sursa;

    ifstream fin("../input1.txt");

    fin >> n;

    int x, y;

    while ( fin >> x >> y ) {

        ma[x][y] = 1;

        m ++;
    }
    cout << "\nVarful sursa pentru BFS: "; cin >> sursa; cout <<endl;

    bool parcurs[100] = {false};

    int distanta[100] = {0};

    queue<int> q;

    q.push(sursa);

    parcurs[sursa] = true;

    distanta[sursa] = 0;

    while (q.empty() == 0) {

        int nod = q.front();

        q.pop();

        for (int i = 1; i <= n; i++) {

            if (ma[nod][i] == 1 && !parcurs[i]) {

                q.push(i);

                distanta[i] = distanta[nod] + 1;

                parcurs[i] = true;
            }
        }
    }
    for ( int i = 1; i <=n ; i ++ )

        cout << "Varful " << i << " (distanta = " << distanta[i] << ")\n";
}

void dfs_visit(int ma[100][100], int n, int varf, int parinti[100], int culoare[100], int distanta[100], int final[100], int &timp) {

    timp ++;

    distanta[varf] = timp;

    culoare[varf] = 1;

    for ( int i = 1; i <= n; i ++ )

        for ( int u = 1; u <= n; u ++ )

            if (culoare[u] == 0 && ma[varf][u] == 1) {

                parinti[u] = varf;

                dfs_visit(ma, n, u, parinti, culoare, distanta, final, timp);
            }
    culoare[varf] = 2;

    timp++;

    final[varf] = timp;
}

void dfs_parcurgere() {

    int n, m = 0, ma[100][100] = {0}, sursa;

    ifstream fin("../input2.txt");

    fin >> n;

    int x, y;

    while ( fin >> x >> y ) {

        ma[x][y] = 1;

        m ++;
    }
    int parinti[100], culoare[100]; //0-alb; 1-gri; 2-negru

    int timp = 0, distanta[100], final[100];

    for (int i = 1; i <= n; i ++ ) {

        parinti[i] = -1;

        culoare[i] = 0;

        distanta[i] = 0;

        final[i] = 0;
    }

    for ( int i = 1; i <= n ; i ++ )

        if ( culoare[i] == 0)

            dfs_visit(ma, n, i, parinti, culoare, distanta, final, timp);

    cout << "\n\nDfs:\n";

    for ( int i = 1; i <= n ; i ++ )

        cout << "Varful " << i << " distanta= " << distanta[i]<< " final= " << final[i] << endl;
}

int main() {

    floyd_warshall();

    bfs_parcurgere();

    dfs_parcurgere();

    return 0;
}