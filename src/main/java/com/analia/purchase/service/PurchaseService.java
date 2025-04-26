package com.analia.purchase.service;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.model.resultset.ShoppingCartResultSet;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.util.Base26;
import com.analia.common.util.PseudoUniqueCodeUtils;
import com.analia.common.util.PseudoUniqueCodeUtils.PseudoUniqueCode;
import com.analia.location.core.LocationCoreLocal;
import com.analia.media.core.FileSystemCoreLocal;
import com.analia.media.core.FileSystemCore.FileType;
import com.analia.purchase.core.PurchaseCoreLocal;
import com.analia.purchase.service.PurchaseServiceLocal;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.impl.SettingsService;
import com.analia.user.core.UserActivityCore;
import com.analia.user.core.UserCore;
import com.analia.vendor.core.VendorCoreLocal;
import com.analia.voucher.core.VoucherCoreLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Session Bean implementation class PurchaseService
 */
@ApplicationScoped
public class PurchaseService implements PurchaseServiceLocal {
    private static final String ITEM = "<tr class=\"item\"><td>${voucher.name}</td><td>${voucher.price}</td></tr>";
    @Inject
    private PurchaseCoreLocal purchaseCoreLocal;
    @Inject
    private UserCore userCoreLocal;
    @Inject
    private VoucherCoreLocal voucherCoreLocal;
    @Inject
    private FileSystemCoreLocal fileSystemCoreLocal;
    @Inject
    private VendorCoreLocal vendorCoreLocal;
    @Inject
    private LocationCoreLocal locationCoreLocal;
    @Inject
    private UserActivityCore userActivityCoreLocal;
    @Inject
    private SettingsService settingsServiceLocal;


    public List<ShoppingCartResultSet> getPurchaseSummary(boolean creditUsed) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        List<ShoppingCartResultSet> voucherDetailViews = userCoreLocal.getShoppingCarts(user.getId());

        System.out.println("USER  ID "+user.getId());

        if (voucherDetailViews == null || voucherDetailViews.isEmpty()) {
            throw new AnaliaException(ExceptionCode.SHOPPING_CART_IS_EMPTY, "Shopping Cart is empty");
        }

        BigInteger[] vouchersIds = getVoucherIds(voucherDetailViews);
        BigInteger[] remainAllowces = getRemainingAllowanceVoucherByUser(vouchersIds);

        BigDecimal grandSubTotal = new BigDecimal(0);
        BigDecimal grandTaxToPaid = new BigDecimal(0);
        BigDecimal grandTotal = new BigDecimal(0);

