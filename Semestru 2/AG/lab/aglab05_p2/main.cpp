#include <fstream>
#include <iostream>
#include <queue>
#include <climits>
#include <cstring>

using namespace std;

void citire (int ma[][100], int &n, int &m, int &sursa, int &destinatie)
{
    ifstream fin("../input1.txt");
    fin >> n >> sursa >> destinatie;
    int x, y, z;
    m = 0;
    while ( fin >> x >> y >> z) {
        ma[x][y] = z;
        m ++;
    }
}

bool bfs_parcurgere(int ma[][100], int &n, int sursa, int destinatie, int parinte[100]) {

    bool parcurs[100];
    memset(parcurs, 0, sizeof(parcurs));
    queue<int> q;
    q.push(sursa);
    parcurs[sursa] = true;
    parinte[sursa] = -1;
    while (!q.empty()) {
        int nod = q.front();
        q.pop();
        for (int i = 0; i < n; i++) {
            if (ma[nod][i] > 0 && !parcurs[i]) {
                q.push(i);
                parinte[i] = nod;
                parcurs[i] = true;
            }
        }
    }
    return parcurs[destinatie];
}

int ford_fulkerson ( int ma[][100], int n, int sursa, int destinatie) {
    int rez[100][100];

    for (int u = 0; u < n; u++)
        for (int v = 0; v < n; v++)
            rez[u][v] = ma[u][v];

    int flux_maxim = 0;
    int parinte[100];

    while (bfs_parcurgere(rez, n, sursa, destinatie, parinte)) {

        int path = INT_MAX;
        for ( int v = destinatie; v != sursa; v = parinte[v])
            path = min(path, rez[parinte[v]][v]);

        for ( int v = destinatie; v != sursa; v = parinte[v]) {

            rez[parinte[v]][v] -= path;
            rez[v][parinte[v]] += path;
        }
        flux_maxim += path;
    }
    return flux_maxim;
}


int main () {
    int ma[100][100], parinte[100];
    for (int u = 0; u < 100; u++){
        for (int v = 0; v < 100; v++)
            ma[u][v] = 0;
        parinte[u] = 0;
    }
    int n, m, sursa, destinatie;
    citire(ma, n, m, sursa, destinatie);
    cout << "Fluxul maxim al grafului este: " << ford_fulkerson(ma, n, sursa, destinatie);

    return 0;
}