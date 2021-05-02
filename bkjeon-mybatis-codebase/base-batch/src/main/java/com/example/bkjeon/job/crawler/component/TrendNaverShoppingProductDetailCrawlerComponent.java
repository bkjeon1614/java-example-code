package com.example.bkjeon.job.crawler.component;

import com.example.bkjeon.entity.crawler.NaverShoppingBestProductDetail;
import com.example.bkjeon.job.crawler.common.CrawlerConfig;
import com.example.bkjeon.job.crawler.common.CrawlerConstant;
import com.example.bkjeon.util.RandomUserAgent;
import com.example.bkjeon.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 네이버 쇼핑 상품 상세 데이터 크롤링 유틸
 */
@Slf4j
@Component
public class TrendNaverShoppingProductDetailCrawlerComponent {

    private static final int DRIVER_TIMEOUT = 120;

    /**
     * 상품 상세의 Doc 추출
     * @param prodId
     * @Desc 상품 상세 데이터 추출 용도
     * @return scriptData
     */
    public String getHtmlDoc(String prodId) {
        String dataScript = null;

        ChromeOptions options = CrawlerConfig.getDefaultChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        String productDetailUrl = String.format(
            CrawlerConstant.NAVER_SHOPPING_PRODUCT_DETAIL_URL,
            prodId
        );
        log.info("------------------------------- Product Detail URL: {}", productDetailUrl);

        try {
            driver.get(productDetailUrl);
            driver.manage().timeouts().implicitlyWait(DRIVER_TIMEOUT, TimeUnit.SECONDS);

            // CASE1: Get Html Data
            Document doc = Jsoup.parse(driver.getPageSource());
            Elements scriptData = doc.getElementsByTag("script");
            dataScript = getDomNextData(scriptData);

            // Data Empty!!
            while (dataScript == null) {
                ChromeOptions newOptions = CrawlerConfig.getDefaultChromeOptions();
                WebDriver newDriver = new ChromeDriver(newOptions);
                newDriver.get(productDetailUrl);
                newDriver.manage().timeouts().implicitlyWait(DRIVER_TIMEOUT, TimeUnit.SECONDS);

                Document newDoc = Jsoup.parse(newDriver.getPageSource());
                Elements newScriptData = newDoc.getElementsByTag("script");
                String newDataScript = getDomNextData(newScriptData);
                if (newDataScript != null) {
                    newDriver.quit();
                    break;
                }

                ThreadUtil.threadSleep(1, 5);
            }

            if (dataScript == null) {
                log.info("------------------------------- Return Data Script Length: {}", dataScript);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getHtmlDoc Parse Error !! {}", e.getMessage());
                log.error("getHtmlDoc Data: {}", driver.getPageSource());
            }
        } finally {
            driver.quit();
        }

        return dataScript;
    }

    /**
     * 네이버 상품 상세 데이터 추출
     * @param scriptData
     * @Desc 네이버는 Tag Id가  __NEXT_DATA__ 인 스크립트에서 데이터를 추출할 수 있음
     * @return dataScript
     */
    private String getDomNextData(Elements scriptData) {
        String dataScript = null;

        for (Object item: scriptData) {
            if (item.toString().contains("__NEXT_DATA__")) {
                dataScript = item
                    .toString()
                    .replace("<script id=\"__NEXT_DATA__\" type=\"application/json\">", "")
                    .replace("</script>", "");
            }

            if (dataScript != null) {
                break;
            }
        }

        return dataScript;
    }

