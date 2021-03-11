# RedstoneModuleDecryptors

Various decryptors used in the analysis of Redstone FOTA Apps. Two kind of encryption were found during the analysis of this fota.

* coredex: this was a simple xor encryption algorithm, for this we've used a bruteforcing approach, so if we are able to get an APK header with a key we use that to decrypt the whole file (found on libcore.jar and libcore64.jar files).
* 1qaz2wsx: more complex encryption which used **PBEWITHMD5ANDDES**, this time the code is mostly obtained from the analyzed file (found on impl_default_4.0.14.jar file, not present in previous versions).

These files were decrypted and loaded in runtime so statically it wasn't possible to discover if they were malicious or not.

How to run the decryptors:

* coredex

```console
$ python3 libs_decryptor_to_dex.py <input_file>
```

* 1qaz2wsx

```console
$ java -jar Decryptor <input_file> <output_file>
```