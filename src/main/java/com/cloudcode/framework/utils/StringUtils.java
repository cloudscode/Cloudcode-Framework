package com.cloudcode.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


public abstract class StringUtils {

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean compare(String arg0, String arg1) {
		return arg0 == null ? arg1 == null : arg0.equals(arg1);
	}

	public static String formatHTML(String s) {
		if (isEmpty(s)) {
			return "";
		}
		return s.replaceAll("\r\n", "<br/>").replaceAll("\n", "<br/>")
				.replaceAll(" ", "&nbsp;");
	}
	
	public static String clearHTML(String html) {
		if (isEmpty(html)) {
			return "";
		}
		String txt = Jsoup.clean(html, Whitelist.none());
		return txt.replace("&nbsp;", " ").replace( "&lt;", "").replace("&gt;", "");
	}

	/**
	 * {@code content.replaceAll("(?s)<!--.*?-->", "").replaceAll("<[^>]+>", "")}
	 * <br />
	 * <ul>
	 * 	<li>http://stackoverflow.com/questions/1699313/how-to-remove-html-tag-in-java</li>
	 * 	<li>http://www.coderanch.com/t/383351/java/java/Reg-Ex-remove-html-comments</li>
	 * </ul>
	 * @param s
	 * @return
	 * @deprecated use new method {@link #clearHTML(String)}
	 */
	public static String oldClearHTML(String s) {
		if (isEmpty(s)) {
			return "";
		}
		return s.replaceAll("(?s)<!--.*?-->", "").replaceAll("</?[^>]+>?", "").replace("&nbsp;", " ").replace(
				"&lt;", "").replace("&gt;", "");
	}

	public static String left(String s, int size) {
		if (s == null) {
			return null;
		}
		if (size < 0) {
			size = 0;
		}
		return s.length() > size ? s.substring(0, size) : s;
	}

