#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define MAX_SIZE 100

int main() {
	int c;
	struct sockaddr_in server;
	char nume_calc[MAX_SIZE];
	char ip[20];

	c = socket(AF_INET, SOCK_STREAM, 0);
	if (c < 0) {
		printf("EROARE crearea socket.");
		return 1;
	}

	memset(&server, 0, sizeof(server));
	server.sin_family = AF_INET;
	server.sin_port = htons(1234);
	server.sin_addr.s_addr = inet_addr("127.0.0.1");

	if (connect(c, (struct sockaddr *)&server, sizeof(server)) < 0) {
		printf("EROARE conectare server.");
		return 1;
	}

	printf("Introduceti numele calculatorului: ");
	fgets(nume_calc, sizeof(nume_calc), stdin);
	nume_calc[strcspn(nume_calc, "\n")] = 0;
	
	send(c, nume_calc, sizeof(nume_calc), 0);
	
	recv(c, &ip, sizeof(ip), 0);
	
	printf("Ip: %s\n", ip);

	close(c);
	return 0;
}
