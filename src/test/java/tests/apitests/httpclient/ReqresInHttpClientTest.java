package tests.apitests.httpclient;

import com.google.gson.Gson;
import models.apimodels.UserModel;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReqresInHttpClientTest {

    HttpClient httpClient;

    @BeforeClass
    public void createClient() {
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    public void checkResponseCodeTest() throws IOException {
        HttpGet get = new HttpGet("https://reqres.in/api/users?page=2");
        HttpResponse httpresponse = httpClient.execute(get);
        Assert.assertEquals(httpresponse.getStatusLine().getStatusCode(), 200, "Response code isn't equal to 200");
    }

    @Test
    public void checkResponseBodyTest() throws IOException {
        HttpGet get = new HttpGet("https://reqres.in/api/users/2");
        HttpResponse httpresponse = httpClient.execute(get);
        Gson gson = new Gson();
        UserModel actualUser = gson.fromJson(EntityUtils.toString(httpresponse.getEntity()), UserModel.class);
        UserModel expectedUser = null;
        try (Reader reader = new FileReader("src/test/resources/user.json")) {
            expectedUser = gson.fromJson(reader, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actualUser, expectedUser, "Users aren't equal");
    }

    @Test
    public void createUserTest() throws IOException {
        HttpPost httpPost = new HttpPost("https://reqres.in/api/users");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", "John"));
        params.add(new BasicNameValuePair("job", "pass"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse httpresponse = httpClient.execute(httpPost);
        Assert.assertEquals(httpresponse.getStatusLine().getStatusCode(), 201);
    }
}
