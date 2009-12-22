package iweb2.ch6.evaluation;

public abstract class Test {

	public Test() {
		super();
	}
	
    public abstract void evaluate();

    protected abstract void calculate();

    protected void print(String val) {
		System.out.print("      ");
		System.out.println(val);
	}

}