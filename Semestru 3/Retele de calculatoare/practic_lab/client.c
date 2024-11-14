#include <stdio.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>

#define MAX_SIZE 1000

int main() {
	int c;
	struct sockaddr_in server;
	int nr;
	
	c = socket(AF_INET, SOCK_STREAM, 0);
	if (c < 0) {
		printf("Eroare la crearea socket-ului.\n");
		return 1;
	}

	memset(&server, 0, sizeof(server));
	server.sin_family = AF_INET;
    	server.sin_port = htons(1234);
    	server.sin_addr.s_addr = inet_addr("127.0.0.1");

	if (connect(c, (struct sockaddr *)&server, sizeof(server)) < 0) {
   	     	printf("Eroare la conectarea la server");
       		return 1;
    	}

	printf("Se citeste un numar N: ");
	scanf("%d", &nr);
	if (nr <= 0 || nr > 1000) {
		printf("Numarul este in afara limitelor.");
		return 1;
	}
	send(c, &nr, sizeof(nr), 0);
	int nr_prime[MAX_SIZE];
	recv(c, nr_prime, sizeof(nr_prime), 0);
	
	printf("Primele %d numere prime sunt: ", nr);
	for ( int i = 0; i < nr; i ++ ) {
		nr_prime[i] = ntohs(nr_prime[i]);
		printf("%d ", nr_prime[i]);
	}
	printf("\n");
	close(c);
}
