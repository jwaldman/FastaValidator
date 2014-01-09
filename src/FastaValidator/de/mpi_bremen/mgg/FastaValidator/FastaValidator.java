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


package de.mpi_bremen.mgg.FastaValidator;

/**
 * The FastaValidator itself - main class of the package. Here everything is glued together. 
 * @author      Jost Waldmann
 *
 */
public class FastaValidator
{
	/**
	 * Represents parsable sequencetypes.
	 */
	public static enum Sequencetype {ALL,DNA,RNA,PROTEIN};
	
	private FastaValidatorCallback _callback;
	private Sequencetype _seqtype;
	
	/**
	 * Constructs a FastaValidator and binds event methods.
	 *
	 * @param callback	event method implementation
	 */	
	public FastaValidator(FastaValidatorCallback callback)
	{
		_callback=callback;
		_seqtype=Sequencetype.ALL;
	}
	
	/**
	 * Sets the sequencetype to parse.
	 *
	 * @param seqtype	selected sequencetype
	 */	
	public void setSequencetype(Sequencetype seqtype)
	{
		_seqtype=seqtype;
	}
	
	
	
	/**
	 * Returns current selected sequencetype.
	 */	
	public Sequencetype getSequencetype()
	{
		return _seqtype;
	}
	
	
	/**
	 * Validates/Parses a fasta-formatted file.
	 *
	 * @param  filename	the file to be parsed 
	 */
	public void validate(String filename) throws java.io.FileNotFoundException, java.io.IOException, FastaValidatorException, FastaHandlingException
	{
		java.io.FileReader rdr=new java.io.FileReader(filename);
		validate(rdr);
		rdr.close();
		rdr=null;
	}
	
	
	
	/**
	 * Validates/Parses a fasta-formatted source.
	 *
	 * @param  reader	the source to be parsed 
	 */    
	public void validate(java.io.Reader reader) throws java.io.IOException, FastaValidatorException, FastaHandlingException
	{
	    switch (_seqtype){
	    case PROTEIN:
		validate(new YylexAA(reader));
		break;
	    case DNA:
		validate(new YylexDNA(reader));
		break;
	    case RNA:
		validate(new YylexRNA(reader));
		break;
	    default:
		validate(new YylexUniversal(reader));
		break;
	    }
	}
	
	
	protected void validate(FastaValidatorLexer lexer) throws java.io.IOException, FastaValidatorException, FastaHandlingException
	{
		boolean firsttok=true;
		int idx=0;
		Yytoken token=new Yytoken(0,"",-1);;
		do
		{
			token=lexer.yylex();
			/*from lexer:  EOF=0, HEADER=1, COMMENTLINE=2, SEQLINE=3, LINETERMINATOR=LT=4*/
			idx=(idx%10)*10+token.index;
			//System.out.println(idx);
			switch (idx){
			case 14: /*HEADER+LT*/ idx=1; break;
			case 24: /*COMMENTLINE+LT*/ idx=2; break;
			case 34: /*SEQLINE+LT*/ idx=3; break;
			case 12: /*HEADER+COMMENTLINE*/
			case 22: /*COMMENTLINE+COMMENTLINE*/
				if (null!=_callback){_callback.commentline(token.text);}
				break;
			case 13: /*HEADER+SEQLINE*/
			case 23: /*COMMENTLINE+SEQLINE*/
			case 33: /*SEQLINE+SEQLINE*/
				if (null!=_callback){_callback.seqline(token.text);}
				break;
			case 30: /*SEQLINE+EOF*/
				break;
			case 31: /*SEQLINE+HEADER*/
				if (null!=_callback){_callback.header(token.text);}
				break;
			default:
				if (!firsttok)
				{
				//throw new FastaFormatException("Error in FASTA format at line "+token.line+".");
					throw new FastaFormatException("Error in FASTA format at line "+token.line+".", token.line);
				}
				else
				{
					if ((token.index==1)&&(null!=_callback))
					{
						_callback.header(token.text);
					}
				firsttok=false;
				}
			}
		} while (token.index!=0);
		if (null!=_callback)
		{
			_callback.eof();
		}
		token=null;
	}
	
}
