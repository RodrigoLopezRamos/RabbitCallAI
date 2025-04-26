package com.analia.web;

import com.analia.web.rs.authorization.DeviceResource;
import com.analia.web.rs.call.AgentResource;
import com.analia.web.rs.dashboard.DashboardResource;
import com.analia.web.rs.filesystem.FileSystemResource;
import com.analia.web.rs.location.LocationResource;
import com.analia.web.rs.metadata.MetaDataResource;
import com.analia.web.rs.purchase.PurchaseResource;
import com.analia.web.rs.social.SocialResource;
import com.analia.web.rs.trade.TradeResource;
import com.analia.web.rs.user.UserActivityResource;
import com.analia.web.rs.user.UserProfileResource;
import com.analia.web.rs.user.UserResource;
import com.analia.web.rs.vendor.VendorLocationResource;
import com.analia.web.rs.vendor.VendorResource;
import com.analia.web.rs.vendor.VendorVoucherResource;
import com.analia.web.rs.voucher.VoucherResource;
import com.analia.web.rs.webhook.VapiMessageResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rs")
@ApplicationScoped
public class   AnaliaApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(DeviceResource.class);
        resources.add(VoucherResource.class);
        resources.add(TradeResource.class);
        resources.add(UserResource.class);
        resources.add(FileSystemResource.class);
        resources.add(LocationResource.class);
        resources.add(MetaDataResource.class);
        resources.add(UserActivityResource.class);
        resources.add(UserProfileResource.class);
        resources.add(VendorResource.class);
        resources.add(PurchaseResource.class);
        resources.add(VendorLocationResource.class);
        resources.add(VendorVoucherResource.class);
        resources.add(SocialResource.class);
       // resources.add(VideoResource.class);
        resources.add(VapiMessageResource.class);
        resources.add(AgentResource.class);
        resources.add(DashboardResource.class);
    }
}
