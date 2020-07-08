package com.weithink.fengkong.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.weithink.fengkong.WeithinkFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactHelper {
    private static final String TAG = ContactHelper.class.getSimpleName();

    public static List<ModelContact> getContactDetails(final Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        ModelContact contact =null;
        List<ModelContact> contacts = new ArrayList<>();

        /**
         * ContactsContract.Data.CONTACT_ID + "=?" + " AND "
         *                         + "(" + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' OR "
         *                         + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "' OR "
         *                         + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE + "' OR "
         *                         + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE + "' OR "
         *                         + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE + "')"
         *
         *                         new String[]{contactId}
         */
        Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data._ID,
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.Data.DISPLAY_NAME,
                        ContactsContract.Data.RAW_CONTACT_ID,
                        ContactsContract.Data.LOOKUP_KEY,
                        ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP,
                        ContactsContract.Data.DATA1,
                        ContactsContract.Data.DATA2,
                        ContactsContract.Data.DATA3,
                        ContactsContract.Data.DATA4,
                        ContactsContract.Data.DATA5,
                        ContactsContract.Data.DATA6,
                        ContactsContract.Data.DATA7,
                        ContactsContract.Data.DATA8,
                        ContactsContract.Data.DATA10},

                 ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' OR "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "' OR "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE + "' OR "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE + "' OR "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE+ "'" ,
                null, null);
        //new String[]{contactId}
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                contact=new ModelContact();
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data._ID));
                contact.setContactId(contactId);
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                contact.setDisplayName(displayName);
                String rowContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
                contact.setRawContactId(rowContactId);
                String lookUpKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.LOOKUP_KEY));
                contact.setLookupKey(lookUpKey);
                contact.setContactLastUpdatedTimestamp(cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP)));

                String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                    setPhoneList(cursor, contact);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                    setEmailList(cursor, contact);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
                    setStructuredName(cursor, contact);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                    setOrganization(cursor, contact);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)) {
                    setAddress(cursor, contact);
                }
                contacts.add(contact);
            }
            WeithinkFactory.getLogger().error("AAA>>>ContactHelper=====获取所有联系人耗时: %s" , (System.currentTimeMillis() - currentTimeMillis) + "，共计：" + contacts.size());
            cursor.close();
        }
        return contacts;
    }

    private static void setPhoneList(Cursor cursor, ModelContact contact) {
        // Phone
        PhoneOrEmail phone = new PhoneOrEmail();
        String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String phoneType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

        String phoneLabel = "";
        if (phoneType != null) {
            if (phoneType.equals(String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM))) {
                phoneLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
            } else {
                phoneLabel = getPhoneType(Integer.valueOf(phoneType));
            }
        }

        phone.setLabel(phoneLabel);
        phone.setValue(phoneNo);
        // if contact no is not match with any contact no in list
        if (!contact.getPhoneList().contains(phone)) {
            contact.getPhoneList().add(phone);
        }
    }

    private static void setEmailList(Cursor cursor, ModelContact contact) {
        // Email
        PhoneOrEmail email = new PhoneOrEmail();
        String emailAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
        String emailType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

        String emailLabel = "";
        if (emailType != null) {
            if (emailType.equals(String.valueOf(ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM))) {
                emailLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.LABEL));
            } else {
                emailLabel = getEmailType(Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))));
            }
        }
        email.setLabel(emailLabel);
        email.setValue(emailAddress);
        contact.getEmailList().add(email);
    }

    private static void setStructuredName(Cursor cursor, ModelContact contact) {
        contact.setFirstName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)));
        contact.setLastName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)));
    }

    private static void setAddress(Cursor cursor, ModelContact contact) {
        // Address
        Address address = new Address();
        address.setStreet(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
        address.setCity(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
        address.setRegion(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
        address.setCountry(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));

        String addressType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
        if (addressType != null && addressType.equals(String.valueOf(ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM))) {
            address.setLabel(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.LABEL)));
        } else {
            if (addressType != null) {
                address.setLabel(getAddressType(Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE)))));
            }
        }
        contact.getAddressList().add(address);
    }

    private static void setOrganization(Cursor cursor, ModelContact contact) {
        contact.setCompany(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)));
        contact.setJobTitle(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE)));
        contact.setDepartment(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT)));
    }

    private static String getPhoneType(int type) {
        switch (type) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
                return "Assistant";
            case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
                return "Callback";
            case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
                return "Car";
            case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
                return "Company Main";
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                return "Fax Home";
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                return "Fax Work";
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                return "Home";
            case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
                return "ISDN";
            case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                return "Main";
            case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
                return "MMS";
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                return "Mobile";
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                return "Other";
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
                return "Other Fax";
            case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
                return "Pager";
            case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
                return "Radio";
            case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
                return "Telex";
            case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
                return "TTY TDD";
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                return "Work";
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                return "Work Mobile";
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
                return "Work Pager";
        }
        return "";
    }

    private static String getEmailType(int type) {
        switch (type) {
            case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                return "Home";
            case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                return "Mobile";
            case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                return "Other";
            case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                return "Work";
        }
        return "";
    }

    private static String getAddressType(int type) {
        switch (type) {
            case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME:
                return "Home";
            case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER:
                return "Other";
            case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK:
                return "Work";
        }
        return "";
    }

    public static byte[] getContactPhoto(Context context, String id) {
        if (!TextUtils.isEmpty(id)) {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id)), true);

            if (inputStream != null) {
                try {
                    byte[] b = new byte[inputStream.available()];
                    inputStream.read(b);
                    return b;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }

    public static class ModelContact {

        private String contactId = "";
        private String rawContactId = "";
        private String firstName = "";
        private String lastName = "";
        private String displayName = "";
        private String lookupKey = "";
        private List<PhoneOrEmail> phoneList = new ArrayList<>();
        private List<PhoneOrEmail> emailList = new ArrayList<>();
        private List<Address> addressList = new ArrayList<>();
        private String company = "";
        private String jobTitle = "";
        private String department = "";
        private String contactLastUpdatedTimestamp;//最后更新时间

        public String getContactLastUpdatedTimestamp() {
            return contactLastUpdatedTimestamp;
        }

        public void setContactLastUpdatedTimestamp(String contactLastUpdatedTimestamp) {
            this.contactLastUpdatedTimestamp = contactLastUpdatedTimestamp;
        }

        public ModelContact() {
        }

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getRawContactId() {
            return rawContactId;
        }

        public void setRawContactId(String rawContactId) {
            this.rawContactId = rawContactId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getLookupKey() {
            return lookupKey;
        }

        public void setLookupKey(String lookupKey) {
            this.lookupKey = lookupKey;
        }

        public List<PhoneOrEmail> getPhoneList() {
            return phoneList;
        }

        public void setPhoneList(List<PhoneOrEmail> phoneList) {
            this.phoneList = phoneList;
        }

        public List<PhoneOrEmail> getEmailList() {
            return emailList;
        }

        public void setEmailList(List<PhoneOrEmail> emailList) {
            this.emailList = emailList;
        }

        public List<Address> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<Address> addressList) {
            this.addressList = addressList;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }

    public static class PhoneOrEmail {

        private String label;
        private String value;

        public PhoneOrEmail(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public PhoneOrEmail() {
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public static class Address {

        private String street = "";
        private String city = "";
        private String region = "";
        private String country = "";
        private String label = "";

        public Address() {
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    // Using ContactsContract.Contacts -> this take more time
    public static void getContactList(Context context) {
        long startTime = System.currentTimeMillis();

        int count = 0;
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        count++;
                        WeithinkFactory.getLogger().error(TAG+"Name/Number :%s " , name + " - " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        WeithinkFactory.getLogger().error(TAG+"getContactList: %s" , (System.currentTimeMillis() - startTime) + " -> Count: " + count);
    }

    // Using ContactsContract.Data -> this take less time
    public static void getContactListNew(Context context) {

        long startTime = System.currentTimeMillis();

        Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data._ID,
                        ContactsContract.Data.DISPLAY_NAME,
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.Data.HAS_PHONE_NUMBER,
                        ContactsContract.Data.DATA1},

                ContactsContract.Data.HAS_PHONE_NUMBER + ">0" + " AND "
                        + "(" + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "')",
                null, null);

        TreeMap<String, ArrayList<String>> contactMap = new TreeMap<>();
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));

                String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                    String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ArrayList<String> arrayList = contactMap.get(displayName);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(phoneNo);
                    contactMap.put(displayName, arrayList);
                }
            }

            int count = 0;
            for (Map.Entry<String, ArrayList<String>> entry : contactMap.entrySet()) {
                if (entry.getValue() != null) {
                    count += entry.getValue().size();
                }
                WeithinkFactory.getLogger().error(TAG+"Name/Number* : %s" , entry.getKey() + " - " + entry.getValue().toString());
            }

            WeithinkFactory.getLogger().error(TAG+"getContactListNew: %s " , (System.currentTimeMillis() - startTime) + " -> Count: " + count);
            cursor.close();
        }
    }

}
