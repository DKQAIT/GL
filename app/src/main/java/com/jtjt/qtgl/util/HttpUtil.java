package com.jtjt.qtgl.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 董凯强 on 2017/11/18.
 */

public class HttpUtil {

    // http://tool.oschina.net/commons/
    private final static Map<String, String> CONTENT_TYPE = new HashMap<>();
    static {
        CONTENT_TYPE.put(".*", "application/octet-stream");
        CONTENT_TYPE.put("0.001", "application/x-001");
        CONTENT_TYPE.put("0.301", "application/x-301");
        CONTENT_TYPE.put("0.323", "text/h323");
        CONTENT_TYPE.put("0.906", "application/x-906");
        CONTENT_TYPE.put("0.907", "drawing/907");
        CONTENT_TYPE.put(".a11", "application/x-a11");
        CONTENT_TYPE.put(".acp", "audio/x-mei-aac");
        CONTENT_TYPE.put(".ai", "application/postscript");
        CONTENT_TYPE.put(".aif", "audio/aiff");
        CONTENT_TYPE.put(".aifc", "audio/aiff");
        CONTENT_TYPE.put(".aiff", "audio/aiff");
        CONTENT_TYPE.put(".anv", "application/x-anv");
        CONTENT_TYPE.put(".apk", "application/vnd.android.package-archive");
        CONTENT_TYPE.put(".asa", "text/asa");
        CONTENT_TYPE.put(".asf", "video/x-ms-asf");
        CONTENT_TYPE.put(".asp", "text/asp");
        CONTENT_TYPE.put(".asx", "video/x-ms-asf");
        CONTENT_TYPE.put(".au", "audio/basic");
        CONTENT_TYPE.put(".avi", "video/avi");
        CONTENT_TYPE.put(".awf", "application/vnd.adobe.workflow");
        CONTENT_TYPE.put(".biz", "text/xml");
        CONTENT_TYPE.put(".bmp", "application/x-bmp");
        CONTENT_TYPE.put(".bot", "application/x-bot");
        CONTENT_TYPE.put(".c4t", "application/x-c4t");
        CONTENT_TYPE.put(".c90", "application/x-c90");
        CONTENT_TYPE.put(".cal", "application/x-cals");
        CONTENT_TYPE.put(".cat", "application/vnd.ms-pki.seccat");
        CONTENT_TYPE.put(".cdf", "application/x-netcdf");
        CONTENT_TYPE.put(".cdr", "application/x-cdr");
        CONTENT_TYPE.put(".cel", "application/x-cel");
        CONTENT_TYPE.put(".cer", "application/x-x509-ca-cert");
        CONTENT_TYPE.put(".cg4", "application/x-g4");
        CONTENT_TYPE.put(".cgm", "application/x-cgm");
        CONTENT_TYPE.put(".cit", "application/x-cit");
        CONTENT_TYPE.put(".class", "java/*");
        CONTENT_TYPE.put(".cml", "text/xml");
        CONTENT_TYPE.put(".cmp", "application/x-cmp");
        CONTENT_TYPE.put(".cmx", "application/x-cmx");
        CONTENT_TYPE.put(".cot", "application/x-cot");
        CONTENT_TYPE.put(".crl", "application/pkix-crl");
        CONTENT_TYPE.put(".crt", "application/x-x509-ca-cert");
        CONTENT_TYPE.put(".csi", "application/x-csi");
        CONTENT_TYPE.put(".css", "text/css");
        CONTENT_TYPE.put(".cut", "application/x-cut");
        CONTENT_TYPE.put(".dbf", "application/x-dbf");
        CONTENT_TYPE.put(".dbm", "application/x-dbm");
        CONTENT_TYPE.put(".dbx", "application/x-dbx");
        CONTENT_TYPE.put(".dcd", "text/xml");
        CONTENT_TYPE.put(".dcx", "application/x-dcx");
        CONTENT_TYPE.put(".der", "application/x-x509-ca-cert");
        CONTENT_TYPE.put(".dgn", "application/x-dgn");
        CONTENT_TYPE.put(".dib", "application/x-dib");
        CONTENT_TYPE.put(".dll", "application/x-msdownload");
        CONTENT_TYPE.put(".doc", "application/msword");
        CONTENT_TYPE.put(".dot", "application/msword");
        CONTENT_TYPE.put(".drw", "application/x-drw");
        CONTENT_TYPE.put(".dtd", "text/xml");
        CONTENT_TYPE.put(".dwf", "Model/vnd.dwf");
        CONTENT_TYPE.put(".dwf", "application/x-dwf");
        CONTENT_TYPE.put(".dwg", "application/x-dwg");
        CONTENT_TYPE.put(".dxb", "application/x-dxb");
        CONTENT_TYPE.put(".dxf", "application/x-dxf");
        CONTENT_TYPE.put(".edn", "application/vnd.adobe.edn");
        CONTENT_TYPE.put(".emf", "application/x-emf");
        CONTENT_TYPE.put(".eml", "message/rfc822");
        CONTENT_TYPE.put(".ent", "text/xml");
        CONTENT_TYPE.put(".epi", "application/x-epi");
        CONTENT_TYPE.put(".eps", "application/x-ps");
        CONTENT_TYPE.put(".eps", "application/postscript");
        CONTENT_TYPE.put(".etd", "application/x-ebx");
        CONTENT_TYPE.put(".exe", "application/x-msdownload");
        CONTENT_TYPE.put(".fax", "image/fax");
        CONTENT_TYPE.put(".fdf", "application/vnd.fdf");
        CONTENT_TYPE.put(".fif", "application/fractals");
        CONTENT_TYPE.put(".fo", "text/xml");
        CONTENT_TYPE.put(".frm", "application/x-frm");
        CONTENT_TYPE.put(".g4", "application/x-g4");
        CONTENT_TYPE.put(".gbr", "application/x-gbr");
        CONTENT_TYPE.put(".gif", "image/gif");
        CONTENT_TYPE.put(".gl2", "application/x-gl2");
        CONTENT_TYPE.put(".gp4", "application/x-gp4");
        CONTENT_TYPE.put(".hgl", "application/x-hgl");
        CONTENT_TYPE.put(".hmr", "application/x-hmr");
        CONTENT_TYPE.put(".hpg", "application/x-hpgl");
        CONTENT_TYPE.put(".hpl", "application/x-hpl");
        CONTENT_TYPE.put(".hqx", "application/mac-binhex40");
        CONTENT_TYPE.put(".hrf", "application/x-hrf");
        CONTENT_TYPE.put(".hta", "application/hta");
        CONTENT_TYPE.put(".htc", "text/x-component");
        CONTENT_TYPE.put(".htm", "text/html");
        CONTENT_TYPE.put(".html", "text/html");
        CONTENT_TYPE.put(".htt", "text/webviewhtml");
        CONTENT_TYPE.put(".htx", "text/html");
        CONTENT_TYPE.put(".icb", "application/x-icb");
        CONTENT_TYPE.put(".ico", "image/x-icon");
        CONTENT_TYPE.put(".ico", "application/x-ico");
        CONTENT_TYPE.put(".iff", "application/x-iff");
        CONTENT_TYPE.put(".ig4", "application/x-g4");
        CONTENT_TYPE.put(".igs", "application/x-igs");
        CONTENT_TYPE.put(".iii", "application/x-iphone");
        CONTENT_TYPE.put(".img", "application/x-img");
        CONTENT_TYPE.put(".ins", "application/x-internet-signup");
        CONTENT_TYPE.put(".ipa", "application/vnd.iphone");
        CONTENT_TYPE.put(".isp", "application/x-internet-signup");
        CONTENT_TYPE.put(".IVF", "video/x-ivf");
        CONTENT_TYPE.put(".java", "java/*");
        CONTENT_TYPE.put(".jfif", "image/jpeg");
        CONTENT_TYPE.put(".jpe", "image/jpeg");
        CONTENT_TYPE.put(".jpe", "application/x-jpe");
        CONTENT_TYPE.put(".jpeg", "image/jpeg");
        CONTENT_TYPE.put(".JPEG", "image/jpeg");
        CONTENT_TYPE.put(".jpg", "application/x-jpg");
        CONTENT_TYPE.put(".jpg", "image/jpeg");
        CONTENT_TYPE.put(".js", "application/x-javascript");
        CONTENT_TYPE.put(".jsp", "text/html");
        CONTENT_TYPE.put(".la1", "audio/x-liquid-file");
        CONTENT_TYPE.put(".lar", "application/x-laplayer-reg");
        CONTENT_TYPE.put(".latex", "application/x-latex");
        CONTENT_TYPE.put(".lavs", "audio/x-liquid-secure");
        CONTENT_TYPE.put(".lbm", "application/x-lbm");
        CONTENT_TYPE.put(".lmsff", "audio/x-la-lms");
        CONTENT_TYPE.put(".ls", "application/x-javascript");
        CONTENT_TYPE.put(".ltr", "application/x-ltr");
        CONTENT_TYPE.put(".m1v", "video/x-mpeg");
        CONTENT_TYPE.put(".m2v", "video/x-mpeg");
        CONTENT_TYPE.put(".m3u", "audio/mpegurl");
        CONTENT_TYPE.put(".m4e", "video/mpeg4");
        CONTENT_TYPE.put(".mac", "application/x-mac");
        CONTENT_TYPE.put(".man", "application/x-troff-man");
        CONTENT_TYPE.put(".math", "text/xml");
        CONTENT_TYPE.put(".mdb", "application/msaccess");
        CONTENT_TYPE.put(".mdb", "application/x-mdb");
        CONTENT_TYPE.put(".mfp", "application/x-shockwave-flash");
        CONTENT_TYPE.put(".mht", "message/rfc822");
        CONTENT_TYPE.put(".mhtml", "message/rfc822");
        CONTENT_TYPE.put(".mi", "application/x-mi");
        CONTENT_TYPE.put(".mid", "audio/mid");
        CONTENT_TYPE.put(".midi", "audio/mid");
        CONTENT_TYPE.put(".mil", "application/x-mil");
        CONTENT_TYPE.put(".mml", "text/xml");
        CONTENT_TYPE.put(".mnd", "audio/x-musicnet-download");
        CONTENT_TYPE.put(".mns", "audio/x-musicnet-stream");
        CONTENT_TYPE.put(".mocha", "application/x-javascript");
        CONTENT_TYPE.put(".movie", "video/x-sgi-movie");
        CONTENT_TYPE.put(".mp1", "audio/mp1");
        CONTENT_TYPE.put(".mp2", "audio/mp2");
        CONTENT_TYPE.put(".mp2v", "video/mpeg");
        CONTENT_TYPE.put(".mp3", "audio/mp3");
        CONTENT_TYPE.put(".mp4", "video/mpeg4");
        CONTENT_TYPE.put(".mpa", "video/x-mpg");
        CONTENT_TYPE.put(".mpd", "application/vnd.ms-project");
        CONTENT_TYPE.put(".mpe", "video/x-mpeg");
        CONTENT_TYPE.put(".mpeg", "video/mpg");
        CONTENT_TYPE.put(".mpg", "video/mpg");
        CONTENT_TYPE.put(".mpga", "audio/rn-mpeg");
        CONTENT_TYPE.put(".mpp", "application/vnd.ms-project");
        CONTENT_TYPE.put(".mps", "video/x-mpeg");
        CONTENT_TYPE.put(".mpt", "application/vnd.ms-project");
        CONTENT_TYPE.put(".mpv", "video/mpg");
        CONTENT_TYPE.put(".mpv2", "video/mpeg");
        CONTENT_TYPE.put(".mpw", "application/vnd.ms-project");
        CONTENT_TYPE.put(".mpx", "application/vnd.ms-project");
        CONTENT_TYPE.put(".mtx", "text/xml");
        CONTENT_TYPE.put(".mxp", "application/x-mmxp");
        CONTENT_TYPE.put(".net", "image/pnetvue");
        CONTENT_TYPE.put(".nrf", "application/x-nrf");
        CONTENT_TYPE.put(".nws", "message/rfc822");
        CONTENT_TYPE.put(".odc", "text/x-ms-odc");
        CONTENT_TYPE.put(".out", "application/x-out");
        CONTENT_TYPE.put(".p10", "application/pkcs10");
        CONTENT_TYPE.put(".p12", "application/x-pkcs12");
        CONTENT_TYPE.put(".p7b", "application/x-pkcs7-certificates");
        CONTENT_TYPE.put(".p7c", "application/pkcs7-mime");
        CONTENT_TYPE.put(".p7m", "application/pkcs7-mime");
        CONTENT_TYPE.put(".p7r", "application/x-pkcs7-certreqresp");
        CONTENT_TYPE.put(".p7s", "application/pkcs7-signature");
        CONTENT_TYPE.put(".pc5", "application/x-pc5");
        CONTENT_TYPE.put(".pci", "application/x-pci");
        CONTENT_TYPE.put(".pcl", "application/x-pcl");
        CONTENT_TYPE.put(".pcx", "application/x-pcx");
        CONTENT_TYPE.put(".pdf", "application/pdf");
        CONTENT_TYPE.put(".pdf", "application/pdf");
        CONTENT_TYPE.put(".pdx", "application/vnd.adobe.pdx");
        CONTENT_TYPE.put(".pfx", "application/x-pkcs12");
        CONTENT_TYPE.put(".pgl", "application/x-pgl");
        CONTENT_TYPE.put(".pic", "application/x-pic");
        CONTENT_TYPE.put(".pko", "application/vnd.ms-pki.pko");
        CONTENT_TYPE.put(".pl", "application/x-perl");
        CONTENT_TYPE.put(".plg", "text/html");
        CONTENT_TYPE.put(".pls", "audio/scpls");
        CONTENT_TYPE.put(".plt", "application/x-plt");
        CONTENT_TYPE.put(".png", "application/x-png");
        CONTENT_TYPE.put(".png", "image/png");
        CONTENT_TYPE.put(".pot", "application/vnd.ms-powerpoint");
        CONTENT_TYPE.put(".ppa", "application/vnd.ms-powerpoint");
        CONTENT_TYPE.put(".ppm", "application/x-ppm");
        CONTENT_TYPE.put(".pps", "application/vnd.ms-powerpoint");
        CONTENT_TYPE.put(".ppt", "application/x-ppt");
        CONTENT_TYPE.put(".ppt", "application/vnd.ms-powerpoint");
        CONTENT_TYPE.put(".pr", "application/x-pr");
        CONTENT_TYPE.put(".prf", "application/pics-rules");
        CONTENT_TYPE.put(".prn", "application/x-prn");
        CONTENT_TYPE.put(".prt", "application/x-prt");
        CONTENT_TYPE.put(".ps", "application/postscript");
        CONTENT_TYPE.put(".ps", "application/x-ps");
        CONTENT_TYPE.put(".ptn", "application/x-ptn");
        CONTENT_TYPE.put(".pwz", "application/vnd.ms-powerpoint");
        CONTENT_TYPE.put(".r3t", "text/vnd.rn-realtext3d");
        CONTENT_TYPE.put(".ra", "audio/vnd.rn-realaudio");
        CONTENT_TYPE.put(".ram", "audio/x-pn-realaudio");
        CONTENT_TYPE.put(".ras", "application/x-ras");
        CONTENT_TYPE.put(".rat", "application/rat-file");
        CONTENT_TYPE.put(".rdf", "text/xml");
        CONTENT_TYPE.put(".rec", "application/vnd.rn-recording");
        CONTENT_TYPE.put(".red", "application/x-red");
        CONTENT_TYPE.put(".rgb", "application/x-rgb");
        CONTENT_TYPE.put(".rjs", "application/vnd.rn-realsystem-rjs");
        CONTENT_TYPE.put(".rjt", "application/vnd.rn-realsystem-rjt");
        CONTENT_TYPE.put(".rlc", "application/x-rlc");
        CONTENT_TYPE.put(".rle", "application/x-rle");
        CONTENT_TYPE.put(".rm", "application/vnd.rn-realmedia");
        CONTENT_TYPE.put(".rmf", "application/vnd.adobe.rmf");
        CONTENT_TYPE.put(".rmi", "audio/mid");
        CONTENT_TYPE.put(".rmj", "application/vnd.rn-realsystem-rmj");
        CONTENT_TYPE.put(".rmm", "audio/x-pn-realaudio");
        CONTENT_TYPE.put(".rmp", "application/vnd.rn-rn_music_package");
        CONTENT_TYPE.put(".rms", "application/vnd.rn-realmedia-secure");
        CONTENT_TYPE.put(".rmvb", "application/vnd.rn-realmedia-vbr");
        CONTENT_TYPE.put(".rmx", "application/vnd.rn-realsystem-rmx");
        CONTENT_TYPE.put(".rnx", "application/vnd.rn-realplayer");
        CONTENT_TYPE.put(".rp", "image/vnd.rn-realpix");
        CONTENT_TYPE.put(".rpm", "audio/x-pn-realaudio-plugin");
        CONTENT_TYPE.put(".rsml", "application/vnd.rn-rsml");
        CONTENT_TYPE.put(".rt", "text/vnd.rn-realtext");
        CONTENT_TYPE.put(".rtf", "application/msword");
        CONTENT_TYPE.put(".rtf", "application/x-rtf");
        CONTENT_TYPE.put(".rv", "video/vnd.rn-realvideo");
        CONTENT_TYPE.put(".sam", "application/x-sam");
        CONTENT_TYPE.put(".sat", "application/x-sat");
        CONTENT_TYPE.put(".sdp", "application/sdp");
        CONTENT_TYPE.put(".sdw", "application/x-sdw");
        CONTENT_TYPE.put(".sis", "application/vnd.symbian.install");
        CONTENT_TYPE.put(".sisx", "application/vnd.symbian.install");
        CONTENT_TYPE.put(".sit", "application/x-stuffit");
        CONTENT_TYPE.put(".slb", "application/x-slb");
        CONTENT_TYPE.put(".sld", "application/x-sld");
        CONTENT_TYPE.put(".slk", "drawing/x-slk");
        CONTENT_TYPE.put(".smi", "application/smil");
        CONTENT_TYPE.put(".smil", "application/smil");
        CONTENT_TYPE.put(".smk", "application/x-smk");
        CONTENT_TYPE.put(".snd", "audio/basic");
        CONTENT_TYPE.put(".sol", "text/plain");
        CONTENT_TYPE.put(".sor", "text/plain");
        CONTENT_TYPE.put(".spc", "application/x-pkcs7-certificates");
        CONTENT_TYPE.put(".spl", "application/futuresplash");
        CONTENT_TYPE.put(".spp", "text/xml");
        CONTENT_TYPE.put(".ssm", "application/streamingmedia");
        CONTENT_TYPE.put(".sst", "application/vnd.ms-pki.certstore");
        CONTENT_TYPE.put(".stl", "application/vnd.ms-pki.stl");
        CONTENT_TYPE.put(".stm", "text/html");
        CONTENT_TYPE.put(".sty", "application/x-sty");
        CONTENT_TYPE.put(".svg", "text/xml");
        CONTENT_TYPE.put(".swf", "application/x-shockwave-flash");
        CONTENT_TYPE.put(".tdf", "application/x-tdf");
        CONTENT_TYPE.put(".tg4", "application/x-tg4");
        CONTENT_TYPE.put(".tga", "application/x-tga");
        CONTENT_TYPE.put(".tif", "image/tiff");
        CONTENT_TYPE.put(".tif", "image/tiff");
        CONTENT_TYPE.put(".tif", "application/x-tif");
        CONTENT_TYPE.put(".tiff", "image/tiff");
        CONTENT_TYPE.put(".tld", "text/xml");
        CONTENT_TYPE.put(".top", "drawing/x-top");
        CONTENT_TYPE.put(".torrent", "application/x-bittorrent");
        CONTENT_TYPE.put(".tsd", "text/xml");
        CONTENT_TYPE.put(".txt", "text/plain");
        CONTENT_TYPE.put(".uin", "application/x-icq");
        CONTENT_TYPE.put(".uls", "text/iuls");
        CONTENT_TYPE.put(".vcf", "text/x-vcard");
        CONTENT_TYPE.put(".vda", "application/x-vda");
        CONTENT_TYPE.put(".vdx", "application/vnd.visio");
        CONTENT_TYPE.put(".vml", "text/xml");
        CONTENT_TYPE.put(".vpg", "application/x-vpeg005");
        CONTENT_TYPE.put(".vsd", "application/x-vsd");
        CONTENT_TYPE.put(".vsd", "application/vnd.visio");
        CONTENT_TYPE.put(".vss", "application/vnd.visio");
        CONTENT_TYPE.put(".vst", "application/vnd.visio");
        CONTENT_TYPE.put(".vst", "application/x-vst");
        CONTENT_TYPE.put(".vsw", "application/vnd.visio");
        CONTENT_TYPE.put(".vsx", "application/vnd.visio");
        CONTENT_TYPE.put(".vtx", "application/vnd.visio");
        CONTENT_TYPE.put(".vxml", "text/xml");
        CONTENT_TYPE.put(".wav", "audio/wav");
        CONTENT_TYPE.put(".wax", "audio/x-ms-wax");
        CONTENT_TYPE.put(".wb1", "application/x-wb1");
        CONTENT_TYPE.put(".wb2", "application/x-wb2");
        CONTENT_TYPE.put(".wb3", "application/x-wb3");
        CONTENT_TYPE.put(".wbmp", "image/vnd.wap.wbmp");
        CONTENT_TYPE.put(".wiz", "application/msword");
        CONTENT_TYPE.put(".wk3", "application/x-wk3");
        CONTENT_TYPE.put(".wk4", "application/x-wk4");
        CONTENT_TYPE.put(".wkq", "application/x-wkq");
        CONTENT_TYPE.put(".wks", "application/x-wks");
        CONTENT_TYPE.put(".wm", "video/x-ms-wm");
        CONTENT_TYPE.put(".wma", "audio/x-ms-wma");
        CONTENT_TYPE.put(".wmd", "application/x-ms-wmd");
        CONTENT_TYPE.put(".wmf", "application/x-wmf");
        CONTENT_TYPE.put(".wml", "text/vnd.wap.wml");
        CONTENT_TYPE.put(".wmv", "video/x-ms-wmv");
        CONTENT_TYPE.put(".wmx", "video/x-ms-wmx");
        CONTENT_TYPE.put(".wmz", "application/x-ms-wmz");
        CONTENT_TYPE.put(".wp6", "application/x-wp6");
        CONTENT_TYPE.put(".wpd", "application/x-wpd");
        CONTENT_TYPE.put(".wpg", "application/x-wpg");
        CONTENT_TYPE.put(".wpl", "application/vnd.ms-wpl");
        CONTENT_TYPE.put(".wq1", "application/x-wq1");
        CONTENT_TYPE.put(".wr1", "application/x-wr1");
        CONTENT_TYPE.put(".wri", "application/x-wri");
        CONTENT_TYPE.put(".wrk", "application/x-wrk");
        CONTENT_TYPE.put(".ws", "application/x-ws");
        CONTENT_TYPE.put(".ws2", "application/x-ws");
        CONTENT_TYPE.put(".wsc", "text/scriptlet");
        CONTENT_TYPE.put(".wsdl", "text/xml");
        CONTENT_TYPE.put(".wvx", "video/x-ms-wvx");
        CONTENT_TYPE.put(".x_b", "application/x-x_b");
        CONTENT_TYPE.put(".x_t", "application/x-x_t");
        CONTENT_TYPE.put(".xap", "application/x-silverlight-app");
        CONTENT_TYPE.put(".xdp", "application/vnd.adobe.xdp");
        CONTENT_TYPE.put(".xdr", "text/xml");
        CONTENT_TYPE.put(".xfd", "application/vnd.adobe.xfd");
        CONTENT_TYPE.put(".xfdf", "application/vnd.adobe.xfdf");
        CONTENT_TYPE.put(".xhtml", "text/html");
        CONTENT_TYPE.put(".xls", "application/x-xls");
        CONTENT_TYPE.put(".xls", "application/vnd.ms-excel");
        CONTENT_TYPE.put(".xlw", "application/x-xlw");
        CONTENT_TYPE.put(".xml", "text/xml");
        CONTENT_TYPE.put(".xpl", "audio/scpls");
        CONTENT_TYPE.put(".xq", "text/xml");
        CONTENT_TYPE.put(".xql", "text/xml");
        CONTENT_TYPE.put(".xquery", "text/xml");
        CONTENT_TYPE.put(".xsd", "text/xml");
        CONTENT_TYPE.put(".xsl", "text/xml");
        CONTENT_TYPE.put(".xslt", "text/xml");
        CONTENT_TYPE.put(".xwd", "application/x-xwd");
    }

