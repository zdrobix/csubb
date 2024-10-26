#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>

#include <unistd.h>

#define BUFFER_SIZE 256

int main() {
    int s, c;
    struct sockaddr_in server, client;
    socklen_t l = sizeof(client);
    char sir1[BUFFER_SIZE], sir2[BUFFER_SIZE];
    int fr = 0;
    char ch = '\0';

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

	if (fork() == 0) {
        	recv(c, sir1, sizeof(sir1), 0);
        	recv(c, sir2, sizeof(sir2), 0);
		fr = 0;
		ch = '\0';
        	int aparitii[256] = {0}; 

        	for (int i = 0; i < strlen(sir1) && i < strlen(sir2); i++) 
            		if (sir1[i] == sir2[i]) 
                		aparitii[(unsigned char)sir1[i]]++;
                
        	for (int i = 0; i < strlen(sir1) && i < strlen(sir2); i ++) 
	    		if (sir1[i] == sir2[i])
	    			if (aparitii[(unsigned char)sir1[i]] > fr) {
					fr = aparitii[(unsigned char)sir1[i]];
					ch = (char)sir1[i];
			
			}		
        
		fr = ntohs(fr);
        	send(c, &ch, sizeof(ch), 0);
        	send(c, &fr, sizeof(fr), 0);
        	close(c);
	}
    }
    return 0;
    }
