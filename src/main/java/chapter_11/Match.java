package chapter_11;

public class Match {
	public final String searchTitle;
	public final String searchString;
	public final String surroundingContext;

	public Match(String searchTitle, String searchString, String surroundingContext) {
		this.searchTitle = searchTitle;
		this.searchString = searchString;
		this.surroundingContext = surroundingContext;
	}
}
