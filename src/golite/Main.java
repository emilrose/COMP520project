package golite;
import golite.parser.*;
import golite.lexer.*;
import golite.node.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import golite.golite_lexer.*;
import golite.weeder.*;
import golite.pretty.*;
import golite.symbol.*;
import golite.codegen.*;
 
class Main {
	
	public static void setOutputFile(String name, String option) throws FileNotFoundException{
		//Redirects System.out
		//Options are either 
		// "out" - reset System.out, 
		// "pretty" - write pretty print to file.pretty.go
		// "symtable" - write symbol table to file.symtab
        // "pptype" - pretty print with types to file.pptype.go
        // "codegen" - print c code to file.c
		String fileName = "";
		String ext;
		if (option == "out") {
			System.setOut(new PrintStream( new FileOutputStream(FileDescriptor.out)));
			return;
		}else if (option == "pretty") {
			ext = ".pretty.go";
		} else if (option == "symtable") {
			ext = ".symtab";
        } else if (option == "pptype") {
            ext = ".pptype.go";
        } else if (option == "codegen") {
            ext = ".c";
        } else {
			throw new IllegalArgumentException("invalid option in setOutputFile()");
		}
		 if (name.matches(".*go")) {
             fileName = name.substring(0, name.length()-3) + ext;
         }
         else {
             fileName = name + ext;
         }
         File output = new File(fileName);
         System.setOut(new PrintStream (new FileOutputStream(output)));
	}
	
    public static void main(String args[]) {
        try {
            PrintStream oldStream = System.out;
            System.setErr(oldStream);
            
            boolean pp = false;
            boolean dumpsymtab = false;
            boolean pptype = false;
            boolean codegen = false;
            String name = "";
            
            int i = 0;
            while (i < args.length) {
                if (args[i].equals("-h")) {
                    System.out.println("Options: -v, -dumpsymtab, -pptype, -pp, -codegen");
                    System.exit(0);
                }
                if (args[i].equals("-v")) {
                    System.out.println("Version 1.0");
                    System.exit(0);
                }
                if (args[i].equals("-dumpsymtab")) {
                    dumpsymtab = true;
                }
                else if (args[i].equals("-pptype")) {
                    pptype = true;
                }
                else if (args[i].equals("-pp")) {
                    pp = true;
                }
                else if (args[i].equals("-codegen")) {
                    codegen = true;
                }
                else if (name.equals("")) {
                    name = args[i];
                }
                i++;
            }

            FileReader file = new FileReader(name);
            Parser p = new Parser (new GoLiteLexer (new PushbackReader(file,1024)));
            Start tree = p.parse();
            Weeder.weed(tree);
            
            if (pp) {
                setOutputFile(name, "pretty");
                PrettyPrinter.print(tree);
            }
            
            //setOutputFile(name, "out");
            
            SymbolTable symTab = new SymbolTable();
            symTab.typeCheck(tree);
            
            if (pptype) {
                setOutputFile(name, "pptype");
                PPType.print(tree);
            }
            
            if (dumpsymtab) {
                setOutputFile(name, "symtable");
                SymbolTable symTab2 = new SymbolTable();
                symTab2.typeCheckAndDump(tree);
            }
            
            if (codegen) {
                setOutputFile(name, "codegen");
                CodeGen.print(tree);
            }
            
            
        }
        catch(Exception e) {
            System.err.println(e);
            //e.printStackTrace();
        }
    }
}
  




