import org.apache.commons.cli.*;

import java.io.*;
import java.util.Properties;

public class Main {

    private static Options options;
    private static File config = null;
    private static boolean verbose = true;
    private static int ticks = 100;
    private static Simulation model;

    public static void main(String args[]) {

//        args = new String[] {};

        boolean hasConfig = false;
        String output_filename = "output";
        File output;

        // create the command line parser
        CommandLineParser parser = new DefaultParser();

        // create Options object
        options = new Options();
        OptionGroup optionGroup = new OptionGroup();

        options.addOption( "q", "quiet", false, "work in silence" );
        optionGroup.addOption(new Option( "h",  "help", false,"print this message" ));
        optionGroup.addOption(new Option( "r",  "run", false,"run the simulation with default parameters" ));
        optionGroup.addOption(Option.builder().longOpt("show-defaults")
                .desc("print default parameters")
                .build());
        optionGroup.addOption( Option.builder("c").longOpt( "config" )
                .desc( "use configure file" )
                .hasArg()
                .argName("FILE")
                .build());
        options.addOption(Option.builder("o").longOpt("output")
                .desc("output file")
                .hasArg()
                .argName("FILE")
                .build());
        options.addOption(Option.builder("t").longOpt("ticks")
                .desc("run the model for TICKS times")
                .hasArg()
                .argName("TICKS")
                .type(Number.class)
                .build());
        options.addOption(Option.builder().longOpt("with-tax")
                .desc("enable tax mode")
                .build());
        options.addOptionGroup(optionGroup);

        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );

            if( args.length == 0 || line.hasOption( "help" ) ) {
                printHelp();
                return;
            }

            if (line.hasOption("show-defaults")) {
                printDefaultParams();
                return;
            }

            if (line.hasOption("quiet")) {
                System.out.println("Verbose Mode On\n");
                verbose = true;
            }

            if (line.hasOption("config")) {
                String file = line.getOptionValue("config");
                config = new File(file);
                if (config.exists() && config.isFile() && parseConfig())
                    hasConfig = true;
                else throw new ParseException("Invalid config file.");
            }

            if( line.hasOption( "ticks" ) ) {
                ticks = ((Number) line.getParsedOptionValue("ticks")).intValue();
            }

            if (line.hasOption("with-tax")) {
                Params.TAX_MODE = true;
            }

            if (line.hasOption("output")) {
                output_filename = line.getOptionValue("output");
            } else if (hasConfig) {
                output_filename = config.getName().replaceAll("[.][^.]+$", "");
            }

            // create the output file
            output = new File(output_filename+".csv");
            int suffix = 0;
            while (output.exists()) {
                suffix++;
                output = new File(output_filename + "_" + suffix + ".csv");
            }

            // Create a Simulation
            model = new Simulation();

            if (verbose) {
                printDefaultParams();
                System.out.println("\nStarting simulation for " + ticks + " times");
            }

            // create output file
//            output.createNewFile();
            PrintStream ps = new PrintStream(output);
            ps.println("Tick, Rich, Middle, Poor, Gini_Idx");

