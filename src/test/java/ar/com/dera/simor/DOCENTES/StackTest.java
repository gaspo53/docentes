package ar.com.dera.simor.DOCENTES;

import java.util.Stack;

import org.junit.Test;

public class StackTest {

	@Test
	public void test(){
		Stack<String> stack = new Stack<String>();
		stack.push("TEST");
		stack.spliterator().forEachRemaining(s -> {
			int count = 0;
			System.err.println(s);
			if (count < 3){
				count++;
			stack.push("JO"+count);
			}
			
		});
	}
}
