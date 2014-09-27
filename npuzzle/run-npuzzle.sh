#!/bin/sh
pwd=`pwd`
cd bin/
java -Xmx512M npuzzle.BusquedaArbol
cd "${pwd}"

