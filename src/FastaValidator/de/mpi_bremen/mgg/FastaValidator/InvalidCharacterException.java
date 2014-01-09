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
 * Specialization of FastaValidatorException, which is thrown when an invalid character occurs 
 * @author      Jost Waldmann
 *
 */
public class InvalidCharacterException extends FastaValidatorException
{
	
	private int _line;
	private String _character;
	
	/**
	 * Returns the line number of the error.
	 */
	public int getLine()
	{
		return _line;
	}
	
	/**
	 * Returns the invalid character which caused the error.
	 */
	public String getCharacter()
	{
		return _character;
	}
	
	/**
	 * Constructs an InvalidCharacterException.
	 *
	 * Sets internal variables (_line and _character).
	 *
	 * @param  s	the errormessage 
	 * @param  character	the character which caused the error
	 * @param  line		the line number where the error occured (starting with 0)
	 */
	public InvalidCharacterException(String s, String character, int line)
	{
		super(s);
		_line=line;
		_character=character;
	}

}
