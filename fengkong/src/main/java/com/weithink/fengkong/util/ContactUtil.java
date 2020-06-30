package com.weithink.fengkong.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

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
                                if (phones != null) {
                                    phones.close();
                                }
                            } catch (Throwable throwable) {
                                if (phones != null) {
                                    try {
                                        phones.close();
                                    } catch (Throwable throwable1) {
                                        throwable.addSuppressed(throwable1);
                                    }
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
                                if (dataCursor != null) {
                                    dataCursor.close();
                                }
                            } catch (Throwable throwable) {
                                if (dataCursor != null) {
                                    try {
                                        dataCursor.close();
                                    } catch (Throwable throwable1) {
                                        throwable.addSuppressed(throwable1);
                                    }
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
                                if (orgCur != null) {
                                    orgCur.close();
                                }
                            } catch (Throwable throwable) {
                                if (orgCur != null) {
                                    try {
                                        orgCur.close();
                                    } catch (Throwable throwable1) {
                                        throwable.addSuppressed(throwable1);
                                    }
                                }
                                throw throwable;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable throwable) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                }
                throw throwable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactArray;
    }
}


