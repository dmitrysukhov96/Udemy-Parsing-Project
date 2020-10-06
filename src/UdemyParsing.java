import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class UdemyPair {
    String title;
    String duration;
    int durationInInt;

    public UdemyPair(String title, String duration, int durationInInt) {
        this.title = title;
        this.duration = duration;
        this.durationInInt = durationInInt;
    }
}

public class UdemyParsing {
    public static void main(String[] args) {
        final String url = "https://www.udemy.com/course/learn-flutter-dart-to-build-ios-android-apps/";
        List<UdemyPair> udemyPairArrayList = new ArrayList<>();
        try {
            final Document document = Jsoup.connect(url).get();
            Elements temp = document.select("div ul li");
            String title = null;
            String duration = "0:00";
            int durationInInt;
            String condition1 = "div.section--row--3PNBT button span, a span div div span.section--item-title--2k1DQ";
            String condition2 = "div span.section--item-content-summary--126oS, a span.section--item-content-summary--126oS";
            System.out.println("\nUnsorted list of free videos:");
            for (Element row : temp) {
                if (!row.select(condition1).text().equals("")) {
                    if (!row.select(condition2).text().equals("")) {
                        System.out.print("\n" + row.select(condition2).text());
                        System.out.print(" " + row.select(condition1).text());
                    }
                }
            }
            System.out.println("\n\nSorted list of free videos:\n");
            for (Element row : temp) {
                if (!row.select(condition1).text().equals("")) {
                    title = row.select(condition1).text();
                    if (!row.select(condition2).text().equals("")) {
                        duration = row.select(condition2).text();
                    }
                    durationInInt = Integer.parseInt(duration.replace(":", ""));
                    UdemyPair udemyPair = new UdemyPair(title, duration, durationInInt);
                    udemyPairArrayList.add(udemyPair);
                }
            }
            udemyPairArrayList.sort(new Comparator<UdemyPair>() {
                @Override
                public int compare(UdemyPair pair1, UdemyPair pair2) {
                    return Integer.compare(pair1.durationInInt, pair2.durationInInt);
                }
            });
            for (UdemyPair udemyPair : udemyPairArrayList) {
                System.out.println(udemyPair.duration + " " + udemyPair.title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}