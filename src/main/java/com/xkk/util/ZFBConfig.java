package com.xkk.util;

import org.springframework.context.annotation.Configuration;

@Configuration
//@ImportResource(value="classpath:zfbinfo.properties")
public class ZFBConfig {
    //    以下配置可以写死，也可从属性文件中取出均可
//    # 支付宝网关名、partnerId和appId
//    @Value("${open_api_domain}")
    public static final String open_api_domain="https://openapi.alipaydev.com/gateway.do";
//    @Value("${mcloud_api_domain}")
    public static final String mcloud_api_domain = "http://mcloudmonitor.com/gateway.do";
//    @Value("${pid}")
    public static final String pid = "2088912482918293";
//    @Value("${appid}")
    public static final String appid = "2021001100687216";

    //    # RSA私钥、公钥和支付宝公钥
//    @Value("${private_key}")
    public static final String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJ7vVczXAgvpf475JnY84MW/tM/uR+CG6HJKARklUYUT1/25sbV4hKVlPcFZa/KHqq3U3s0nP4NYaz82idMksyPIBJ0nOQgytQ8GDsIrCoiIZq4ftU/zeS67d/oVe55X7MkLiD3gNkAL8qcZD3rQYytC+2Cqovdo8c+dkW169fpOCQhdaUrJa/Xbracv522HPTLZRlwRAtU23mEBs6+GIVFmrJ4ASuqq37RQc1fDkQc5NjU9kuehGn2BVHIMIvXc3CDU32cl8scfMaGRipjoHVRL37gM7gVPHdcnC0EhYaUgzVbw7QFLTH0yWGUjQN3qcUd2I2e+fPgHXHUpHeiezxAgMBAAECggEAIaywzC4SQdOm4kOJZaZ2KQd5y+hB0Ga1j+His6t0d16s49KKdi/NsZ/d0jjjb7NfaXkIUrLx1fWPJhk0W+qUMad3OVhYH0RQh+neL+LFFQXXbKGM2SrKaLzYV/de+sgzsgd8TblLVXTubDSOGU+IVhJIVpJGuS2LRijANBf5zfgT3Jz+X5TecMFf0ZUOBke2as6cqYspc1sNDqJ6bhY9Sx7Ho4AM4deYndsD31a4rC/i1uhejtG9zUbypWqK9zaukUA7A63x4Ryeckx87AflTdVXREOMczjAwB8doSlLv2zQ5M9zTFoYP0fHjyMX3d6NqYCl305ZDRaBGGCGSS0NtQKBgQDza0isvJ+CeOcjUFeuddrzah3Ul7+9JfLI/OEljtW6ReWRJzKyUzRfRU99f81gFfyXG/l8SWHQyYptSXRL+4+QmGAJUUxb1ziUVxp9aayybGa6765rjerwOAmqEbKOY5+gCagcgIZ3kNXNai6Al0CtJ/laeUgRyqIDJ0QW8mJs+wKBgQDUXsV9sCeMNuoC9mv+YxiEN2Dy73to8QT0C1Kt+2Lf/rFcAaUXSOAyPXmhu/5ZG/6n+xptl3v6VqlK2VC5h0X2tIneTuEQNvje/HR4bfARSLbqP9/FDUkMtIVV43JReGxJS0n0NnWbRsbXKcfDFoW4VeSGRrIbIf7KubD009sSAwKBgEkAyPf9Tf1sUy3VH6bT88mC7tVeeYO5yNmcdc20y8akxdfFoL+4koFsIQ3rcWW60llja2xp+asLdreWCwnqtUDRYiQJPNRidLaQdQJd2gxc1GaGEZJyKp7Gv6dAzYNEv4/YtZFuvmVHi8eia7XnJt+HmJIYw5OKaRJ3CUW0he5XAoGBAMHOOyyYtX6gHq7Vc3iTtqvyXedV5am/SmXsb2eXIoSIShNM7Pxy5Sb0MywGA/gONDfYUCXzOJEcxSIpcCHf8VlTKP5XvcyuXjAg86lzqsIbOdJMuEl79k5B7+yRxKHU4/1/V94mVO1/ibaRylufEilHOWXQ3nkkbQFUtMi5DhNBAoGBAOnqyyCIf4VcCHwiuGx8al5DdyBUZQKrxzcQZrHD6ZP1v0t4mxSkJPzYP8m+wqsK/ilHKV0G47sYIEHBM4ITjvRH284AQqdJmzyujiHBzm4HZiPe5dboJ7HBH4CQCYXd9yls/z/BS0qm6evKTQaHrFVn5S/dnlKv5g/NN3Yv9s2a";
//    @Value("${public_key}")
    public static final String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAye71XM1wIL6X+O+SZ2PODFv7TP7kfghuhySgEZJVGFE9f9ubG1eISlZT3BWWvyh6qt1N7NJz+DWGs/NonTJLMjyASdJzkIMrUPBg7CKwqIiGauH7VP83kuu3f6FXueV+zJC4g94DZAC/KnGQ960GMrQvtgqqL3aPHPnZFtevX6TgkIXWlKyWv1262nL+dthz0y2UZcEQLVNt5hAbOvhiFRZqyeAErqqt+0UHNXw5EHOTY1PZLnoRp9gVRyDCL13Nwg1N9nJfLHHzGhkYqY6B1US9+4DO4FTx3XJwtBIWGlIM1W8O0BS0x9MlhlI0Dd6nFHdiNnvnz4B1x1KR3ons8QIDAQAB";
//    #SHA1withRsa对应支付宝公钥

//    @Value("${alipay_public_key}")
    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjNXsVTFA9SFp72nAuDyEa31YDTqXhBOfWq1Aj0SFnIFoBm7vMD13t7warM169ZFwNFGExfU8iD5Gq+BFOkegcqfHZmgSNouyE+T5dWH3njCpfhDZsQArJBCqNVFOu47Z4ekIc9upug0d7Rth1SSsOKLlJ9jqTDS73FxgkjTlFRO29CoSTMoR+LekkSSuRarSdhhis6ezQnLPSNREh9eK3coSYWQhM2zxFL9ZjHXMyay8a3F14Q8C89GwmWFTOQiv2yEe+CadsqsZfaxdXBDi17Uqi8nMw303QjgEMGaXeTi2zH0XEAloKwFLDN81ygcHF42WXEuQ44iY2l1aYsheAQIDAQAB";
    //    #SHA256withRsa对应支付宝公钥

    //    # 签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa
//    @Value("${sign_type}")
    public static final String sign_type = "RSA2";
//    ********************************************以下不用暂时注释掉*********************************************
//    # 当面付最大查询次数和查询间隔（毫秒）
    /*max_query_retry = 5
    query_duration = 5000

    # 当面付最大撤销次数和撤销间隔（毫秒）
    max_cancel_retry = 3
    cancel_duration = 2000

    # 交易保障线程第一次调度延迟和调度间隔（秒）
    heartbeat_delay = 5
    heartbeat_duration = 900*/


}
