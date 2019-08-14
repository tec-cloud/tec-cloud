package com.tec.cloud.api.quote.dto;


import com.tec.platform.common.core.BaseObj;

import java.util.List;
import java.util.Map;

/**
 * @author tec
 */
public class ShanbayDto extends BaseObj {
    /**
     * id : fmywp
     * author : Ella Baker
     * content : Give light and people will find the way.
     * assign_date : 2019-01-13
     * ad_url : https://h10.shanbay.com/s/track?st=s&url=https%3A%2F%2Fwww.shanbay.com%2Fweb%2Fplan365%2F&ct=transformer&x_data=%7B%22_%22%3A+%228d58fd%22%7D&x_cdata=%7B%22campaign_code%22%3A+%22kc98hu5tv%22%7D
     * origin_img_urls : ["https://media-image1.baydn.com/soup_pub_image/ccdbwr/9d79cf0c31111ead3b5d973858291b46.fdfffe3475a48a069e226827e14a4ef9.png@!fhd_webp","https://media-image1.qiniu.baydn.com/soup_pub_image/ccdbwr/9d79cf0c31111ead3b5d973858291b46.fdfffe3475a48a069e226827e14a4ef9.png?imageView2/2/w/1080/format/webp"]
     * share_img_urls : ["https://media-image1.baydn.com/soup_pub_image/ccdbwr/6596e8035a100d9c18d283779c3ab1fa.04d6991174dbfdf0aa9259913ac8792d.png@!w720","https://media-image1.qiniu.baydn.com/soup_pub_image/ccdbwr/6596e8035a100d9c18d283779c3ab1fa.04d6991174dbfdf0aa9259913ac8792d.png?imageView2/2/w/720/"]
     * share_url : https://www.shanbay.com/soup/mobile/quote/2019-01-13/
     * share_urls : {"wechat":"https://www.shanbay.com/soup/mobile/quote/2019-01-13/","weibo":"https://www.shanbay.com/soup/mobile/quote/2019-01-13/","qzone":"https://www.shanbay.com/soup/mobile/quote/2019-01-13/","shanbay":"https://www.shanbay.com/soup/mobile/quote/2019-01-13/","wechat_user":"https://www.shanbay.com/soup/mobile/quote/2019-01-13/"}
     * track_object : {"code":"abb22","share_url":"https://www.shanbay.com/soup/mobile/quote/2019-01-13/","object_id":2473}
     * translation : 给予光亮，人们就能找到前方的路。
     */

    private String id;
    /**
     * 作者
     */
    private String author;
    /**
     * 内容
     */
    private String content;
    /**
     * 日期
     */
    private String assignDate;
    /**
     * 广告地址
     */
    private String adUrl;
    /**
     * 分享链接
     */
    private String shareUrl;
    private Map shareUrls;
    private Map trackObject;

    /**
     * 翻译
     */
    private String translation;
    /**
     * 原始图片 - 不带摘录
     */
    private List<String> originImgUrls;
    /**
     * 分享图片 - 包含摘录
     */
    private List<String> shareImgUrls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Map getShareUrls() {
        return shareUrls;
    }

    public void setShareUrls(Map shareUrls) {
        this.shareUrls = shareUrls;
    }

    public Map getTrackObject() {
        return trackObject;
    }

    public void setTrackObject(Map trackObject) {
        this.trackObject = trackObject;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public List<String> getOriginImgUrls() {
        return originImgUrls;
    }

    public void setOriginImgUrls(List<String> originImgUrls) {
        this.originImgUrls = originImgUrls;
    }

    public List<String> getShareImgUrls() {
        return shareImgUrls;
    }

    public void setShareImgUrls(List<String> shareImgUrls) {
        this.shareImgUrls = shareImgUrls;
    }
}
