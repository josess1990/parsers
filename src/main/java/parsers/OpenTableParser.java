package parsers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.xml.utils.PrefixResolver;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.dom.XPathNSResolver;

public class OpenTableParser {

	final String OPENTABLE_BASE_URL = "https://www.opentable.com/s/?";
	// final String BOOKING_URL ="https://www.opentable.com/s/?covers=2";
	private PrintWriter writer; 
 
	
	public OpenTableParser() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		try {
			
			this.writer = new PrintWriter("openTableRest-"+dateFormat.format(date), "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRestaurants(String cityId, String lvl) {
		
		/*
		 * area_id: Identifies the city
		 */
		
		/*
		 * Metro_id:identifies the country
		 */

		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(true);
		client.getOptions().setPopupBlockerEnabled(false);
		client.getOptions().setRedirectEnabled(true);
		client.getOptions().setThrowExceptionOnScriptError(false);
	    client.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    client.getOptions().setUseInsecureSSL(true);
		client.getOptions().setCssEnabled(true);  
		client.getOptions().setJavaScriptEnabled(false);  
		client.getCookieManager().setCookiesEnabled(true);
		try {  
		  String searchUrl = "https://www.opentable.com/s/?covers=2&dateTime=2017-11-22%2023%3A00&latitude=51.511233&longitude=-0.136036&metroId=72&term=paris&enableSimpleCuisines=true&freetext%5BLimit%5D=200&pageType=0";
		  HtmlPage page = client.getPage(searchUrl);
		  List<DomElement> listElem = page.getByXPath("//div[@class='result content-section-list-row cf with-times']");
		 System.out.println(listElem.size());
		 for (DomElement elem: listElem) {
						 
			 DomElement nameDom = (DomElement) elem.getByXPath(".//span[@class='rest-row-name-text']").get(0);
			 String name =   nameDom.getTextContent();

			 System.out.println("NAME:" + name);
			 
			 String idStr = elem.getAttribute("data-rid");
			 System.out.println("ID:" + idStr);
			 
			 DomElement containerDom = (DomElement) elem.getByXPath(".//div[@class='rest-row-image']").get(0);

			 DomElement linkDom  = (DomElement) containerDom.getElementsByTagName("a").get(0);
			 String linkUrl =  "https://opentable.com" +linkDom.getAttribute("href");
			 System.out.println(linkUrl);
			 
			 DomElement imageDom  = (DomElement) containerDom.getElementsByTagName("img").get(0);
			 String imgUrl = "http:" + imageDom.getAttribute("data-src");
			 System.out.println("IMAGE:" + imgUrl);
			 
			 System.out.println("---------------------------------");

			writer.println();
				writer.println(idStr + "," +name + "," + linkUrl +"," + imgUrl);
				writer.close();
		 }
		 return "achuuu";
		  
		}catch(Exception e){
		  e.printStackTrace();
			return "nineverr";
		}


	}
}
