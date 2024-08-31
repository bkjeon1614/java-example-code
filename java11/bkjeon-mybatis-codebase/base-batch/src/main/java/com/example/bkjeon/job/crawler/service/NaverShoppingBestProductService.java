package com.example.bkjeon.job.crawler.service;

import com.example.bkjeon.entity.crawler.NaverShoppingBestProduct;
import com.example.bkjeon.entity.crawler.NaverShoppingBestProductDetail;
import com.example.bkjeon.job.crawler.common.CrawlerConfig;
import com.example.bkjeon.job.crawler.common.CrawlerConstant;
import com.example.bkjeon.job.crawler.component.TrendNaverShoppingProductDetailCrawlerComponent;
import com.example.bkjeon.mapper.crawler.NaverShoppingBestProductMapper;
import com.example.bkjeon.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverShoppingBestProductService {

    @Value("${chromedriver.id}")
    private String webDriverId;

    @Value("${chromedriver.path}")
    private String webDriverPath;

    private final NaverShoppingBestProductMapper naverShoppingBestProductMapper;
    private final TrendNaverShoppingProductDetailCrawlerComponent trendNaverShoppingProductDetailCrawlerComponent;

    @PostConstruct
    private void initSettings() {
        System.setProperty(webDriverId, webDriverPath);
    }

    public List<NaverShoppingBestProduct> getNaverShoppingBeautyProductCrawling(
        String logYmd,
        String[] categoryIdArr,
        int currentCategoryIdIndex
    ) {
        List<NaverShoppingBestProduct> naverShoppingBestProductList = new ArrayList<>();

        long start = System.currentTimeMillis();

        try {
            ChromeOptions options = CrawlerConfig.getDefaultChromeOptions();
            WebDriver driver = new ChromeDriver(options);

            String productUrl = String.format(
                CrawlerConstant.NAVER_SHOPPING_BEST_PRODUCT_URL,
                categoryIdArr[currentCategoryIdIndex]
            );
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> Request URL: {}", productUrl);

            driver.get(productUrl);
            List<WebElement> itemList = driver.findElements(By.className("_itemSection"));
            // String categoryName = driver.findElement(By.xpath("//div[@class='search_breadcrumb']/a[last()]")).getText();

            int productRank = 1;
            for (WebElement element : itemList) {
                String productId = element.getAttribute("data-nv-mid");
                WebElement imageElement = element.findElement(By.className("_productLazyImg"));
                String productName = imageElement.getAttribute("alt");
                String categoryId = categoryIdArr[currentCategoryIdIndex];

                NaverShoppingBestProductDetail naverShoppingBestProductDetail =
                    trendNaverShoppingProductDetailCrawlerComponent.getBestProductDetail(
                        logYmd,
                        categoryId,
                        productId,
                        trendNaverShoppingProductDetailCrawlerComponent.getHtmlDoc(productId)
                    );
                if (naverShoppingBestProductDetail == null) {
                    if (log.isInfoEnabled()) {
                        log.info(
                            "========================= NaverShoppingBestProductDetail Empty ProductId: {} !!",
                            productId
                        );
                    }
                }

                NaverShoppingBestProduct naverShoppingBestProduct = NaverShoppingBestProduct.builder()
                        .logYmd(logYmd)
                        .productId(productId)
                        .productName(productName)
                        .categoryId(categoryId)
                        .productRank(productRank)
                        .brandName(naverShoppingBestProductDetail.getBrandName())
                        .productRegisterYmd(naverShoppingBestProductDetail.getProductRegisterYmd())
                        .zzimCount(naverShoppingBestProductDetail.getZzimCount())
                        .reviewScore(naverShoppingBestProductDetail.getReviewScore())
                        .sellerCount(naverShoppingBestProductDetail.getSellerCount())
                        .shippingPrice(naverShoppingBestProductDetail.getShippingPrice())
                        .productPrice(naverShoppingBestProductDetail.getProductPrice())
                        .build();
                naverShoppingBestProductList.add(naverShoppingBestProduct);

                log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Insert Data: {}", naverShoppingBestProduct.toString());
                productRank++;
                ThreadUtil.threadSleep(1, 10);
            }
        } catch (Exception e) {
            log.error("네이버 쇼핑 베스트100 상품 크롤링 에러 {}", e);
        }

        return naverShoppingBestProductList;
    }

    @Transactional
    public int setNaverShoppingBestProduct(List<NaverShoppingBestProduct> naverShoppingBestProductList) {
        int insertCnt = 0;
        try {
            insertCnt = naverShoppingBestProductMapper.insertNaverShoppingBestProductList(naverShoppingBestProductList);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>> setNaverShoppingBestProduct Error !! {}", e);
        }

        return insertCnt;
    }

    @Transactional
    public int delNaverShoppingBestProduct(String logYmd, String categoryIds) {
        int delCnt = 0;

        try {
            delCnt = naverShoppingBestProductMapper.deleteNaverShoppingBestProduct(logYmd, categoryIds);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>> delNaverShoppingBestProduct Error !! {}", e);
        }
        return delCnt;
    }

}
