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
 * Interface defining necessary FastaValidator-events. 
 * This interface needs to be implemented to react on events occuring during the 
 * validation/parsing process. It is passed to the validator-class as a parameter in the constructor.
 * If no reaction is desired NULL can be passed.
 * @author      Jost Waldmann
 */
public interface FastaValidatorCallback
{

	/**
	 * This method is invoked when a headerline occured.
	 *
	 * @param  header	the parsed headerline (including ">") 
	 */
	public void header(String header) throws FastaHandlingException;
	
	/**
	 * This method is invoked when a commentline occured.
	 *
	 * @param  commentline	the parsed commentline (including ";") 
	 */
	public void commentline(String commentline) throws FastaHandlingException;
	
	/**
	 * This method is invoked when a sequenceline occured.
	 *
	 * Please note that this method is called for each line.
	 * Normally sequences are distributed over multiple lines.
	 *
	 * @param  seqline	the parsed sequenceline 
	 */
	public void seqline(String seqline) throws FastaHandlingException;
	
	/**
	 * This method is invoked at the end of file.
	 */
	public void eof() throws FastaHandlingException;

}
