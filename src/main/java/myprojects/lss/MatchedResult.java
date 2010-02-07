package myprojects.lss;
/*
 * Domain class to represent a pair of name and its corresponding sequence number.
 * Do not need this class if java supported tuples ?
 */
public class MatchedResult implements Comparable<MatchedResult>{
	private final String name;
	private final Integer sequenceNumber;
	

	public MatchedResult(String name, Integer sequenceNumber) {
		this.name = name;
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime
				* result
				+ ((getSequenceNumber() == null) ? 0 : getSequenceNumber().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchedResult other = (MatchedResult) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getSequenceNumber() == null) {
			if (other.getSequenceNumber() != null)
				return false;
		} else if (!getSequenceNumber().equals(other.getSequenceNumber()))
			return false;
		return true;
	}

	public int compareTo(MatchedResult o) {
		if(this.getSequenceNumber() < o.getSequenceNumber())
			return -1;
		if(this.getSequenceNumber() > o.getSequenceNumber())
			return 1;
		return 0;
	}
	
	

	@Override
	public String toString() {
		return "MatchedResult [name=" + name + ", sequenceNumber="
				+ sequenceNumber + "]";
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public String getName() {
		return name;
	}
	
	
}
