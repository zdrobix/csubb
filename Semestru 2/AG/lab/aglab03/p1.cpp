#include <iostream>
#include <fstream>

using namespace std;

void problema_1() {

    ifstream fin("input_p1.txt");

    ofstream fout("output.txt");

    int inf = 5000;

    int V, E, S, md[100][100], dist[100], p[100];

    fin >> V >> E >> S;

    for ( int i = 0; i < V; i ++ ) {

        dist[i] = inf;

        p[i] = -1;

        for (int j = 0; j < V; j++)

            md[i][j] = 0;
    }
    for( int i = 1; i <=E; i ++)
    {
        int x, y, w;

        fin >> x>> y >> w;

        md[x][y] = w;
    }
    dist[S]=0;

    for(int i=0; i < V-1 ; i++)

        for(int u = 0; u < V; u++)

            for ( int v= 0; v < V; v ++ )

                if(md[u][v] != 0 && dist[u] < inf)

                    if(dist[v] > dist[u] + md[u][v]) {

                        dist[v] = dist[u] + md[u][v];

                        p[v] = u;
                    }


    for(int i = 0; i < V; i ++)

        fout<<"Drumul de la "<<S<<" la "<<i<<" este de cost "<<dist[i]<<'\n';

    fout<<endl;
}

int main(int argc, char ** argv) {

    int optiune = 1;

    while(optiune) {

        cout << "1. Problema 1\n2. Problema 2\n4. Iesire\n";

        cout << "Introduceti optiunea: ";

        cin >> optiune;

        if (optiune==1) {

            problema_1();

        } else if (optiune == 4) {

            optiune = 0;
        }
    }

    return 0;
}