package com.analia.web.rs.chatbot;//package com.analia.web.rs.chatbot;
//
//import com.analia.common.exception.AnaliaException;
//import com.analia.common.model.City;
//import com.analia.common.model.Story;
//import com.analia.common.model.resultset.TradeResultSet;
//import com.analia.common.model.resultset.VoucherResultSet;
//import com.analia.common.util.Base26;
//import com.analia.location.service.LocationServiceLocal;
//import com.analia.media.service.FileSystemServiceLocal;
//import com.analia.metadata.persistence.CategoryFacade;
//import com.analia.notification.service.NotificationServiceLocal;
//import com.analia.social.service.SocialServiceLocal;
//import com.analia.trade.service.TradeServiceLocal;
//import com.analia.voucher.service.VoucherServiceLocal;
//import com.analia.web.rs.AnaliaResource;
//import com.analia.web.util.RequestUtils;
//import com.analia.web.util.ResponseUtils;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Path("/rs/chatbot")
//public class ChatBotResource extends AnaliaResource {
//
//
//    private static final String PROJECT_ID = "panda-217701";
//@Inject
//    ChatBotServiceLocal chatBotService;
//@Inject
//    VoucherServiceLocal voucherServiceLocal;
//@Inject
//    TradeServiceLocal tradeServiceLocal;
//@Inject
//    LocationServiceLocal locationServiceLocal;
//@Inject
//    FileSystemServiceLocal fileSystemServiceLocal;
//@Inject
//    UserServiceLocal userServiceLocal;
//@Inject
//    NotificationServiceLocal notificationServiceLocal;
//@Inject
//    UserActivityServiceLocal userActivityServiceLocal;
//@Inject
//    SocialServiceLocal socialServiceLocal;
//    CategoryFacade c;
//    private int DEFAULT_PAGE = 0;
//    private int DEFAULT_PAGE_SIZE = 100;
//
//    /**
//     * @param input
//     * @return
//     */
//    @POST
//    @Path("/talk.s")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Response talk(Map<String, Object> input) throws Exception {
//        Response response = null;
//        String phrase = RequestUtils.getMandatoryStringForKey("phrase", input);
//        String userCode = RequestUtils.getMandatoryStringForKey("userCode", input);
//        BigInteger userId = Base26.decode(userCode);
//        try {
//            userActivityServiceLocal.chat(userId, phrase);
//            response = Response.ok(ResponseUtils.createSucessResponse()).build();
//        } catch (AnaliaException e) {
//            response = ResponseUtils.buildErrorResponse(e);
//        }
//        return response;
//    }
//
//
//    /**
//     * {
//     * "fulfillmentText": "the effect or effects of this strain is happy,relaxed,euphoric,uplifted,creative, some other question",
//     * "fulfillmentMessages": [
//     * {
//     * "text": {
//     * "text": [
//     * "the effect or effects of this strain is happy,relaxed,euphoric,uplifted,creative, some other question"
//     * ]
//     * }
//     * }
//     * ],
//     * "source": "webhook"
//     * }
//     *
//     * @param input
//     * @return
//     */
//    @POST
//    @Path("/webhook")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Response webhook(String input) throws Exception {
//        Response response = null;
//        WebhookRequest.Builder builder = WebhookRequest.newBuilder();
//        JsonFormat.parser().merge(input, builder);
//        WebhookRequest webhookRequest = builder.build();
//
//        Context context = webhookRequest.getQueryResult().getOutputContexts(0);
//
//        Value value = context.getParameters().getFieldsOrThrow("strains");
//        String querySearchParam = value.getStringValue();
//
//        HashMap<String, Object> result = new HashMap<>();
//        List<HashMap<String, Object>> resultListTrades = new ArrayList<>();
//        List<HashMap<String, Object>> resultListVoucher = new ArrayList<>();
//        List<HashMap<String, Object>> resultListCities = new ArrayList<>();
//        List<HashMap<String, Object>> resultListStories = new ArrayList<>();
//        try {
//            List<VoucherResultSet> vouchers = voucherServiceLocal.getVouchers(querySearchParam, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
//            for (VoucherResultSet voucherResultSet : vouchers) {
//                HashMap<String, Object> voucherJSON = new HashMap<>();
//                voucherJSON.put("voucherLocationCode", Base26.encode(voucherResultSet.getId()));
//                voucherJSON.put("voucherTitle", voucherResultSet.getTitle());
//                voucherJSON.put("voucherCategoryName", voucherResultSet.getCategoryName());
//                voucherJSON.put("voucherCategoryCode", Base26.encode(voucherResultSet.getCategoryId()));
//                voucherJSON.put("media", buildResponseForMedias(fileSystemServiceLocal, voucherResultSet.getDirectoryId()));
//                resultListVoucher.add(voucherJSON);
//            }
//        } catch (AnaliaException a) {
//            a.printStackTrace();
//        }
//
//        try {
//            List<TradeResultSet> trades = tradeServiceLocal.getTrades(querySearchParam, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
//            for (TradeResultSet tradeResultSet : trades) {
//                HashMap<String, Object> tradeJSON = new HashMap<>();
//                tradeJSON.put("tradeLocationCode", Base26.encode(tradeResultSet.getId()));
//                tradeJSON.put("tradeTitle", tradeResultSet.getTitle());
//                tradeJSON.put("tradeDescription", tradeResultSet.getDescription());
//                tradeJSON.put("media", buildResponseForMedias(fileSystemServiceLocal, tradeResultSet.getDirectoryId()));
//                resultListTrades.add(tradeJSON);
//            }
//        } catch (AnaliaException a) {
//            a.printStackTrace();
//        }
//
//        try {
//            List<City> cities = locationServiceLocal.getCities(querySearchParam);
//            for (City city : cities) {
//                HashMap<String, Object> cityJSON = new HashMap<>();
//                cityJSON.put("locationCode", Base26.encode(city.getId()));
//                cityJSON.put("zoneCode", Base26.encode(city.getZoneId()));
//                cityJSON.put("name", city.getName());
//                resultListCities.add(cityJSON);
//            }
//        } catch (AnaliaException a) {
//            a.printStackTrace();
//        }
//        try {
//
//            List<Story> stories = socialServiceLocal.searchStory(querySearchParam, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
//            for (Story story : stories) {
//                HashMap<String, Object> storyJSON = new HashMap<>();
//                storyJSON.put("storyCode", Base26.encode(story.getId()));
//                storyJSON.put("storyTitle", story.getTitle());
//                storyJSON.put("name", story.getName());
//                storyJSON.put("description", story.getDescription());
//                resultListStories.add(storyJSON);
//            }
//
//        } catch (AnaliaException a) {
//            a.printStackTrace();
//        }
//        result.put("stories", resultListStories);
//        result.put("vouchers", resultListVoucher);
//        result.put("trades", resultListTrades);
//        result.put("cities", resultListCities);
//
//        String json = new Gson().toJson(result);
//
//        WebhookResponse webhookResponse = WebhookResponse.newBuilder()
//                .setFulfillmentText(json)
//                .setSource("webhook")
//                .build();
//        response = Response.ok(JsonFormat.printer().print(webhookResponse)).build();
//        return response;
//    }
//}