        int i = 0;
        for (ShoppingCartResultSet shoppingCartResultSet : voucherDetailViews) {
            VoucherDetailView voucherDetailView = shoppingCartResultSet.getVoucherDetailView();

            BigInteger remainingInventory = voucherDetailView.getMaxInventory().subtract(voucherDetailView.getSold());
            BigInteger remainingAllowance = remainAllowces != null ? remainAllowces[i] : BigInteger.valueOf(100);

            if (remainingInventory.compareTo(BigInteger.valueOf(0)) == 0) {
                throw new AnaliaException(ExceptionCode.VOUCHER_SOLD_OUT, Base26.encode(voucherDetailView.getVoucherId()));
            }

            if (remainingInventory.compareTo(shoppingCartResultSet.getQuantity()) < 0) {
                shoppingCartResultSet.setQuantity(BigInteger.valueOf(0));
            }

            if (remainingAllowance.compareTo(shoppingCartResultSet.getQuantity()) < 0) {
                shoppingCartResultSet.setQuantity(BigInteger.valueOf(0));
            }
            BigDecimal[] priceAndCreditsToPay = resolvePrice(creditUsed, voucherDetailView, shoppingCartResultSet.getQuantity());
            BigDecimal price = priceAndCreditsToPay[0]; // Price
            BigDecimal subTotal = price.multiply(new BigDecimal(shoppingCartResultSet.getQuantity()));
            BigDecimal taxToPaid = new BigDecimal(0);
            BigDecimal total = new BigDecimal(0);

            taxToPaid = new BigDecimal(taxToPaid.add((subTotal.multiply(shoppingCartResultSet.getVoucherVendorTaxGroupView().getPercentage()))).toString());
            total = subTotal.add(taxToPaid);

            shoppingCartResultSet.setPrice(price);
            shoppingCartResultSet.setCreditsToPay(priceAndCreditsToPay[1] != null ? priceAndCreditsToPay[1].toBigInteger() : BigInteger.valueOf(0));
            shoppingCartResultSet.setSubTotal(subTotal);
            shoppingCartResultSet.setTaxToPaid(taxToPaid);
            shoppingCartResultSet.setTotal(total);

            grandSubTotal = grandSubTotal.add(new BigDecimal(subTotal.toString()));
            grandTaxToPaid = grandTaxToPaid.add(new BigDecimal(taxToPaid.toString()));
            grandTotal = grandTotal.add(new BigDecimal(total.toString()));

            shoppingCartResultSet.setGrandSubTotal(new BigDecimal(grandSubTotal.toString()));
            shoppingCartResultSet.setGrandTaxToPaid(new BigDecimal(grandTaxToPaid.toString()));
            shoppingCartResultSet.setGrandTotal(new BigDecimal(grandTotal.toString()));
        }

