// Copyright (C) 2014 Jost Waldmann
//
// This file is part of the FastaValidator Java Library. This library is free
// software; you can redistribute it and/or modify it under the
// terms of the GNU Lesser General Public License in version 3 (LGPL3) as 
// published by the Free Software Foundation.

// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// Lesser General Public License for more details.

// You should have received a copy of the Lesser General Public License along
// with this library; see the file COPYING.  If not, write to the Free
// Software Foundation, 59 Temple Place - Suite 330, Boston, MA 02111-1307,
// USA.

// If you have any questions, send an email to megx@mpi-bremen.de.

package de.mpi_bremen.mgg;

import de.mpi_bremen.mgg.FastaValidator.FastaValidator;
import javax.swing.SwingUtilities;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * The FastaValidatorUi application - main class of the package. Extracts
 * command line arguments and triggers validation, dependent on the selected
 * mode (command line or gui).
 *
 * @author Jost Waldmann
 *
 */
public class FastaValidatorUi {

    public static CommandLine cmdline;

    private static FastaValidator.Sequencetype getSeqtype(String seqtype) {
        if (seqtype.length() > 0) {
            if ("ALL".equalsIgnoreCase(seqtype))
                return FastaValidator.Sequencetype.ALL;
            if ("DNA".equalsIgnoreCase(seqtype))
                return FastaValidator.Sequencetype.DNA;
            if ("RNA".equalsIgnoreCase(seqtype))
                return FastaValidator.Sequencetype.RNA;
            if ("PROTEIN".equalsIgnoreCase(seqtype))
                return FastaValidator.Sequencetype.PROTEIN;
        }
        System.err.println("WARNING: unknown sequencetype given - fallback to sequence type ALL.");
        return FastaValidator.Sequencetype.ALL;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create Options object
        Options options = new Options();
        // add verbose option
        options.addOption("v", "verbose", false, "verbose mode");
        // add inputfile option
        options.addOption("f", "file", true, "FASTA-formatted input file");
        // add help option
        options.addOption("h", "help", false, "print this help message");
        // add sequencetype option
        options.addOption("t", "seqtype", true, "sequence type (allowed values: all|dna|rna|protein)");
        // add gui-mode option
        options.addOption("nogui", false, "start in non-interactive mode");

        //create the cmdline-parser	
        GnuParser parser = new GnuParser();

        // parse the command line arguments
        try {
            //get cmdline arguments
            cmdline = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println(exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("FastaValidatorUi", options, true);
            System.exit(1); //indicate error to external environment
        }

        if (cmdline.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("FastaValidatorUi", options, true);
            System.exit(0); //indicate no errors to external environment
        }


        //which mode? (gui or cmdline)
        if (cmdline.hasOption("nogui")) { //cmdline-mode

            //create new cmdline-gui
            FvCmdline cmdlinegui = new FvCmdline();

            //set verbosity
            cmdlinegui.setVerbose(cmdline.hasOption("v"));

            //handle input file
            if (cmdline.hasOption("f")) {
                cmdlinegui.setInput(cmdline.getOptionValue("f"));
            }

            //set sequencetype
            if (cmdline.hasOption("t")){
                cmdlinegui.setSequencetype(getSeqtype(cmdline.getOptionValue("t")));
            }else{
                cmdlinegui.setSequencetype(FastaValidator.Sequencetype.ALL);
            }

            //trigger validation and exit with exitcode
            int exitcode=cmdlinegui.validate().getValue();
            System.exit(exitcode);

        } else { //gui-mode
            //start gui in its own thread
            SwingUtilities.invokeLater(
                    new Runnable() {
                public void run() {
                    FvGui w = new FvGui();
                }
            });
        }
    }
}
