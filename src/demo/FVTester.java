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


import de.mpi_bremen.mgg.FastaValidator.*;

class FVTester implements FastaValidatorCallback
{
	
	public void header(String header)
	{
		System.out.println(header);
	}
	
	public void commentline(String commentline)
	{
		System.out.println(commentline);
	}
	
	public void seqline(String seqline)
	{
		System.out.println(seqline);
	}
	
	public void eof()
	{
		System.out.println("end of file");
	}

	public static void main(String argv[]) throws Exception
	{
		if (argv.length == 0)
		{
			System.out.println("Usage : java FVTester <inputfile>");
		}
		else {
			try
			{
				FastaValidator FV=new FastaValidator(new FVTester());
				FV.setSequencetype(FastaValidator.Sequencetype.ALL);
				FV.validate(argv[0]);
			}
			catch (java.io.IOException e) //io error
			{
				System.out.println("ERROR: "+e.getMessage());
				e.printStackTrace();
			} 
			catch (InvalidCharacterException e) //invalid character(s) found in fasta file
			{
				System.out.println("ERROR: "+e.getMessage()+" (line: "+e.getLine()+", char: "+e.getCharacter()+")");
			}
			catch (FastaFormatException e) //file not in fasta format
			{
				System.out.println("ERROR: "+e.getMessage()+" (line: "+e.getLine()+")");
			}
			catch (FastaHandlingException e)//error from callback methods; thrown by user
			{
				System.out.println("ERROR: "+e.getMessage());
			}
		}
		System.out.println("");
	} //main

}

