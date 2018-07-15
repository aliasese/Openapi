# ClientSecret生成策略
> OpenAPI认证流程第三方使用

* 1\.拼接字符串：


| 属性名称 | 英文名称 | 示例           |
|---------|---------|---------------|
|租客ID    |tenant_id|    a000001|
|第三方应用系统ID|client_id| 2df6e135-e7dc-4d84-93e9-3862322547d3|
|第三方应用系统IP|ip|199.206.189.78,168.188.66.88|

以上键值对，按照ASCII代码正序排序，生成明文字符串：

```
client_id2df6e135-e7dc-4d84-93e9-3862322547d3ip199.206.189.78,168.188.66.88tenant_ida000001
```
* 2\.
在上述字符串前后拼接该ClientID申请信息记录在PostgreSQL数据库里面唯一标识id:ce167086-c1c9-474a-84f1-b05d941d9b63,得到：新的明文字符串：
```
ce167086-c1c9-474a-84f1-b05d941d9b63client_id2df6e135-e7dc-4d84-93e9-3862322547d3ip199.206.189.78,168.188.66.88tenant_ida000001ce167086-c1c9-474a-84f1-b05d941d9b63
```
* 3\.
使用MD5 HASH CODE加密算法，对上述字符串进行加密:

```java
public static String md5(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes("UTF-8"));
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext.toLowerCase();
    }
```

得到密文，即ClientSecret：
```
9a609c4e9bc7f52ed522e6bd1b5063c8
```