    /**
     * 상품 상세 데이터 추출
     * @return naverShoppingBestProductDetail
     */
    public NaverShoppingBestProductDetail getBestProductDetail(
        String logYmd,
        String categoryId,
        String productId,
        String scriptData
    ) {
        NaverShoppingBestProductDetail naverShoppingBestProductDetail = null;

        // init
        int zzimCount;
        String brandName = null;
        String productRegisterYmd;
        float reviewScore = 0;
        String sellerCount;
        int shippingPrice = 0;
        int productPrice = 0;

        try {
            // 찜하기 API 호출
            zzimCount = getZzimCnt(productId);

            // 상품 상세 데이터 추출
            log.info(">>>>>>>>>>>>>>>>>>>>>>>> 상품 상세 데이터 추출 진행");
            JSONObject jObject = new JSONObject(scriptData);
            JSONObject productInfoData = jObject
                .getJSONObject("props")
                .getJSONObject("pageProps")
                .getJSONObject("initialState")
                .getJSONObject("catalog")
                .getJSONObject("info");

            // 브랜드명
            log.info(">>>>>>>>>>>>>>>>>>>>>>>> 브랜드명 추출 진행");
            brandName = productInfoData.getString("brandName");

            // 등록일
            log.info(">>>>>>>>>>>>>>>>>>>>>>>> 등록일 추출 진행");
            productRegisterYmd = productInfoData.getString("registrationYearMonth")
                    .replaceAll("\\.", "");

            // 평점
            log.info(">>>>>>>>>>>>>>>>>>>>>>>> 평점 추출 진행");
            try {
                if (productInfoData.getString("reviewScore") != null) {
                    reviewScore = Float.parseFloat(productInfoData.getString("reviewScore"));
                }
            } catch (Exception e) {
                log.error("=============================== 평점 reviewScore Crawling Error !! {}", e);
            }

            // 판매처 총 개수
            log.info(">>>>>>>>>>>>>>>>>>>>>>>> 판매처 총 개수 추출 진행");
            sellerCount = productInfoData.getString("productCount");

            // 배송비, 가격
            log.info(">>>>>>>>>>>>>>>>>>>>>>>> 배송비, 가격 추출 진행");
            try {
                JSONObject productInfoCatelogObj = jObject
                    .getJSONObject("props")
                    .getJSONObject("pageProps")
                    .getJSONObject("initialState")
                    .getJSONObject("catalog");
                if (!productInfoCatelogObj.isNull("products")) {
                    JSONArray productInfoProductArr = productInfoCatelogObj.getJSONArray("products");
                    for (int i = 0; i < productInfoProductArr.length(); i++) {
                        JSONObject obj = productInfoProductArr.getJSONObject(i);

                        // 배송비
                        if (!obj.isNull("productsPage")) {
                            JSONArray productArr = obj.getJSONObject("productsPage").getJSONArray("products");
                            for (int j = 0; j < productArr.length(); j++) {
                                JSONObject productObj = productArr.getJSONObject(j);

                                if (i == 0 && productObj.getString("deliveryFee") != null) {
                                    // 인기순 상품 첫 로우에서 추출
                                    shippingPrice = Integer.parseInt(productObj.getString("deliveryFee"));
                                    productPrice = Integer.parseInt(productObj.getString("pcPrice"));
                                }
                            }   // END sub products
                        }
                    }   // END products
                }
            } catch (Exception e) {
                log.error(
                    "=============================== 배송비, 가격 productsPage > deliveryFee, pcPrice Crawling Error !! {}",
                    e
                );
            }

            // 데이터 반환
            naverShoppingBestProductDetail = NaverShoppingBestProductDetail.builder()
                .logYmd(logYmd)
                .categoryId(categoryId)
                .productId(productId)
                .brandName(brandName)
                .productRegisterYmd(productRegisterYmd)
                .zzimCount(zzimCount)
                .reviewScore(reviewScore)
                .sellerCount(Integer.parseInt(sellerCount))
                .shippingPrice(shippingPrice)
                .productPrice(productPrice)
                .build();
            log.info("Data: {}", naverShoppingBestProductDetail.toString());
            log.info(
                "Where: logYmd: {}, categoryId: {}, productId: {}",
                logYmd,
                categoryId,
                productId
            );
        } catch (Exception e) {
            log.error("getBestProductDetail Parse Error !! {}", e.getMessage());
            log.error("getBestProductDetail Data: {}", naverShoppingBestProductDetail.toString());
        }

        // 데이터 반환
        return naverShoppingBestProductDetail;
    }

    // 짬히기 API 호출
    private int getZzimCnt(String productId) {
        int zzimCnt = 0;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            String zzimApiUrl = String.format(CrawlerConstant.NAVER_SHOPPING_PRODUCT_DETAIL_ZZIM_URL, productId);
            log.info("------------------------------- Get Zzim Cnt URL: {}", zzimApiUrl);
            HttpGet zzimResponse = setHeader(new HttpGet(zzimApiUrl), productId);
            HttpResponse keywordResponse = client.execute(zzimResponse);
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(keywordResponse.getEntity())).getJSONObject(
                "zzim"
            ).getJSONObject(productId);
            zzimCnt = Integer.parseInt(jsonObject.get("count").toString());
        } catch (Exception e) {
            log.error("getZzimCnt Parse Error !! {}", e.getMessage());
        }

        return zzimCnt;
    }

    private HttpGet setHeader(HttpGet httpGet, String productId){
        httpGet.setHeader(
            "referer",
            "https://search.shopping.naver.com/catalog/"
                    + productId + "?nv_mid="
                    + productId + "&section=user&sort=pdgr"
        );
        httpGet.setHeader("user-agent", RandomUserAgent.getRandomUserAgent());
        httpGet.setHeader("accept", "application/json, text/plain, */*");
        httpGet.setHeader("host", "search.shopping.naver.com");
        httpGet.setHeader("urlprefix", "/api");
        return httpGet;
    }

}
