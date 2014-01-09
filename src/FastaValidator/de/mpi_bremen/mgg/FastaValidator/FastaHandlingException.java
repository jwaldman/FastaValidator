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
 * Thrown by implementations of the FastaValidatorCallback to indicate that 
 * their handling of the parsed data failed. Applications can subclass this
 * exception when to indicate application-specific problems during the parsing
 * process.
 * @author		Jan Gerken
 */
public class FastaHandlingException extends Exception {

	/**
	 * Creates a new instance of this exception with <code>null</code> as its 
	 * detail message and cause.
	 */
	public FastaHandlingException() {
		super();
	}
	
	/**
	 * Creates a new instance of this exception with the given detail message
	 * but no cause.
	 *
	 * @param detailMessage		the detail message
	 */
	public FastaHandlingException(final String detailMessage) {
		super(detailMessage);
	}
	
	/**
	 * Creates a new instance of this exception with the given cause and a 
	 * detail message containing the string representation of <code>cause</code>
	 * or code <code>null</code> if <code>cause == null</code>.
	 *
	 * @param cause				the cause
	 */
	public FastaHandlingException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Creates a new instance of this exception with the given detail message 
	 * and cause.
	 *
	 * @param detailMessage		the detail message
	 * @param cause				the cause
	 */
	public FastaHandlingException(final String detailMessage, final Throwable cause) {
		super(detailMessage, cause);
	}
	
}
		