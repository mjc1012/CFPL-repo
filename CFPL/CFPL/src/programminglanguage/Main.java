package programminglanguage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws Exception {
		SymbolTable globalSymbolTable = new SymbolTable();
		DeclaredVar globalDeclaredVar = new DeclaredVar();
		String program = ""; 
		File file = new File("D:\\eclipse\\4-3-22 code\\tokenizer\\tokenizer\\src\\programminglanguage\\tokenizer\\CFPL.txt");
		Scanner scanFile = new Scanner(file);
		
		while(scanFile.hasNextLine()) {
			program = program.concat(scanFile.nextLine() + "\n");
		}

		Lexer lexer = new Lexer("<CFPL>", program);
		ArrayList<Token> tokens = lexer.make_tokens();
		if(lexer.getError() != null) {
			System.out.println(lexer.getError().toString());
		}
		else {
			Parser parser = new Parser(tokens);
			Context context1 = new Context(globalDeclaredVar);
			Context context2 = new Context(globalSymbolTable);
			ParseResult ast = parser.parse(context1, context2);
			if(ast.getError() != null) {
				System.out.println(ast.getError().toString());
			}
			else {
				Interpreter interpreter = new Interpreter();
				RuntimeResult result = interpreter.visit(ast, context1, context2);
				if(interpreter.getScanner() != null) {//added change
					interpreter.getScanner().close(); 
				}
				
				if(result.getError() != null) {
					System.out.println(result.getError().toString());
				}
				// added change
			}
		}
		scanFile.close();
	}
}
 