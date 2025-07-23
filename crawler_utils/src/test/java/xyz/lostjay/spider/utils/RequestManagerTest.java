package xyz.lostjay.spider.utils;

import java.io.IOException;
import java.net.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestManagerTest {

    @Test
    public void testGetOkHttpClientWithoutProxy() {
        // Test getting OkHttpClient without proxy
        OkHttpClient client = RequestManager.getOkHttpClient();

        // Verify client is not null
        assertNotNull(client);

        // Verify proxy is null
        assertNull(client.proxy());
    }

    @Test
    public void testGetOkHttpClientWithProxy() throws IOException {
        // Create a test proxy
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new java.net.InetSocketAddress("192.168.22.81", 10002));

        // Test getting OkHttpClient with proxy
        OkHttpClient client = RequestManager.getOkHttpClient(proxy);

        Request request = new Request.Builder().url("https://riat-.blog.csdn.net/article/details/148353833").build();
        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();
        if (body != null) {
            System.out.println(body.string());
            // Verify client is not null
            assertNotNull(client);
            // Verify proxy is set correctly
            assertEquals(proxy, client.proxy());
        } else {
            System.out.println("body is null");
        }
    }
}
