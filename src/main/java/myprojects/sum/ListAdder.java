package myprojects.sum;

import java.util.List;
import java.util.Stack;

public class ListAdder {

	private final Stack<Integer> list = new Stack<Integer>();

	public ListAdder(List<Integer> list) {
		this.list.addAll(list);
	}

	public Integer computeSum() {
		return computeSum(list, 0);
	}

	private Integer computeSum(Stack<Integer> s, int element) {
		int sum = 0;
		if(s.size() == 0)
			return element;
		sum = sum + element + computeSum(s, s.pop());
		return sum;
	}

}
