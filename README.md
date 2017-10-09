# Compilation & Installation

```
cd bsdconv
gradle assemble
sudo bin/elasticsearch-plugin install file://`readlink -f build/distributions/bsdconv-${VERSION}.zip`
```