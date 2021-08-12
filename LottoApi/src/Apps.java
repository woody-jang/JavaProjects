import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Apps {
	private static Set<String> getLinks(String page) throws IOException {
		String url = "http://www.saramin.co.kr/zf_user/search/recruit?search_area=main&search_done=y&search_optional_item=n&searchType=default_mysearch&recruitSort=relation&recruitPageCount=100&searchword=java&recruitPage="
				+ page;
		Document doc = Jsoup.connect(url).timeout(5000).get();
		Elements links = doc.select("a[href]");
		Set<String> result = new HashSet<>();
		for (Element link : links) {
			String attr = link.attr("href");
			if (attr.startsWith("/zf_user/jobs/relay/view"))
				result.add("http://www.saramin.co.kr" + attr);
		}
		return result;
	}

	private static String getDocument(String url) {
		try {
			Document document = Jsoup.connect(url).get();
			StringBuilder sb = new StringBuilder();
			String description = document.select("meta[name=description]").first().attr("content");
			String[] list = description.split(", (?=[경력:|학력:|연봉:|홈페이지:])");
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = list).length, b = 0; b < i;) {
				String desc = arrayOfString1[b];
				sb.append(desc);
				sb.append("\n");
				b++;
			}
			sb.append(url);
			sb.append("\n");
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws IOException {
		File file = new File("saramin.txt");
		PrintWriter pw = new PrintWriter(file);
		if (args.length == 0)
			args = new String[] { "1" };
		System.out.println("-- Loading Pages --");
		Set<String> links = getLinks(args[0]);
		links.parallelStream().map(url -> getDocument(url)).forEach(pw::write);
//		links.parallelStream().map(url -> getDocument(url)).forEach(System.out::println);
		pw.flush();
		pw.close();
		System.out.println("-- END --");
	}
}
