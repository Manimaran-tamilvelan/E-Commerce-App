package com.fullcreative.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@Controller
public class OAuthGoogle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/oauth")
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String authorizationCode = request.getParameter("code");

		if (authorizationCode == null) {

			response.sendRedirect("/welcome");
		}
		// String SCOPE = request.getParameter("scope");
		if (authorizationCode != null && authorizationCode.length() > 0) {

			final String TOKEN_ENDPOINT = "https://www.googleapis.com/oauth2/v4";
			final String GRANT_TYPE = "authorization_code";
			final String REDIRECT_URI = "http://localhost:8080/oauth";
			final String CLIENT_ID = "628485492305-clc02nguilt3cn9aimatqolcgd8ivc7c.apps.googleusercontent.com";
			final String CLIENT_SECRET = "gjUVkVctpjwXNtvvBq13QUYP";

			// System.out.println(authorizationCode);

			HttpPost httpPost = new HttpPost(
					TOKEN_ENDPOINT + "/token?grant_type=" + URLEncoder.encode(GRANT_TYPE, StandardCharsets.UTF_8.name())
							+ "&code=" + URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8.name())
							+ "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name())
							+ "&client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.name())
							+ "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8.name()));

			httpPost.setHeader("Host", "www.googleapis.com");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

			// System.out.println(httpPost.toString());

			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpPost);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));

			StringBuilder total = new StringBuilder();

			String line1 = null;

			while ((line1 = bufferedReader.readLine()) != null) {
				total.append(line1);
			}

			bufferedReader.close();
			System.out.println(total.toString());
			String jsonString = total.toString();

			String[] keyValuePairs = jsonString.split(",");

			String[] accessTokenSplit = keyValuePairs[0].split(":");

			// String[] refreshTokenSplit = keyValuePairs[2].split(":");

			// removing " & space
			String accessToken = accessTokenSplit[1].substring(2, accessTokenSplit[1].length() - 1);

			// String refreshToken = refreshTokenSplit[1].substring(2,
			// refreshTokenSplit[1].length() - 1);

			// System.out.println(accessToken);
			// System.out.println(refreshToken);

			String getUserDetails = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;

			StringBuilder result = new StringBuilder();
			URL url = new URL(getUserDetails);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			// System.out.println(result.toString());

			String refreshToken1 = null;
			String[] responseProperties = jsonString.split(",");
			for (String responseProperty : responseProperties) {
				if (responseProperty.contains("refresh_token")) {
					String[] tempResponseToken = responseProperty.split(":");
					refreshToken1 = tempResponseToken[1].substring(2, tempResponseToken[1].length() - 1);

				}
			}

			// System.out.println(refreshToken1);

			String userDetails = result.toString();

			String eMail = null;
			String[] responseProperties1 = userDetails.split(",");
			for (String responseProperty : responseProperties1) {
				if (responseProperty.contains("email")) {
					// System.out.println(responseProperty);
					String[] mailWithQuotes = responseProperty.split(":");
					eMail = mailWithQuotes[1].substring(2, mailWithQuotes[1].length() - 1);

					break;
				}
			}
			System.out.println(eMail);

			String sub = null;
			String[] responseProperties2 = userDetails.split(",");
			for (String responseProperty : responseProperties2) {
				if (responseProperty.contains("sub")) {
					// System.out.println(responseProperty);
					String[] subWithQuotes = responseProperty.split(":");
					sub = subWithQuotes[1].substring(2, subWithQuotes[1].length() - 1);

					break;
				}
			}
			System.out.println(sub);

			HttpSession sess = request.getSession();

			// sess.setAttribute("currentid", sub);
			// sess.setAttribute("currentemail", eMail);

			sess.setAttribute("currentUser", eMail);
			sess.setAttribute("src", "user");

			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
			PersistenceManager pm = pmf.getPersistenceManager();

			// String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));

			OAuthUsers userid = new OAuthUsers(sub, eMail);

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
				}
			}

			if (refreshToken1 != null) {

				RefToken refToken = new RefToken(eMail, refreshToken1);

				// Query getQ = pm.newQuery(User.class, "userName == '" + userName + "'");

				try {

					pm.currentTransaction().begin();

					// System.out.println(getQ.execute());

					pm.makePersistent(refToken);

					pm.currentTransaction().commit();

				}

				finally {

					if (pm.currentTransaction().isActive()) {
						System.out.println("rollback");
						pm.currentTransaction().rollback();
					}
				}

			}

			response.sendRedirect("/welcome");

		}

		else {
			// Handle failure
		}
	}
}