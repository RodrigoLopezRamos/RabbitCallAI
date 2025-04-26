package com.analia.voucher.service;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.location.GeoLocation;
import com.analia.common.model.*;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.util.ArrayUtils;
import com.analia.common.util.PaginatedArrayList;
import com.analia.filters.core.FilterLogic;
import com.analia.filters.core.FilterLogicLocal;
import com.analia.media.core.FileSystemCore;
import com.analia.media.core.FileSystemCoreLocal;

import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.impl.SettingsService;
import com.analia.user.core.UserCore;
import com.analia.voucher.core.VoucherCore;
import com.analia.voucher.core.VoucherCoreLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class VoucherService implements VoucherServiceLocal {
    public static final int PAGE_SIZE = 10;

    @Inject
    private VoucherCore voucherCoreLocal;

    @Inject
    private FileSystemCore fileSystemCoreLocal;

    @Inject
    private SettingsService settingsServiceLocal;

    @Inject
    private FilterLogic filterLogicLocal;

    @Inject
    private UserCore userCoreLocal;


    public List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, int page, int sortTypeId, BigInteger categoryId, Integer pageSize, List<Filter> filters) throws AnaliaException {
        UserLocation userLocation = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_LOCATION, UserLocation.class);

        List<VoucherResultSet> vouchers = null;
        if (categoryId == null) {
            vouchers = voucherCoreLocal.getVouchers(voucherTypeId, userLocation.getZoneId());
        } else {
            vouchers = voucherCoreLocal.getVouchers(voucherTypeId, categoryId, userLocation.getZoneId());
        }
        if (filters != null && !filters.isEmpty()) {
            for (Filter filter : filters) {
                if (filter.getFilterTypeId() != Constants.FILTER_TYPE_ID_VOUCHER) ;
                {
                    throw new AnaliaException(ExceptionCode.BAD_REQUEST, "Filter is not allowed for this operation");
                }
            }
            vouchers = filterLogicLocal.doFilter(vouchers, filters);
        }
        vouchers = GeoLocation.getDistances(vouchers, userLocation.getLatitude(), userLocation.getLongitude());
        switch (sortTypeId) {
            case Constants.SORT_TYPE_DATE:
                vouchers = ArrayUtils.sortByDistance(vouchers);
                break;

            case Constants.SORT_TYPE_DISTANCE:
                vouchers = ArrayUtils.sortByDistance(vouchers);
                break;

            default:
                throw new AnaliaException(ExceptionCode.SERVER_ERROR, "SortType is not defined ! Baboso!");
        }

        PaginatedArrayList<VoucherResultSet> paginatedArrayList = new PaginatedArrayList<>(vouchers, pageSize == null ? PAGE_SIZE : pageSize, page);
        List<VoucherResultSet> result = paginatedArrayList.getCurrentPage();
        VoucherResultSet[] vrResultSets = new VoucherResultSet[result.size()];
        this.userCoreLocal.applyUserTrending(userLocation.getUserId(), result.toArray(vrResultSets));
        return result;
    }


    public int totalVoucherCountByZoneId(BigInteger categoryTypeId, BigInteger categoryId) throws AnaliaException {
        UserLocation userLocation = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_LOCATION, UserLocation.class);
        return voucherCoreLocal.totalVoucherCountByZoneId(categoryTypeId, categoryId == null ? new BigInteger("2") : categoryId, userLocation.getZoneId());
    }


    public Voucher getVoucherByVoucherVendorLocationId(BigInteger voucherVendorLocationId) throws AnaliaException {
        VoucherVendor voucherVendor = voucherCoreLocal.getVoucherVendor(voucherVendorLocationId);
        if (voucherVendor == null) {
            return null;
        }
        return voucherCoreLocal.getVoucherById(voucherVendor.getVoucherId());
    }


    public Voucher saveVoucher(Voucher voucher) throws AnaliaException {
        return voucherCoreLocal.saveVoucher(voucher);
    }


    public VoucherCategory saveVoucherCategory(VoucherCategory voucherCategory) throws AnaliaException {
        return voucherCoreLocal.saveVoucherCategory(voucherCategory);
    }


    public List<VoucherResultSet> getVouchersByVendorLocationId(BigInteger vendorLocationId, BigInteger voucherTypeId, BigInteger categoryId, int page, Integer pageSize, Integer sortType) throws AnaliaException {
        return voucherCoreLocal.getVouchersByVendorLocationId(vendorLocationId, voucherTypeId, categoryId);
    }


    public VoucherVendor getVoucherVendorByVendorId(BigInteger voucherVendorId) throws AnaliaException {
        return voucherCoreLocal.getVoucherVendor(voucherVendorId);
    }


    public List<VoucherType> getVoucherTypes() throws AnaliaException {
        return voucherCoreLocal.getVoucherTypes();
    }


    public VoucherDetailView getVoucherDetailViewByVoucherVendorLocationId(BigInteger voucherVendorLocationId) throws AnaliaException {
        return voucherCoreLocal.getVoucherDetailViewByVendorLocationVoucherId(voucherVendorLocationId);
    }


    public List<VoucherDetailView> getVouchersByVendor() throws AnaliaException {
        return null;
    }


    public BigInteger saveVoucher(BigInteger vendorLocationVoucherId, BigInteger categoryId, BigDecimal price, BigInteger voucherTypeId, BigInteger zoneId, String voucherProvider, String title, String shortDescription, String description, String website, BigDecimal retailValue, Date notBefore, Date notAfter, Date startDate, Date endDate, BigInteger maxInventory, BigInteger maximunPerUser, BigInteger statusId, String termsUrl, String finePrint, boolean disabled, String instructions, BigInteger taxGroupId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Vendor vendor = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
        VendorLocation vendorLocation = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_LOCATION, VendorLocation.class);

        VoucherDetailView voucherDetailView = vendorLocationVoucherId == null ? null : voucherCoreLocal.getVoucherDetailViewByVendorLocationVoucherId(vendorLocationVoucherId);

        Voucher voucher = voucherDetailView == null ? new Voucher() : voucherCoreLocal.getVoucherById(voucherDetailView.getVoucherId()) == null ? new Voucher() : voucherCoreLocal.getVoucherById(voucherDetailView.getVoucherId());
        voucher.setDescription(description);
        voucher.setDirectoryId(voucherDetailView == null ? fileSystemCoreLocal.saveDirectory(BigInteger.ZERO, user.getId(), null, vendor.getId()).getId() : voucher.getDirectoryId());
        voucher.setDisabled(disabled);
        voucher.setFinePrint(finePrint);
        voucher.setMaximunPerUser(maximunPerUser);
        voucher.setMaxInventory(maxInventory);
        voucher.setNotAfter(notAfter);
        voucher.setNotBefore(notBefore);
        voucher.setStartDate(startDate);
        voucher.setEndDate(endDate);
        voucher.setPrice(price);
        voucher.setTitle(title);
        voucher.setProcessingFeePercent(voucherDetailView == null ? BigDecimal.valueOf(settingsServiceLocal.getDoubleValueForSettingKey(SettingsCore.SYSTEM_PROCESSING_FEE_PERCENTAGE)) : voucher.getProcessingFeePercent());
        voucher.setRetailValue(retailValue);
        voucher.setShortDescription(shortDescription);
        voucher.setStatusId(statusId);
        voucher.setStatusChangedDate(new Date());
        voucher.setStatusChangedBy(user.getId());
        voucher.setTermsUrl(termsUrl);
        voucher.setVoucherProvider(voucherProvider);
        voucher.setVoucherTypeId(voucherTypeId);
        voucher.setWebsite(website);
        voucher.setZoneId(zoneId);
        voucher.setPurchaseCreditsReward(BigInteger.valueOf(0));
        voucher.setUnlockingCredits(BigInteger.valueOf(0));
        voucherCoreLocal.saveVoucher(voucher);

        VoucherCategory voucherCategory = voucherDetailView == null ? new VoucherCategory() : voucherCoreLocal.getVoucherCategory(voucherDetailView.getVoucherCategoryId());
        voucherCategory.setVoucherId(voucher.getId());
        voucherCategory.setCategoryId(categoryId);
        voucherCategory.setCreatedDatetime(voucherDetailView == null ? new Date() : voucherCategory.getCreatedDatetime());
        voucherCoreLocal.saveVoucherCategory(voucherCategory);

        VoucherVendor voucherVendor = voucherDetailView == null ? new VoucherVendor() : voucherCoreLocal.getVoucherVendor(voucherDetailView.getVouchervendorId());
        voucherVendor.setInstructions(instructions);
        voucherVendor.setRelativePercentage(voucherDetailView == null ? new BigDecimal("90") : voucherDetailView.getProcessingFeePercent());
        voucherVendor.setStatusChangedBy(user.getId());
        voucherVendor.setTaxGroupId(taxGroupId);
        voucherVendor.setVoucherCategoryId(voucherCategory.getId());
        voucherVendor.setVoucherPercent(voucherDetailView == null ? BigDecimal.valueOf(settingsServiceLocal.getDoubleValueForSettingKey(SettingsCore.SYSTEM_VOUCHER_FEE_PERCENTAGE)) : voucherVendor.getVoucherPercent());
        voucherVendor.setVendorId(vendor.getId());
        voucherVendor.setVerification(true);
        voucherVendor.setStatusId(statusId);
        voucherVendor.setVoucherId(voucher.getId());
        voucherCoreLocal.saveVoucherVendor(voucherVendor);

        VendorLocationVoucher vendorLocationVoucher = voucherDetailView == null ? new VendorLocationVoucher() : voucherCoreLocal.getVoucherVendorLocation(voucherDetailView.getVendorLocationVoucherId());
        vendorLocationVoucher.setVendorLocationId(vendorLocation.getId());
        vendorLocationVoucher.setVoucherVendorId(voucherVendor.getId());
        vendorLocationVoucher.setCreatedBy(voucherDetailView == null ? user.getId() : vendorLocationVoucher.getCreatedBy());
        vendorLocationVoucher.setDisabled(false);
        voucherCoreLocal.saveVendorLocationVoucher(vendorLocationVoucher);

        if (voucherDetailView != null) {
            if (voucherVendor.getVendorId() != vendor.getId()) {
                throw new AnaliaException(ExceptionCode.INVALID_OPERATION, "You cannot modify chatbot from another vendor");
            }
        }
        return vendorLocationVoucher.getId();
    }


    public BigInteger saveVoucher(BigInteger vendorLocationVoucherId, BigInteger categoryId, BigDecimal price, BigInteger voucherTypeId, BigInteger zoneId, String voucherProvider, String title, String shortDescription, String description, String website, BigDecimal retailValue, Date notBefore, Date notAfter, Date startDate, Date endDate, BigInteger maxInventory, BigInteger maximunPerUser, BigInteger statusId, String termsUrl, String finePrint, boolean disabled, String instructions, BigInteger taxGroupId, BigInteger vendorId, BigInteger vendorLocationId) throws AnaliaException {
        VoucherDetailView voucherDetailView = vendorLocationVoucherId == null ? null : voucherCoreLocal.getVoucherDetailViewByVendorLocationVoucherId(vendorLocationVoucherId);

        Voucher voucher = voucherDetailView == null ? new Voucher() : voucherCoreLocal.getVoucherById(voucherDetailView.getVoucherId()) == null ? new Voucher() : voucherCoreLocal.getVoucherById(voucherDetailView.getVoucherId());
        voucher.setDescription(description);
        voucher.setDirectoryId(voucherDetailView == null ? fileSystemCoreLocal.saveDirectory(BigInteger.ZERO, BigInteger.ONE, null, vendorId).getId() : voucher.getDirectoryId());
        voucher.setDisabled(disabled);
        voucher.setFinePrint(finePrint);
        voucher.setMaximunPerUser(maximunPerUser);
        voucher.setMaxInventory(maxInventory);
        voucher.setNotAfter(notAfter);
        voucher.setNotBefore(notBefore);
        voucher.setStartDate(startDate);
        voucher.setEndDate(endDate);
        voucher.setPrice(price);
        voucher.setTitle(title);
        voucher.setProcessingFeePercent(voucherDetailView == null ? BigDecimal.valueOf(settingsServiceLocal.getDoubleValueForSettingKey(SettingsCore.SYSTEM_PROCESSING_FEE_PERCENTAGE)) : voucher.getProcessingFeePercent());
        voucher.setRetailValue(retailValue);
        voucher.setShortDescription(shortDescription);
        voucher.setStatusId(statusId);
        voucher.setStatusChangedDate(new Date());
        voucher.setStatusChangedBy(BigInteger.ONE);
        voucher.setTermsUrl(termsUrl);
        voucher.setVoucherProvider(voucherProvider);
        voucher.setVoucherTypeId(voucherTypeId);
        voucher.setWebsite(website);
        voucher.setZoneId(zoneId);
        voucher.setPurchaseCreditsReward(BigInteger.valueOf(0));
        voucher.setUnlockingCredits(BigInteger.valueOf(0));
        voucherCoreLocal.saveVoucher(voucher);

        VoucherCategory voucherCategory = voucherDetailView == null ? new VoucherCategory() : voucherCoreLocal.getVoucherCategory(voucherDetailView.getVoucherCategoryId());
        voucherCategory.setVoucherId(voucher.getId());
        voucherCategory.setCategoryId(categoryId);
        voucherCategory.setCreatedDatetime(voucherDetailView == null ? new Date() : voucherCategory.getCreatedDatetime());
        voucherCoreLocal.saveVoucherCategory(voucherCategory);

        VoucherVendor voucherVendor = voucherDetailView == null ? new VoucherVendor() : voucherCoreLocal.getVoucherVendor(voucherDetailView.getVouchervendorId());
        voucherVendor.setInstructions(instructions);
        voucherVendor.setRelativePercentage(voucherDetailView == null ? new BigDecimal("90") : voucherDetailView.getProcessingFeePercent());
        voucherVendor.setStatusChangedBy(BigInteger.ONE);
        voucherVendor.setTaxGroupId(taxGroupId);
        voucherVendor.setVoucherCategoryId(voucherCategory.getId());
        voucherVendor.setVoucherPercent(voucherDetailView == null ? BigDecimal.valueOf(settingsServiceLocal.getDoubleValueForSettingKey(SettingsCore.SYSTEM_VOUCHER_FEE_PERCENTAGE)) : voucherVendor.getVoucherPercent());
        voucherVendor.setVendorId(vendorId);
        voucherVendor.setVerification(true);
        voucherVendor.setStatusId(statusId);
        voucherVendor.setVoucherId(voucher.getId());
        voucherCoreLocal.saveVoucherVendor(voucherVendor);

        VendorLocationVoucher vendorLocationVoucher = voucherDetailView == null ? new VendorLocationVoucher() : voucherCoreLocal.getVoucherVendorLocation(voucherDetailView.getVendorLocationVoucherId());
        vendorLocationVoucher.setVendorLocationId(vendorLocationId);
        vendorLocationVoucher.setVoucherVendorId(voucherVendor.getId());
        vendorLocationVoucher.setCreatedBy(voucherDetailView == null ? BigInteger.ONE : vendorLocationVoucher.getCreatedBy());
        vendorLocationVoucher.setDisabled(false);
        voucherCoreLocal.saveVendorLocationVoucher(vendorLocationVoucher);
        return vendorLocationVoucher.getId();
    }


    public List<Tax> getTaxNames() throws AnaliaException {
        return voucherCoreLocal.getTaxes();
    }

    /**
     *
     */

    public List<Voucher> getVouchersBoughtInPurchaseForVoucherId(BigInteger voucherId) throws AnaliaException {
        return voucherCoreLocal.getVouchersBoughtInPurchaseForVoucherId(voucherId);
    }


    public List<VoucherResultSet> getVouchers(String querySearchParam, int page, int pageSize) throws AnaliaException {
        return voucherCoreLocal.getVouchers(querySearchParam);
    }


    public Voucher getVoucherByExternalId(BigInteger externalId, BigInteger vendorId) throws AnaliaException {
        return voucherCoreLocal.getVoucherByExternalId(externalId, vendorId);
    }
}
