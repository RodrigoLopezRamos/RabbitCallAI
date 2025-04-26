package com.analia.web.rs.vendor;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Location;
import com.analia.common.model.resultset.view.VendorLocationDetailView;
import com.analia.common.util.Base26;
import com.analia.vendor.service.VendorServiceLocal;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/rs/vendor/location")
@ApplicationScoped
public class VendorLocationResource {

@Inject
    private VendorServiceLocal vendorServiceLocal;

    /**
     * @param reqBody
     * @return
     */
    @Path("/save.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response saveVendorLocation(HashMap<String, Object> reqBody) {
        Response response = null;
        try {
            String locationCode = RequestUtils.getStringForKey("locationCode", reqBody);
            String corporateName = RequestUtils.getMandatoryStringForKey("corporateName", reqBody);
            String vendorName = RequestUtils.getMandatoryStringForKey("vendorName", reqBody);
            String locationName = RequestUtils.getMandatoryStringForKey("locationName", reqBody);
            String address1 = RequestUtils.getMandatoryStringForKey("address1", reqBody);
            String address2 = RequestUtils.getStringForKey("address2", reqBody);
            String city = RequestUtils.getMandatoryStringForKey("city", reqBody);
            String province = RequestUtils.getMandatoryStringForKey("province", reqBody);
            String postalCode = RequestUtils.getMandatoryStringForKey("postalCode", reqBody);
            String country = RequestUtils.getMandatoryStringForKey("country", reqBody);
            String phoneNumber = RequestUtils.getMandatoryStringForKey("phoneNumber", reqBody);
            String email = RequestUtils.getMandatoryStringForKey("locationEmail", reqBody);

            double latitude = RequestUtils.getDoubleForKey("latitude", reqBody);
            double longitude = RequestUtils.getDoubleForKey("longitude", reqBody);


            BigInteger locationId = Base26.decode(locationCode);
            Location location = vendorServiceLocal.saveVendorLocation(locationId, corporateName, vendorName, address1, address2, city, province, postalCode, country, phoneNumber, locationName, email, latitude, longitude);

            HashMap<String, Object> result = new HashMap<>();
            result.put("locationCode", Base26.encode(location.getId()));

            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    /**
     * @return
     */
    @Path("/all.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getAllVendorLocations() {
        Response response = null;
        try {
            List<HashMap<String, Object>> listResult = new ArrayList<>();
            List<VendorLocationDetailView> locationList = vendorServiceLocal.getAllVendorLocationDetailViewForVendor();
            for (VendorLocationDetailView vendorLocationDetailView : locationList) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("locationName", vendorLocationDetailView.getVendorLocationName());
                result.put("address1", vendorLocationDetailView.getAddress1());
                result.put("address2", vendorLocationDetailView.getAddress2());
                result.put("city", vendorLocationDetailView.getCityName());
                result.put("province", vendorLocationDetailView.getProvince());
                result.put("postalCode", vendorLocationDetailView.getPostalCode());
                result.put("country", vendorLocationDetailView.getCountry());
                result.put("phoneNumber", vendorLocationDetailView.getPhoneNumber());
                result.put("email", vendorLocationDetailView.getLocationEmail());
                result.put("locationCode", Base26.encode(vendorLocationDetailView.getVendorlocationId()));
                listResult.add(result);
            }
            response = Response.ok(listResult).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

}
