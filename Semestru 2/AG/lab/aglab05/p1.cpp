#include <fstream>
#include <vector>
#include <string>
#include <queue>


using namespace std;


bool bfs(int parent[], int V,int E,vector<int> adj[],vector<vector<int>> &adjMatrix)
{
    vector<bool> visited(V,false);
    queue<int> noduri;
    noduri.push(0);
    visited[0]=true;
    while(!noduri.empty())
    {
        int nc=noduri.front();
        noduri.pop();
        for(auto nv:adj[nc])
        {
            if(!visited[nv] &&adjMatrix[nc][nv]>0)
            {
                noduri.push(nv);
                parent[nv]=nc;
                visited[nv]=true;
            }
        }
    }
    return(visited[V-1] );

}
int edmondsKarp(int V,int E,vector<int> adj[],vector<vector<int>> &adjMatrix)
{
    int sursa=0;
    int u,v;
    int destinatie=V-1;
    int parent[V];
    int max_flow=0;
    while(bfs(parent,V,E,adj,adjMatrix))
    {
        int pathFlow=INT_MAX;
        for(v=V-1;v!=0;v=parent[v])
        {
            u=parent[v];
            pathFlow=min(pathFlow,adjMatrix[u][v]);
        }
        for(v=V-1;v!=0;v=parent[v])
        {
            u=parent[v];
            adjMatrix[u][v]-=pathFlow;
            adjMatrix[v][u]+=pathFlow;
        }
        max_flow+=pathFlow;
    }
    return max_flow;
}

int graf_conex(int n, int m, int ma[100][100]) {
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

bool eulerian (int ma[][100], int n, int m) {
    if (graf_conex(n, m, ma) == 0 )
        return false;
    for ( int i = 0; i < n; i ++ ) {
        int sum =0;
        for (int j = 0; j < n; j++)
            sum += ma[i][j];
        if (sum % 2 != 0) return false;
    }
    return true;
}

int main(int argc,char* argv[]) {
    int V,E;
    int x,y,c;
    if (argc != 5 ) return 1;
    ifstream fin(argv[1]);
    ofstream fout(argv[2]);
    fin>>V>>E;
    vector<int> adj[V];
    vector<vector<int>> adjMatrix(V,vector<int>(V,0));
    while(fin>>x>>y>>c)
    {
        adjMatrix[x][y]=c;
        adj[x].push_back(y);
        adj[y].push_back(x);
    }
    fout<<edmondsKarp(V,E,adj,adjMatrix);


    int ma[100][100], n, m;
    for ( int i = 0; i < 100 ; i ++ )
        for ( int j = 0; j < 100; j ++ )
            ma[i][j] = 0;
    string input3 = argv[3];
    string output3 = argv[4];
    ifstream in(input3);
    in >> n >> m;
    int x1, y1;
    while (in >> x1 >> y1 ) {
        ma[x1][y1] = 1;
        ma[y1][x1] = 1;
    }
    ofstream out(output3);
    out << eulerian(ma, n, m);

}