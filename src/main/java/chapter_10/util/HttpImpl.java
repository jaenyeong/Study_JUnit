package chapter_10.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpImpl implements Http {

	@Override
	public String get(String url) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response = client.execute(request);

		try {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} finally {
			response.close();
		}
	}
}
