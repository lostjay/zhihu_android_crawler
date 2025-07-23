from unittest import IsolatedAsyncioTestCase

from curl_cffi.requests import post, AsyncSession

from zhihu_android.android import ZhihuAndroidCrawler


class TestZhihuAndroid(IsolatedAsyncioTestCase):

    def test_get_sign(self):
        response = post('http://localhost:17007/sign', json={
            'path': '/api/v4/questions/423731362/answers',
            'authorization': 'Bearer 1234567890',
            'uuid': '1234567890',
            'appVersion': '1.0.0'
        })
        print(response.text)

    async def test_get_answers_from_author(self):
        async with ZhihuAndroidCrawler() as crawler:
            count = 0
            async for answer in crawler.get_answers_from_author('ec4769672e4d9c9c5e7fc427084a13c3'):
                print(answer)
                count += 1
                if count == 2:
                    break
            count = 0
            async for answer in crawler.get_answers_from_author('24952e878240f9408eb885b0424fe4a7'):
                print(answer)
                count += 1
                if count == 2:
                    break

    async def test_search(self):
        async with ZhihuAndroidCrawler() as crawler:
            data = await crawler.search('芙宁娜')
            print(data)
