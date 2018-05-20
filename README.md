# Wealth Distribution

A model simulates the distribution of wealth.

## Build with Maven
Build and run integration tests as follows:

    $ mvn package

This will compile the project and pack all the packages. The built artifact `wealthdist-jar-with-dependencies.jar` will be generated in `target` directory.

## Usage
Simply Start with default model parameters:

    $ java -jar wealthdist-jar-with-dependencies.jar -r
    
Specify how long simulation runs with `-t` arg:

    $ java -jar wealthdist-jar-with-dependencies.jar -r -t 2000
    
Enable tax option with `--with-tax` arg:

    $ java -jar wealthdist-jar-with-dependencies.jar -r --with-tax
    
You can also specify a config file to pass parameters:

    $ java -jar wealthdist-jar-with-dependencies.jar -c params.config
    
For config file format and available parameters, see example config file `param.config`.

## Credits and References

* Wilensky, U. (1998). NetLogo Wealth Distribution model. 
http://ccl.northwestern.edu/netlogo/models/WealthDistribution. 
Center for Connected Learning and Computer-Based Modeling, Northwestern University, Evanston, IL.
* Wilensky, U. (1999). NetLogo. http://ccl.northwestern.edu/netlogo/. Center for Connected Learning and Computer-Based Modeling, Northwestern University, Evanston, IL.
