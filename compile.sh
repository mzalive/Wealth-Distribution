#!/bin/bash

cur_dir="$( cd "$( dirname "$0" )" && pwd )"

echo "$cur_dir"

function compile(){
    makedir="$cur_dir"
    srcdir="$cur_dir/src"
    libdir="$cur_dir/lib"
    classdir="$cur_dir/classes"

    rm -rf "$libdir"
    mkdir "$libdir"

    rm -rf tmp
    mkdir tmp
    cd tmp
    wget http://mirror.intergrid.com.au/apache/commons/cli/binaries/commons-cli-1.4-bin.tar.gz
    tar -xvzf commons-cli-1.4-bin.tar.gz
    mv commons-cli-1.4/commons-cli-1.4.jar "$libdir"
    cd ..

    rm -rf "$srcdir/sources.list"
    find "$srcdir" -name "*.java" > "$srcdir/sources.list"
    cat  "$srcdir/sources.list"

    rm -rf "$classdir"
    mkdir "$classdir"

    javac -d "$classdir" -encoding utf-8 -cp .:"$libdir/commons-cli-1.4.jar" -g -sourcepath "$srcdir" @"$srcdir/sources.list"

    cd "$classdir"
    jar -cvfm "$makedir/wealth-distribution.jar" "$makedir/MANIFEST.MF" *


    chmod a+x "$makedir/wealth-distribution.jar"

    rm -rf tmp
    rm -rf "$classdir"
    rm "$srcdir/sources.list"

}

compile
exit 0
