#!/usr/bin/env python3
import socket as so
import sys

def main():

    SIZE = 1024
    port = int(sys.argv[1])
    encoding = 'ascii'
    hostIP = sys.argv[2]

    sk =  so.socket(so.AF_INET, so.SOCK_STREAM)
    sk.connect((hostIP, port))

    sir = input("Enter a string: ")
    sk.sendall(sir.encode(encoding))
    
    sir_reversed = sk.recv(SIZE)
    print(sir_reversed.decode(encoding).rstrip('\x00'))
    sk.close()

if __name__ == "__main__":
    main()
