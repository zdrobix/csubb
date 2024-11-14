#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define MAX_SIZE 1000

int ePrim(int nr) {
	for ( int d = 2; d <= nr / 2; d ++)
		if (nr % d == 0)
			return 0;
	return 1;
}

int main() {
	int s, c;
	struct sockaddr_in server, client;
	socklen_t l = sizeof(client);
	
	int nr, nr_prime[MAX_SIZE];

	s = socket(AF_INET, SOCK_STREAM, 0);
    	if (s < 0) {
        	printf("Eroare la crearea socket-ului");
       		return 1;
    	}

    	memset(&server, 0, sizeof(server));
    	server.sin_family = AF_INET;
    	server.sin_addr.s_addr = INADDR_ANY;
    	server.sin_port = htons(1234);

    	if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
        	printf("Eroare la bind");
        	return 1;
    	}

    	listen(s, 5);

    	while (1) {
       		c = accept(s, (struct sockaddr *)&client, &l);
	        printf("S-a conectat un client.\n");
		recv(c, &nr, sizeof(nr), 0);
		int prim = 2;
		for ( int i = 0; i < nr; i ++ ) {
			nr_prime[i] = htons(prim);
			prim ++;
			while (ePrim(prim) == 0)
				prim ++;	
		}
		send(c, nr_prime, sizeof(nr_prime), 0);
		close(c);
	
	}
	return 0;	
}
