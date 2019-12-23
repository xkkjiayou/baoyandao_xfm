package com.xkk.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.xkk.pojo.Product;
import com.xkk.pojo.Trade;
import com.xkk.pojo.User;
import com.xkk.service.PurchaseService;
import com.xkk.util.SessionUtil;
import com.xkk.util.ZFBConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class AlipayController {

    private static AlipayTradeService tradeService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Autowired
    PurchaseService purchaseService;

    @RequestMapping("/pay/{productid}")
    @ResponseBody
    public String pay(@PathVariable("productid") int productid, HttpSession session){

        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            String json="{\"status\":\"no_login\",\"message\":\"111\"}";
            return json;
        }
        Product product = purchaseService.getProductByProductId(productid);

        String tradeno = test_trade_precreate(product);
        Trade trade = new Trade();
        trade.setPrice(product.getPrice());
        trade.setProductid(product.getProductid());
        trade.setUserid(user.getUserid());
        trade.setTradeno(tradeno);

        purchaseService.addTrade(trade);

        String json="{\"status\":\"error\",\"message\":\"111\"}";
        if(tradeno!=null && !tradeno.equals("")) {
            json = "{\"status\":\"success\",\"message\":\"/byd_zfb_qr_code/" + tradeno + ".png\"}";
        }
        return json;
    }

    @RequestMapping("/validate_trade/{productid}")
    @ResponseBody
    public String validate_trade(@PathVariable("productid") int productid,HttpSession session){

        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            String json="{\"status\":\"no_login\",\"message\":\"111\"}";
            return json;
        }
        Trade trade = purchaseService.getTradeByProductid(productid,user.getUserid());

        String json="{\"status\":\"error\",\"message\":\"111\"}";
        if(trade!=null ) {

            String downloadurl = purchaseService.getProductByProductId(trade.getProductid()).getDownloadurl();
            json = "{\"status\":\"success\",\"message\":\""+downloadurl+"\"}";
        }
        return json;
    }

    public String test_trade_precreate(Product product) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "tradeprecreate" + System.currentTimeMillis()
                + (long) (Math.random() * 10000000L);
        System.out.println(outTradeNo);
        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = product.getProductname();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(product.getPrice());

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = product.getProductname();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(ZFBConfig.pid);

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 2, 2);
        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://todaydream.cn/alipay_return")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                System.out.println("支付宝预下单成功:");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format("C:/byd_zfb_qr_code/%s.png",
                        response.getOutTradeNo());
                System.out.println("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;

            case FAILED:

                System.out.println(result.getResponse().getBody());
                System.out.println("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                System.out.println("系统异常，预下单状态未知!!!");
                break;

            default:
                System.out.println("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return outTradeNo;
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            System.out.println("code:"+response.getCode()+"--msg:"+response.getMsg());
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                System.out.println("subCode:"+response.getSubCode()+"--subMsg:"+response.getSubMsg());
            }
            System.out.println("body:"+response.getBody());
        }
    }

    /**
     * 扫码付异步结果通知
     * https://docs.open.alipay.com/194/103296
     * @param request
     */
    @RequestMapping("/alipay_return")
    @ResponseBody
    public String notify(HttpServletRequest request) throws AlipayApiException {
        // 一定要验签，防止黑客篡改参数
        System.out.println("进入回调接口，购买成功");
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        parameterMap.forEach((key, value) -> System.out.println(key+"="+value[0]));

        // https://docs.open.alipay.com/54/106370
        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        String out_trade_no = params.get("out_trade_no");
        System.out.println("out_trade_no:===："+out_trade_no);

        int rs = purchaseService.updateTradeStatus(out_trade_no);
        System.out.println("zfb_result");
        return "success";
//
//        boolean flag = AlipaySignature.rsaCheckV1(params,
//                ZFBConfig.alipay_public_key,
//                "GBK",
//                ZFBConfig.sign_type
//        );
//        System.out.println(flag);
//        if (flag) {
//            /**
//             * TODO 需要严格按照如下描述校验通知数据的正确性
//             *
//             * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
//             * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
//             * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
//             *
//             * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
//             * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
//             * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
//             *
//             **/
//            String out_trade_no = (String) requestParams.get("out_trade_no");
//            System.out.println("out_trade_no:===："+out_trade_no);
//            purchaseService.updateTradeStatus(out_trade_no);
//            map.put("status","支付成功");
//            return "zfb_result";
//        }

    }




}