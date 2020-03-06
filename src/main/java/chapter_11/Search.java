package chapter_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
	private String searchString;
	private String searchTitle;
	private Exception exception = null;
	private List<Match> matches = new ArrayList<>();
	private int surroundingCharacterCount = 100;
	private InputStream inputStream;
	final static Logger LOGGER = Logger.getLogger(Search.class.getName());

	public Search(String urlString, String searchString, String searchTitle) throws IOException {
		this(new URL(urlString).openConnection().getInputStream(), searchString, searchTitle);
	}

	public Search(InputStream inputStream, String searchString, String searchTitle) {
		this.inputStream = inputStream;
		this.searchString = searchString;
		this.searchTitle = searchTitle;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public boolean errored() {
		return exception != null;
	}

	public Exception getError() {
		return exception;
	}

	public void execute() {
		try {
			search();
		} catch (IOException exc) {
			exception = exc;
		}
	}

	public void setSurroundingCharacterCount(int count) {
		surroundingCharacterCount = count;
	}

	private void addMatches(String line, Pattern pattern) {
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			start -= surroundingCharacterCount;
			if (start <= 0) start = 0;
			end += surroundingCharacterCount;
			if (end >= line.length()) end = line.length();
			matches.add(new Match(searchTitle, searchString, line.substring(start, end)));
		}
	}

	private void search() throws IOException {
		Pattern pattern = Pattern.compile(searchString);
		matches.clear();

		LOGGER.info("searching matches for pattern:" + pattern);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				addMatches(line, pattern);
			}
		} finally {
			if (reader != null)
				reader.close();
		}
	}
}
