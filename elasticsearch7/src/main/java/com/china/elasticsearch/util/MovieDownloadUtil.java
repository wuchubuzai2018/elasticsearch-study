package com.china.elasticsearch.util;

import com.china.elasticsearch.bean.MovieEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 80s电影信息的基本爬虫的工具类
 * @date 2019-08-18
 */
public class MovieDownloadUtil {

   public static final String ROOT_URL =  "http://www.kk80s.com";

    public static final String BASIC_URL = ROOT_URL + "/movie/list/-----p";

    public static final String CSS_PATH = "#body div#block1.clearfix.noborder ul.me1.clearfix li";

    public static final String DEATAIL_CSS_PATH = "#body div#block1.clearfix div#minfo.clearfix div.info";

    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:61.0) Gecko/20100101 Firefox/61.0";

    public static final int TOTAL_PAGE = 4;

    public static final Pattern p = Pattern.compile("([0-9]+)");

    public static void main(String[] args){
        List<MovieEntity> movieEntities = startGetMovies();
        System.out.println(movieEntities);
    }


    public static List<MovieEntity> startGetMovies(){
        List<MovieEntity> movieList = new ArrayList<MovieEntity>();
        try{
            //遍历每一个页面,每个页面大概25个电影
            for(int i = 0;i < TOTAL_PAGE;i++){
                System.out.println("----------开始爬取第" + (i + 1) + "页数据");
                Document document = Jsoup.connect(BASIC_URL + (i + 1)).userAgent(USER_AGENT).get();
                Elements movies = document.select(CSS_PATH);
                if(movies != null && movies.size() > 0) {
                    movieList.addAll(getMovieList(movies));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return movieList;
    }


    private static List<MovieEntity> getMovieList(Elements movies) throws Exception {
        List<MovieEntity> movieList = new ArrayList<MovieEntity>();
        for(Element element : movies){
            MovieEntity entity = new MovieEntity();

            Element aEle = element.select("h3.h3 a").get(0);
            String movieName = aEle.text();//电影名称
            String href = aEle.attr("href");
            String movieId = href.substring(href.lastIndexOf("/") + 1,href.length());//电影Id

            Document detailDocument = Jsoup.connect(ROOT_URL + href).userAgent(USER_AGENT).get();
            Element infoEle = detailDocument.select(DEATAIL_CSS_PATH).get(0);
            String deMovieName = infoEle.selectFirst("h1.font14w").text();
            int year = 0;
            try{
                String yearStr = deMovieName.substring(deMovieName.lastIndexOf("(") + 1,deMovieName.lastIndexOf(")"));//年份
                if("未知".equals(yearStr)){
                    year = 0;
                }else{
                    year = Integer.parseInt(yearStr);
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(movieName);
            }



            String tip = infoEle.selectFirst(".tip") != null ? infoEle.selectFirst(".tip").text() :"";//版本提示

            Element firstClearfixDiv = infoEle.select("div.clearfix").get(0);
            try{
                //设置基本类型的信息
                setTypeInfo(firstClearfixDiv,entity);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(movieName);
            }

            //获取评分
            String scoreText = firstClearfixDiv.nextElementSibling().child(0).text();
            if(scoreText != null && scoreText.contains("豆瓣评分")){
                scoreText = scoreText.replace("豆瓣评分：","").trim();
            }else{
                if(!scoreText.contains("评论")){
                    System.out.println(movieName);
                    System.out.println(scoreText);
                }
                scoreText = "0";
            }

            entity.setMovieId(movieId);
            entity.setMovieName(movieName);
            entity.setYear(year);
            entity.setTip(tip);
            entity.setScore(Double.parseDouble(scoreText));

            movieList.add(entity);
        }
        return movieList;
    }


    private static void setTypeInfo(Element firstClearfixDiv,MovieEntity entity) {
        Elements spans = firstClearfixDiv.select(">span");
        for(Element span : spans){
            String flag = span.select(".font_888").get(0).text();
            Elements actors = span.select("a");

            if("演员：".equals(flag)){
                String actor = getActorAndType(actors);
                entity.setActors(actor);
            }
            if("类型：".equals(flag)){
                String type = getActorAndType(actors);
                entity.setType(type);
            }
            if("地区：".equals(flag)){
                String area = getActorAndType(actors);
                entity.setArea(area);
            }
            if("语言：".equals(flag)){
                String language = getActorAndType(actors);
                entity.setLanguage(language);
            }
            if("导演：".equals(flag)){
                String director = getActorAndType(actors);
                entity.setDirector(director);
            }
            if("片长：".equals(flag)){
//                String minute = span.text().replace("片长：","")
//                        .replace("分钟","")
//                        .replace(" India: ","")
//                        .replace(" Hong Kong: ","")
//                        .replace(" France: ","")
//                        .replace(" USA: ","")
//                        .replace(" UK: ","")
//                        .replace("min","")
//                        .replace("(台湾)","")
//                        .replace("中国大陆)","")
//                        .replace("(美国/中国大陆)","")
//                        .replace(" Argentina: ","")
//                        .replace(" Japan: ","").trim();
                String minute = getMinute(span.text());

                entity.setMinute(Integer.parseInt(minute));
            }
        }
    }

    private static String getActorAndType(Elements as) {
        List<String> actorList = new ArrayList<String>();
        for (Element actor : as) {
            actorList.add(actor.text());
        }
        String actorsStr = StringUtils.collectionToDelimitedString(actorList, " ");
        return actorsStr;
    }


    /**
     * 提取电影时长
     * @param srcText
     * @return
     */
    public static String getMinute(String srcText){
        Matcher matcher = p.matcher(srcText);
        if(matcher.find()){
            String m = matcher.group();
            return m;
        }
        return "0";
    }

}