            // run simulation
            for (int i = 0; i < ticks; ++i) {
                model.go();

                if (verbose) {
                    System.out.print("Tick: " + (i + 1) + ", ");
                    System.out.print("Rich: " + model.getCount_rich() + ", ");
                    System.out.print("Middle: " + model.getCount_middle() + ", ");
                    System.out.print("Poor: " + model.getCount_poor() + ", ");
                    System.out.println("Gini-index: " + model.getGini_index());
                }

                ps.print((i + 1) + ", ");
                ps.print(model.getCount_rich() + ", ");
                ps.print(model.getCount_middle() + ", ");
                ps.print(model.getCount_poor() + ", ");
                ps.println(model.getGini_index());

            }



        }
        catch( ParseException exp ) {
            System.out.println(exp.getMessage());
            System.out.println("\nTry `-h` or `--help` for more options");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create output file.");
        }

    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("wealthdist [options]", options );
    }

    private static void printDefaultParams() {
        System.out.println("Parameters: ");
        System.out.println("TICKS = " + ticks);
        System.out.println("HEIGHT = " + Params.HEIGHT);
        System.out.println("WIDTH = " + Params.WIDTH);
        System.out.println("MAX_GRAIN = " + Params.MAX_GRAIN);
        System.out.println("PERCENT_BEST_LAND = " + Params.PERCENT_BEST_LAND);
        System.out.println("NUM_PEOPLE = " + Params.NUM_PEOPLE);
        System.out.println("LIFE_EXPECTANCY_MIN = " + Params.LIFE_EXPECTANCY_MIN);
        System.out.println("LIFE_EXPECTANCY_MAX = " + Params.LIFE_EXPECTANCY_MAX);
        System.out.println("METABOLISM_MAX = " + Params.METABOLISM_MAX);
        System.out.println("MAX_VISION = " + Params.MAX_VISION);
        System.out.println("GRAIN_GROWTH_INTERVAL = " + Params.GRAIN_GROWTH_INTERVAL);
        System.out.println("TAX_MODE = " + Params.TAX_MODE);
        System.out.println("TAX_RATE_RICH = " + Params.TAX_RATE_RICH);
        System.out.println("TAX_RATE_MIDDLE = " + Params.TAX_RATE_MIDDLE);
        System.out.println("TAX_RATE_POOR = " + Params.TAX_RATE_POOR);
    }

    private static boolean parseConfig() {
        if (verbose)
            System.out.println("Parsing Config file: " + config.getName());
        boolean success = true;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(config));
            if (properties.getProperty("TICKS") != null)
                ticks = Integer.parseInt(properties.getProperty("TICKS"));
            if (properties.getProperty("HEIGHT") != null)
                Params.HEIGHT = Integer.parseInt(properties.getProperty("HEIGHT"));
            if (properties.getProperty("WIDTH") != null)
                Params.WIDTH = Integer.parseInt(properties.getProperty("WIDTH"));
            if (properties.getProperty("MAX_GRAIN") != null)
                Params.MAX_GRAIN = Integer.parseInt(properties.getProperty("MAX_GRAIN"));
            if (properties.getProperty("PERCENT_BEST_LAND") != null)
                Params.PERCENT_BEST_LAND = Double.parseDouble(properties.getProperty("PERCENT_BEST_LAND"));
            if (properties.getProperty("NUM_PEOPLE") != null)
                Params.NUM_PEOPLE = Integer.parseInt(properties.getProperty("NUM_PEOPLE"));
            if (properties.getProperty("LIFE_EXPECTANCY_MIN") != null)
                Params.LIFE_EXPECTANCY_MIN = Integer.parseInt(properties.getProperty("LIFE_EXPECTANCY_MIN"));
            if (properties.getProperty("LIFE_EXPECTANCY_MAX") != null)
                Params.LIFE_EXPECTANCY_MAX = Integer.parseInt(properties.getProperty("LIFE_EXPECTANCY_MAX"));
            if (properties.getProperty("METABOLISM_MAX") != null)
                Params.METABOLISM_MAX = Integer.parseInt(properties.getProperty("METABOLISM_MAX"));
            if (properties.getProperty("MAX_VISION") != null)
                Params.MAX_VISION = Integer.parseInt(properties.getProperty("MAX_VISION"));
            if (properties.getProperty("GRAIN_GROWTH_INTERVAL") != null)
                Params.GRAIN_GROWTH_INTERVAL = Integer.parseInt(properties.getProperty("GRAIN_GROWTH_INTERVAL"));
            if (properties.getProperty("NUM_GRAIN_GROWN") != null)
                Params.NUM_GRAIN_GROWN = Integer.parseInt(properties.getProperty("NUM_GRAIN_GROWN"));
            if (properties.getProperty("TAX_MODE") != null)
                Params.TAX_MODE = Boolean.parseBoolean(properties.getProperty("TAX_MODE"));
            if (properties.getProperty("TAX_RATE_RICH") != null)
                Params.TAX_RATE_RICH = Double.parseDouble(properties.getProperty("TAX_RATE_RICH"));
            if (properties.getProperty("TAX_RATE_MIDDLE") != null)
                Params.TAX_RATE_MIDDLE = Double.parseDouble(properties.getProperty("TAX_RATE_MIDDLE"));
            if (properties.getProperty("TAX_RATE_POOR") != null)
                Params.TAX_RATE_POOR = Double.parseDouble(properties.getProperty("TAX_RATE_POOR"));

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }
}
