import java.util.ArrayList;

public class InvertedIndexEntry {
	int docId;
	int termFrequency;
	ArrayList<Integer> termPositions;
	
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public int getTermFrequency() {
		return termFrequency;
	}
	public void setTermFrequency(int termFrequency) {
		this.termFrequency = termFrequency;
	}
	public ArrayList<Integer> getTermPositions() {
		return termPositions;
	}
	public void setTermPositions(ArrayList<Integer> termPositions) {
		this.termPositions = termPositions;
	}
}
