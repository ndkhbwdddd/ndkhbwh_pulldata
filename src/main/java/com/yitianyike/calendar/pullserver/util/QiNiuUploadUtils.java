package com.yitianyike.calendar.pullserver.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;

public class QiNiuUploadUtils {
	public static final String ACCESS_KEY = "Q-hCY0VbL4F6NTX3TgRvE_T3vcpNEo2Gr3S9RA-b";
	public static final String SECRET_KEY = "gcbkJ68bYW35EvIV_JOojLaCb3SLv05FZfe-W6VL";
	static Auth AUTH = Auth.create(ACCESS_KEY, SECRET_KEY);
	UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone0()));
	BucketManager bucketManager = new BucketManager(AUTH, new Configuration(Zone.zone0()));

	public static String upZip(String upUrl) {
		UpMap u = new UpMap();
		String responseString = u.getPublic_base_url();
		File file = new File(upUrl);
		String fileToBase64 = fileToBase64(file);
		do {

			Configuration cfg = new Configuration(Zone.zone0());
			UploadManager uploadManager = new UploadManager(cfg);
			StringMap putPolicy = new StringMap();
			putPolicy.put("saveKey", UUID.randomUUID().toString().replace("-", "") + ".zip");

			long expireSeconds = 3600000;

			String key = null;
			try {

				byte[] uploadBytes = Base64.decode(fileToBase64, Base64.DEFAULT);

				ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
				String upToken = AUTH.uploadToken(u.getPublic_bucket_name(), null, expireSeconds, putPolicy);
				Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
				// 解析上传成功的结果
				String bodyString = response.bodyString();
				responseString += JSONObject.parseObject(bodyString).getString("key");
			} catch (Exception ex) {
				break;
			}

		} while (false);
		return responseString;
	}

	public static String fileToBase64(File file) {
		String base64 = null;
		InputStream in = null;
		try {

			in = new FileInputStream(file);

			byte[] bytes = new byte[in.available()];

			int length = in.read(bytes);

			base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return base64;
	}
}
