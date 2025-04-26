package com.analia.web.rs.metadata;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Category;
import com.analia.common.model.resultset.ReviewResultSet;
import com.analia.common.util.Base26;
import com.analia.metadata.service.MetaDataServiceLocal;
import com.analia.web.rs.AnaliaResource;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/metadata")
@ApplicationScoped
public class MetaDataResource extends AnaliaResource {
    @Inject
    private MetaDataServiceLocal metaDataServiceLocal;

    @POST
    @Path("/reviews.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getReviews(Map<String, Object> input) {
        Response response = null;
        try {
            BigInteger tradeId = RequestUtils.getStringForKey("tradeLocationCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("tradeLocationCode", input)) : null;
            BigInteger voucherId = RequestUtils.getStringForKey("voucherVendorLocationCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("voucherVendorLocationCode", input)) : null;
            Integer page = RequestUtils.getIntegerForKey("page", input);
            Integer pageSize = RequestUtils.getIntegerForKey("pageSize", input);

            if (voucherId == null && tradeId == null) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY);
            }
            List<ReviewResultSet> reviews = metaDataServiceLocal.getReviewsByTradeOrVoucherId(tradeId, voucherId, page, pageSize);
            List<HashMap<String, Object>> resultList = new ArrayList<>();
            for (ReviewResultSet reviewResultSet : reviews) {
                HashMap<String, Object> jsonReview = new HashMap<>();
                jsonReview.put("scoreGiven", reviewResultSet.scoreGiven);
                jsonReview.put("text", reviewResultSet.text);
                jsonReview.put("sequenceNumber", reviewResultSet.sequenceNumber);
                jsonReview.put("userName", reviewResultSet.name);
                jsonReview.put("userCode", Base26.encode(BigInteger.valueOf(reviewResultSet.userid)));
                resultList.add(jsonReview);
            }
            response = Response.ok(resultList).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/create-review.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createReview(Map<String, Object> input) {
        Response response = null;
        try {
            BigInteger tradeId = RequestUtils.getStringForKey("tradeLocationCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("tradeLocationCode", input)) : null;
            BigInteger voucherId = RequestUtils.getStringForKey("voucherVendorLocationCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("voucherVendorLocationCode", input)) : null;
            String text = RequestUtils.getMandatoryStringForKey("text", input);
            int scoreGiven = RequestUtils.getMandatoryIntForKey("scoreGiven", input);
            if (voucherId == null && tradeId == null) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY);
            }
            metaDataServiceLocal.saveReview(tradeId, voucherId, scoreGiven, text);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/categories.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getCategoriesByCategoryTypeId(Map<String, Object> input) {
        Response response = null;
        try {
            BigInteger categoryTypeId = Base26.decode(RequestUtils.getMandatoryStringForKey("categoryTypeCode", input));
            List<Category> categories = metaDataServiceLocal.getCategoriesByCategoryTypeId(categoryTypeId);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Category category : categories) {
                HashMap<String, Object> jsonCategory = new HashMap<>();
                jsonCategory.put("categoryCode", Base26.encode(category.getId()));
                jsonCategory.put("categoryTypeCode", Base26.encode(category.getCategoryTypeId()));
                jsonCategory.put("directoryCode", Base26.encode(category.getDirectoryId()));
                jsonCategory.put("categoryName", category.getName());
                jsonCategory.put("disabled", category.isDisabled());
                result.add(jsonCategory);
            }
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }
}
