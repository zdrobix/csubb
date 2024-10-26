#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

void deservire_client(int c) {
  // deservirea clientului
  uint16_t a, b, suma;
  recv(c, &a, sizeof(a), MSG_WAITALL);
  recv(c, &b, sizeof(b), MSG_WAITALL);
  a = ntohs(a);
  b = ntohs(b);
  suma = a + b;
  suma = htons(suma);
  send(c, &suma, sizeof(suma), 0);
  close(c);
  // sfarsitul deservirii clientului;
}
 
int main(int argc, char ** argv) {
  int s;
  struct sockaddr_in server, client;
  int c;
  socklen_t l = sizeof(client);
  
  s = socket(AF_INET, SOCK_STREAM, 0);
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
 
  listen(s, 5);
  
  l = sizeof(client);
  memset(&client, 0, sizeof(client));
  
  while (1) {
    c = accept(s, (struct sockaddr *) &client, &l);
    printf("S-a conectat un client.\n");
    if (fork() == 0) { // fiu
      deservire_client(c);
      return 0;
    }
    // se executa doar in parinte pentru ca fiul se termina mai sus din cauza exit-ului    
  }
}
