package com.analia.common.model;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.util.PseudoUniqueCodeUtils;
import com.analia.common.util.PseudoUniqueCodeUtils.PseudoUniqueCode;
import com.analia.common.util.SHA256Util;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;


@NamedQuery(name = "getUserBySessionToken", query = "select u from User u where u.sessionToken = :token and u.disabled = false")
@NamedQuery(name = "getUserByTokenAndUserIdentifier", query = "select u from User u  where (u.loginToken = :token)  and   (u.userIdentifier = :userIdentifier)  and   (u.disabled = false)")
@NamedQuery(name = "getUserByIdentifierAndTypeId", query = "select u from User u where(u.userIdentifier = :userIdentifier) and (u.userTypeId = :userTypeId) and (u.disabled = false)")
@NamedQuery(name = "getUserProfileByKeyAndUserId", query = "select up from UserProfile up where (up.key = :key) and (up.userId = :userId)")
@NamedQuery(name = "getUserProfile", query = "select up from UserProfile up where (up.userId = :userId)")
@NamedQuery(name = "getUserProfileWithPrefix", query = "select up from UserProfile up where (up.userId = :userId) and (up.key like :prefix) order by up.userId")
@NamedQuery(name = "getUserProfileByTags", query = "select up from UserProfile up where (up.value in :tags)")
@NamedQuery(name = "getUserDirectory", query = "select ud from UserDirectory ud where (ud.userId = :userId) and (ud.name = :name)")
@NamedQuery(name = "getUserDirectoryByDirectoryId", query = "select ud from UserDirectory ud where (ud.userId = :userId) and (ud.directoryId = :directoryId)")
@NamedQuery(name = "deleteUserProfile", query = "delete from UserProfile u where u.id =:userProfileId")
@NamedQuery(name = "getUserTrending", query = "select ut from UserTrending ut where (ut.userId = :userId)")
@NamedQuery(name = "getUserTrendingByUserAndVoucherId", query = "select ut from UserTrending ut where (ut.userId =:userId) and (ut.voucherId =:voucherId)")
@NamedQuery(name = "getUserTrendingByUserAndTradeId", query = "select ut from UserTrending ut where (ut.userId = :userId) and (ut.tradeId = :tradeId)")
@NamedQuery(name = "getUserTrendingByUserAndStoryId", query = "select ut from UserTrending ut where (ut.userId = :userId) and (ut.storyId = :storyId)")
@NamedQuery(name = "getUserByConfirmationCode", query = "select u from User u where u.confirmationCode = :confirmationCode and u.disabled = false")
@NamedQuery(name = "getUserLocation", query = "select ul from UserLocation ul where (ul.userId =:userId) and (ul.zoneId=:zoneId)")
@NamedQuery(name = "getDefaultUserLocation", query = "select ul from UserLocation ul where (ul.userId =:userId) and (ul.defaultLocation=true)")
@NamedQuery(name = "getUserLocations", query = "select ul from UserLocation ul where (ul.userId =:userId)")
@NamedQuery(name = "resetUserLocations", query = "update UserLocation ul set ul.defaultLocation = false where (ul.userId =:userId)")
@NamedQuery(name = "getShoppingCart", query = "select sc from ShoppingCart sc where (sc.userId = :userId and sc.voucherId =:voucherId)")
@NamedQuery(name = "getShoppingCarts", query = "select sc.quantity , vd, vvt from VoucherDetailView vd , VoucherVendorTaxGroupView vvt , ShoppingCart sc where (sc.userId = :userId) and (vvt.voucherId = sc.voucherId) and (vd.voucherId = sc.voucherId) and (vvt.equalOrHigherThan <= (vd.price * sc.quantity)) and (vvt.lowerThan > (vd.price * sc.quantity))")
@NamedQuery(name = "deleteShoppingCart", query = "delete from ShoppingCart sc where (sc.id = :id)")
@NamedQuery(name = "deleteAllShoppingCart", query = "delete from ShoppingCart sc where (sc.userId = :userId)")
@NamedQuery(name = "getUserPendingsForApprovalInGroup", query = "select u from User u , Grouping g , UserGrouping ug where (g.createdBy =:userId) and (u.id !=:userId) and (g.name =:groupName) and (ug.groupingId = g.id) and (u.id = ug.userId) order by ug.timestamp DESC")
@NamedQuery(name = "removeUserFromGrouping", query = "delete from UserGrouping u where (u.groupingId = :groupingId) and (u.userId = :userId)")
@NamedQuery(name = "getUserGropingByGroupIdAndUserId", query = "select u from UserGrouping u where (u.groupingId = :groupingId) and (u.userId = :userId)")
@NamedQuery(name = "getGroupingByName", query = "select g from Grouping g where (g.name =:name)")
@NamedQuery(name = "getGroupingByNameWithUserId", query = "select g from Grouping g where (g.name =:name) and (g.createdBy =:userId)")
@NamedQuery(name = "getUserWithIds", query = "select u from User u where (u.id in :ids)")
@NamedQuery(name = "getUserProfileForPossibleMatchingUsers", query = "select up from User u , UserProfile up where (u.id in :ids) and (up.userId = u.id) order by up.userId DESC")
@NamedQuery(name = "getTagsByUserId", query = "select t from Tag t , UserTag ut where (ut.tagId = t.id) and(ut.userId=:userId) order by t.name")
@NamedQuery(name = "getTagByName", query = "select t from Tag t where (t.name = :name) and(t.disabled = false)")
@NamedQuery(name = "getUserTag", query = "select ut from UserTag ut where (ut.userId = :userId) and (ut.tagId = :tagId) and(ut.disabled = false)")
@NamedQuery(name = "getUserForMatching", query = "select DISTINCT u from User u, UserTag ut, Persona p where u.id != :userId and u.persona = p.id and p.gender =:gender and ut.userId = u.id and ut.tagId IN (select ut2.tagId FROM UserTag ut2 WHERE ut2.tagId = ut.tagId and ut.userId = u.id) and u.disabled = false")
@NamedQuery(name = "getUserRole", query = "select ur from UserRole ur where ur.userId=:userId and ur.vendorId = :vendorId")
@NamedQuery(name = "getUserByIdentifierAndTypeIdAndVendorId", query = "select u from User u , UserRole ur where(u.userIdentifier = :userIdentifier) and (u.userTypeId = :userTypeId) and (ur.userId=u.id) and (ur.vendorId =:vendorId)and (u.disabled = false)")