	public static String right(String s, int size) {
		if (s == null) {
			return null;
		}
		if (size < 0) {
			size = 0;
		}
		return s.length() > size ? s.substring(s.length() - size) : s;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean compareIgnoreCase(String arg0, String arg1) {
		return arg0 == null ? arg1 == null : arg0.equalsIgnoreCase(arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean compareIgnoreEmpty(String arg0, String arg1) {
		if (isEmpty(arg0) && isEmpty(arg1)) {
			return true;
		} else {
			return compare(arg0, arg1);
		}
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean compareIgnoreCaseAndEmpty(String arg0, String arg1) {
		if (isEmpty(arg0) && isEmpty(arg1)) {
			return true;
		} else {
			return compareIgnoreCase(arg0, arg1);
		}
	}

	public final static boolean matches(String pattern, String source) {
		if (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1) {
			return Pattern.matches(pattern.replace(".", "\\.").replace("*",
					".*")
					+ "$", source);
		} else {
			return source.equals(pattern);
		}
	}
	
	public static boolean contains(String arg0, String arg1) {
		if (isEmpty(arg0) || isEmpty(arg1)) {
			return false;
		} else {
			return arg0.contains(arg1);
		}
	}
	
	public static boolean contains(String[] arg0, String arg1) {
		if (arg0 == null || arg0.length == 0) {
			return false;
		}
		for (String arg: arg0) {
			if (contains(arg, arg1)) {
				return true;
			}
		}
		return false;
	}

	public final static boolean isEmpty(String arg0) {
		return arg0 == null || arg0.trim().equals("");
	}

	public static String join(String[] array, char separator) {
		return org.apache.commons.lang.StringUtils.join(array, separator);
	}

	public static String join(String[] array, String string) {
		return org.apache.commons.lang.StringUtils.join(array, string);
	}
	
	public static String join(List<String> list, char separator) {
		return org.apache.commons.lang.StringUtils.join(list.iterator(), separator);
	}
	
	public static String join(List<String> list, String string) {
		return org.apache.commons.lang.StringUtils.join(list.iterator(), string);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] parseKeywords(String keywordsString,
			char[] separators) {

		if (StringUtils.isEmpty(keywordsString)) {
			return new String[0];
		}
		for (int i = 0; i < separators.length; i++) {
			keywordsString = keywordsString.replace(separators[i], ',');
		}
		String[] ss = keywordsString.split(",");
		List list = new ArrayList();
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i].trim();
			if (!isEmpty(s) && !list.contains(s)) {
				list.add(s);
			}
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 获得
	 * 
	 * @param str
	 * @return
	 */
	public static int getStringDisplayLength(String str) {
		try {
			return str.getBytes("utf-8").length;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	}

	public static String clearNullChars(String s0) {
		try {
			byte[] data = s0.getBytes("utf-8");
			int i = 0;
			for (; i < data.length; i++) {
				if (data[i] != 0) {
					break;
				}
			}
			String s1 = new String(data, i, data.length - i, "utf-8");
			return s1;
		} catch (Exception e) {
			return s0;
		}
	}

	public static String displayLeft(String str, int size) {
		if (isEmpty(str)) {
			return str;
		}
		int i = 0, j = 0;
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (j >= str.length()) {
				break;
			}
			char c = str.charAt(j++);
			if (c < 0 || c > 0x7f) {
				i = i + 2;
			} else {
				i++;
			}
			if (i > size) {
				break;
			}
			sb.append(c);

		}
		return sb.toString();
	}

	/**
	 * 是否像32位UUID
	 * @param s
	 * @return
	 */
	public static boolean likeUUID(String s) {
		if (s == null) {
			return false;
		} else if (s.length() != 32) {
			return false;
		} else
			try {
				if (s.getBytes("utf-8").length == 32) {
					return true;
				}
			} catch (UnsupportedEncodingException e) {}
			return false;
		
	}
	
	public static String replaceBigNumberChars (String s) {
		if (s == null) {
		    return null;
		}
		StringBuffer sb = new StringBuffer();
		int l = s.length();
		for (int i = 0; i < l; i++) {
		    char c = s.charAt(i);
		    switch (c) {
			    case '０': sb.append('0'); break;
			    case '１': sb.append('1'); break;
			    case '２': sb.append('2'); break;
			    case '３': sb.append('3'); break;
			    case '４': sb.append('4'); break;
			    case '５': sb.append('5'); break;
			    case '６': sb.append('6'); break;
			    case '７': sb.append('7'); break;
			    case '８': sb.append('8'); break;
			    case '９': sb.append('9'); break;
			    default: sb.append(c); break;
		    }
		}
		return sb.toString();
	    }
	
    /**
     * This method based on code from the String taglib at Apache Jakarta:
     * http://cvs.apache.org/viewcvs/jakarta-taglibs/string/src/org/apache/taglibs/string/util/StringW.java?rev=1.16&content-type=text/vnd.viewcvs-markup
     * Copyright (c) 1999 The Apache Software Foundation.
     * Author: timster@mac.com
     *
     * @param str
     * @param lower
     * @param upper
     * @param appendToEnd
     * @return
     */
    public static String truncateNicely(String str, int lower, int upper, String appendToEnd) {
        // strip markup from the string
        String str2 = clearHTML(str);
        boolean diff = (str2.length() < str.length());
        
        // quickly adjust the upper if it is set lower than 'lower'
        if(upper < lower) {
            upper = lower;
        }
        
        // now determine if the string fits within the upper limit
        // if it does, go straight to return, do not pass 'go' and collect $200
        if(str2.length() > upper) {
            // the magic location int
            int loc;
            
            // first we determine where the next space appears after lower
            loc = str2.lastIndexOf(' ', upper);
            
            // now we'll see if the location is greater than the lower limit
            if(loc >= lower) {
                // yes it was, so we'll cut it off here
                str2 = str2.substring(0, loc);
            } else {
                // no it wasnt, so we'll cut it off at the upper limit
                str2 = str2.substring(0, upper);
                loc = upper;
            }
            
            // HTML was removed from original str
            if (diff) {
                
                // location of last space in truncated string
                loc = str2.lastIndexOf(' ', loc);
                
                // get last "word" in truncated string (add 1 to loc to eliminate space
                String str3 = str2.substring(loc+1);
                
                // find this fragment in original str, from 'loc' position
                loc = str.indexOf(str3, loc) + str3.length();
                
                // get truncated string from original str, given new 'loc'
                str2 = str.substring(0, loc);
                
                // get all the HTML from original str after loc
                str3 = extractHTML(str.substring(loc));
                
                // remove any tags which generate visible HTML
                // This call is unecessary, all HTML has already been stripped
                //str3 = removeVisibleHTMLTags(str3);
                
                // append the appendToEnd String and
                // add extracted HTML back onto truncated string
                str = str2 + appendToEnd + str3;
            } else {
                // the string was truncated, so we append the appendToEnd String
                str = str2 + appendToEnd;
            }
            
        }
        
        return str;
    }
    
    public static String truncateText(String str, int lower, int upper, String appendToEnd) {
        // strip markup from the string
        String str2 = clearHTML(str);
        
        // quickly adjust the upper if it is set lower than 'lower'
        if(upper < lower) {
            upper = lower;
        }
        
        // now determine if the string fits within the upper limit
        // if it does, go straight to return, do not pass 'go' and collect $200
        if(str2.length() > upper) {
            // the magic location int
            int loc;
            
            // first we determine where the next space appears after lower
            loc = str2.lastIndexOf(' ', upper);
            
            // now we'll see if the location is greater than the lower limit
            if(loc >= lower) {
                // yes it was, so we'll cut it off here
                str2 = str2.substring(0, loc);
            } else {
                // no it wasnt, so we'll cut it off at the upper limit
                str2 = str2.substring(0, upper);
                loc = upper;
            }
            // the string was truncated, so we append the appendToEnd String
            str = str2 + appendToEnd;
        }
        return str;
    }
    
    /**
     * Extract (keep) JUST the HTML from the String.
     * @param str
     * @return
     */
    public static String extractHTML(String str) {
        if (str == null) return "";
        StringBuffer ret = new StringBuffer(str.length());
        int start = 0;
        int beginTag = str.indexOf("<");
        int endTag = 0;
        if (beginTag == -1)
            return str;
        
        while (beginTag >= start) {
            endTag = str.indexOf(">", beginTag);
            
            // if endTag found, keep tag
            if (endTag > -1) {
                ret.append( str.substring(beginTag, endTag+1) );
                
                // move start forward and find another tag
                start = endTag + 1;
                beginTag = str.indexOf("<", start);
            }
            // if no endTag found, break
            else {
                break;
            }
        }
        return ret.toString();
    }
    
    private static String clearElementStyle(String elementName, String str){
    	String pattern = "<(?i)"+elementName+"[^>]+>";
        String rep = "<"+elementName+">";
        str = str.replaceAll(pattern, rep);
        return str;

    }
    
    public static String clearWordStyle(String str){
    	String[] els = new String[] { "p", "span", "div", "strong" };
    	for(String el : els){
    		str = clearElementStyle(el, str);
    	}
    	return str;
    }
}
