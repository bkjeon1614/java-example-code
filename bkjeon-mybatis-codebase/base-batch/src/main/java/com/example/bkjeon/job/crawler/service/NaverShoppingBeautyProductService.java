package com.example.bkjeon.job.crawler.service;

import com.example.bkjeon.common.utils.HttpUtil;
import com.example.bkjeon.feature.crawler.NaverShoppingBeautyProduct;
import com.example.bkjeon.feature.crawler.NaverShoppingBeautyProductMapper;
import com.example.bkjeon.job.crawler.common.CrawlerConstant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverShoppingBeautyProductService {

    private NaverShoppingBeautyProductMapper naverShoppingBeautyProductMapper;

    public List<NaverShoppingBeautyProduct> getNaverShoppingBeautyProductCrawling(
        String logYmd,
        String[] categoryIdArr,
        int currentCategoryIdIndex
    ) {
        List<NaverShoppingBeautyProduct> naverShoppingBeautyProductList = new ArrayList<>();

        try {
            String menuId = categoryIdArr[currentCategoryIdIndex];
            String[] fullCategories = categoryIdArr[currentCategoryIdIndex].split("/");
            String categoryNo = fullCategories[fullCategories.length - 1];

            String verticalCode;
            if (menuId.equals("10002562")) {
                verticalCode = "HEALTHY";
            } else if (menuId.equals("10001099")) {
                verticalCode = "HANDMADE";
            } else {
                verticalCode = "BEAUTY";
            }

            String requestUrl = String.format(CrawlerConstant.naverShoppingBeautyUrl, menuId, verticalCode);

            GetMethod method = new GetMethod(requestUrl);
            String response = HttpUtil.requestUrl(method);

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(response, JsonObject.class);
            JsonArray productList = json.get("products").getAsJsonArray();
            List<Map<String, Object>> parseProductList = gson.fromJson(productList, List.class);

            int productRank = 1;
            for (int i=0; i<parseProductList.size(); i++) {
                NaverShoppingBeautyProduct naverShoppingBeautyProduct = NaverShoppingBeautyProduct.builder()
                    .logYmd(logYmd)
                    .productNo(MapUtils.getString(parseProductList.get(i), "_id"))
                    .productName(HttpUtil.getCleanData(MapUtils.getString(parseProductList.get(i), "name")))
                    .categoryNo(categoryNo)
                    .productRank(productRank)
                    .build();
                naverShoppingBeautyProductList.add(naverShoppingBeautyProduct);

                productRank++;
            }
        } catch (Exception e) {
            log.error("네이버 쇼핑 뷰티 상품 크롤링 에러 {}", e.getMessage());
        }

        return naverShoppingBeautyProductList;
    }

    @Transactional
    public void setNaverShoppingBeautyProduct(NaverShoppingBeautyProduct naverShoppingBeautyProduct) {
        try {
            naverShoppingBeautyProductMapper.insertNaverShoppingBeautyProduct(naverShoppingBeautyProduct);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>> setNaverShoppingBeautyProduct Error !! {}", e);
        }
    }

    @Transactional
    public int delNaverShoppingBeautyProduct(String logYmd, String categoryIds) {
        int delCnt = 0;

        try {
            delCnt = naverShoppingBeautyProductMapper.deleteNaverShoppingBeautyProduct(logYmd, categoryIds);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>> delNaverShoppingBeautyProduct Error !! {}", e);
        }
        return delCnt;
    }

}