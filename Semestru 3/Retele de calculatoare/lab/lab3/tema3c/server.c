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
  int s;
  struct sockaddr_in server, client;
  
  socklen_t l = sizeof(client);
  
  char sirCitit[MAX_SIZE], caracterCitit;
  int pozitiiCaracter[MAX_SIZE];
  
  s = socket(AF_INET, SOCK_DGRAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(atoi(argv[1]));
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
  
  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }
 
  memset(&pozitiiCaracter, -1, sizeof(pozitiiCaracter));
  
  if (recvfrom(s, sirCitit, sizeof(sirCitit), 0, (struct sockaddr *) &client, &l) < 0) {
	close(s);
	return 1;
  }
  if (recvfrom(s, &caracterCitit, 1, 0, (struct sockaddr *) &client, &l) < 0) {
	  close(s);
	  return 1;
  }

  int index = 0;
  for ( int i = 0; i < MAX_SIZE; i ++ ) 
  {
	if (sirCitit[i] == caracterCitit) {
		pozitiiCaracter[index] = htons(i);
		index ++;
	}
  }
  index = htons(index);
  if (sendto(s, pozitiiCaracter, sizeof(pozitiiCaracter), 0, (struct sockaddr *) &client, l) < 0) {
	  close(s);
	  return 1;
  }
  if (sendto(s, &index, sizeof(index), 0, (struct sockaddr *) &client, l) < 0) {
	  close(s);
	  return 1;
  }
    
  

  close(s);
}