// @NamedQuery( name = "getReviewsByTradeId", query = "select r.id, r.tradeId, r.secuenceNumber, r.date, r.scoreGiven, r.text, u.id as userId, p.name from Review r, User u, Persona p where (r.tradeId =:tradeId) and (u.persona=p) and (r.createdBy = u.id) order by r.createDatetime asc")
// @NamedQuery( name = "getReviewsByVoucherId", query = "select r from Review r where (r.voucherId=:voucherId) order by r.createDatetime asc")
// @NamedQuery( name = "getReviewByTradeIdAndUserId", query = "select r from Review r where (r.tradeId=:tradeId) and (r.createdBy =:userId) order by r.createDatetime asc")
// @NamedQuery( name = "getReviewByVoucherIdAndUserId", query = "select r from Review r where (r.voucherId=:voucherId) and (r.createdBy=:userId) order by r.createDatetime asc")
// @NamedQuery( name = "getCategoriesByCategoryTypeId", query = "select c from Category c where (c.categoryTypeId=:categoryTypeId) and (c.disabled = false)")
// @NamedQuery( name = "categorySearchParam", query = "select c from Category c where (c.name=:categoryTypeId) and (c.disabled = false)")


@Entity
@Table(name = "USER")
public class User extends AnaliaEntity {
    public static final int USER_TYPE_EMAIL_ID = 1;
    public static final int USER_TYPE_FACEBOOK_ID = 2;
    public static final int USER_TYPE_GMAIL_ID = 3;
    public static final int USER_TYPE_APPLE_ID = 4;


