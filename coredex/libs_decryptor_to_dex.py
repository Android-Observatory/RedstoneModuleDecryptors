#!/usr/bin/env python3
#-*- coding: utf-8 -*-


import sys
import os
from ctypes import c_uint


# CONSTANT VALUES

# magic value found on different encrypted ".jar" files
# this commonly is an 8 byte magic and the last byte
# changes in found samples.
# magic_value = "coredex"
magic_value = [99, 111, 114, 101, 100, 101, 120]

if len(sys.argv) != 2:
    print ("USAGE: %s <lib_to_decrypt>" % (sys.argv[0]))
    sys.exit(-1)

library_name = sys.argv[1]

print ("[+] Analyzing %s file" % (library_name))

lib_file = open(library_name, 'rb')
# read all the bytes
lib_bytes = lib_file.read()

magic_string = ""

print ("[+] Checking if file has coredex magic")
for i in range(len(magic_value)):
    if lib_bytes[i] != magic_value[i]:
        print ("[-] Incorrect magic byte at the beginning, not coredex file")
        sys.exit(-1)
    magic_string += chr(magic_value[i])

magic_string += chr(lib_bytes[len(magic_value)])

print ("[*] Magic header found: %s" % (magic_string))


# get size from header
print ("[+] Reading size to decrypt")
size_to_decrypt_bytes = lib_bytes[8:8+4]
size_to_decrypt = int.from_bytes(size_to_decrypt_bytes, byteorder='little', signed=False)

print ("[*] Size to decrypt is: %d" % (size_to_decrypt))

# Bruteforce the key
print ("[+] Bruteforcing key")
key = -1
for k in range(256): # check from 0 to 255 (unsigned byte)
    if lib_bytes[12] ^ k == ord('P') and lib_bytes[13] ^ k == ord('K') and lib_bytes[14] ^ k == 0x3:
        key = k
        break

if key == -1:
    print ("[-] Key not found, sorry :(")
    sys.exit(-1)
else:
    print ("[*] Key found = %d" % (key))

# decrypt with key found
decrypted_lib = []

for i in range(size_to_decrypt):
    decrypted_lib.append(lib_bytes[i + 12] ^ key)

dex_file = library_name.replace(".jar",".apk")

with open(dex_file,'wb') as j:
    j.write(bytearray(decrypted_lib))

print ("[!] File written to: %s" % (dex_file))