## 扇贝-每日一句
博客链接：[https://blog.tecchen.tech/api-quote.html](https://blog.tecchen.tech/api-quote.html)

#### 前言

偶然看到某个小伙伴的博客（https://alili.tech/ ）有查询扇贝的每日一句，加上打字效果，让我感觉很新奇。所以爬了下扇贝的每日一句接口，放到自己的服务器上，后续集成到自己的博客里面。
API共分为几个接口：  
第一个接口是直接获取当天的每日一句，返回的字段较多，有些字段并未标注什么含义，也没有什么作用；  
第二个接口是查询历史的每日一句，因为本服务是从2019-01-12开始提供的，所以历史的数据是比较简单的结构。当然2019-01-12开始的每日一句都保存了下来，再查询就和第一种接口的数据是一致的了。  
第三个接口是随机查询历史的每日一句。  

你也可以通过Spring Cloud进行调用，我使用程序员DD的注册中心：http://eureka.didispace.com/
应用名称：QUOTE-API

使用中存在任何问题，可通过邮箱tecchen@aliyun.com联系我~（ 如果侵权，请联系我删除 ）

另外还有个天气预报的API（应用名称：WEATHER-API）还在开发中，敬请关注~

## 每日一句API接口

#### 扇贝- 每日一句

- 请求地址

`https://api.tecchen.tech/api/quote/`

- 请求方式

*GET*

- 请求参数示例

*无*

- 请求参数说明

*无*

- 返回参数示例
```json
{
	"code": 0,
	"message": "成功",
	"data": {
		"id": "ihscc",
		"author": "Juvenal",
		"content": "Never does nature say one thing and wisdom another.",
		"assignDate": "2019-01-19",
		"adUrl": "https://h10.shanbay.com/s/track?st=s&url=https%3A%2F%2Fwww.shanbay.com%2Fweb%2Fplan365%2F&ct=transformer&x_data=%7B%22_%22%3A+%228d58fd%22%7D&x_cdata=%7B%22campaign_code%22%3A+%22kc98hu5tv%22%7D",
		"shareUrl": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/",
		"shareUrls": {
			"weibo": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/",
			"shanbay": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/",
			"wechat": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/",
			"qzone": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/",
			"wechat_user": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/"
		},
		"trackObject": {
			"code": "abb22",
			"share_url": "https://www.shanbay.com/soup/mobile/quote/2019-01-19/",
			"object_id": 2485
		},
		"translation": "自然与智慧永不相悖。",
		"originImgUrls": [
			"https://media-image1.baydn.com/soup_pub_image/ccdbwr/fd3e0cb49ece0faeab0ec1126dab342f.aaf209e859f6598d50ec3d3593e6596b.png@!fhd_webp",
			"https://media-image1.qiniu.baydn.com/soup_pub_image/ccdbwr/fd3e0cb49ece0faeab0ec1126dab342f.aaf209e859f6598d50ec3d3593e6596b.png?imageView2/2/w/1080/format/webp"
		],
		"shareImgUrls": [
			"https://media-image1.baydn.com/soup_pub_image/ccdbwr/61fcabc6631b1deac4804fcb84739ba2.3a1761e8ed5c49723ee6040d390fe416.png@!w720",
			"https://media-image1.qiniu.baydn.com/soup_pub_image/ccdbwr/61fcabc6631b1deac4804fcb84739ba2.3a1761e8ed5c49723ee6040d390fe416.png?imageView2/2/w/720/"
		]
	}
}
```


- 返回参数说明

|参数名称|类型|说明|备注|
|:-----|:-----|:-----|:-----|
| author |string  | 作者/出处 |  |
| content |string  | 摘录 |  |
| assignDate |string  | 日期 |  |
| translation |string  | 翻译 |  |
| originImgUrls | Array  | 原始图片链接 | 不包含摘录 |
| shareImgUrls | Array  | 分享图片链接 | 包含摘录 |

#### 扇贝- 历史 - 每日一句

获取指定日期（格式：yyyy-MM-dd）的每日一句

- 请求地址

`https://api.tecchen.tech/api/quote/{requestDate}/`

- 请求方式

*GET*

- 请求参数示例

`https://api.tecchen.tech/api/quote/2019-11-11/`

- 请求参数说明

|参数名称|类型|说明|备注|
|:-----|:-----|:-----|:-----|
| requestDate |string  | 日期 | 格式：yyyy-MM-dd |

- 返回参数示例
```json
{
	"code": 0,
	"message": "成功",
	"data": {
		"id": null,
		"author": "约翰·梅纳德·凯恩斯",
		"content": "Ideas shape the course of history.",
		"assignDate": "2019-01-11",
		"adUrl": null,
		"shareUrl": null,
		"shareUrls": null,
		"trackObject": null,
		"translation": "想法影响着历史进程。",
		"originImgUrls": [
			"https://media-image1.baydn.com/soup_pub_image/ccdbwr/312d1addd474109097d8be4ceaf44d4c.88972797e32da926072b61a867ea17eb.png?x-oss-process=image/format,jpg"
		],
		"shareImgUrls": null
	}
}
```


- 返回参数说明

|参数名称|类型|说明|备注|
|:-----|:-----|:-----|:-----|
| author |string  | 作者/出处 |  |
| content |string  | 摘录 |  |
| assignDate |string  | 日期 |  |
| translation |string  | 翻译 |  |
| originImgUrls | Array  | 原始图片链接 | 不包含摘录 |

#### 随机获取一句历史的每日一句

随机获取一句历史的每日一句，可以用于博客首页，每次访问，内容都会变化，更吸引读者。

- 请求地址

`https://api.tecchen.tech/api/quote/history/random/`

- 请求方式

*GET*

- 请求参数示例

*无*

- 请求参数说明

*无*

- 返回参数示例

```json
{
	"code": 0,
	"message": "成功",
	"data": {
		"id": null,
		"author": "Baltasar Gracian",
		"content": "A wise man gets more use from his enemies than a fool from his friends.",
		"assignDate": "2017-11-14",
		"adUrl": null,
		"shareUrl": null,
		"shareUrls": null,
		"trackObject": null,
		"translation": "智者从敌人身上学到的，多过愚者从朋友身上学到的。",
		"originImgUrls": [
			"https://media-image1.baydn.com/soup_pub_image/qqnjfi/e50a1650c2a23de9526d8d6c4c30cf46.f707e8b26a5e5baa5b1cd32858ded05b.png?x-oss-process=image/format,jpg"
		],
		"shareImgUrls": null
	}
}
```

- 返回参数说明

|参数名称|类型|说明|备注|
|:-----|:-----|:-----|:-----|
| author |string  | 作者/出处 |  |
| content |string  | 摘录 |  |
| assignDate |string  | 日期 |  |
| translation |string  | 翻译 |  |
| originImgUrls | Array  | 原始图片链接 | 不包含摘录 |

#### 小彩蛋
浏览器访问`https://api.tecchen.tech/quote/index.html`发现小精彩哦～～～

#### 通用的返回报文格式

|参数名称|类型|说明|备注|
|:-----|:-----|:-----|:-----|
| code |int  | 编码 | 0:成功; 400:参数非法; 500:服务器错误;其他:请根据message确认 不是HTTP的请求状态码|
| message |string  | 成功／错误信息 |  |
| data |object  | 数据 | 以json格式返回  |


## 更新记录
#### v1.0.0 
2019-01-13 
- 扇贝每日一句
- 支持历史的每日一句
- 彩蛋页面

#### v1.0.1 
2019-01-19
- 优化返回的json
- 优化定时任务获取日志逻辑
- 支持HTTPS
- 随机获取一句历史的每日一句

#### v1.0.2 
2019-02-18
- 接入Spring cloud gateway
- 静态页面和动态请求分离
- 修复因框架升级造成的乱码问题
- 调整小彩蛋体验路径，优化移动端图片显示

#### v1.0.3
2019-07-20
- 框架优化调整
- 默认支持https接口
- 添加eolinker的API文档和API测试

#### v1.0.4
2020-02-13
- 域名更新

#### v1.0.5
2020-06-29
- 代码优化
- 添加数据修复测试类

#### v2.0.1
2020-08-27
- 迁移仓库至：https://github.com/tec-cloud/tec-cloud
- 项目仍以"quote-api"发布

#### v2.1.1
2020-09-13
- release v1.0
- code review
- add some bugs to be fixed next release version

## 贡献者
- ![Candyメ奶糖](https://avatars1.githubusercontent.com/u/25560405?s=64&v=4) Candyメ奶糖（tecchen@aliyun.com）
- ![Candyメ工会](https://avatars0.githubusercontent.com/u/45727341?s=64&v=4) Candyメ工会（10836893@qq.com）

未完待续……  
持续更新……  
感谢支持

#### LICENSE
[【MIT】](LICENSE.md)
