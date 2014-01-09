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
import de.mpi_bremen.mgg.FastaValidator.*;
%%

/**
 * Universal lexer for the FASTA format - build using jflex. 
 * @author      Jost Waldmann
 *
 */
%public
%class YylexUniversal 
%implements FastaValidatorLexer
%yylexthrow{
FastaValidatorException
%yylexthrow}

%line
%unicode
%{
 
%}
LT = \r\n|\n|\r|\u2028|\u2029|\u000B|\u000C|\u0085

%%

{LT} {return (new Yytoken(4,yytext(),yyline));}
^>.+$ {return (new Yytoken(1,yytext(),yyline));}
^;.*$ {return (new Yytoken(2,yytext(),yyline));}
^[A-Za-z\*\.\-]+[A-Za-z\ \*\.\-]* {return (new Yytoken(3,yytext(),yyline));}
. {throw new InvalidCharacterException("Illegal character \""+yytext()+"\" found at line "+yyline+".", yytext(), yyline);}
<<EOF>> {return (new Yytoken(0,"",yyline));}

