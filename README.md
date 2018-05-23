# Wealth Distribution

A model simulates the distribution of wealth.
Derived from the Wealth Distribution model in NetLogo

## Build with Maven
Build and run integration tests as follows:

    $ mvn package

This will compile the project and pack all the packages.
The built artifact `wealthdist-jar-with-dependencies.jar` will be generated in `target` directory.

## Build with Javac
Though it's recommended to build with maven, it's still able to compile without maven.

    // Make sure you have permission to execute the script:
    $ sudo chmod +x ./compile.sh

    // then execute the script
    $ ./compile.sh

## Run Experiments in Batch
All configuration files stored in the `experiment_params` directory can be fed to the jar with the experiment.sh script.

You can run the script to execute all the experiments in a batch,
*please ensure you have correctly compiled the project named as `wealth-distribution.jar`*

    // Make sure you have permission to execute the script:
    $ sudo chmod +x ./experiment.sh

    // then execute the script
    $ ./experiment.sh

All generated output files will be stored in the outputs directory.

## Usage
Simply Start with default model parameters:

    $ java -jar wealthdist-jar-with-dependencies.jar -r

Specify how long simulation runs with `-t` option:

    $ java -jar wealthdist-jar-with-dependencies.jar -r -t 2000

You can also specify a config file to pass parameters:

    $ java -jar wealthdist-jar-with-dependencies.jar -c params.config

For config file format and available parameters, see example config file `param.config`.

### Output
The model produces a csv file consists of the statistics of each ticks.

By default, the output filename is generated automatically,
or you can provide a custom one using `-o` option.

CLI output is on by default unless provided with `-q` option.

### Extension

Enable extension feature (incoming tax) with `--with-tax` option:

    $ java -jar wealthdist-jar-with-dependencies.jar -r --with-tax

Or use config file to manipulate detailed tax rate:

    # Income tax rate settings
    TAX_MODE = true
    TAX_RATE_RICH = 0.3
    TAX_RATE_MIDDLE = 0.1
    TAX_RATE_POOR = 0.0

Use negative tax rate to represent subsidies.

## Credits and References

* Wilensky, U. (1998). NetLogo Wealth Distribution model.
http://ccl.northwestern.edu/netlogo/models/WealthDistribution.
Center for Connected Learning and Computer-Based Modeling, Northwestern University, Evanston, IL.
* Wilensky, U. (1999). NetLogo. http://ccl.northwestern.edu/netlogo/.
Center for Connected Learning and Computer-Based Modeling, Northwestern University, Evanston, IL.
