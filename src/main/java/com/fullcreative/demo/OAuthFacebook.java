package com.fullcreative.demo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.oauth.OAuthRequestException;

@Controller
public class OAuthFacebook {

	@RequestMapping("/oauth1")
	public void Oauthfb(HttpServletRequest req, HttpServletResponse res)
			throws OAuthRequestException, UnsupportedOperationException, IOException {

		String authorizationCode = req.getParameter("code");

		if (authorizationCode == null) {

			res.sendRedirect("/welcome");
		}

		if (authorizationCode != null && authorizationCode.length() > 0) {

			final String TOKEN_ENDPOINT = "https://graph.facebook.com/oauth/access_token";
			final String GRANT_TYPE = "authorization_code";
			final String REDIRECT_URI = "http://localhost:8080/oauth1";
			final String CLIENT_ID = "326846368586443";
			final String CLIENT_SECRET = "XXXX";

			// Generate POST request
			HttpPost httpPost = new HttpPost(
					TOKEN_ENDPOINT + "?grant_type=" + URLEncoder.encode(GRANT_TYPE, StandardCharsets.UTF_8.name())
							+ "&code=" + URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8.name())
							+ "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name())
							+ "&client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.name()));

			String clientCredentials = CLIENT_ID + ":" + CLIENT_SECRET;
			String encodedClientCredentials = new String(Base64.encodeBase64(clientCredentials.getBytes()));
			httpPost.setHeader("Authorization", "Basic " + encodedClientCredentials);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Reader reader = new InputStreamReader(httpResponse.getEntity().getContent());
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();

			String accessToken = null;
			String[] responseProperties = line.split(",");

			// System.out.println(responseProperties[0]);

			String[] responseProperties1 = responseProperties[0].split(":");

			// System.out.println(responseProperties1[1]);
			accessToken = responseProperties1[1];

			String finalaccessToken = accessToken.substring(1, accessToken.length() - 1);

			String requestUrl = "https://graph.facebook.com/v8.0/me?fields=id%2Cname%2Cemail&access_token="
					+ finalaccessToken;
			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(requestUrl);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("method", "post"));

			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));

			httpResponse = httpClient.execute(httpPost);

			// Extract feed data from response
			bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String feedJson = bufferedReader.readLine();
			System.out.println("User Details: " + feedJson);

			String[] fields = feedJson.split(",");
			String[] splitID = fields[0].split(":");
			String id = splitID[1].substring(1, splitID[1].length() - 1);
			// System.out.println(id);

			String[] splitEmail = fields[2].split(":");
			String email = splitEmail[1].substring(1, splitEmail[1].length() - 2);

			String decodedEmail = email.replace("\\u0040", "@");
			// System.out.println(decodedEmail);

			httpClient.close();

			HttpSession sess = req.getSession();
			// sess.setAttribute("currentid", id);
			// sess.setAttribute("currentemail", decodedEmail);

			
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
			PersistenceManager pm = pmf.getPersistenceManager();
			
			//String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));


			OAuthUsers userid = new OAuthUsers(id, decodedEmail);

			// Query getQ = pm.newQuery(User.class, "userName == '" + userName + "'");

			try {

				pm.currentTransaction().begin();
				
				// System.out.println(getQ.execute());

				pm.makePersistent(userid);


				pm.currentTransaction().commit();

			}

			finally {

				if (pm.currentTransaction().isActive()) {
					System.out.println("rollback");
					pm.currentTransaction().rollback();
				}}
			

			sess.setAttribute("currentUser", decodedEmail);
			sess.setAttribute("src", "user");

			res.sendRedirect("/welcome");

		} else {
			// Handle failure
		}

	}

}
