 #include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFFER_SIZE 256

int main() {
    int c;
    struct sockaddr_in server;
    char sir1[BUFFER_SIZE], sir2[BUFFER_SIZE];
    char ch;
    int fr;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socket-ului");
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

    printf("S1: ");
    fgets(sir1, sizeof(sir1), stdin);
    sir1[strcspn(sir1, "\n")] = 0;
    printf("S2: ");
    fgets(sir2, sizeof(sir2), stdin);
    sir2[strcspn(sir2, "\n")] = 0;
    send(c, sir1, sizeof(sir1), 0);
    send(c, sir2, sizeof(sir2), 0);
    recv(c, &ch, sizeof(ch), 0);
    recv(c, &fr, sizeof(fr), 0);
    fr = htons(fr);
    if (fr > 1)
    	printf("Caracterul este: '%c' (apare de %d ori)\n", ch, fr);
    else printf("Caracterul este: '%c' (apare odata)\n", ch);
    close(c);
    return 0;
}
