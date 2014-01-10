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

import de.mpi_bremen.mgg.FastaValidator.FastaFormatException;
import de.mpi_bremen.mgg.FastaValidator.FastaHandlingException;
import de.mpi_bremen.mgg.FastaValidator.FastaValidator;
import de.mpi_bremen.mgg.FastaValidator.FastaValidatorCallback;
import de.mpi_bremen.mgg.FastaValidator.FastaValidatorException;
import de.mpi_bremen.mgg.FastaValidator.InvalidCharacterException;
import java.io.InputStreamReader;

/**
 * The command line user interface part of the application.
 *
 * @author Jost Waldmann
 *
 */
public class FvCmdline implements FastaValidatorCallback {

    /**
     * Represents exit values.
     */
    public static enum EXITCODE {

        VALID(0),
        UNKNOWN_ERROR(1),
        IO_ERROR(2),
        INVALID_CHAR(3),
        INVALID_FORMAT(4),
        HANDLING_ERROR(5),
        USER_ERROR(6);
        private final int value;

        private EXITCODE(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }
    //input filename
    private String mInput;
    //sequence type
    private FastaValidator.Sequencetype mSequencetype;
    //verbosity
    private boolean mVerbose;

    /**
     * Constructs the command line interface validation class.
     *
     */
    public FvCmdline() {
        mInput = "";
        mSequencetype = FastaValidator.Sequencetype.ALL;
        mVerbose = false;
    }

    /**
     * Header function of the callback interface.
     *
     */
    public void header(String header) {
        //System.out.println(header);
    }

    /**
     * Commentline function of the callback interface.
     *
     */
    public void commentline(String commentline) {
        //System.out.println(commentline);
    }

    /**
     * Seqline function of the callback interface.
     *
     */
    public void seqline(String seqline) {
        //System.out.println(seqline);
    }

    /**
     * Eof function of the callback interface.
     *
     */
    public void eof() {
        //System.out.println("end of file");
    }

    /**
     * Performs the validation of the input data.
     *
     * @return exit code
     */
    public EXITCODE validate() {
        //create a FastaValidator instance
        FastaValidator FV = new FastaValidator(this);

        //set sequence type 
        FV.setSequencetype(mSequencetype);

        //do validation
        try {
            if (mInput.length() == 0) //note: isEmpty() is the better choice but is not available in java < 1.6
            { //read from stdin
                FV.validate(new InputStreamReader(System.in));
            } else { //read from file
                FV.validate(mInput);
            }
        } catch (java.io.IOException e) //io error
        {
            if (mVerbose) {
                System.err.println("ERROR: " + e.getMessage());
            }
            return EXITCODE.IO_ERROR;
        } catch (InvalidCharacterException e) //invalid character(s) found in fasta file
        {
            if (mVerbose) {
                System.out.println("WARNING: " + e.getMessage());
            }
            return EXITCODE.INVALID_CHAR;
        } catch (FastaFormatException e) //file not in fasta format
        {
            if (mVerbose) {
                System.out.println("WARNING: " + e.getMessage());
            }
            return EXITCODE.INVALID_FORMAT;
        } catch (FastaHandlingException e)//error from callback methods; thrown by user
        {
            if (mVerbose) {
                System.out.println("WARNING: " + e.getMessage());
            }
            return EXITCODE.HANDLING_ERROR;
        } catch (FastaValidatorException e)//error from callback methods; thrown by user
        {
            if (mVerbose) {
                System.out.println("WARNING: " + e.getMessage());
            }
            return EXITCODE.USER_ERROR;
        } catch (Exception e) //all other errors
        {
            System.err.println("ERROR: An unknown error occured.");
            return EXITCODE.UNKNOWN_ERROR;
        }
        return EXITCODE.VALID;
    }

    /**
     * Sets the input filename to parse.
     *
     * @param newinput	filename
     */
    public void setInput(String newinput) {
        mInput = newinput;
    }

    /**
     * Sets the sequence type to parse.
     *
     * @param seqtype	selected sequencetype
     */
    public void setSequencetype(FastaValidator.Sequencetype seqtype) {
        mSequencetype = seqtype;
    }

    /**
     * Enables/Disables the verbosity.
     *
     * @param verbose	enabled/disabled verbosity
     */
    public void setVerbose(boolean verbose) {
        mVerbose = verbose;
    }
}
