#!/bin/bash

cur_dir="$( cd "$( dirname "$0" )" && pwd )"
echo "$cur_dir"

function expeirement(){
    pwd="$cur_dir"
    configdir="$cur_dir/experiment_params"
    outputdir="$cur_dir/outputs"

    rm -rf "$outputdir"
    mkdir "$outputdir"

    for file in "$configdir"/*.config
    do
        echo "$file"
        java -jar wealth-distribution.jar -c "$file"
        mv *.csv "$outputdir"
    done
}

expeirement
exit 0
