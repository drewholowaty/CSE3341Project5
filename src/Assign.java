class Assign implements Stmt {
	int type;
	Id assignTo;
	Id assignFrom;
	Expr expr;
	
	public void parse() {
		assignTo = new Id();
		assignTo.parse();
		Parser.expectedToken(Core.ASSIGN);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() == Core.INPUT) {
			type = 0;
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.LPAREN);
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.NEW) {
			type = 1;
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.CLASS);
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.SHARE) {
			type = 2;
			Parser.scanner.nextToken();
			assignFrom = new Id();
			assignFrom.parse();
		} else {
			type = 3;
			expr = new Expr();
			expr.parse();
		}
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		assignTo.print();
		System.out.print("=");
		if (type == 0) {
			System.out.print("input()");
		} else if (type == 1) {
			System.out.print("new class");
		} else if (type == 2) {
			System.out.print("share ");
			assignFrom.print();
		} else {
			expr.print();
		}
		System.out.println(";");
	}
	
	public void execute() {
		if (type == 0) {
			// Doing a "input"-assign
			assignTo.storeValue(Executor.getNextData());
		} else if (type == 1) {
			// Doing a "new"-assign
			assignTo.heapAllocate();
			// increment refCount and print statement
			Executor.refCount++;
			System.out.println("gc:" + Executor.refCount);
		} else if (type == 2) {
			// Doing a "share"-assign
			assignTo.referenceCopy(assignFrom);
		} else {
			// Doing a regular assign
			assignTo.storeValue(expr.execute());
		}
	}
}



