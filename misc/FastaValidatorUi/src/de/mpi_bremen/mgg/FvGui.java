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
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;
import javax.swing.JComboBox;

/**
 * The graphical user interface part of the application.
 *
 * @author Jost Waldmann
 *
 */
public class FvGui extends JFrame implements FastaValidatorCallback {

    //selected sequence type
    private FastaValidator.Sequencetype mSequencetype;
    //message at the bottom of the window
    private JLabel mResultInfo;
    //dropped filename label at the top of the window
    private JLabel mFilename;
    //messagetext at the bottom of the window
    private String message;
    //combobox for sequence type selection
    private JComboBox mSeqtypeBox;
    //available sequence type names
    final static String[] seqtypeStrings = {"ALL SEQUENCES", "DNA SEQUENCES", "RNA SEQUENCES", "PROTEIN SEQUENCES"};
    //available sequence types
    final static FastaValidator.Sequencetype[] seqtype = {FastaValidator.Sequencetype.ALL, FastaValidator.Sequencetype.DNA, FastaValidator.Sequencetype.RNA, FastaValidator.Sequencetype.PROTEIN};

    /**
     * Constructs the command line interface validation class.
     *
     */
    public FvGui() {
        super("FastaValidator GUI");

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        //add combobox for sequence type selection
        mSeqtypeBox = new JComboBox(seqtypeStrings);
        mSeqtypeBox.setSelectedIndex(0);
        cp.add(mSeqtypeBox, BorderLayout.PAGE_START);

        Container mMainContent = new Container();
        mMainContent.setLayout(new BorderLayout());

        //add filename text-placeholder
        mFilename = new JLabel("-", JLabel.CENTER);
        mMainContent.add(mFilename, BorderLayout.PAGE_START);

        //add drop-fasta text
        JLabel centerText = new JLabel("Drop your FASTA file here.", JLabel.CENTER);
        centerText.setForeground(Color.GRAY);
        mMainContent.add(centerText, BorderLayout.CENTER);

        //add validation result text-placeholder
        mResultInfo = new JLabel("-", JLabel.CENTER);
        mMainContent.add(mResultInfo, BorderLayout.SOUTH);

        //enable intuitive quit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set window size constraints
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension minD = new Dimension();
        //minimum width/height is max( 10% of screen, 200 pixels )
        minD.setSize(Math.max((int) (d.width / 10), 200), Math.max((int) (d.height / 10), 200));
        setSize(minD);
        setMinimumSize(minD);

        //add drag functionality
        DropTarget dropTarget = new DropTarget(this, createDropTargetListener(this));

        message = "";

        //add main content and make window visible
        cp.add(mMainContent, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Implements drag and drop functionality. Triggers validation when a file
     * is dropped.
     *
     */
    private DropTargetListener createDropTargetListener(final JFrame window) {
        return new DropTargetListener() {
            public void dragEnter(DropTargetDragEvent e) {
            }

            public void dragExit(DropTargetEvent e) {
            }

            public void dragOver(DropTargetDragEvent e) {
            }

            public void drop(DropTargetDropEvent e) {
                try {
                    Transferable tr = e.getTransferable();
                    DataFlavor[] flavors = tr.getTransferDataFlavors();
                    for (int i = 0; i < flavors.length; i++) {
                        if (flavors[i].isFlavorJavaFileListType()) {
                            // accept (just temporary too see whats in there)
                            e.acceptDrop(e.getDropAction());
                            List files = (List) tr.getTransferData(flavors[i]);

                            //update/prepare gui before validation
                            message = "";
                            mSeqtypeBox.setEnabled(false);
                            mResultInfo.setText("validating...");
                            mFilename.setText(files.get(0).toString());
                            //trigger validation
                            if (validate(files.get(0).toString())) {
                                //successful validation
                                mResultInfo.setText("VALID");
                                getContentPane().setBackground(Color.GREEN);
                                window.setBackground(Color.GREEN);
                            } else {
                                //non successful validation
                                mResultInfo.setText("INVALID - (" + message + ")");
                                getContentPane().setBackground(Color.RED);
                                window.setBackground(Color.RED);
                            }
                            e.dropComplete(true);

                            //update gui
                            mSeqtypeBox.setEnabled(true);
                            pack();

                            return;
                        }
                    }
                } catch (Exception ex) {
                	getContentPane().setBackground(Color.YELLOW);
                	mResultInfo.setText("ERROR: " + ex.getMessage());
                    ex.printStackTrace();
                }
                //a problem occured
                e.rejectDrop();

                //update gui
                mSeqtypeBox.setEnabled(true);
                pack();
            }

            public void dropActionChanged(DropTargetDragEvent e) {
            }
        };
    } //createDropTargetLsitener()

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
     * @return validity of the input data
     */
    protected boolean validate(String mInput) {
        //create a FastaValidator instance
        FastaValidator FV = new FastaValidator(this);

        //set sequence type from combobox
        FV.setSequencetype(seqtype[mSeqtypeBox.getSelectedIndex()]);

        //do validation
        try {
            FV.validate(mInput);
        } catch (java.io.IOException e) //io error
        {
            message = "ERROR: " + e.getMessage();
            System.err.println("ERROR: " + message);
            //e.printStackTrace();
            return false;
        } catch (InvalidCharacterException e) //invalid character(s) found in fasta file
        {
            message = e.getMessage();
            System.out.println("WARNING: " + message);
            return false;
        } catch (FastaFormatException e) //file not in fasta format
        {
            message = e.getMessage();
            System.out.println("WARNING: " + message);
            return false;
        } catch (FastaHandlingException e)//error from callback methods; thrown by user
        {
            message = e.getMessage();
            System.out.println("WARNING: " + message);
            return false;
        } catch (FastaValidatorException e)//error from callback methods; thrown by user
        {
            message = e.getMessage();
            System.out.println("WARNING: " + message);
            return false;
        }
        return true;
    }
}
