#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <arpa/inet.h>

#define MAX_SIZE 100

int main(int argc, char **argv) {
  int c;
  struct sockaddr_in server, client;

  socklen_t l = sizeof(client);
  
  char sirCitit[MAX_SIZE], caracterCitit;
  int pozitiiCaracter[MAX_SIZE];
  
  c = socket(AF_INET, SOCK_DGRAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(atoi(argv[1]));
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr(argv[2]);

  printf("Se introduce un sir de caractere: ");
  fgets(sirCitit, MAX_SIZE, stdin);
  sirCitit[strcspn(sirCitit, "\n")] = 0;

  printf("Caracterul cautat: ");
  caracterCitit = getchar();

  sendto(c, sirCitit, sizeof(sirCitit), 0, (struct sockaddr *) &server, sizeof(server));
  sendto(c, &caracterCitit, 1, 0, (struct sockaddr *) &server, sizeof(server));

  recvfrom(c, pozitiiCaracter, sizeof(pozitiiCaracter), MSG_WAITALL, (struct sockaddr *) &client, &l);
  int numarPozitii;
  recvfrom(c, &numarPozitii, sizeof(numarPozitii), MSG_WAITALL, (struct sockaddr *) &client, &l);

  numarPozitii = ntohs(numarPozitii);
  printf("Pozitiile pe care apare caracterul %c: ", caracterCitit);
  for ( int i = 0; i < numarPozitii; i ++) {
	pozitiiCaracter[i] = ntohs(pozitiiCaracter[i]);
	printf("%i ", pozitiiCaracter[i]);
  }
  printf("\n");
  close(c);
}
