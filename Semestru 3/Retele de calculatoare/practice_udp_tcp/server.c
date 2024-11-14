#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>

#define MAX_SIZE 100

int main() {
	int s, c;
	struct sockaddr_in server, client;
	socklen_t l = sizeof(client);
	char nume_calc[MAX_SIZE];

	s = socket(AF_INET, SOCK_STREAM, 0);
	if (s < 0) {
		printf("EROARE creare socket.");
		return 1;
	}

	memset(&server, 0, sizeof(server));
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(1234);

	if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
		printf("EROARE bind.");
		return 0;
	}
	listen(s, 5);

	while(1) {
		c = accept(s, (struct sockaddr *)&client, &l);
		printf("Client conectat.");

		recv(c, nume_calc, sizeof(nume_calc), 0);
		struct hostent *ip_name = gethostbyname(nume_calc);
		int i =0;
        	struct in_addr *addr = (struct in_addr *) ip_name->h_addr_list[i];
		send(c, inet_ntoa(*addr), sizeof(inet_ntoa(*addr)), 0);
		close(c);
			
	}
	
}