    /**
     * 根据文件后缀名解析类型
     * @param file
     * @return
     */
    public static MediaType getContentType(File file) {
        String[] s = file.getName().split("\\.");

        String type = "*/*";
        if (s.length > 1) {
            String t = CONTENT_TYPE.get("." + s[s.length - 1]);
            if (t != null) type = t;
        }

        return MediaType.parse(type);
    }

    /**
     * 生成一个 {@link MultipartBody.Part}
     * @param key
     * @param file
     * @return
     */
    public static MultipartBody.Part parsePart(String key, File file) {
        MediaType mediaType = getContentType(file);
        RequestBody body = RequestBody.create(mediaType, file);
        return MultipartBody.Part.createFormData(key, file.getName(), body);
    }


//    public RequestBody getFileTwo( File file) {
//        RequestBody fileTwo = RequestBody.create(MediaType.parse("image/*"), new File(Environment.getExternalStorageDirectory()
//                + file.separator + "original.png"));
//
//        return fileTwo;
//    }



////    多文件上传
//public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
//    List<MultipartBody.Part> parts = new ArrayList<>(files.size());
//    for (File file : files) {
//        // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
//        parts.add(part);
//    }
//    return parts;
//}



//    多文件上传
    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file[]", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    /**
     * 字符转JSON
     * @param s
     * @return
     */
    public static String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * JSON转字符
     * @param json
     * @return
     */
    public static String json2String(String json) {
        if (json.indexOf('"') == 0) json = json.substring(1);
        if (json.lastIndexOf('"') == json.length() - 1) json = json.substring(0, json.length() - 1);
        return json
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\/", "/")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }

    /**
     * Android_6.0_com.packageName_v1.0.0
     * @param context
     * @return
     */
    public static String getDefaultUserAgent(Context context) {
        String versionName = "Unknown";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format(
                "Android_%s_%s_%s",
                Build.VERSION.RELEASE,
                context.getPackageName(),
                versionName
        );
    }
}