#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>

#include <unistd.h>

#define BUFFER_SIZE 1024

int main(int argc, char** argv) {
    int s, c;
    struct sockaddr_in server, client;
    socklen_t l = sizeof(client);
    char sir[BUFFER_SIZE];
    char sir_reversed[BUFFER_SIZE];

    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Eroare la crearea socket-ului");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY; 
    server.sin_port = htons(atoi(argv[1]));

    if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Eroare la bind");
        return 1;
    }

    listen(s, 5);

    while (1) {
        c = accept(s, (struct sockaddr *)&client, &l);
        printf("S-a conectat un client.\n");

	if (fork() == 0) {
		//close(c);
        	recv(c, sir, sizeof(sir), 0);
		int len = strlen(sir);
		for (int i = 0; i < len; i ++) {
			sir_reversed[i] = sir[len - i - 1];
		}
		sir_reversed[len] = 0;
		send(c, sir_reversed, 1 + len, 0);
		close(c);
		exit(0);
	}
	close(c);
    }
    return 0;
}
