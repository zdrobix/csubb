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
  int numarPozitii = 0;
  
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

  if (sendto(c, sirCitit, sizeof(sirCitit), 0, (struct sockaddr *) &server, sizeof(server)) < 0) {
	  close(c);
	  return 1;
  }
  if (sendto(c, &caracterCitit, 1, 0, (struct sockaddr *) &server, sizeof(server)) < 0) {
	  close(c);
	  return 1;
  }

  if (recvfrom(c, pozitiiCaracter, sizeof(pozitiiCaracter), MSG_WAITALL, (struct sockaddr *) &client, &l) < 0) {
	  close(c);
	  return 1;
  }
  if (recvfrom(c, &numarPozitii, sizeof(numarPozitii), MSG_WAITALL, (struct sockaddr *) &client, &l) < 0) {
	  close(c);
	  return 1;
  }
  
  numarPozitii = ntohs(numarPozitii);
  if (numarPozitii == 0) {
	  printf("Caracterul %c nu apare pe nicio pozitie", caracterCitit);
  	  close(c);
	  return 0;
  }	  
  printf("Pozitiile pe care apare caracterul %c: ", caracterCitit);
  for ( int i = 0; i < numarPozitii; i ++) {
	pozitiiCaracter[i] = ntohs(pozitiiCaracter[i]);
	printf("%i ", pozitiiCaracter[i]);
  }
  printf("\n");
  close(c);
}
