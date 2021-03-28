package com.example.bkjeon.job.crawler.common;

public class CrawlerConstant {

    // 네이버 쇼핑 뷰티윈도 / 헬시윈도우 랭킹
    public static final String naverShoppingBeautyUrl =
        "https://shopping.naver.com/v1/products?"
            + "menuId=%s"
            + "&page=1"
            + "&pageSize=100"
            + "&subVertical=%s"
            + "&sort=POPULARITY"
            + "&filter=all"
            + "&displaytype=CATEGORY_HOME"
            + "&includeZzim=true"
            + "&includeBrandInfo=true"
            + "&includeRanking=true"
            + "&includeRankingByMenus=true"
            + "&includeStoreCategoryName=true";

    // 네이버 쇼핑 카테고리별 클릭량
    public static final String naverShoppingDataLabClickRateUrl
            = "https://datalab.naver.com/shoppingInsight/getCategoryClickTrend.naver";
}
