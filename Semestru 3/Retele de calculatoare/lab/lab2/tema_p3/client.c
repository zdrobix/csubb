 #include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFFER_SIZE 256

int main(int argc, char **argv) {
    int c;
    struct sockaddr_in server;
    char sir[BUFFER_SIZE];
    char sir_reversed;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socket-ului");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1])); 
    server.sin_addr.s_addr = inet_addr(argv[2]); 

    if (connect(c, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Eroare la conectarea la server");
        return 1;
    }

    printf("Please enter a string: ");
    fgets(sir, sizeof(sir), stdin);
    sir[strcspn(sir1, "\n")] = 0;
    send(c, sir, sizeof(sir), 0);
    recv(c, sir_reversed, sizeof(sir_reversed), 0);
    printf("Inversul sirului %s este %s\n", sir, sir_reversed);
    close(c);
    return 0;
}
