package com.wepay.model;

import java.io.IOException;

import org.json.*;

import com.wepay.net.WePayResource;
import com.wepay.exception.WePayException;
import com.wepay.model.data.*;

public class Account extends WePayResource {
		
	protected Long accountId;
	protected String state;
	protected Long createTime;
	protected AccountBalancesObjectData[] balances;
	protected AccountStatusesObjectData[] statuses;
	protected String[] actionReasons;
	protected String[] disabledReasons;
	protected FeeScheduleData[] feeSchedule;
	protected AccountData accountData;
	protected Long [] rbitIds;
	protected String[] supportedCardTypes;
	protected Long disablementTime;
	protected Long ownerUserId;
	 
	public Account(Long accountId) {
		this.accountId = accountId;
	}

	public static Account fetch(Long accountId, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		return Account.fetch(accountId, headerData);
	}

	public static Account fetch(Long accountId, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		params.put("account_id", accountId);
		String response = request("/account", params, headerData);
		Account a = gson.fromJson(response, Account.class);
		AccountData ad = gson.fromJson(response, AccountData.class);
		a.accountData = ad;
		return a;
	}

	public static Account[] find(AccountFindData findData, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		return Account.find(findData, headerData);
	}

	public static Account[] find(AccountFindData findData, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		if (findData.name != null) params.put("name", findData.name);
		if (findData.referenceId != null) params.put("reference_id", findData.referenceId);
		if (findData.sortOrder != null) params.put("sort_order", findData.sortOrder);
		if (findData.start != null) params.put("start", findData.start);
		if (findData.limit != null) params.put("limit", findData.limit);
		JSONArray results = new JSONArray(request("/account/find", params, headerData));
		Account[] found = new Account[results.length()];
		for (int i = 0; i < found.length; i++) {
			Account a = gson.fromJson(results.get(i).toString(), Account.class);
			AccountData ad = gson.fromJson(results.get(i).toString(), AccountData.class);
			a.accountData = ad;
			found[i] = a;
		}
		return found;
	}

	public static Account create(AccountData data, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		return Account.create(data, headerData);
	}

	public static Account create(AccountData data, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		params.put("name", data.name);
		params.put("description", data.description);
		if (data.referenceId != null) params.put("reference_id", data.referenceId);
		if (data.type != null) params.put("type", data.type);
		if (data.imageUri != null) params.put("image_uri", data.imageUri);
		if (data.gaqDomains != null) params.put("gaq_domains", data.gaqDomains);
		if (data.themeObject != null) params.put("theme_object", ThemeObjectData.buildThemeObject(data.themeObject));
		if (data.mcc != null) params.put("mcc", data.mcc);
		if (data.callbackUri != null) params.put("callback_uri", data.callbackUri);
		if (data.country != null) params.put("country", data.country);
		if (data.currencies != null) params.put("currencies", data.currencies);
		if (data.countryOptions != null) params.put("country_options", CountryOptionsData.buildCountryOptions(data.countryOptions));
		if (data.feeScheduleSlot != null) params.put("fee_schedule_slot", data.feeScheduleSlot);
		if (data.supportContactNumber != null) params.put("support_contact_number", InternationalPhoneNumberData.buildInternationalPhoneNumber(data.supportContactNumber));

		if (data.rbits != null) {
			String rbitsJson = gson.toJson(data.rbits);
			params.put("rbits", new JSONArray(rbitsJson));
		}
		String response = request("/account/create", params, headerData);
		Account a = gson.fromJson(response, Account.class);
		a.accountData = data;
		return a;
	}

	public void modify(AccountData data, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		this.modify(data, headerData);
	}

