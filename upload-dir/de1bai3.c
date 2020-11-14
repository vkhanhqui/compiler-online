#include <stdio.h>
#include <stdlib.h>
#define MAX_VERTICES 100
#define INF 999999

typedef struct{
	int A[MAX_VERTICES][MAX_VERTICES];
	int m, n;
} Graph;

void add_edge(Graph *G, int u, int v, int w){
	G->A[u][v] = w;G->A[v][u] = w;
}

void init_graph(Graph *G, int n){
	G->n = n;
	int u, v;
	for(u=1;u<=G->n;u++){
		for(v=1;v<=G->n;v++){
			G->A[u][v] = 0;
		}
	}
}

int p[MAX_VERTICES], pi[MAX_VERTICES];
int mark[MAX_VERTICES];

int Prim(Graph *G, Graph *T){
	//define
	init_graph(T, G->n);
	int i, u, v;
	for(u=1;u<=G->n;u++){
		pi[u] = INF;
		mark[u] = 0;
	}
	pi[1] = 0;
	mark[1] = 1;
	for(i=1;i<=G->n;i++){
		if(G->A[1][i] != 0){
			p[i] = 1;
			pi[i] = G->A[1][i];
		}
	}
	//
	int sum_w = 0;
	for(i=1;i<G->n;i++){
		int min_pi = INF, min_u;
		//
		for(u=1;u<=G->n;u++){
			if(mark[u] == 0){
				if(min_pi > pi[u]){
					min_u = u;
					min_pi = pi[u];
				}				
			}
		}
		//
		u = min_u;
		mark[u] = 1; 
		//
		add_edge(T, u, p[u], min_pi);
		sum_w += min_pi;
		for(v=1;v<=G->n;v++){
			if(G->A[u][v] != 0 && mark[v] == 0){
				if(pi[v] > G->A[u][v]){
					pi[v] = G->A[u][v];
					p[v] = u;					
				}
			}
		}
	}
	return sum_w;
}

int main(){
	Graph G, T;
	int n, m, u, v, w, e;
	FILE *file = freopen("dothi.txt", "r", stdin);
	scanf("%d%d", &n, &m);
	init_graph(&G, n);
	for (e = 0; e < m; e++) {
		scanf("%d%d%d", &u, &v, &w);
		add_edge(&G, u, v, w);
	}
	int s;scanf("%d",&s);
	int result=s/Prim(&G, &T);
	printf("%d\n", result);
}
