class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner S = new Scanner(args[0]);
		//Scanner S = new Scanner("src/Correct/2.code");
		Parser.scanner = S;
		
		Program prog = new Program();
		
		prog.parse();
		
		//prog.print();
		
		prog.execute(args[1]);
		//prog.execute("src/Correct/0.data");
	}
}