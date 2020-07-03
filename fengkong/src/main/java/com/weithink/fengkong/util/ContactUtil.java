package com.weithink.fengkong.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.weithink.fengkong.bean.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactUtil {
    private Context context;

    public ContactUtil(Context context) {
        this.context = context;
    }

    private String getStringValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }

    public JSONArray readAllContacts() {
        JSONArray contactArray = new JSONArray();
        ContentResolver resolver = this.context.getContentResolver();
        try {
            Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            try {
                int contactIdIndex = 0;
                int nameIndex = 0;
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        contactIdIndex = cursor.getColumnIndex("_id");
                        nameIndex = cursor.getColumnIndex("display_name");
                    }
                    while (cursor.moveToNext()) {
                        String contactId = cursor.getString(contactIdIndex);
                        String name = cursor.getString(nameIndex);
                        JSONObject jsonObject = new JSONObject();
                        contactArray.put(jsonObject);
                        jsonObject.put("phoneId", contactId);
                        jsonObject.put("displayName", name);
                        jsonObject.put("firstName", "");
                        jsonObject.put("middleName", "");
                        jsonObject.put("lastName", "");
                        jsonObject.put("mobile", "");
                        jsonObject.put("position", "");
                        jsonObject.put("company", "");
                        jsonObject.put("remarks", "");
                        try {
                            Cursor phones = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id=" + contactId, null, null);
                            try {
                                int phoneIndex = 0;
                                if (phones != null &&
                                        phones.getCount() > 0) {
                                    phoneIndex = phones.getColumnIndex("data1");
                                    if (phones.getCount() > 1) {
                                        StringBuilder builder = new StringBuilder();
                                        while (phones.moveToNext()) {
                                            String phoneNumber = phones.getString(phoneIndex);
                                            builder.append(phoneNumber).append("@=@");
                                        }
                                        jsonObject.put("mobile", builder.toString());
                                    } else {
                                        while (phones.moveToNext()) {
                                            String phoneNumber = phones.getString(phoneIndex);
                                            jsonObject.put("mobile", phoneNumber);
                                        }
                                    }
                                }
                                if (phones != null)
                                    phones.close();
                            } catch (Throwable throwable) {
                                if (phones != null)
                                    try {
                                        phones.close();
                                    } catch (Throwable throwable1) {
                                        throwable.addSuppressed(throwable1);
                                    }
                                throw throwable;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Cursor dataCursor = resolver.query(ContactsContract.Data.CONTENT_URI, null, "contact_id = " + contactId, null, null);
                            try {
                                while (dataCursor != null && dataCursor.moveToNext()) {
                                    String mimeType = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                                    if ("vnd.android.cursor.item/name".equals(mimeType)) {
                                        String firstName = dataCursor.getString(dataCursor.getColumnIndex("data3"));
                                        jsonObject.put("firstName", getStringValue(firstName));
                                        String middleName = dataCursor.getString(dataCursor.getColumnIndex("data5"));
                                        jsonObject.put("middleName", getStringValue(middleName));
                                        String lastName = dataCursor.getString(dataCursor.getColumnIndex("data2"));
                                        jsonObject.put("lastName", getStringValue(lastName));
                                        continue;
                                    }
                                    if ("vnd.android.cursor.item/note".equals(mimeType)) {
                                        String remarks = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        jsonObject.put("remarks", remarks);
                                    }
                                }
                                if (dataCursor != null)
                                    dataCursor.close();
                            } catch (Throwable throwable) {
                                if (dataCursor != null)
                                    try {
                                        dataCursor.close();
                                    } catch (Throwable throwable1) {
                                        throwable.addSuppressed(throwable1);
                                    }
                                throw throwable;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String orgWhere = "contact_id = ? AND mimetype = ?";
                        String[] orgWhereParams = {contactId, "vnd.android.cursor.item/organization"};
                        try {
                            Cursor orgCur = this.context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, orgWhere, orgWhereParams, null);
                            try {
                                if (orgCur != null && orgCur.moveToFirst()) {
                                    String company = orgCur.getString(orgCur.getColumnIndex("data1"));
                                    jsonObject.put("company", company);
                                    String jobTitle = orgCur.getString(orgCur.getColumnIndex("data4"));
                                    jsonObject.put("position", jobTitle);
                                }
                                if (orgCur != null)
                                    orgCur.close();
                            } catch (Throwable throwable) {
                                if (orgCur != null)
                                    try {
                                        orgCur.close();
                                    } catch (Throwable throwable1) {
                                        throwable.addSuppressed(throwable1);
                                    }
                                throw throwable;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (cursor != null)
                    cursor.close();
            } catch (Throwable throwable) {
                if (cursor != null)
                    try {
                        cursor.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactArray;
    }


    public List<Contact> getContacts() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList<Contact> contacts = new ArrayList<>();
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        ContentResolver resolver = this.context.getContentResolver();
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        StringBuilder sb = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();

                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                contact.setName(name);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.STATUS_TIMESTAMP
                };
                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = resolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                StringBuilder num = new StringBuilder();
//                String timestmp = phonesCusor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STATUS_TIMESTAMP);
                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        num.append(phonesCusor.getString(0) + ",");
                    } while (phonesCusor.moveToNext());

                } else {
                    String str;
                    if (phonesCusor.getCount() == 0) {
                        str = "";
                    } else {
                        str = phonesCusor.getString(0);
                    }
                    num.append(str);
                }
                if (num.length() > 10) {
                    num.replace(num.lastIndexOf(","), num.length(), "");
                }
                contact.setNumber(num.toString());
                contacts.add(contact);
                if (phonesCusor != null) {
                    phonesCusor.close();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        Log.e("AAA>>>", "getContacts=====获取所有联系人耗时: " + (System.currentTimeMillis() - currentTimeMillis) + "，共计：" + contacts.size());
        return contacts;
    }

    public static List<Contact> getContacts(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = null;
        ContentResolver cr = context.getContentResolver();
        try {
            cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, "sort_key");
            if (cursor != null) {
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int mobileNoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String mobileNo, displayName;
                while (cursor.moveToNext()) {
                    Contact contact = new Contact();
                    if (mobileNoIndex < 0) {
                        mobileNo = "";
                    } else {
                        mobileNo = cursor.getString(mobileNoIndex);
                    }
                    displayName = cursor.getString(displayNameIndex);
                    contact.setName(displayName);
                    contact.setNumber(mobileNo);
                    contacts.add(contact);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                Log.e("AAA>>>>>>getContacts", "获取所有联系人耗时: " + (System.currentTimeMillis() - currentTimeMillis) + "，共计：" + cursor.getCount());
                cursor.close();
            }
        }
        return contacts;
    }
}


