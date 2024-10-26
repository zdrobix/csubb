import socket as so
import sys

SIZE = 1024
encoding = 'ascii'

port = int(sys.argv[1])
hostIP = sys.argv[2]

sk = so.socket(so.AF_INET, so.SOCK_STREAM)
sk.connect((hostIP, port))

sir = input("Enter a string: ")
sk.sendall(sir.encode(encoding))

sir_reversed = sk.recv(SIZE)

print(sir_reversed)
sk.close()
