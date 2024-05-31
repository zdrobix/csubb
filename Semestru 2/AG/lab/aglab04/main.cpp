#include <iostream>
#include <fstream>
#include <vector>
#include <climits>

using namespace std;

typedef struct {
    int key;
    int parent;
    bool extras;
}str;

void read_graph (int ma[][100], int &n) {
    ifstream fin("../input.txt");
    fin >> n;
    for ( int i = 0; i < n; i ++ )
        for ( int j = 0; j < n; j ++ )
            ma[i][j] = 0;
    int x, y, z;
    while(fin >> x >> y >> z) {
        ma[x][y] = z;
        ma[y][x] = z;
    }
}

void init_struct ( str vect[100], int n ) {
    for ( int i = 0; i < n; i ++ ) {
        vect[i].key = INT_MAX;
        vect[i].parent = -1;
        vect[i].extras = false;
    }
}

bool este_vid (str vect[100], int n) {
    for ( int i = 0; i < n; i ++ ) {
        if (!vect[i].extras )
            return false;
    }
    return true;
}

int ia_minim (str vect[100], int n) {
    int min = INT_MAX, index = -1;
    for (int i = 0; i < n; i++) {
        if (!vect[i].extras)
            if (min > vect[i].key) {
                index = i;
                min = vect[i].key;
            }
    }
    if ( index != -1 )
        vect[index].extras = true;
    return index;
}

void prim (str vect[100], int ma[][100], int n, int start) {
    vect[start].key = 0;
    while ( !este_vid(vect, n) ) {
        int u = ia_minim(vect, n);
        if ( u == -1 ) break;
        for ( int v = 0; v < n; v ++ )
            if (ma[u][v] != 0 and !vect[v].extras and ma[u][v] < vect[v].key) {
                vect[v].parent = u;
                vect[v].key = ma[u][v];
            }
    }
}

int main() {
    int ma[100][100], n, start;
    str vect[100];
    read_graph(ma, n);
    init_struct(vect, n);
    cout << "Nodul din care se porneste: ";
    cin >> start;
    prim(vect, ma, n, start);
    cout << "\n\nP ";
    for ( int i = 0; i < n; i ++ ) {
        cout << vect[i].parent << ' ';
    }
    cout << "\nK  ";
    for ( int i = 0; i < n; i ++ ) {
        cout << vect[i].key << ' ';
    }
    return 0;
}

/*
 * d(1, 2) = 1
 * P -1 2 1 2 3 6 7 6 2
 * K  0 1 1 7 9 2 1 1 2
 *
 * d(1, 2) = 8
 * P -1 0 8 2 3 6 7 6 2
 * K  0 4 2 7 9 2 1 1 2
 *
 */