    private static final long serialVersionUID = 1L;
    private static final String PASS_SALT = "*&*^!(*!^@&*%#&!^#*!^&*%@&";
    private static final String SESSION_SALT = "#!#!#RLR#@#@";
    private static final int PASSWORD_MINIMUM_LENGTH = 7;
    private static String LOGOUT_SESSION_TOKEN_PREFIX = "__loggedOut__";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "usertype_id")
    private BigInteger userTypeId;
    @Column(name = "user_identifier")
    private String userIdentifier;
    @Column(name = "login_token")
    private String loginToken;
    @Column(name = "session_token")
    private String sessionToken;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    @Column(name = "system_user")
    private boolean systemUser;
    @Column(name = "unconfirmed_email")
    private boolean unconfirmedEmail;
    @Column(name = "confirmation_code")
    private String confirmationCode;
    @Column(name = "temp_password")
    private boolean tempPassword;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "disabled")
    private boolean disabled;
    @Column(name = "timestamp")
    private Date timestamp;
    @Column(name = "persona_id")
    private BigInteger persona;

    @Transient
    private Persona personaObj;


    /**
     * @param id
     * @param password
     * @return
     */
    public static String generateLoginToken(BigInteger id, String password) {
        return SHA256Util.SHA256(id + PASS_SALT + password);
    }


    public BigInteger getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(BigInteger userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public boolean isSystemUser() {
        return systemUser;
    }

    public void setSystemUser(boolean systemUser) {
        this.systemUser = systemUser;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public boolean isUnconfirmedEmail() {
        return unconfirmedEmail;
    }

    public void setUnconfirmedEmail(boolean unconfirmedEmail) {
        this.unconfirmedEmail = unconfirmedEmail;
    }


    /**
     * @param password
     * @throws AnaliaException
     */
    public void setLoginToken(String password) throws AnaliaException {
        this.loginToken = User.generateLoginToken(id, password);
    }

    /**
     * @param password
     * @return
     * @throws AnaliaException
     */
    public String generateSessionToken(String password) throws AnaliaException {
        if (!this.isPasswordValid(password)) {
            throw new AnaliaException(ExceptionCode.AUTHENTICATION_FAILED, "Wrong username and/or password");
        }
        this.sessionToken = SHA256Util.SHA256(this.userTypeId + password + SESSION_SALT + new Date()) + PseudoUniqueCodeUtils.generatePseudoUniqueCode(PseudoUniqueCode.PSEUDO_CODE_LARGE);
        return this.sessionToken;
    }

    /**
     * @param password
     * @return
     * @throws AnaliaException
     */
    public boolean isPasswordValid(String password) throws AnaliaException {
        if (this.userTypeId != null && this.userTypeId.intValue() == USER_TYPE_EMAIL_ID) {
            return (this.loginToken != null && password != null && User.generateLoginToken(id, password).equals(this.loginToken));
        } else if (this.userTypeId != null && this.userTypeId.intValue() == USER_TYPE_FACEBOOK_ID) {
            return this.loginToken != null && this.loginToken.equals(password);
        } else {
            throw new AnaliaException(ExceptionCode.USER_WRONG_TYPE, "Invalid user type specified");
        }
    }

    public void logout() {
        this.sessionToken = LOGOUT_SESSION_TOKEN_PREFIX + this.sessionToken;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public boolean isTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(boolean tempPassword) {
        this.tempPassword = tempPassword;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Persona getPersona() {
        return personaObj;
    }

    public void setPersona(Persona persona) {
        this.personaObj = persona;
    }

    public BigInteger getPersonaId() {
        return persona;
    }

    public void setPersonaId(BigInteger personaId) {
        this.persona = personaId;
    }

    public static void main(String[] args) {
        System.out.println(generateLoginToken(BigInteger.ONE,"toor"));
    }
}
