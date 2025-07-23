package xyz.lostjay.spider.zhihu.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class ZhihuAndroidAppEmulatorTest {

    private ZhihuAndroidAppEmulator emulator;

    @Before
    public void setUp() {
        emulator = new ZhihuAndroidAppEmulator();
    }

    @Test
    public void testInitialization() {
        assertNotNull("Emulator should not be null", emulator);
    }

    @Test
    public void testAesEncrypt() {
        String input = "298f238983222388218f29218d298d298080822a2183228822898f8d838b2a29";
        String expected = "8d80d2ed710caf2ba9b182e48755acb27ec6c58ee07225dd087f7e828a93176c32e83d9b512eb1f0a2b036b677c4f439";
        byte[] raw = ZhihuAndroidAppEmulator.hexStringToByteArray(input);
        byte[] result = emulator.aesEncrypt(raw);
        String resultString = ZhihuAndroidAppEmulator.bytesToHexString(result);
        assertEquals("AES encryption result should match expected output", expected, resultString);
    }

    @Test
    public void testGetSign() {
        String path = "/api/v4/search_v3?gk_version=gz-gaokao&q=%E6%B7%80%E7%B2%89%E8%82%A0%E5%A1%8C&t=general&lc_idx=0&correction=1&offset=0&advertCount=0&limit=20&is_real_time=0&show_all_topics=0&search_source=Suggestion&filter_fields=&city=&pin_flow=false&ruid=undefined&recq=undefined&pre_search_hash_id=&raw_query=type%3Dcontent%26q%3D%25E6%25B7%2580%25E7%25B2%2589%25E8%2582%25A0%26target%3Dblank%26extra_from_login%3Dtrue";
        String authorization = "Bearer 2.1vLffQQAAAAAAYByqx8NZGAwAAABgAlVNRDklZgA481caJWtIspnOgPOYMLY3nBO5RA";
        String uuid = "AGAcqsfDWRhLBbB5f6a4DsP9GxvvQtHYefs=";
        String appVersion = "9.41.0";
        String expected = "1.0_TkDh3rIMXxdWckHYS6pccb3Jyk3QsRruBL+9QUVjK5wx1D5noh1y8FFwOXm7yPg2";
        String sign = emulator.getSign(path, authorization, uuid, appVersion);
        assertEquals("Sign should match expected output", expected, sign);
    }
}
