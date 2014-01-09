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
 * General exception class, which is thrown when an error occurs during the parsing/validation process 
 * @author      Jost Waldmann
 *
 */
public class FastaValidatorException extends Exception
{

	/**
	 * Constructs an empty FastaValidatorException.
	 *
	 * Behaves like Exception().
	 */
	public FastaValidatorException()
	{
	}
	
	/**
	 * Constructs a FastaValidatorException with errormessage.
	 *
	 * @param  s	the errormessage 
	 */
	public FastaValidatorException(String s)
	{
		super(s);	
	}

}