        ShoppingCartResultSet shoppingCartResultSet = voucherDetailViews.get(0);
        shoppingCartResultSet.setGrandSubTotal(new BigDecimal(grandSubTotal.toString()));
        shoppingCartResultSet.setGrandTaxToPaid(new BigDecimal(grandTaxToPaid.toString()));
        shoppingCartResultSet.setGrandTotal(new BigDecimal(grandTotal.toString()));
        return voucherDetailViews;
    }


    BigInteger[] getRemainingAllowanceVoucherByUser(BigInteger[] vourchersId) throws AnaliaException {
        BigInteger[] a = {BigInteger.valueOf(100), BigInteger.valueOf(100)};
        return a;
    }

    /**
     * @param cartResultSets
     * @return
     */
    private BigInteger[] getVoucherIds(List<ShoppingCartResultSet> cartResultSets) {
        BigInteger[] result = new BigInteger[cartResultSets.size()];
        int i = 0;
        for (ShoppingCartResultSet shoppingCartResultSet : cartResultSets) {
            result[i] = shoppingCartResultSet.getVoucherDetailView().getVoucherId();
            i++;
        }
        return result;
    }

    /**
     * @param creditUsed
     * @param voucherDetailView
     * @param amount
     * @return
     * @throws AnaliaException
     */
    //TODO GAS PLEASE CHANGE TO A TUPLE
    public BigDecimal[] resolvePrice(boolean creditUsed, VoucherDetailView voucherDetailView, BigInteger amount) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        BigDecimal[] result = new BigDecimal[2];
        BigDecimal creditsToPay = new BigDecimal(0);
        if (creditUsed) {
            if (voucherDetailView.isCashEnabled()) {
                throw new AnaliaException(ExceptionCode.VOUCHER_IS_CASH_ONLY, Base26.encode(voucherDetailView.getVoucherId()));
            }
            UserWallet wallet = userCoreLocal.getWalletForUserId(user.getId());
            if ((voucherDetailView.getPrice().compareTo(BigDecimal.ZERO) > 0) && (wallet.getCredits().compareTo((voucherDetailView.getUnlockingCredits().multiply(amount)))) < 0) {
                if (wallet.getCredits().compareTo(voucherDetailView.getUnlockingCredits()) < 0) {
                    throw new AnaliaException(ExceptionCode.VOUCHER_CREDITS_REQUIRED, "Voucher Credits Required");
                }
                throw new AnaliaException(ExceptionCode.INSUFICIENT_CREDITS, "User does not have enough credits");
            }
            result[0] = voucherDetailView.getRetailValue();
            creditsToPay = new BigDecimal(amount.multiply(voucherDetailView.getUnlockingCredits()));
        } else {
            if (voucherDetailView.isCreditEnabled()) {
                throw new AnaliaException(ExceptionCode.CREDITS_REQUIRED, "Credits required");
            }
            result[0] = voucherDetailView.getPrice();
        }
        result[1] = creditsToPay;
        return result;
    }

    /**
     *
     */

    public String checkout(BigInteger sourceId, boolean creditUsed) throws AnaliaException {
        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        // StripeGateway stripeGateway = new StripeGateway();
        List<ShoppingCartResultSet> shoppingCartResultSets = getPurchaseSummary(creditUsed);

        String stripeApiKey = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_STRIPE_KEY);
        Source source = sourceId == null ? purchaseCoreLocal.getDefaultSourceByUserId(user.getId()) : purchaseCoreLocal.getSourceByUser(sourceId, user.getId());
        if (source == null) { //MEANS IS A ORDER
            source = new Source();
            source.setUserId(user.getId());
            source.setDisabled(false);
            source.setMaskedCardNumber("ORDER");
            source.setExpiryMonth(12);
            source.setHolderName(user.getPersona().getName());
            source.setNickname(user.getPersona().getEmail());
            source.setExpiryYear(20);
            source.setSourcetypeId(BigInteger.valueOf(1));
            source.setToken(user.getPersona().getEmail());
            purchaseCoreLocal.saveSource(source);
        }
        UserProfile userProfile = userCoreLocal.getValueOnUserProfileByKeyAndUserId(UserCore.STRIPE_CLIENT_ID_PROFILE_KEY, user.getId());

        String paymentReferenceNumber = null;

        Directory directory = fileSystemCoreLocal.saveDirectory(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), user.getId(), user.getId(), vendorId);

        ShoppingCartResultSet shoppingCartResultSet = shoppingCartResultSets.get(0);
        Purchase purchase = new Purchase();
        purchase.setVouchervendorId(shoppingCartResultSet.getVoucherVendorTaxGroupView().getVoucherVendorId());
        purchase.setAmountPaid(shoppingCartResultSet.getGrandTotal());
        purchase.setSourceId(source.getId());
        purchase.setCreatedDatetime(new Date());
        purchase.setCreditsPaid(shoppingCartResultSet.getCreditsToPay());
        purchase.setDirectoryId(directory.getId());
        purchase.setProcessingFeePercent(shoppingCartResultSet.getVoucherDetailView().getProcessingFeePercent());
        purchase.setTotalVouchers(shoppingCartResultSet.getQuantity());
        purchase.setTotalTaxPaid(shoppingCartResultSet.getGrandTaxToPaid());
        purchase.setUserId(user.getId());
        purchase.setCreditsEarned(shoppingCartResultSet.getVoucherDetailView().getPurchaseCreditsReward().multiply(shoppingCartResultSet.getQuantity()));
        String reference = PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_STANDARD);

        purchaseCoreLocal.savePurchase(purchase);

        String invoiceTemplate = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_RECEIPT_TEMPLATE);
        BigDecimal feePaid = shoppingCartResultSet.getGrandTotal().multiply(shoppingCartResultSet.getVoucherDetailView().getProcessingFeePercent()).divide(new BigDecimal(100));

        // Charge Source
        if (shoppingCartResultSet.getGrandTotal().compareTo(BigDecimal.ZERO) > 0) {
            paymentReferenceNumber = reference; //stripeGateway.createStripeCharge(stripeApiKey, stripeCustomerId, shoppingCartResultSet.getGrandTotal(), "CAD"); //TODO FIX Current
        }

        purchase.setPaymentReference(paymentReferenceNumber);
        purchaseCoreLocal.savePurchase(purchase);

        // Reloaded to get totalCount and TotalReemdemed performed by triggers
        purchase = purchaseCoreLocal.getPurchase(purchase.getId());

        //stripeGateway.setStripeDefaultSource(stripeApiKey, source, stripeCustomerId);
        String merchantCode = PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_STANDARD);

        String voucherContent = invoiceTemplate.replace("${voucher.merchant.invoice}", merchantCode);
        String voucherContent0 = voucherContent.replace("${voucher.user.card}", source.getMaskedCardNumber());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String voucherContent1 = voucherContent0.replace("${voucher.merchant.date}", dateFormat.format(new Date()));
        String voucherContent2 = voucherContent1.replace("${voucher.user.name}", user.getPersona().getName());
        String voucherContent3 = voucherContent2.replace("${voucher.user.email}", user.getPersona().getEmail());

        StringBuilder items = new StringBuilder();

        for (ShoppingCartResultSet shoppingCartResultSetDetail : shoppingCartResultSets) {
            String name = "";
            if (shoppingCartResultSetDetail.getQuantity().compareTo(BigInteger.valueOf(0)) == 0) {
                name = String.format("X%d %s", shoppingCartResultSetDetail.getQuantity(), shoppingCartResultSetDetail.getVoucherDetailView().getTitle());
            } else {
                name = String.format("%s", shoppingCartResultSetDetail.getVoucherDetailView().getTitle());

            }
            items.append(ITEM.replace("${voucher.name}", name).replace("${voucher.price}", getLocalizedBigDecimalValue(shoppingCartResultSetDetail.getSubTotal(), Locale.ENGLISH)));
        }
        String voucherContent4 = voucherContent3.replace("${voucher.tax}", String.format("$ %s", getLocalizedBigDecimalValue(purchase.getTotalTaxPaid(), Locale.ENGLISH)));
        String voucherContent5 = voucherContent4.replace("${voucher.subtotal}", String.format("$ %s", getLocalizedBigDecimalValue(purchase.getAmountPaid().subtract(purchase.getTotalTaxPaid()), Locale.ENGLISH)));
        String voucherContent6 = voucherContent5.replace("${voucher.total}", String.format("$ %s", getLocalizedBigDecimalValue(purchase.getAmountPaid(), Locale.ENGLISH)));
        String voucherContent7 = voucherContent6.replace("${voucher.items}", items);

        String name = Base26.encode(purchase.getId()) + "_" + 1 + Constants.HTML_SUFFIX;
        String path = directory.getId() + "/" + name;

        fileSystemCoreLocal.saveFileInFileSystem(purchase.getDirectoryId(), name, new ByteArrayInputStream(voucherContent.getBytes()));
        File file = fileSystemCoreLocal.saveFile(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), FileType.HTML, purchase.getDirectoryId(), 0, name, "Voucher Generated", path, user.getId(), FileType.HTML.toString());

        for (ShoppingCartResultSet cartResultSet : shoppingCartResultSets) {
            String voucherCode = PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_STANDARD);
            purchaseCoreLocal.savePurchaseDetail(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), purchase.getId(), cartResultSet.getVoucherVendorTaxGroupView().getVoucherVendorId(), cartResultSet.getVoucherVendorTaxGroupView().getTaxGroupId(), file.getId(), voucherCode, cartResultSet.getVoucherDetailView().getProcessingFeePercent(), feePaid, user.getId());

        }
        //DELETE ITEMS FROM SHOPPING CART
        userCoreLocal.manageCart(BigInteger.valueOf(0), BigInteger.valueOf(-2), user.getId());
        return voucherContent7;
    }


    public List<PurchaseDetailView> getPurchaseListByUser() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return purchaseCoreLocal.getPurchaseListByUserId(user.getId());
    }


    protected String getLocalizedBigDecimalValue(BigDecimal input, Locale locale) {
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(input);
    }
}
