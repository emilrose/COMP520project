package golite.golite_lexer;

import golite.node.*;
import golite.lexer.*;
import java.io.*;

// customized lexer to deal with semicolon insertion
// http://golang.org/ref/spec#Lexical_elements

public class GoLiteLexer extends Lexer {
    
    // keep track of the last token that wasn't an ignored token
    protected Token previousToken;
    
    protected void filter() throws LexerException, IOException
    {
        /*
         If this.token is a newline, line comment, multiline block comment, or
         EOF, then check previousToken. Replace this.token with a semicolon 
         if previousToken is one of the following:
            - an identifier (includes "int", "float64", etc)
            - an integer, floating-point, rune, or string literal
            - one of the keywords break, continue, fallthrough, or return
            - one of the operators and delimiters ++, --, ), ], or }
         */
        if (this.token instanceof TNewline
            | this.token instanceof TLineComment
            | (this.token instanceof TBlockComment && this.token.getText().contains("\n"))
            | this.token instanceof EOF)
        {
            int line = this.token.getLine();
            int pos = this.token.getPos();
            
            if (previousToken instanceof TId
                | previousToken instanceof TBool
                | previousToken instanceof TInt
                | previousToken instanceof TFloat64
                | previousToken instanceof TRune
                | previousToken instanceof TString
                | previousToken instanceof TIntLit
                | previousToken instanceof TFloatLit
                | previousToken instanceof TRuneLit
                | previousToken instanceof TStrLit
                | previousToken instanceof TBreak
                | previousToken instanceof TContinue
                | previousToken instanceof TFallthrough
                | previousToken instanceof TReturn
                | previousToken instanceof TPlusPlus
                | previousToken instanceof TMinusMinus
                | previousToken instanceof TRParen
                | previousToken instanceof TRBracket
                | previousToken instanceof TRBrace)
            {
                this.token = new TSemi(line, pos);
                //System.out.println("inserting semicolon");
            }
            // otherwise the token will be discarded during parsing
        }
        // update previousToken
        if (!(this.token instanceof TLineComment
            | this.token instanceof TBlockComment
            | this.token instanceof TNotLfSpace
            | this.token instanceof TNewline
            | this.token==null))
        {
            previousToken = this.token;
        }
    }
    
    public GoLiteLexer(@SuppressWarnings("hiding") final PushbackReader in)
    {
        super(in);
    }
    
    public GoLiteLexer(@SuppressWarnings("hiding") IPushbackReader in)
    {
        super(in);
    }

}