	public void modify(AccountData data, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		params.put("account_id", this.accountId);
		if (data.name != null) params.put("name", data.name);
		if (data.description != null) params.put("description", data.description);
		if (data.referenceId != null) params.put("reference_id", data.referenceId);
		if (data.imageUri != null) params.put("image_uri", data.imageUri);
		if (data.gaqDomains != null) params.put("gaq_domains", data.gaqDomains);		
		if (data.themeObject != null) params.put("theme_object", ThemeObjectData.buildThemeObject(data.themeObject));
		if (data.callbackUri != null) params.put("callback_uri", data.callbackUri);
		if (data.countryOptions != null) params.put("country_options", CountryOptionsData.buildCountryOptions(data.countryOptions));
		if (data.feeScheduleSlot != null) params.put("fee_schedule_slot", data.feeScheduleSlot);
		if (data.supportContactNumber != null) params.put("support_contact_number", InternationalPhoneNumberData.buildInternationalPhoneNumber(data.supportContactNumber));

		if (data.rbits != null) {
			String rbitsJson = gson.toJson(data.rbits);
			params.put("rbits", new JSONArray(rbitsJson));
		}

		String response = request("/account/modify", params, headerData);
		Account a = gson.fromJson(response, Account.class);
		AccountData ad = gson.fromJson(response, AccountData.class);
		ad.callbackUri = data.callbackUri;
		this.accountId = a.accountId;
		this.state = a.state;
		this.createTime = a.createTime;
		this.balances = a.balances;
		this.statuses = a.statuses;
		this.actionReasons = a.actionReasons;
		this.accountData = ad;
	}

	public void delete(String reason, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		this.delete(reason, headerData);
	}

	public void delete(String reason, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		params.put("account_id", this.accountId);
		if (reason != null) params.put("reason", reason);
		request("/account/delete", params, headerData);
	}

	public String getUpdateUri(AccountUpdateUriData data, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		return this.getUpdateUri(data, headerData);
	}

	public String getUpdateUri(AccountUpdateUriData data, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		params.put("account_id", this.accountId);
		if (data.mode != null) {
			params.put("mode", data.mode);
		}

		if (data.redirectUri != null) {
			params.put("redirect_uri", data.redirectUri);
		}

		if (data.prefillInfo != null) {
			params.put("prefill_info", KYCPrefillInfoData.buildPrefillInfo(data.prefillInfo));
		}

		JSONObject object = new JSONObject(request("/account/get_update_uri", params, headerData));
		return object.getString("uri");
	}

	public AccountReserveData getReserveDetails(String currency, String accessToken) throws JSONException, IOException, WePayException {
		HeaderData headerData = new HeaderData();
		headerData.accessToken = accessToken;
		return this.getReserveDetails(currency, headerData);
	}

	public AccountReserveData getReserveDetails(String currency, HeaderData headerData) throws JSONException, IOException, WePayException {
		JSONObject params = new JSONObject();
		params.put("account_id", this.accountId);
		if (currency != null) params.put("currency", currency);
		String response = request("/account/get_reserve_details", params, headerData);
		AccountReserveData ar = gson.fromJson(response, AccountReserveData.class);
		return ar;
	}
	
	public Long getAccountId() {
		return accountId;
	}
	
	public String getName() {
		return accountData.name;
	}
	
	public String getState() {
		return state;
	}
    
	public String getCountry() {
		return accountData.country;
	}

    public String[] getCurrencies() {
		return accountData.currencies;
	}

	public FeeScheduleData[] getFeeSchedule() {
		return feeSchedule;
	}
    
	public String getDescription() {
		return accountData.description;
	}
	
	public String getReferenceId() {
		return accountData.referenceId;
	}
	
	public String[] getGaqDomains() {
		return accountData.gaqDomains;
	}
	
	public ThemeObjectData getThemeObject() {
		return accountData.themeObject;
	}

	public String getType() {
		return accountData.type;
	}
	
	public String getImageUri() {
		return accountData.imageUri;
	}

	public Long getCreateTime() {
		return createTime;
	}
	
	public AccountBalancesObjectData[] getBalances() {
		return balances;
	}
	
	public AccountStatusesObjectData[] getStatuses() {
		return statuses;
	}
	
	public String[] getActionReasons() {
		return actionReasons;
	}

	public String[] getDisabledReasons() {
		return disabledReasons;
	}
	
	public String getMcc() {
		return accountData.mcc;
	}
	
	public String getCallbackUri() {
		return accountData.callbackUri;
	}
	
	public Long[] getRbitIds() {		
		return rbitIds;		
	}

	public String[] getSupportedCardTypes() {		
		return supportedCardTypes;		
	}

	public InternationalPhoneNumberData getSupportContactNumber() {
		return accountData.supportContactNumber;
	}

	public Long getDisablementTime() {
		return disablementTime;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}
}
