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
 * Representation of a Token (internal - from lexer) 
 * @author      Jost Waldmann
 *
 */
class Yytoken {
	public int index;
	public String text;
	public int line;
	
	Yytoken (int index, String text, int line) {
		this.index = index;
		this.text = text;
		this.line = line;
	}
	
	public String toString() {
	return	"Text   : "+text+
			"\nindex : "+index+
			"\nline  : "+line;
	}
}

